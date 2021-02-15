package com.komiut.conductor.ui.mpesa;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MpesaViewModel extends AndroidViewModel {

    private final LiveData<List<MpesaMessage>> getMessages;
    private Repository repository;

    public MpesaViewModel(Application application) {
        super(application);
        this.repository = new Repository(application);
        getMessages=repository.getMpesaMessages();
    }

    public LiveData<List<MpesaMessage>> getAllMessages(){return getMessages;}

    public  void  insertMessage(MpesaMessage mpesaMessage){
        repository.insertMpesaMessage(mpesaMessage);
    }

    public LiveData<List<MpesaMessage>> getMpesaMessagesByDate(String date) {
        return repository.getMpesaMessagesByDate(date);
    }
}
