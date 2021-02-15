package com.komiut.conductor.ui.mpesa;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.DatePicker;
import android.widget.Toast;



import com.komiut.conductor.databinding.FragmentMpesaTransactionsBinding;
import com.labo.kaji.fragmentanimations.MoveAnimation;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MpesaTransactionsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    FragmentMpesaTransactionsBinding binding;
    NavController navController;
    MpesaViewModel cashViewModel;
    private int day;
    private int month;
    private int year;
    int myday, myMonth, myYear, myHour, myMinute;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cashViewModel=new ViewModelProvider(this).get(MpesaViewModel.class);
        binding= FragmentMpesaTransactionsBinding.inflate(inflater,container,false);
        navController= Navigation.findNavController(container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.button8.setOnClickListener(view1 -> {

            Calendar calendar=Calendar.getInstance();
            year=calendar.get(Calendar.YEAR);
            month=calendar.get(Calendar.MONTH);
            day=calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog=new DatePickerDialog(requireActivity(),this,year,month,day);
            datePickerDialog.show();
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        myYear = year;
        myday = day;
        myMonth = month;

        SimpleDateFormat old=new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat newPattern=new SimpleDateFormat("dd/MM/yy");
        try {
            myMonth+=1;
            Date date=old.parse(myday+"/"+myMonth+"/"+myYear);
            String output=newPattern.format(date);
            navController.navigate(MpesaTransactionsFragmentDirections.actionMpesaTransactionsFragmentToMpesaTransactionViewerFragment(output));
        } catch (ParseException e) {
            e.printStackTrace();
        }



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