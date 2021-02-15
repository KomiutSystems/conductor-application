package com.komiut.conductor.ui.transactions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.komiut.conductor.R;
import com.komiut.conductor.retrofit.TransactionResponse;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {
 List<Testt> transactions;

    public TransactionsAdapter(List<Testt> transactions) {
        this.transactions=transactions;
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_item,parent,false);

        return new TransactionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {

        Testt transactionResponse=transactions.get(position);
        holder.date.setText(transactionResponse.getDate());
        holder.amount.setText(transactionResponse.getAmount());
        holder.transactionPlaceholder.setText(transactionResponse.getTransaction());
        holder.transactionsType.setText(transactionResponse.getTransaction());
//        holder.transactionsType.setText(transactionResponse.getTransaction());
//
//        if(transactionResponse.getBankId()>0)
//        {
//            holder.transactionPlaceholder.setText("M");
//            holder.transactionsType.setText("Transacted via Mpesa");
//
//        }
//        else {
//            holder.transactionPlaceholder.setText("C");
//            holder.transactionsType.setText("Cash Transaction");
//        }


    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public  static class TransactionsViewHolder extends RecyclerView.ViewHolder{

        TextView transactionsType,amount,date,transactionPlaceholder;
        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionsType=itemView.findViewById(R.id.transactionType);
            transactionPlaceholder=itemView.findViewById(R.id.transactionPlaceholder);
            amount=itemView.findViewById(R.id.transactionAmount);
            date=itemView.findViewById(R.id.transactionDate);
        }
    }
}
