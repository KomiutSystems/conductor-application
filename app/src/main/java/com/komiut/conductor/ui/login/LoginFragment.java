package com.komiut.conductor.ui.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.komiut.conductor.CONSTANTS;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentLoginBinding;
import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.model.Receipt;
import com.komiut.conductor.model.SubRoute;
import com.komiut.conductor.retrofit.APIService;
import com.komiut.conductor.retrofit.ApiUtils;
import com.komiut.conductor.retrofit.COMMONRETROFIT;
import com.komiut.conductor.retrofit.LoginRequest;
import com.komiut.conductor.retrofit.LoginResponse;
import com.komiut.conductor.retrofit.SaccoInfo;
import com.komiut.conductor.retrofit.SubRoutesResponse;
import com.komiut.conductor.retrofit.TokenInterceptor;
import com.komiut.conductor.retrofit.UserIn;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.zcs.sdk.Beeper;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Led;
import com.zcs.sdk.LedLightModeEnum;
import com.zcs.sdk.Printer;
import com.zcs.sdk.SdkResult;
import com.zcs.sdk.Sys;
import com.zcs.sdk.bluetooth.emv.BluetoothHandler;
import com.zcs.sdk.card.CardReaderManager;
import com.zcs.sdk.card.ICCard;
import com.zcs.sdk.card.IDCard;
import com.zcs.sdk.card.MagCard;
import com.zcs.sdk.card.RfCard;
import com.zcs.sdk.card.SLE4428Card;
import com.zcs.sdk.card.SLE4442Card;
import com.zcs.sdk.emv.EmvHandler;
import com.zcs.sdk.exteranl.ExternalCardManager;
import com.zcs.sdk.fingerprint.FingerprintListener;
import com.zcs.sdk.fingerprint.FingerprintManager;
import com.zcs.sdk.pin.pinpad.PinPadManager;
import com.zcs.sdk.print.PrnStrFormat;
import com.zcs.sdk.print.PrnTextFont;
import com.zcs.sdk.print.PrnTextStyle;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.andref.rx.network.RxNetwork;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class LoginFragment extends Fragment implements FingerprintListener {

    FragmentLoginBinding binding;
    NavController navController;
    DriverManager mDriverManager = DriverManager.getInstance();
    Sys mSys = mDriverManager.getBaseSysDevice();
    FingerprintManager mFingerprintManager = mDriverManager.getFingerprintManager();

    EmvHandler mEmvHandler = EmvHandler.getInstance();
    PinPadManager mPadManager = mDriverManager.getPadManager();
    Printer mPrinter = mDriverManager.getPrinter();
    Beeper mBeeper = mDriverManager.getBeeper();
    Led mLed = mDriverManager.getLedDriver();
    BluetoothHandler mBluetoothHandler = mDriverManager.getBluetoothHandler();
    ExternalCardManager mExternalCardManager =
            mDriverManager.getExternalCardManager();

    private APIService mAPIService;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ConnectivityManager connectivityManager;
    private boolean connected = false;
    private static final String TAG = "LoginFragment";

    LoginViewModel loginViewModel;


    @Override
    public void onStart() {
        super.onStart();
        mFingerprintManager.addFignerprintListener(this);
        mFingerprintManager.init();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        navController = Navigation.findNavController(container);
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        context = getActivity();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sharedPreferences = context.getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String statusAuth = sharedPreferences.getString(getString(R.string.authentication_status), "null");

        if (statusAuth.equals(String.valueOf(CONSTANTS.LOGGEDIN))) {

            navController.navigate(LoginFragmentDirections.actionLoginFragment2ToMainActivity2());
            return null;

        }


        return binding.getRoot();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String isFirstTimeUser = sharedPreferences.getString(getString(R.string.is_first_time), "YES");
        mAPIService = ApiUtils.getAPIService();

        binding.btnLogin.setOnClickListener(v ->
        {

            String email, password;
            email = binding.email.getEditText().getText().toString();
            password = binding.password.getEditText().getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(requireActivity().getCurrentFocus(), "All Fields Needed to continue.", Snackbar.LENGTH_LONG).show();
                return;
            }


            binding.progress.getRoot().setVisibility(View.VISIBLE);


            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(email);
            loginRequest.setPassword(password);


            mAPIService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        editor.putString(getString(R.string.access_token), response.body().getAccessToken());
                        editor.putString(getString(R.string.authentication_status), String.valueOf(CONSTANTS.LOGGEDIN));
                        editor.apply();

                        String access = response.body().getAccessToken();


                        TokenInterceptor tokenInterceptor = new TokenInterceptor(response.body().getAccessToken());

                        OkHttpClient client = new OkHttpClient.Builder()
                                .addInterceptor(tokenInterceptor)
                                .build();

                        Retrofit retrofit = new Retrofit.Builder()
                                .client(client)
                                .baseUrl(COMMONRETROFIT.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        Log.d(TAG, "onResponse: inside login");
                        retrofit.create(APIService.class).getUserData().enqueue(new Callback<UserIn>() {
                            @Override
                            public void onResponse(Call<UserIn> call, Response<UserIn> response) {
                                if (response.isSuccessful()) {


                                    editor.putString(getString(R.string.user_email), response.body().getEmail());
                                    editor.putString(getString(R.string.user_name), response.body().getFirstname() + " " + response.body().getLastname());
                                    editor.putString(getString(R.string.user_phone), response.body().getPhone());
                                    editor.putString(getString(R.string.user_plate), response.body().getPlate());
                                    editor.apply();

                                    Log.d(TAG, "onResponse: inside getuserdata");
                                    retrofit.create(APIService.class).saccoInfo(access).enqueue(new Callback<SaccoInfo>() {
                                        @Override
                                        public void onResponse(Call<SaccoInfo> call, Response<SaccoInfo> response) {
                                            if (response.isSuccessful()) {
                                                editor.putString(getString(R.string.sacco), response.body().getSacco());
                                                editor.putString(getString(R.string.logo), response.body().getLogo());
                                                editor.putString(getString(R.string.sacco_motto), response.body().getSaccoMotto());
                                                editor.putString(getString(R.string.customer_care_no), response.body().getCustomerCareNo());
                                                editor.apply();

                                                Log.d(TAG, "onResponse: inside saccoinfo");
                                                retrofit.create(APIService.class).GetSubRoutes(access).enqueue(new Callback<List<SubRoutesResponse>>() {

                                                    @Override
                                                    public void onResponse(Call<List<SubRoutesResponse>> call, Response<List<SubRoutesResponse>> response) {
                                                        if(response.isSuccessful())
                                                        {

                                                            Log.d(TAG, "onResponse: inside get routes");
                                                            for (SubRoutesResponse subres :
                                                                    response.body()) {
                                                                subres.setId(response.body().indexOf(subres));
                                                                loginViewModel.SaveSubRoutes(subres);
                                                            }
                                                                    navController.navigate(LoginFragmentDirections.actionLoginFragment2ToMainActivity2());
                                                                    requireActivity().finishAffinity();


                                                            Log.d(TAG, "onResponse: finished this");

                                                            return;

                                                        }
                                                        Toast.makeText(getContext(), "oops", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<List<SubRoutesResponse>> call, Throwable t) {

                                                        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });



                                            }
                                            Log.d(TAG, "onResponse: error"+response.errorBody().toString());

                                        }

                                        @Override
                                        public void onFailure(Call<SaccoInfo> call, Throwable t) {
                                            Log.d(TAG, "onFailure: error"+t.getLocalizedMessage());
                                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }

                            }

                            @Override
                            public void onFailure(Call<UserIn> call, Throwable t) {
                                Snackbar.make(requireActivity().getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        });
                        return;


                    }


                    binding.progress.getRoot().setVisibility(View.GONE);
                    Snackbar.make(requireActivity().getCurrentFocus(), "Email or Password Invalid.", Snackbar.LENGTH_LONG).show();


                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    binding.progress.getRoot().setVisibility(View.GONE);

//                    Snackbar.make(requireActivity().getCurrentFocus(),"Check your internet connection then try again.",Snackbar.LENGTH_LONG).show();
                    new AlertDialog.Builder(requireActivity())
                            .setIcon(R.drawable.logo)
                            .setTitle("Login Error")
                            .setMessage("There was a problem while logging in.Would you like to continue with fingerprint login?")
                            .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                            .setPositiveButton("Okay", (dialogInterface, i) -> navController.navigate(LoginFragmentDirections.actionLoginFragment2ToFingerprintVerificationFragment()))
                            .show();


//                    navController.navigate(LoginFragmentDirections.actionLoginFragment2ToFingerprintVerificationFragment());
                }
            });


        });
    }


    private void getFingerprint() {

        mFingerprintManager.capture();

    }


    @Override
    public void onEnrollmentProgress(int i, int i1, int i2) {


    }

    @Override
    public void onAuthenticationFailed(int i) {

    }

    @Override
    public void onAuthenticationSucceeded(int i, Object o) {

    }

    @Override
    public void onGetImageComplete(int result, byte[] imgBuff) {

        /*if (result == 0) {
            final Bitmap bitmap = BitmapFactory.decodeByteArray(imgBuff, 0, imgBuff.length);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    navController.navigate(LoginFragmentDirections.actionLoginFragment2ToMainActivity());
                    requireActivity().finish();
//                    binding.imageView.setImageBitmap(bitmap);
                }
            });

            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "No fingerprint detected", Toast.LENGTH_SHORT).show();
            }
        });*/
        
        /*if (result != 0) {
            try {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String name = sdf.format(new Date()) + ".bmp";
// convert raw format image to bmp

                final Bitmap bitmap = mFingerprintManager.generateBmp(imgBuff, name);

                Log.d("TAG", "onGetImageComplete: success");

            } catch (Exception e) {

                Log.d("TAG", "onGetImageComplete: error occured");
                e.printStackTrace();
            }
        } else {
            Log.d("TAG", "onGetImageComplete: something nothis");
        }
        */
    }


    @Override
    public void onGetImageFeature(int i, byte[] bytes) {
        Toast.makeText(context, Arrays.toString(bytes), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetImageISOFeature(int i, byte[] bytes) {

        Toast.makeText(context, Arrays.toString(bytes), Toast.LENGTH_SHORT).show();
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.UP, enter, 700);
        } else {
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 700);
        }
    }
}