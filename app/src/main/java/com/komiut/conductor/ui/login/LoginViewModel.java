package com.komiut.conductor.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.model.SubRoute;
import com.komiut.conductor.repository.Repository;
import com.komiut.conductor.retrofit.SubRoutesResponse;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {


    Repository repository;

    LiveData<List<SubRoutesResponse>> subroutes;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        subroutes=repository.GetSubRoutes();


    }


    public void SaveSubRoutes(SubRoutesResponse subRoutesResponses)
    {
        repository.InsertSubRoutes(subRoutesResponses);
    }



}
