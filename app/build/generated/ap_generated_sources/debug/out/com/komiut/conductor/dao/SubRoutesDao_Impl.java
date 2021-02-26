package com.komiut.conductor.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.komiut.conductor.retrofit.SubRoutesResponse;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SubRoutesDao_Impl implements SubRoutesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SubRoutesResponse> __insertionAdapterOfSubRoutesResponse;

  public SubRoutesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSubRoutesResponse = new EntityInsertionAdapter<SubRoutesResponse>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `SubRoutesResponse` (`id`,`substage`,`departure`,`destination`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SubRoutesResponse value) {
        stmt.bindLong(1, value.getId());
        if (value.getSubstage() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getSubstage());
        }
        if (value.getDeparture() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDeparture());
        }
        if (value.getDestination() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDestination());
        }
      }
    };
  }

  @Override
  public void SaveSubroutes(final SubRoutesResponse subRoutes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSubRoutesResponse.insert(subRoutes);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<SubRoutesResponse>> GetAllSubRoutes() {
    final String _sql = "SELECT * FROM SubRoutesResponse";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"SubRoutesResponse"}, false, new Callable<List<SubRoutesResponse>>() {
      @Override
      public List<SubRoutesResponse> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubstage = CursorUtil.getColumnIndexOrThrow(_cursor, "substage");
          final int _cursorIndexOfDeparture = CursorUtil.getColumnIndexOrThrow(_cursor, "departure");
          final int _cursorIndexOfDestination = CursorUtil.getColumnIndexOrThrow(_cursor, "destination");
          final List<SubRoutesResponse> _result = new ArrayList<SubRoutesResponse>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final SubRoutesResponse _item;
            _item = new SubRoutesResponse();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpSubstage;
            _tmpSubstage = _cursor.getString(_cursorIndexOfSubstage);
            _item.setSubstage(_tmpSubstage);
            final String _tmpDeparture;
            _tmpDeparture = _cursor.getString(_cursorIndexOfDeparture);
            _item.setDeparture(_tmpDeparture);
            final String _tmpDestination;
            _tmpDestination = _cursor.getString(_cursorIndexOfDestination);
            _item.setDestination(_tmpDestination);
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
