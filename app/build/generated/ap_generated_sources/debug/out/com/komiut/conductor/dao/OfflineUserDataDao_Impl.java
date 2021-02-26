package com.komiut.conductor.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.komiut.conductor.model.OfflineUser;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class OfflineUserDataDao_Impl implements OfflineUserDataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<OfflineUser> __insertionAdapterOfOfflineUser;

  public OfflineUserDataDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOfflineUser = new EntityInsertionAdapter<OfflineUser>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `OfflineUser` (`id`,`user_email`,`user_name`,`user_phone`,`user_plate`,`fingerprint`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, OfflineUser value) {
        stmt.bindLong(1, value.getId());
        if (value.getUser_email() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUser_email());
        }
        if (value.getUser_name() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUser_name());
        }
        if (value.getUser_phone() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUser_phone());
        }
        if (value.getUser_plate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getUser_plate());
        }
        if (value.getFingerprint() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindBlob(6, value.getFingerprint());
        }
      }
    };
  }

  @Override
  public void SaveUser(final OfflineUser offlineUser) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfOfflineUser.insert(offlineUser);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<OfflineUser>> GetAllUsers() {
    final String _sql = "SELECT * FROM OfflineUser";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"OfflineUser"}, false, new Callable<List<OfflineUser>>() {
      @Override
      public List<OfflineUser> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "user_email");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "user_name");
          final int _cursorIndexOfUserPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "user_phone");
          final int _cursorIndexOfUserPlate = CursorUtil.getColumnIndexOrThrow(_cursor, "user_plate");
          final int _cursorIndexOfFingerprint = CursorUtil.getColumnIndexOrThrow(_cursor, "fingerprint");
          final List<OfflineUser> _result = new ArrayList<OfflineUser>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final OfflineUser _item;
            _item = new OfflineUser();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpUser_email;
            _tmpUser_email = _cursor.getString(_cursorIndexOfUserEmail);
            _item.setUser_email(_tmpUser_email);
            final String _tmpUser_name;
            _tmpUser_name = _cursor.getString(_cursorIndexOfUserName);
            _item.setUser_name(_tmpUser_name);
            final String _tmpUser_phone;
            _tmpUser_phone = _cursor.getString(_cursorIndexOfUserPhone);
            _item.setUser_phone(_tmpUser_phone);
            final String _tmpUser_plate;
            _tmpUser_plate = _cursor.getString(_cursorIndexOfUserPlate);
            _item.setUser_plate(_tmpUser_plate);
            final byte[] _tmpFingerprint;
            _tmpFingerprint = _cursor.getBlob(_cursorIndexOfFingerprint);
            _item.setFingerprint(_tmpFingerprint);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
