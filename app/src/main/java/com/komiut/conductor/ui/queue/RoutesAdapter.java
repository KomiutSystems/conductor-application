package com.komiut.conductor.ui.queue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.R;
import com.komiut.conductor.retrofit.APIService;
import com.komiut.conductor.retrofit.ApiUtils;
import com.komiut.conductor.retrofit.COMMONRETROFIT;
import com.komiut.conductor.retrofit.QueueRequest;
import com.komiut.conductor.retrofit.QueueRequestResponse;
import com.komiut.conductor.retrofit.RouteResponce;
import com.komiut.conductor.retrofit.TokenInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoutesAdapter  extends RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder> {


    List<RouteResponce> routeResponceList;
    private Context context;
    APIService apiService;
    String acccess;

    public RoutesAdapter(List<RouteResponce> routeResponceList, String acccess) {
        this.routeResponceList=routeResponceList;
        apiService= ApiUtils.getAPIService();
        this.acccess=acccess;
    }

    @NonNull
    @Override
    public RoutesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.routes_items,parent,false);
        context=parent.getContext();
        return new RoutesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesViewHolder holder, int position) {

        RouteResponce responce=routeResponceList.get(position);
        RoutesViewHolder view=holder;
        view.destination.setText(responce.getDeparture()+" to "+responce.getDestination());

        view.plate.setText("Stage name: "+responce.getName());

        Object distance_to_queue=responce.getDistanceToQueue();

        double distance=responce.getDistance();
        view.request.setOnClickListener(view1 -> {

            if(distance_to_queue==null)
            {
               Snackbar.make(holder.itemView,"You are too far away to queue. Try again later.",Snackbar.LENGTH_LONG).show();
                return;
            }
           Double num=new Double(distance_to_queue.toString());

            if(distance<=num)
            {

                QueueRequest queueRequest=new QueueRequest();
                queueRequest.setAmount("10");
                queueRequest.setPassengers("15");
                queueRequest.setRoute_id(String.valueOf(responce.getId()));


                prepareRetrofit(queueRequest,holder.itemView);


            }

        });


    }

    private void prepareRetrofit(QueueRequest queueRequest, View itemView) {
        TokenInterceptor tokenInterceptor = new TokenInterceptor(acccess);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(COMMONRETROFIT.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(APIService.class).RequestQueue(acccess,queueRequest).enqueue(new Callback<QueueRequestResponse>() {
            @Override
            public void onResponse(Call<QueueRequestResponse> call, Response<QueueRequestResponse> response) {
                if(response.isSuccessful())
                {


                    Snackbar.make(itemView,"You have been queued successfully",Snackbar.LENGTH_LONG).show();
                    return;
                }
                Snackbar.make(itemView,"Something went wrong try again later.",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<QueueRequestResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return routeResponceList.size();
    }

    public class RoutesViewHolder extends RecyclerView.ViewHolder {

        TextView amount,destination,plate;
        Button request;
        public RoutesViewHolder(@NonNull View itemView) {
            super(itemView);
            plate=itemView.findViewById(R.id.plate);
            destination=itemView.findViewById(R.id.destination);
            amount=itemView.findViewById(R.id.amount);
            request=itemView.findViewById(R.id.btnRequest);
        }
    }
}
