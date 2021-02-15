package com.komiut.conductor.ui.queue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.MainActivityViewModel;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentQueueBinding;
import com.komiut.conductor.retrofit.APIService;
import com.komiut.conductor.retrofit.COMMONRETROFIT;
import com.komiut.conductor.retrofit.QueueResponse;
import com.komiut.conductor.retrofit.TokenInterceptor;
import com.komiut.conductor.retrofit.TransactionResponse;
import com.komiut.conductor.ui.transactions.Testt;
import com.komiut.conductor.ui.transactions.TransactionsAdapter;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QueueFragment extends Fragment {

    FragmentQueueBinding binding;
    NavController navController;

    private MainActivityViewModel mainActivityViewModel;

    QueueAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        binding=FragmentQueueBinding.inflate(inflater,container,false);

        navController= Navigation.findNavController(container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.requestQueue.setOnClickListener(view1 -> {

            navController.navigate(QueueFragmentDirections.actionNavigationQueueToRequestQueueFragment());
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


//        mainActivityViewModel.getUserMutableLiveData().getValue().getUser_plate()

        retrofit.create(APIService.class).getQueues(mainActivityViewModel.getUserMutableLiveData().getValue().getUser_plate()).enqueue(new Callback<List<QueueResponse>>() {
            @Override
            public void onResponse(Call<List<QueueResponse>> call, Response<List<QueueResponse>> response) {
                if (response.isSuccessful()) {


                    if(response.body().size()==0)
                    {
                        Snackbar.make(requireActivity().getCurrentFocus(),"No Queue data found",Snackbar.LENGTH_LONG).show();

                    }
                    else
                    {
                        adapter=new QueueAdapter(response.body());
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                        binding.recyclerView.setHasFixedSize(true);
                        binding.recyclerView.setAdapter(adapter);
                    }

                }


            }

            @Override
            public void onFailure(Call<List<QueueResponse>> call, Throwable t) {
            }
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