package com.komiut.conductor.ui.passenger;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.komiut.conductor.MainActivity;
import com.komiut.conductor.MainActivityViewModel;
import com.komiut.conductor.R;
import com.komiut.conductor.model.Booking;
import com.komiut.conductor.model.CarLocation;
import com.komiut.conductor.model.PassengerMapCoordinates;
import com.komiut.conductor.retrofit.APIService;
import com.komiut.conductor.retrofit.ApiUtils;
import com.komiut.conductor.retrofit.COMMONRETROFIT;
import com.komiut.conductor.retrofit.QueueRequestResponse;
import com.komiut.conductor.retrofit.RetrofitClient;
import com.komiut.conductor.retrofit.TokenInterceptor;
import com.komiut.conductor.ui.transactions.Testt;

import java.sql.Array;
import java.util.List;
import java.util.Random;
import java.util.TooManyListenersException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.PassengerViewHolder> {
    List<Boarding> passList;

    Context context;
    //Notifications
    private static final String CHANNEL_ID = "komiut";
    private static final String CHANNEL_NAME = "komiut";
    private static final String CHANNEL_DESC = "Komiut";
    private static final String TAG = "PassengerAdapter";

    DatabaseReference databaseReference;
    Double late, loge, latitude, longitude;
    float[] results = new float[1];
    String access_token;
    NavController navController;

    public PassengerAdapter(List<Boarding> passList, String access_token, NavController navController) {
        this.passList = passList;
        this.access_token = access_token;
        this.navController = navController;
    }

    @NonNull
    @Override
    public PassengerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_item, parent, false);

        context = parent.getContext();
        return new PassengerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerViewHolder holder, int position) {

        Boarding passengerResponse = passList.get(position);
        String from = passengerResponse.getDepart() != null ? String.valueOf(passengerResponse.getDepart()) : passengerResponse.getDeparture();
        String latitude = passengerResponse.getLatitude() != null ? String.valueOf(passengerResponse.getLatitude()) : String.valueOf(passengerResponse.getLatitudeFrom());
        String longitude = passengerResponse.getLongitude() != null ? String.valueOf(passengerResponse.getLongitude()) : String.valueOf(passengerResponse.getLongitudeFrom());
        String boarded = passengerResponse.getBoarded();
        String name = (String) passengerResponse.getName();
        Integer id = passengerResponse.getId();

//        holder.depart.setText(passengerResponse.getDepart() != null ? (CharSequence) passengerResponse.getDepart() : passengerResponse.getDeparture());
        holder.depart.setText(from);
        holder.dest.setText(passengerResponse.getDest() != null ? (CharSequence) passengerResponse.getDest() : passengerResponse.getDestination());
        holder.name.setText(name);
        holder.phone.setText((CharSequence) passengerResponse.getPhone());


      /*  if (boarded == "0") {
            holder.paymentStatus.setText("Status:Not Boarded");
        }*/


        /*CarLocation carLocation = new CarLocation();
        Double latitude = carLocation.getLatitude();
        Double longitude = carLocation.getLatitude();


        latitude = passengerResponse.getLatitudeFrom();
        longitude = passengerResponse.getLongitudeFrom();*/

        PassengerMapCoordinates coordinates = new PassengerMapCoordinates();
        coordinates.setLatitutde(Double.valueOf(latitude));
        coordinates.setLongitude(Double.valueOf(longitude));


        //Get Coordinates from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Locations").child(passengerResponse.getPlate());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Device location
                String latitude1 = snapshot.child("latitude").getValue().toString();
                String longitude2 = snapshot.child("longitude").getValue().toString();

                Log.d("Lat", latitude1);
                Log.d("Lat", longitude2);

                late = Double.valueOf(latitude1);
                loge = Double.valueOf(longitude2);

                Location.distanceBetween(Double.valueOf(latitude), Double.valueOf(longitude), late, loge, results);

                Log.d("Res Late", String.valueOf(late));
                Log.d("Res Long", String.valueOf(loge));


//        Location.distanceBetween();

                float distanceKm = results[0] / 1000;
                //      float distanceKm = (float) 5;
                String distance = String.format("%.2f", distanceKm);

                Log.d("Results", String.valueOf(distance));

                holder.distanceAway.setText(distance + " Km away");

                if (distanceKm == 0.30) {

                    new AlertDialog.Builder(context).setMessage("Pick the Passenger")
                            .setTitle("Passenger name: " + name)
                            .show();


                }
                if (distanceKm <= 5) {
                    displayNotification(name, from);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.btnBoard.setOnClickListener(v -> {

            Log.d(TAG, "onClick: Entered into button ");

            TokenInterceptor tokenInterceptor = new TokenInterceptor(access_token);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(tokenInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(COMMONRETROFIT.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofit.create(APIService.class).board(access_token, passengerResponse.getId()).enqueue(new Callback<Booking>() {
                @Override
                public void onResponse(Call<Booking> call, Response<Booking> response) {
                    if (response.isSuccessful()) {

                        Log.d(TAG, "onResponse: inised board retrofit");
                        Snackbar.make(v, "Passenger boarded successfully 1", Snackbar.LENGTH_LONG).show();
                        holder.paymentStatus.setText("Status: Boarded");
                        holder.btnBoard.setVisibility(View.GONE);

                        notifyDataSetChanged();


                        return;
                    }

                    Log.d(TAG, "onResponse: Error" + response.body().toString());
                    Snackbar.make(v, "Passenger boarded successfully", Snackbar.LENGTH_LONG).show();


                }

                @Override
                public void onFailure(Call<Booking> call, Throwable t) {
                    Snackbar.make(v, "Something went wrong try again later.", Snackbar.LENGTH_LONG).show();
                    Log.d(TAG, "onFailure: Failed intenernet connections");
                }
            });

        });



        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TokenInterceptor tokenInterceptor = new TokenInterceptor(access_token);

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(tokenInterceptor)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl(COMMONRETROFIT.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                retrofit.create(APIService.class).cancelBooking(access_token, passengerResponse.getId()).enqueue(new Callback<Booking>() {
                    @Override
                    public void onResponse(Call<Booking> call, Response<Booking> response) {
                        if (response.isSuccessful()) {

                            Snackbar.make(v, "Passenger booking cancelled successfully", Snackbar.LENGTH_LONG).show();

                            holder.btnCancel.setVisibility(View.GONE);
                            return;
                        }
                        Snackbar.make(v, "Passenger booking cancelled successfully", Snackbar.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<Booking> call, Throwable t) {
                        Snackbar.make(v, "Something went wrong try again later.", Snackbar.LENGTH_LONG).show();
                    }
                });
                notifyDataSetChanged();

            }


        });

        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(PassengerFragmentDirections.actionNavigationPassengerToPassengerMapsFragment(coordinates));

                //Inflate the fragment
                //getFragmentManager().beginTransaction().add(R.id.container, passengerMapsFragment).commit();

                Toast.makeText(context, "You clicked " + position, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return passList.size();
    }


    public static class PassengerViewHolder extends RecyclerView.ViewHolder {

        TextView depart, dest, name, phone, paymentStatus, distanceAway;
        Button btnBoard, btnCancel, btnMap;

        public PassengerViewHolder(@NonNull View itemView) {
            super(itemView);
            depart = itemView.findViewById(R.id.txtDepart);
            dest = itemView.findViewById(R.id.txtDest);
            name = itemView.findViewById(R.id.txtName);
            phone = itemView.findViewById(R.id.txtPhone);
            paymentStatus = itemView.findViewById(R.id.txtPaymentStatus);
            distanceAway = itemView.findViewById(R.id.txtDistanceAwayKm);
            btnBoard = itemView.findViewById(R.id.btnBoard);
            btnMap = itemView.findViewById(R.id.btnMap);
            btnCancel = itemView.findViewById(R.id.btnCancel);


        }
    }

    public void displayNotification(String name, String from) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,
                CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(name + " is a few meters away")
                .setContentText("Pick the passenger at " + from)
                .setPriority(Notification.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, mBuilder.build());
//        Snackbar.make(requireActivity().getCurrentFocus(), name + "is a few km away pickup" + from, Snackbar.LENGTH_LONG).show();

        Log.d("Res display", name);

    }


}
