package com.komiut.conductor.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.repository.Repository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    Repository repository;

    private LiveData<List<CashTransaction>> cashPaymnets;




    public DashboardViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        cashPaymnets=repository.getCashMessages();

    }

    public LiveData<List<CashTransaction>> getCashPaymnets() {
        return cashPaymnets;
    }
    public LiveData<List<CashTransaction>> getCashPaymnetsByDate(String date) {
        return repository.getCashMessagesByDate(date);
    }

    public LiveData<List<MpesaMessage>> getMpesaPaymnetsForToday(String date) {
        return repository.getMpesaMessagesByDate(date);
    }


}