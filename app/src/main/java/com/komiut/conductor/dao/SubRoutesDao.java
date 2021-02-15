package com.komiut.conductor.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.model.SubRoute;
import com.komiut.conductor.retrofit.SubRoutesResponse;

import java.util.List;

@Dao
public interface SubRoutesDao {

    @Query("SELECT * FROM SubRoutesResponse")
    LiveData<List<SubRoutesResponse>> GetAllSubRoutes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void SaveSubroutes(SubRoutesResponse subRoutes);

}
