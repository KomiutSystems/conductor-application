package com.komiut.conductor.ui.queue;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.komiut.conductor.MainActivity;
import com.komiut.conductor.MainActivityViewModel;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentRequestQueueBinding;
import com.komiut.conductor.model.CarLocation;
import com.komiut.conductor.retrofit.APIService;
import com.komiut.conductor.retrofit.ApiUtils;
import com.komiut.conductor.retrofit.COMMONRETROFIT;
import com.komiut.conductor.retrofit.RouteRequest;
import com.komiut.conductor.retrofit.RouteResponce;
import com.komiut.conductor.retrofit.TokenInterceptor;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class RequestQueueFragment extends Fragment {


    FragmentRequestQueueBinding binding;
    private APIService mAPIService;
    private SharedPreferences sharedPreferences;
    private String acccess;
    MainActivityViewModel mainActivityViewModel;
    LocationManager locationManager;
    LocationListener locationListener;
    List<RouteResponce> routeResponceList=new ArrayList<>();
    RoutesAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestQueueBinding.inflate(inflater, container, false);
        mAPIService = ApiUtils.getAPIService();
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
        acccess = sharedPreferences.getString(getString(R.string.access_token), "null");
        adapter=new RoutesAdapter(routeResponceList,acccess);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setHasFixedSize(true);



        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(getContext(), "No permissions", Toast.LENGTH_SHORT).show();

            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null)
                    {
                        // Logic to handle location object
//                        Toast.makeText(getContext(), String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
                        acccess = sharedPreferences.getString(getString(R.string.access_token), "null");
                        RouteRequest routeRequest = new RouteRequest();
                        routeRequest.setLatitude(-0.192075);
                        routeRequest.setLongitude(36.1285689);

//                        routeRequest.setLatitude(location.getLatitude());
//                        routeRequest.setLongitude(location.getLongitude());


                        TokenInterceptor tokenInterceptor = new TokenInterceptor(acccess);

                        OkHttpClient client = new OkHttpClient.Builder()
                                .addInterceptor(tokenInterceptor)
                                .build();

                        Retrofit retrofit = new Retrofit.Builder()
                                .client(client)
                                .baseUrl(COMMONRETROFIT.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        retrofit.create(APIService.class).FetchRoutes(acccess, routeRequest).enqueue(new Callback<List<RouteResponce>>() {
                            @Override
                            public void onResponse(Call<List<RouteResponce>> call, Response<List<RouteResponce>> response) {
                                if (response.isSuccessful()) {

                                    routeResponceList.addAll(response.body());
                                    adapter.notifyDataSetChanged();
                                    return;


                                }
                                Toast.makeText(getContext(), "Routes not fetched", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<List<RouteResponce>> call, Throwable t) {
                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    Toast.makeText(getContext(), "No location fetched", Toast.LENGTH_SHORT).show();
                });

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