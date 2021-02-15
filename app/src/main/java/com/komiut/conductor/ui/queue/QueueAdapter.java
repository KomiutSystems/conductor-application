package com.komiut.conductor.ui.queue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.komiut.conductor.R;
import com.komiut.conductor.retrofit.QueueResponse;

import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueViewHolder> {

    List<QueueResponse> queueResponseArrayList;


    public QueueAdapter(List<QueueResponse> queueResponseArrayList) {
        this.queueResponseArrayList = queueResponseArrayList;
    }

    private Context context;

    @NonNull
    @Override
    public QueueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.queue_list_item,parent,false);
        context=parent.getContext();

        return new QueueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QueueViewHolder holder, int position) {

        QueueResponse response=queueResponseArrayList.get(position);
        holder.amount.setText("KSh. "+response.getAmount().toString());
        holder.plate.setText(response.getPlate());
        holder.destination.setText(response.getDeparture()+" to "+response.getDestination());

    }

    @Override
    public int getItemCount() {
        return queueResponseArrayList.size();
    }

    public class QueueViewHolder extends RecyclerView.ViewHolder{

        TextView amount,destination,plate;
        public QueueViewHolder(@NonNull View itemView) {
            super(itemView);
            plate=itemView.findViewById(R.id.plate);
            destination=itemView.findViewById(R.id.destination);
            amount=itemView.findViewById(R.id.amount);

        }
    }
}
