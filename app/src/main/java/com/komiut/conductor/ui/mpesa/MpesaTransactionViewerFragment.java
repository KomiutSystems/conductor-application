package com.komiut.conductor.ui.mpesa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentCashTransactionsViewerBinding;
import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.ui.cash.CashTransactionsAdapter;
import com.komiut.conductor.ui.cash.CashTransactionsViewerFragmentArgs;
import com.komiut.conductor.ui.cash.CashViewModel;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.util.ArrayList;
import java.util.List;

public class MpesaTransactionViewerFragment extends Fragment {
    MpesaViewModel mpesaViewModel;
    FragmentCashTransactionsViewerBinding binding;
    private NavController navController;
    String dateOfTransaction;
    List<CashTransaction> cashTransactionList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mpesaViewModel=new ViewModelProvider(this).get(MpesaViewModel.class);
        binding= FragmentCashTransactionsViewerBinding.inflate(inflater,container,false);
        navController= Navigation.findNavController(container);
        dateOfTransaction= CashTransactionsViewerFragmentArgs.fromBundle(getArguments()).getDateOfTransaction();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textView24.setText("Transactions for the date "+dateOfTransaction);

        mpesaViewModel.getMpesaMessagesByDate(dateOfTransaction).observe(getViewLifecycleOwner(),mpesaTransactions -> {

            if(mpesaTransactions.size()==0)return;
            MpesaTransactionsAdapter adapter=new MpesaTransactionsAdapter(mpesaTransactions);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setAdapter(adapter);


            int total=0;
            for (MpesaMessage message :
                    mpesaTransactions) {
                String beginAMount=message.getAmount().substring(3);
                String endAmount=beginAMount.substring(0,beginAMount.length()-3);
                total+=Integer.parseInt(endAmount);
            }

            binding.total.setText("Kshs. "+total);

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