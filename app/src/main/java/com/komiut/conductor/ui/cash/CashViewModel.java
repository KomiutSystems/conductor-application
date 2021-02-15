package com.komiut.conductor.ui.cash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.model.SubRoute;
import com.komiut.conductor.repository.Repository;
import com.komiut.conductor.retrofit.Stage;
import com.komiut.conductor.retrofit.SubRoutesResponse;

import java.util.List;

public class CashViewModel extends AndroidViewModel {


    private MutableLiveData<Integer> counter=new MutableLiveData<>(0);
    private Repository repository;
    LiveData<List<SubRoutesResponse>> subroutes;

    public CashViewModel(@NonNull Application application) {
        super(application);
       repository=new Repository(application);
        subroutes=repository.GetSubRoutes();
    }

    public LiveData<Boolean> saveCashTransaction(CashTransaction cashTransaction){

         repository.insertCashTransaction(cashTransaction);
         return new MutableLiveData<Boolean>(true);
    }
    public LiveData<List<CashTransaction>> getCashTransaction( ){


         return repository.getCashMessages();
    }
    public LiveData<List<CashTransaction>> getCashTransactionByDate(String date ){


         return repository.getCashMessagesByDate(date);
    }




    public void resetCounter(int count)
    {
        counter.setValue( count);
    }
    public void setCounter(int count)
    {
       counter.setValue( counter.getValue()+count);
    }
    public LiveData<Integer> getCounte() {
        return counter;
    }

    public void SaveSubRoutes(SubRoutesResponse subRoutesResponses)
    {
        repository.InsertSubRoutes(subRoutesResponses);
    }

    public LiveData<List<SubRoutesResponse>> getSubroutes() {
        return subroutes;
    }

//    public LiveData<List<Stage>> getStage() { return stage;}
}