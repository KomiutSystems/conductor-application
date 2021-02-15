package com.komiut.conductor.ui.transactions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.MainActivityViewModel;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentTransactionsBinding;
import com.komiut.conductor.retrofit.APIService;
import com.komiut.conductor.retrofit.ApiUtils;
import com.komiut.conductor.retrofit.COMMONRETROFIT;
import com.komiut.conductor.retrofit.TokenInterceptor;
import com.komiut.conductor.retrofit.TransactionResponse;
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

public class TransactionsFragment extends Fragment {

    FragmentTransactionsBinding binding;
    private APIService mAPIService;
    MainActivityViewModel mainActivityViewModel;
    TransactionsAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivityViewModel=new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        binding=FragmentTransactionsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAPIService = ApiUtils.getAPIService();

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
                int lastvisible=((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if(lastvisible==recyclerView.getChildCount())
                {
                    Toast.makeText(getContext(), "last viewed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TokenInterceptor tokenInterceptor=new TokenInterceptor(mainActivityViewModel.getAccesstoken());

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(COMMONRETROFIT.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofit.create(APIService.class).getTransactions().enqueue(new Callback<List<TransactionResponse>>() {
            @Override
            public void onResponse(Call<List<TransactionResponse>> call, Response<List<TransactionResponse>> response) {
                if (response.isSuccessful()) {


                   binding.progress.setVisibility(View.GONE);
                   binding.mainContent.setVisibility(View.VISIBLE);

                    List<Testt> testtList = new ArrayList<>();
                    int accountTotal=0;

                    for (TransactionResponse res :
                            response.body()) {

                        Testt testt=new Testt();

                        if(res.getMpesaId()<1)
                        {
                            testt.setTransaction("Cash");
                        }
                        else
                        {
                            testt.setTransaction("Mpesa");
                        }

                        testt.setDate(res.getCreatedAt());

                        accountTotal+=res.getAmount();
                        testt.setAmount(String.valueOf(res.getAmount()));
                        testtList.add(testt);

                    }

                    binding.textView5.setText("KSH "+String.valueOf(accountTotal));



                    adapter=new TransactionsAdapter(testtList);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    binding.recyclerView.setHasFixedSize(true);
                    binding.recyclerView.setAdapter(adapter);
                    return;
                }
                try {
                    binding.error.setText(response.errorBody().string() + "holakds");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<TransactionResponse>> call, Throwable t) {

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