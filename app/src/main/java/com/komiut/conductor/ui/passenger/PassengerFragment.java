package com.komiut.conductor.ui.passenger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.MainActivity;
import com.komiut.conductor.MainActivityViewModel;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentPassengerBinding;
import com.komiut.conductor.model.CarLocation;
import com.komiut.conductor.retrofit.APIService;
import com.komiut.conductor.retrofit.ApiUtils;
import com.komiut.conductor.retrofit.COMMONRETROFIT;
import com.komiut.conductor.retrofit.Passenger;
import com.komiut.conductor.retrofit.TokenInterceptor;
import com.komiut.conductor.retrofit.TransactionResponse;
import com.komiut.conductor.ui.transactions.Testt;
import com.komiut.conductor.ui.transactions.TransactionsAdapter;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.NOTIFICATION_SERVICE;


public class PassengerFragment extends Fragment {

    FragmentPassengerBinding binding;
    MainActivityViewModel mainActivityViewModel;
    PassengerAdapter passengerAdapter;
    List<Boarding> boardingList = new ArrayList<>();
    MainActivity mainActivity = new MainActivity();
    MainActivityViewModel viewModel;

    //Notifications
    private static final String CHANNEL_ID = "komiut";
    private static final String CHANNEL_NAME = "komiut";
    private static final String CHANNEL_DESC = "Komiut";

    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        binding = FragmentPassengerBinding.inflate(inflater, container, false);
        navController= Navigation.findNavController(container);
        passengerAdapter = new PassengerAdapter(boardingList,mainActivityViewModel.getAccesstoken(),navController);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(passengerAdapter);

//        binding.recyclerView.


        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

/*
                if(!recyclerView.canScrollVertically(1)){

                    Toast.makeText(getContext(), "Can load more", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                int lastvisible = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (lastvisible == recyclerView.getChildCount()) {
                    Toast.makeText(getContext(), "last viewed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TokenInterceptor tokenInterceptor = new TokenInterceptor(mainActivityViewModel.getAccesstoken());

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(COMMONRETROFIT.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofit.create(APIService.class).getPassengers(mainActivityViewModel.getAccesstoken()).enqueue(new Callback<List<Boarding>>() {


            @Override
            public void onResponse(Call<List<Boarding>> call, Response<List<Boarding>> response) {
                if (response.isSuccessful()) {

                    binding.progress.setVisibility(View.GONE);

                    if (!response.body().isEmpty()) {
                        boardingList.addAll(response.body());
                        passengerAdapter.notifyDataSetChanged();
                        Snackbar.make(requireActivity().getCurrentFocus(), "Available Passengers", Snackbar.LENGTH_LONG).show();

                    } else {
                        Snackbar.make(requireActivity().getCurrentFocus(), "No available Passengers", Snackbar.LENGTH_LONG).show();

                    }

                    return;
                }

                Toast.makeText(getContext(), "After", Toast.LENGTH_LONG).show();
                Log.d("RES", response.toString());

              /*  try {
                  //  binding.error.setText(response.errorBody().string() + "holakds");
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onFailure(Call<List<Boarding>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();

            }
        });


    }

    public static void playAssetSound(Context context, String soundFileName) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();

            AssetFileDescriptor descriptor = context.getAssets().openFd(soundFileName);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}