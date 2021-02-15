package com.komiut.conductor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.komiut.conductor.model.CarLocation;
import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.repository.Repository;
import com.komiut.conductor.retrofit.SaccoInfo;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    MutableLiveData<String> accesstoken = new MutableLiveData<>();
    MutableLiveData<SaccoInfo> saccoInfoMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CarLocation> carLocationMutableLiveData = new MutableLiveData<>();
    Repository repository;

    MutableLiveData<OfflineUser> userMutableLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

        userMutableLiveData = new MutableLiveData<>();
    }

    public void setCarLocationMutableLiveData(CarLocation carLocationMutableLiveData) {
        this.carLocationMutableLiveData.setValue( carLocationMutableLiveData);
    }
    public void updateCashTransaction(CashTransaction cashTransaction) {
        repository.insertCashTransaction(cashTransaction);
    }

    public void setUserMutableLiveData(OfflineUser usermodel) {
        this.userMutableLiveData.setValue(usermodel);
    }

    public MutableLiveData<CarLocation> getCarLocationMutableLiveData() {
        return carLocationMutableLiveData;
    }

    public String getAccesstoken() {
        return accesstoken.getValue();
    }

    public LiveData<List<CashTransaction>> SyncData() {
        return repository.getCashMessages();

    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken.setValue(accesstoken);
    }

    public MutableLiveData<OfflineUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<SaccoInfo> getSaccoInfoMutableLiveData() {
        return saccoInfoMutableLiveData;
    }

    public void setSaccoInfoMutableLiveData(SaccoInfo saccoInfo) {
        this.saccoInfoMutableLiveData.setValue(saccoInfo);
    }
}
