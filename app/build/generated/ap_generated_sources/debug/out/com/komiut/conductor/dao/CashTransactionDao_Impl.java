package com.komiut.conductor.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.komiut.conductor.model.CashTransaction;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CashTransactionDao_Impl implements CashTransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CashTransaction> __insertionAdapterOfCashTransaction;

  public CashTransactionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCashTransaction = new EntityInsertionAdapter<CashTransaction>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `CashTransaction` (`user_id`,`selectedDepart`,`selectedDest`,`regno`,`passname`,`passphone`,`nopass`,`amount`,`luggage`,`amtGiven`,`stringTotal`,`stringChange`,`date`,`offlineDataAccessDate`,`uniqueID`,`cashId`,`status`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CashTransaction value) {
        stmt.bindLong(1, value.getUser_id());
        if (value.getSelectedDepart() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getSelectedDepart());
        }
        if (value.getSelectedDest() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSelectedDest());
        }
        if (value.getRegno() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRegno());
        }
        if (value.getPassname() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPassname());
        }
        if (value.getPassphone() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPassphone());
        }
        if (value.getNopass() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getNopass());
        }
        if (value.getAmount() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getAmount());
        }
        if (value.getLuggage() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getLuggage());
        }
        if (value.getAmtGiven() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getAmtGiven());
        }
        if (value.getStringTotal() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getStringTotal());
        }
        if (value.getStringChange() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getStringChange());
        }
        if (value.getDate() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getDate());
        }
        if (value.getOfflineDataAccessDate() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getOfflineDataAccessDate());
        }
        if (value.getUniqueID() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getUniqueID());
        }
        if (value.getCashId() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getCashId());
        }
        final int _tmp;
        _tmp = value.isStatus() ? 1 : 0;
        stmt.bindLong(17, _tmp);
      }
    };
  }

  @Override
  public void saveTransaction(final CashTransaction cashTransaction) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCashTransaction.insert(cashTransaction);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<CashTransaction>> getAllCashTransactionNotSynced(final boolean status) {
    final String _sql = "SELECT * FROM CashTransaction WHERE status=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final int _tmp;
    _tmp = status ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    return __db.getInvalidationTracker().createLiveData(new String[]{"CashTransaction"}, false, new Callable<List<CashTransaction>>() {
      @Override
      public List<CashTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfSelectedDepart = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedDepart");
          final int _cursorIndexOfSelectedDest = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedDest");
          final int _cursorIndexOfRegno = CursorUtil.getColumnIndexOrThrow(_cursor, "regno");
          final int _cursorIndexOfPassname = CursorUtil.getColumnIndexOrThrow(_cursor, "passname");
          final int _cursorIndexOfPassphone = CursorUtil.getColumnIndexOrThrow(_cursor, "passphone");
          final int _cursorIndexOfNopass = CursorUtil.getColumnIndexOrThrow(_cursor, "nopass");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfLuggage = CursorUtil.getColumnIndexOrThrow(_cursor, "luggage");
          final int _cursorIndexOfAmtGiven = CursorUtil.getColumnIndexOrThrow(_cursor, "amtGiven");
          final int _cursorIndexOfStringTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "stringTotal");
          final int _cursorIndexOfStringChange = CursorUtil.getColumnIndexOrThrow(_cursor, "stringChange");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfOfflineDataAccessDate = CursorUtil.getColumnIndexOrThrow(_cursor, "offlineDataAccessDate");
          final int _cursorIndexOfUniqueID = CursorUtil.getColumnIndexOrThrow(_cursor, "uniqueID");
          final int _cursorIndexOfCashId = CursorUtil.getColumnIndexOrThrow(_cursor, "cashId");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<CashTransaction> _result = new ArrayList<CashTransaction>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CashTransaction _item;
            _item = new CashTransaction();
            final int _tmpUser_id;
            _tmpUser_id = _cursor.getInt(_cursorIndexOfUserId);
            _item.setUser_id(_tmpUser_id);
            final String _tmpSelectedDepart;
            _tmpSelectedDepart = _cursor.getString(_cursorIndexOfSelectedDepart);
            _item.setSelectedDepart(_tmpSelectedDepart);
            final String _tmpSelectedDest;
            _tmpSelectedDest = _cursor.getString(_cursorIndexOfSelectedDest);
            _item.setSelectedDest(_tmpSelectedDest);
            final String _tmpRegno;
            _tmpRegno = _cursor.getString(_cursorIndexOfRegno);
            _item.setRegno(_tmpRegno);
            final String _tmpPassname;
            _tmpPassname = _cursor.getString(_cursorIndexOfPassname);
            _item.setPassname(_tmpPassname);
            final String _tmpPassphone;
            _tmpPassphone = _cursor.getString(_cursorIndexOfPassphone);
            _item.setPassphone(_tmpPassphone);
            final String _tmpNopass;
            _tmpNopass = _cursor.getString(_cursorIndexOfNopass);
            _item.setNopass(_tmpNopass);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final String _tmpLuggage;
            _tmpLuggage = _cursor.getString(_cursorIndexOfLuggage);
            _item.setLuggage(_tmpLuggage);
            final String _tmpAmtGiven;
            _tmpAmtGiven = _cursor.getString(_cursorIndexOfAmtGiven);
            _item.setAmtGiven(_tmpAmtGiven);
            final String _tmpStringTotal;
            _tmpStringTotal = _cursor.getString(_cursorIndexOfStringTotal);
            _item.setStringTotal(_tmpStringTotal);
            final String _tmpStringChange;
            _tmpStringChange = _cursor.getString(_cursorIndexOfStringChange);
            _item.setStringChange(_tmpStringChange);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            final String _tmpOfflineDataAccessDate;
            _tmpOfflineDataAccessDate = _cursor.getString(_cursorIndexOfOfflineDataAccessDate);
            _item.setOfflineDataAccessDate(_tmpOfflineDataAccessDate);
            final String _tmpUniqueID;
            _tmpUniqueID = _cursor.getString(_cursorIndexOfUniqueID);
            _item.setUniqueID(_tmpUniqueID);
            final String _tmpCashId;
            _tmpCashId = _cursor.getString(_cursorIndexOfCashId);
            _item.setCashId(_tmpCashId);
            final boolean _tmpStatus;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfStatus);
            _tmpStatus = _tmp_1 != 0;
            _item.setStatus(_tmpStatus);
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
  public LiveData<List<CashTransaction>> getAllCashTransaction() {
    final String _sql = "SELECT * FROM CashTransaction";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"CashTransaction"}, false, new Callable<List<CashTransaction>>() {
      @Override
      public List<CashTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfSelectedDepart = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedDepart");
          final int _cursorIndexOfSelectedDest = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedDest");
          final int _cursorIndexOfRegno = CursorUtil.getColumnIndexOrThrow(_cursor, "regno");
          final int _cursorIndexOfPassname = CursorUtil.getColumnIndexOrThrow(_cursor, "passname");
          final int _cursorIndexOfPassphone = CursorUtil.getColumnIndexOrThrow(_cursor, "passphone");
          final int _cursorIndexOfNopass = CursorUtil.getColumnIndexOrThrow(_cursor, "nopass");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfLuggage = CursorUtil.getColumnIndexOrThrow(_cursor, "luggage");
          final int _cursorIndexOfAmtGiven = CursorUtil.getColumnIndexOrThrow(_cursor, "amtGiven");
          final int _cursorIndexOfStringTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "stringTotal");
          final int _cursorIndexOfStringChange = CursorUtil.getColumnIndexOrThrow(_cursor, "stringChange");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfOfflineDataAccessDate = CursorUtil.getColumnIndexOrThrow(_cursor, "offlineDataAccessDate");
          final int _cursorIndexOfUniqueID = CursorUtil.getColumnIndexOrThrow(_cursor, "uniqueID");
          final int _cursorIndexOfCashId = CursorUtil.getColumnIndexOrThrow(_cursor, "cashId");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<CashTransaction> _result = new ArrayList<CashTransaction>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CashTransaction _item;
            _item = new CashTransaction();
            final int _tmpUser_id;
            _tmpUser_id = _cursor.getInt(_cursorIndexOfUserId);
            _item.setUser_id(_tmpUser_id);
            final String _tmpSelectedDepart;
            _tmpSelectedDepart = _cursor.getString(_cursorIndexOfSelectedDepart);
            _item.setSelectedDepart(_tmpSelectedDepart);
            final String _tmpSelectedDest;
            _tmpSelectedDest = _cursor.getString(_cursorIndexOfSelectedDest);
            _item.setSelectedDest(_tmpSelectedDest);
            final String _tmpRegno;
            _tmpRegno = _cursor.getString(_cursorIndexOfRegno);
            _item.setRegno(_tmpRegno);
            final String _tmpPassname;
            _tmpPassname = _cursor.getString(_cursorIndexOfPassname);
            _item.setPassname(_tmpPassname);
            final String _tmpPassphone;
            _tmpPassphone = _cursor.getString(_cursorIndexOfPassphone);
            _item.setPassphone(_tmpPassphone);
            final String _tmpNopass;
            _tmpNopass = _cursor.getString(_cursorIndexOfNopass);
            _item.setNopass(_tmpNopass);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final String _tmpLuggage;
            _tmpLuggage = _cursor.getString(_cursorIndexOfLuggage);
            _item.setLuggage(_tmpLuggage);
            final String _tmpAmtGiven;
            _tmpAmtGiven = _cursor.getString(_cursorIndexOfAmtGiven);
            _item.setAmtGiven(_tmpAmtGiven);
            final String _tmpStringTotal;
            _tmpStringTotal = _cursor.getString(_cursorIndexOfStringTotal);
            _item.setStringTotal(_tmpStringTotal);
            final String _tmpStringChange;
            _tmpStringChange = _cursor.getString(_cursorIndexOfStringChange);
            _item.setStringChange(_tmpStringChange);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            final String _tmpOfflineDataAccessDate;
            _tmpOfflineDataAccessDate = _cursor.getString(_cursorIndexOfOfflineDataAccessDate);
            _item.setOfflineDataAccessDate(_tmpOfflineDataAccessDate);
            final String _tmpUniqueID;
            _tmpUniqueID = _cursor.getString(_cursorIndexOfUniqueID);
            _item.setUniqueID(_tmpUniqueID);
            final String _tmpCashId;
            _tmpCashId = _cursor.getString(_cursorIndexOfCashId);
            _item.setCashId(_tmpCashId);
            final boolean _tmpStatus;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfStatus);
            _tmpStatus = _tmp != 0;
            _item.setStatus(_tmpStatus);
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
  public LiveData<List<CashTransaction>> getCashMessagesByDate(final String date) {
    final String _sql = "SELECT * FROM CashTransaction WHERE offlineDataAccessDate=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"CashTransaction"}, false, new Callable<List<CashTransaction>>() {
      @Override
      public List<CashTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfSelectedDepart = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedDepart");
          final int _cursorIndexOfSelectedDest = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedDest");
          final int _cursorIndexOfRegno = CursorUtil.getColumnIndexOrThrow(_cursor, "regno");
          final int _cursorIndexOfPassname = CursorUtil.getColumnIndexOrThrow(_cursor, "passname");
          final int _cursorIndexOfPassphone = CursorUtil.getColumnIndexOrThrow(_cursor, "passphone");
          final int _cursorIndexOfNopass = CursorUtil.getColumnIndexOrThrow(_cursor, "nopass");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfLuggage = CursorUtil.getColumnIndexOrThrow(_cursor, "luggage");
          final int _cursorIndexOfAmtGiven = CursorUtil.getColumnIndexOrThrow(_cursor, "amtGiven");
          final int _cursorIndexOfStringTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "stringTotal");
          final int _cursorIndexOfStringChange = CursorUtil.getColumnIndexOrThrow(_cursor, "stringChange");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfOfflineDataAccessDate = CursorUtil.getColumnIndexOrThrow(_cursor, "offlineDataAccessDate");
          final int _cursorIndexOfUniqueID = CursorUtil.getColumnIndexOrThrow(_cursor, "uniqueID");
          final int _cursorIndexOfCashId = CursorUtil.getColumnIndexOrThrow(_cursor, "cashId");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<CashTransaction> _result = new ArrayList<CashTransaction>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CashTransaction _item;
            _item = new CashTransaction();
            final int _tmpUser_id;
            _tmpUser_id = _cursor.getInt(_cursorIndexOfUserId);
            _item.setUser_id(_tmpUser_id);
            final String _tmpSelectedDepart;
            _tmpSelectedDepart = _cursor.getString(_cursorIndexOfSelectedDepart);
            _item.setSelectedDepart(_tmpSelectedDepart);
            final String _tmpSelectedDest;
            _tmpSelectedDest = _cursor.getString(_cursorIndexOfSelectedDest);
            _item.setSelectedDest(_tmpSelectedDest);
            final String _tmpRegno;
            _tmpRegno = _cursor.getString(_cursorIndexOfRegno);
            _item.setRegno(_tmpRegno);
            final String _tmpPassname;
            _tmpPassname = _cursor.getString(_cursorIndexOfPassname);
            _item.setPassname(_tmpPassname);
            final String _tmpPassphone;
            _tmpPassphone = _cursor.getString(_cursorIndexOfPassphone);
            _item.setPassphone(_tmpPassphone);
            final String _tmpNopass;
            _tmpNopass = _cursor.getString(_cursorIndexOfNopass);
            _item.setNopass(_tmpNopass);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            _item.setAmount(_tmpAmount);
            final String _tmpLuggage;
            _tmpLuggage = _cursor.getString(_cursorIndexOfLuggage);
            _item.setLuggage(_tmpLuggage);
            final String _tmpAmtGiven;
            _tmpAmtGiven = _cursor.getString(_cursorIndexOfAmtGiven);
            _item.setAmtGiven(_tmpAmtGiven);
            final String _tmpStringTotal;
            _tmpStringTotal = _cursor.getString(_cursorIndexOfStringTotal);
            _item.setStringTotal(_tmpStringTotal);
            final String _tmpStringChange;
            _tmpStringChange = _cursor.getString(_cursorIndexOfStringChange);
            _item.setStringChange(_tmpStringChange);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            final String _tmpOfflineDataAccessDate;
            _tmpOfflineDataAccessDate = _cursor.getString(_cursorIndexOfOfflineDataAccessDate);
            _item.setOfflineDataAccessDate(_tmpOfflineDataAccessDate);
            final String _tmpUniqueID;
            _tmpUniqueID = _cursor.getString(_cursorIndexOfUniqueID);
            _item.setUniqueID(_tmpUniqueID);
            final String _tmpCashId;
            _tmpCashId = _cursor.getString(_cursorIndexOfCashId);
            _item.setCashId(_tmpCashId);
            final boolean _tmpStatus;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfStatus);
            _tmpStatus = _tmp != 0;
            _item.setStatus(_tmpStatus);
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
