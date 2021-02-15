package com.komiut.conductor;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.komiut.conductor.model.CarLocation;
import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.retrofit.APIService;
import com.komiut.conductor.retrofit.ApiUtils;
import com.komiut.conductor.retrofit.COMMONRETROFIT;
import com.komiut.conductor.retrofit.CarLocationResponse;
import com.komiut.conductor.retrofit.CashTransactionResponse;
import com.komiut.conductor.retrofit.LogoutResponse;
import com.komiut.conductor.retrofit.MpesaServerData;
import com.komiut.conductor.retrofit.MpesaServerResponse;
import com.komiut.conductor.retrofit.TokenInterceptor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import io.andref.rx.network.RxNetwork;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    MainActivityViewModel viewModel;
    private String acccess;
    APIService apiService;
    ArrayList<String> receiptArrayList = new ArrayList<>();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    LocationRequest locationRequest;

    FusedLocationProviderClient fusedLocationClient;
    LocationCallback locationCallback;
    List<MpesaServerData> mpesaServerDataList = new ArrayList<>();

    public double latitude;
    public double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        apiService = ApiUtils.getAPIService();
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        createLocationRequest();


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;

                db.getReference("Locations").child(viewModel.userMutableLiveData.getValue().getUser_plate()).setValue(locationResult.getLastLocation()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

//                        Toast.makeText(MainActivity.this, "succccccess", Toast.LENGTH_SHORT).show();
                        return;
//                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SendLocationData();
                handler.postDelayed(this, 1000 * 5 * 60);
            }
        }, 1000 * 5 * 60);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sharedPreferences = getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
        String user_email = sharedPreferences.getString(getString(R.string.user_email), "null");
        String user_name = sharedPreferences.getString(getString(R.string.user_name), "null");
        String user_phone = sharedPreferences.getString(getString(R.string.user_phone), "null");
        String user_plate = sharedPreferences.getString(getString(R.string.user_plate), "null");

        OfflineUser offlineUser = new OfflineUser();
        offlineUser.setUser_email(user_email);
        offlineUser.setUser_name(user_name);
        offlineUser.setUser_phone(user_phone);
        offlineUser.setUser_plate(user_plate);

        viewModel.setUserMutableLiveData(offlineUser);

        SendMpesaData();
        editor = sharedPreferences.edit();
        acccess = sharedPreferences.getString(getString(R.string.access_token), "null");

        if (acccess.equals("null")) {
            finish();
            return;
        }
        viewModel.setAccesstoken(acccess);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard,
                R.id.navigation_receipts,
                R.id.navigation_queue,
                R.id.navigation_passenger
        )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        RxNetwork.stateChanges(this, connectivityManager)
                .subscribe(state -> {
                    if (state.equals(NetworkInfo.State.CONNECTED)) {
                        SyncDataNow();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    protected void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void SendLocationData() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {

                TokenInterceptor tokenInterceptor = new TokenInterceptor(viewModel.getAccesstoken());

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(tokenInterceptor)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl(COMMONRETROFIT.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CarLocation carLocation = new CarLocation();
                carLocation.setLongitude(location.getLongitude());
                carLocation.setLatitude(location.getLatitude());
                carLocation.setDevice_serial("serial");
                carLocation.setRegno(viewModel.userMutableLiveData.getValue().getUser_plate());
                carLocation.setDevice_imei("imei");
                carLocation.setAndroid_id("android id");

                latitude = location.getLatitude();
                longitude = location.getLongitude();



                retrofit.create(APIService.class).SaveLocation(acccess, carLocation).enqueue(new Callback<CarLocationResponse>() {
                    @Override
                    public void onResponse(Call<CarLocationResponse> call, Response<CarLocationResponse> response) {
                        if (response.isSuccessful()) {
//                        Toast.makeText(MainActivity.this, response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                            return;
                        }
//                        Toast.makeText(MainActivity.this, "location Not sent", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CarLocationResponse> call, Throwable t) {
//                    Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    public void getLocationData() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {


                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();




                CarLocation carLocation = new CarLocation();
                carLocation.setLongitude(location.getLongitude());
                carLocation.setLatitude(location.getLatitude());
                carLocation.setDevice_serial("serial");
                carLocation.setRegno(viewModel.userMutableLiveData.getValue().getUser_plate());
                carLocation.setDevice_imei("imei");
                carLocation.setAndroid_id("android id");


            }
        });


    }

    private void SyncDataNow() {

        SendCashData();
//        SendMpesaData();
    }

    private void SendMpesaData() {

        TokenInterceptor tokenInterceptor = new TokenInterceptor(viewModel.getAccesstoken());


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(COMMONRETROFIT.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


//                cash +="\n" +cashTrans.getPassname();


        ContentResolver cr = getContentResolver();


        Cursor smsCursor = cr.query(Uri.parse("content://sms/inbox"), null, "address='MPESA'", null, null);
        int indexBody = smsCursor.getColumnIndex("body");


        if (indexBody < 0 || !smsCursor.moveToFirst()) return;


        String dates = "";

        do {
            String str = smsCursor.getString(indexBody);

            if (str.contains("received")) {
                receiptArrayList.add(str);

                String[] words = str.split("\\s+");

                /*MpesaMessage mpesaMessage = new MpesaMessage();
                mpesaMessage.setPhonenumber(words[8]);
                mpesaMessage.setTransactionId(words[0]);
                mpesaMessage.setFirstname(words[9]);
                mpesaMessage.setSecondname(words[10]);
                mpesaMessage.setAmount(words[5].substring(2));
                mpesaMessage.setTime(words[4] + " " + words[5].substring(0, 2));
                mpesaMessage.setDate(words[2]);*/
                MpesaServerData mpesaServerData = new MpesaServerData(words[9], words[10], words[0], viewModel.userMutableLiveData.getValue().getUser_plate(), words[5].substring(2).replaceAll("[^0-9]", ""), words[8], words[2] + " at " + words[4] + " " + words[5].substring(0, 2));

                mpesaServerDataList.add(mpesaServerData);


            }

        }
        while (smsCursor.moveToNext());

        for (MpesaServerData mpesa :
                mpesaServerDataList) {

            retrofit.create(APIService.class).SendMpesaMessages(viewModel.getAccesstoken(), mpesa).enqueue(new Callback<MpesaServerResponse>() {
                @Override
                public void onResponse(Call<MpesaServerResponse> call, Response<MpesaServerResponse> response) {
                    if (response.isSuccessful()) {

//                            Toast.makeText(MainActivity.this, "success brouh", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    Snackbar.make(getCurrentFocus(), "nothing here not available", Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<MpesaServerResponse> call, Throwable t) {
//                    Snackbar.make(getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });

        }

    }

    private void SendCashData() {
        viewModel.SyncData().observe(MainActivity.this, s ->
        {
            TokenInterceptor tokenInterceptor = new TokenInterceptor(viewModel.getAccesstoken());

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(tokenInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(COMMONRETROFIT.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            for (CashTransaction cashTrans :
                    s) {


                retrofit.create(APIService.class).PayCash(viewModel.getAccesstoken(), cashTrans).enqueue(new Callback<CashTransactionResponse>() {
                    @Override
                    public void onResponse(Call<CashTransactionResponse> call, Response<CashTransactionResponse> response) {
                        if (response.isSuccessful()) {

                            cashTrans.setStatus(true);
                            viewModel.updateCashTransaction(cashTrans);
                            return;
                        }

                    }

                    @Override
                    public void onFailure(Call<CashTransactionResponse> call, Throwable t) {

                    }
                });

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Confirm logout")
                    .setMessage("Would you like to logout out of Komiut")
                    .setNegativeButton("Cancel", (dialogInterface, i) -> Snackbar.make(getCurrentFocus(), "Logout canceled", Snackbar.LENGTH_LONG).show())
                    .setPositiveButton("Okay", (dialogInterface, i) -> {


                        TokenInterceptor tokenInterceptor = new TokenInterceptor(viewModel.getAccesstoken());

                        OkHttpClient client = new OkHttpClient.Builder()
                                .addInterceptor(tokenInterceptor)
                                .build();

                        Retrofit retrofit = new Retrofit.Builder()
                                .client(client)
                                .baseUrl(COMMONRETROFIT.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        retrofit.create(APIService.class).logout(viewModel.getAccesstoken()).enqueue(new Callback<LogoutResponse>() {
                            @Override
                            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                                if (response.isSuccessful()) {

                                    editor.putString(getString(R.string.authentication_status), String.valueOf(CONSTANTS.LOGGEDOUT));
                                    editor.apply();
//                                    Snackbar.make(getCurrentFocus(),"LOGOUT data available",Snackbar.LENGTH_LONG).show();

                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    finishAffinity();

                                    return;
                                }
                                Snackbar.make(getCurrentFocus(), response.errorBody().toString(), Snackbar.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                                Snackbar.make(getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        });


//                        editor.putString(getString(R.string.access_token),"");
//                        editor.putString(getString(R.string.authentication_status), String.valueOf(CONSTANTS.LOGGEDOUT));
//                        editor.apply();
//

//                        Snackbar.make(getCurrentFocus(),acccess,Snackbar.LENGTH_LONG).show();
                    }).show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }





}