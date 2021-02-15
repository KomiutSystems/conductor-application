package com.komiut.conductor.ui.fingerprint;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.repository.Repository;

import java.util.List;



public class FingerprintViewModel extends AndroidViewModel {
    private final Repository repository;
    private final LiveData<List<OfflineUser>> offlineUsers;



    public FingerprintViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        offlineUsers=repository.getOfflineUser();
    }


    public LiveData<List<OfflineUser>> getOfflineUsers() {
        return offlineUsers;
    }



}
