package com.komiut.conductor.ui.mpesa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.komiut.conductor.R;
import com.komiut.conductor.model.MpesaMessage;

import java.util.List;

public class MpesaTransactionsAdapter extends RecyclerView.Adapter<MpesaTransactionsAdapter.MpesaTransactionViewHolder> {

    List<MpesaMessage> mpesaMessageList;

    public MpesaTransactionsAdapter(List<MpesaMessage> mpesaMessageList) {
        this.mpesaMessageList = mpesaMessageList;
    }

    @NonNull
    @Override
    public MpesaTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mpesa_transaction_viewer_item, parent, false);
        return new MpesaTransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MpesaTransactionViewHolder holder, int position) {

        MpesaMessage transaction = mpesaMessageList.get(position);
        holder.transactionId.setText(transaction.getTransactionId());
        holder.name.setText(transaction.getFirstname() + " " + transaction.getSecondname());
        holder.fare.setText(transaction.getAmount());
        holder.time.setText(transaction.getTime());
    }

    @Override
    public int getItemCount() {
        return mpesaMessageList.size();
    }

    public class MpesaTransactionViewHolder extends RecyclerView.ViewHolder {

        TextView transactionId, name, fare, time;

        public MpesaTransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionId = itemView.findViewById(R.id.transactionId);
            name = itemView.findViewById(R.id.name);
            fare = itemView.findViewById(R.id.fare);
            time = itemView.findViewById(R.id.time);
        }
    }
}
