package com.komiut.conductor.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.komiut.conductor.model.CashTransaction;

import java.util.List;

@Dao
public interface CashTransactionDao {

    @Query("SELECT * FROM CashTransaction WHERE status=:status")
    LiveData<List<CashTransaction>> getAllCashTransactionNotSynced(boolean status);

    @Query("SELECT * FROM CashTransaction")
    LiveData<List<CashTransaction>> getAllCashTransaction();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTransaction(CashTransaction cashTransaction);

    @Query("SELECT * FROM CashTransaction WHERE offlineDataAccessDate=:date")
    LiveData<List<CashTransaction>> getCashMessagesByDate(String date);

}
