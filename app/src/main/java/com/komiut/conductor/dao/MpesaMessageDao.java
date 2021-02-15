package com.komiut.conductor.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.google.firebase.firestore.auth.User;
import com.komiut.conductor.model.MpesaMessage;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MpesaMessageDao {

    @Query("SELECT * FROM MpesaMessage")
    LiveData<List<MpesaMessage>> getAllMpesaMessages();

    @Update
    void UpdateMpesaPrintStatus(MpesaMessage mpesaMessage);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAll(MpesaMessage mpesaMessages);

    @Delete
    void delete(MpesaMessage user);

    @Query("SELECT * FROM MpesaMessage WHERE date=:date")
    LiveData<List<MpesaMessage>> getAllMpesaMessagesByDate(String date);
}
