package com.komiut.conductor.ui.cash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.komiut.conductor.R;
import com.komiut.conductor.model.CashTransaction;

import java.sql.Timestamp;
import java.util.List;

public class CashTransactionsAdapter extends RecyclerView.Adapter<CashTransactionsAdapter.CashTransactionViewHolder> {

    List<CashTransaction> cashTransactionList;
    public CashTransactionsAdapter( List<CashTransaction> cashTransactionList) {
        this.cashTransactionList=cashTransactionList;
    }

    @NonNull
    @Override
    public CashTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cash_transaction_viewer_item,parent,false);
        return new CashTransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CashTransactionViewHolder holder, int position) {

        CashTransaction transaction=cashTransactionList.get(position);

        String depart, dest;
     /*   Timestamp ts = Timestamp.valueOf(transaction.getDate());
        Long val = ts.getTime();*/

        depart = transaction.getSelectedDepart();
        dest =transaction.getSelectedDest();
        holder.fare.setText("Ksh "+ transaction.getAmount());
        holder.name.setText(transaction.getPassname());
        holder.route.setText(depart+" >> "+dest);
        holder.time.setText(transaction.getDate().substring(8,12)+ " Hrs");
    }

    @Override
    public int getItemCount() {
        return cashTransactionList.size();
    }

    public class CashTransactionViewHolder extends RecyclerView.ViewHolder{

        TextView fare,name,route,time;
        public CashTransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            fare=itemView.findViewById(R.id.fare);
            name=itemView.findViewById(R.id.name);
            route=itemView.findViewById(R.id.route);
            time=itemView.findViewById(R.id.time);
        }
    }
}
