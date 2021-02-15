package com.komiut.conductor.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.komiut.conductor.model.OfflineUser;

import java.util.List;

@Dao
public interface OfflineUserDataDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void SaveUser(OfflineUser offlineUser);

    @Query("SELECT * FROM OfflineUser")
    LiveData<List<OfflineUser>> GetAllUsers();

}
