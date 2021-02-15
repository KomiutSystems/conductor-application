package com.komiut.conductor.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.komiut.conductor.dao.CashTransactionDao;
import com.komiut.conductor.dao.MpesaMessageDao;
import com.komiut.conductor.dao.OfflineUserDataDao;
import com.komiut.conductor.dao.SubRoutesDao;
import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.model.SubRoute;
import com.komiut.conductor.retrofit.SubRoutesResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MpesaMessage.class, CashTransaction.class, OfflineUser.class, SubRoutesResponse.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "KomiutDBTest")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract MpesaMessageDao mpesaMessageDao();

    public abstract CashTransactionDao cashTransactionDao();

    public abstract OfflineUserDataDao offlineUserDataDao();
    public abstract SubRoutesDao subRoutesDao();


}
