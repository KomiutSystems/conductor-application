package com.komiut.conductor.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.komiut.conductor.dao.CashTransactionDao;
import com.komiut.conductor.dao.MpesaMessageDao;
import com.komiut.conductor.dao.OfflineUserDataDao;
import com.komiut.conductor.dao.SubRoutesDao;
import com.komiut.conductor.db.AppDatabase;
import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.model.SubRoute;
import com.komiut.conductor.retrofit.APIService;
import com.komiut.conductor.retrofit.ApiUtils;
import com.komiut.conductor.retrofit.CashTransactionResponse;
import com.komiut.conductor.retrofit.SubRoutesResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.komiut.conductor.db.AppDatabase.dbExecutor;

public class Repository {


    public final MpesaMessageDao mpesaMessagesDao;
    private final CashTransactionDao cashTransactionDao;
    private final OfflineUserDataDao offlineUserDataDao;
    private final SubRoutesDao subRoutesDao;

    private APIService mAPIService;
    ApiUtils apiUtils;

    private LiveData<List<MpesaMessage>> mpesaMessages;
    private LiveData<List<MpesaMessage>> mpesaMessagesByDate;
    private LiveData<List<CashTransaction>> cashMessages;
    private LiveData<List<SubRoutesResponse>> subRoutes;
    private LiveData<List<OfflineUser>> offlineUsers;

    public Repository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        mpesaMessagesDao = db.mpesaMessageDao();
        cashTransactionDao = db.cashTransactionDao();
        offlineUserDataDao = db.offlineUserDataDao();
        subRoutesDao = db.subRoutesDao();

        offlineUsers=offlineUserDataDao.GetAllUsers();
        subRoutes=subRoutesDao.GetAllSubRoutes();
        mpesaMessages = mpesaMessagesDao.getAllMpesaMessages();
        cashMessages = cashTransactionDao.getAllCashTransactionNotSynced(false);

        mAPIService = ApiUtils.getAPIService();
    }


    public LiveData<List<MpesaMessage>> getMpesaMessages() {
        return mpesaMessages;
    }
    public LiveData<List<MpesaMessage>> getMpesaMessagesByDate(String date) {
        return mpesaMessagesDao.getAllMpesaMessagesByDate(date);
    }

    public void insertMpesaMessage(MpesaMessage mpesaMessage) {
        dbExecutor.execute(() -> {
            mpesaMessagesDao.insertAll(mpesaMessage);
        });
    }

    public LiveData<List<CashTransaction>> getCashMessages() {
        return cashMessages;
    }



    public LiveData<List<SubRoutesResponse>> GetSubRoutes() {
        return subRoutes;
    }
    public void InsertSubRoutes(SubRoutesResponse subRoutesResponses) {
        dbExecutor.execute(() -> subRoutesDao.SaveSubroutes(subRoutesResponses));
    }


    public void insertCashTransaction(CashTransaction cashTransaction) {
        dbExecutor.execute(() -> {
            cashTransactionDao.saveTransaction(cashTransaction);
        });
    }

    public void insertOfflineUser(OfflineUser offlineUser) {
        dbExecutor.execute(() -> {
            offlineUserDataDao.SaveUser(offlineUser);
        });
    }


    public LiveData<List<OfflineUser>> getOfflineUser() {
        return offlineUserDataDao.GetAllUsers();
    }

    public LiveData<List<CashTransaction>> getCashMessagesByDate(String date) {
        return cashTransactionDao.getCashMessagesByDate(date);
    }

    public void UpdateMpesamessage(MpesaMessage mpesaMessage) {
         mpesaMessagesDao.UpdateMpesaPrintStatus(mpesaMessage);
    }
}
