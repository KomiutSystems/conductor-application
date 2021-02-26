package com.komiut.conductor.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.komiut.conductor.model.MpesaMessage;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MpesaMessageDao_Impl implements MpesaMessageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MpesaMessage> __insertionAdapterOfMpesaMessage;

  private final EntityDeletionOrUpdateAdapter<MpesaMessage> __deletionAdapterOfMpesaMessage;

  private final EntityDeletionOrUpdateAdapter<MpesaMessage> __updateAdapterOfMpesaMessage;

  public MpesaMessageDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMpesaMessage = new EntityInsertionAdapter<MpesaMessage>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `MpesaMessage` (`transactionId`,`date`,`time`,`amount`,`phonenumber`,`firstname`,`secondname`,`printStatus`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MpesaMessage value) {
        if (value.getTransactionId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getTransactionId());
        }
        if (value.getDate() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDate());
        }
        if (value.getTime() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTime());
        }
        if (value.getAmount() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAmount());
        }
        if (value.getPhonenumber() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPhonenumber());
        }
        if (value.getFirstname() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFirstname());
        }
        if (value.getSecondname() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getSecondname());
        }
        final int _tmp;
        _tmp = value.isPrintStatus() ? 1 : 0;
        stmt.bindLong(8, _tmp);
      }
    };
    this.__deletionAdapterOfMpesaMessage = new EntityDeletionOrUpdateAdapter<MpesaMessage>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `MpesaMessage` WHERE `transactionId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MpesaMessage value) {
        if (value.getTransactionId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getTransactionId());
        }
      }
    };
    this.__updateAdapterOfMpesaMessage = new EntityDeletionOrUpdateAdapter<MpesaMessage>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `MpesaMessage` SET `transactionId` = ?,`date` = ?,`time` = ?,`amount` = ?,`phonenumber` = ?,`firstname` = ?,`secondname` = ?,`printStatus` = ? WHERE `transactionId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MpesaMessage value) {
        if (value.getTransactionId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getTransactionId());
        }
        if (value.getDate() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDate());
        }
        if (value.getTime() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTime());
        }
        if (value.getAmount() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAmount());
        }
        if (value.getPhonenumber() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPhonenumber());
        }
        if (value.getFirstname() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFirstname());
        }
        if (value.getSecondname() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getSecondname());
        }
        final int _tmp;
        _tmp = value.isPrintStatus() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        if (value.getTransactionId() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getTransactionId());
        }
      }
    };
  }

  @Override
  public long insertAll(final MpesaMessage mpesaMessages) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfMpesaMessage.insertAndReturnId(mpesaMessages);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final MpesaMessage user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMpesaMessage.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void UpdateMpesaPrintStatus(final MpesaMessage mpesaMessage) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMpesaMessage.handle(mpesaMessage);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<MpesaMessage>> getAllMpesaMessages() {
    final String _sql = "SELECT * FROM MpesaMessage";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"MpesaMessage"}, false, new Callable<List<MpesaMessage>>() {
      @Override
      public List<MpesaMessage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "transactionId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfPhonenumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phonenumber");
          final int _cursorIndexOfFirstname = CursorUtil.getColumnIndexOrThrow(_cursor, "firstname");
          final int _cursorIndexOfSecondname = CursorUtil.getColumnIndexOrThrow(_cursor, "secondname");
          final int _cursorIndexOfPrintStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "printStatus");
          final List<MpesaMessage> _result = new ArrayList<MpesaMessage>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MpesaMessage _item;
            _item = new MpesaMessage();
            final String _tmpTransactionId;
            _tmpTransactionId = _cursor.getString(_cursorIndexOfTransactionId);
            _item.setTransactionId(_tmpTransactionId);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            final String _tmpTime;
            _tmpTime = _cursor.getString(_cursorIndexOfTime);
            _item.setTime(_tmpTime);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final String _tmpPhonenumber;
            _tmpPhonenumber = _cursor.getString(_cursorIndexOfPhonenumber);
            _item.setPhonenumber(_tmpPhonenumber);
            final String _tmpFirstname;
            _tmpFirstname = _cursor.getString(_cursorIndexOfFirstname);
            _item.setFirstname(_tmpFirstname);
            final String _tmpSecondname;
            _tmpSecondname = _cursor.getString(_cursorIndexOfSecondname);
            _item.setSecondname(_tmpSecondname);
            final boolean _tmpPrintStatus;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPrintStatus);
            _tmpPrintStatus = _tmp != 0;
            _item.setPrintStatus(_tmpPrintStatus);
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

  @Override
  public LiveData<List<MpesaMessage>> getAllMpesaMessagesByDate(final String date) {
    final String _sql = "SELECT * FROM MpesaMessage WHERE date=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"MpesaMessage"}, false, new Callable<List<MpesaMessage>>() {
      @Override
      public List<MpesaMessage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "transactionId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfPhonenumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phonenumber");
          final int _cursorIndexOfFirstname = CursorUtil.getColumnIndexOrThrow(_cursor, "firstname");
          final int _cursorIndexOfSecondname = CursorUtil.getColumnIndexOrThrow(_cursor, "secondname");
          final int _cursorIndexOfPrintStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "printStatus");
          final List<MpesaMessage> _result = new ArrayList<MpesaMessage>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MpesaMessage _item;
            _item = new MpesaMessage();
            final String _tmpTransactionId;
            _tmpTransactionId = _cursor.getString(_cursorIndexOfTransactionId);
            _item.setTransactionId(_tmpTransactionId);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            final String _tmpTime;
            _tmpTime = _cursor.getString(_cursorIndexOfTime);
            _item.setTime(_tmpTime);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final String _tmpPhonenumber;
            _tmpPhonenumber = _cursor.getString(_cursorIndexOfPhonenumber);
            _item.setPhonenumber(_tmpPhonenumber);
            final String _tmpFirstname;
            _tmpFirstname = _cursor.getString(_cursorIndexOfFirstname);
            _item.setFirstname(_tmpFirstname);
            final String _tmpSecondname;
            _tmpSecondname = _cursor.getString(_cursorIndexOfSecondname);
            _item.setSecondname(_tmpSecondname);
            final boolean _tmpPrintStatus;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPrintStatus);
            _tmpPrintStatus = _tmp != 0;
            _item.setPrintStatus(_tmpPrintStatus);
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
