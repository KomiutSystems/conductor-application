package com.komiut.conductor.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.komiut.conductor.dao.CashTransactionDao;
import com.komiut.conductor.dao.CashTransactionDao_Impl;
import com.komiut.conductor.dao.MpesaMessageDao;
import com.komiut.conductor.dao.MpesaMessageDao_Impl;
import com.komiut.conductor.dao.OfflineUserDataDao;
import com.komiut.conductor.dao.OfflineUserDataDao_Impl;
import com.komiut.conductor.dao.SubRoutesDao;
import com.komiut.conductor.dao.SubRoutesDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile MpesaMessageDao _mpesaMessageDao;

  private volatile CashTransactionDao _cashTransactionDao;

  private volatile OfflineUserDataDao _offlineUserDataDao;

  private volatile SubRoutesDao _subRoutesDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(7) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `MpesaMessage` (`transactionId` TEXT NOT NULL, `date` TEXT, `time` TEXT, `amount` TEXT, `phonenumber` TEXT, `firstname` TEXT, `secondname` TEXT, `printStatus` INTEGER NOT NULL, PRIMARY KEY(`transactionId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CashTransaction` (`user_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `selectedDepart` TEXT, `selectedDest` TEXT, `regno` TEXT, `passname` TEXT, `passphone` TEXT, `nopass` TEXT, `amount` TEXT, `luggage` TEXT, `amtGiven` TEXT, `stringTotal` TEXT, `stringChange` TEXT, `date` TEXT, `offlineDataAccessDate` TEXT, `uniqueID` TEXT, `cashId` TEXT, `status` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `OfflineUser` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_email` TEXT, `user_name` TEXT, `user_phone` TEXT, `user_plate` TEXT, `fingerprint` BLOB)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `SubRoutesResponse` (`id` INTEGER NOT NULL, `substage` TEXT, `departure` TEXT, `destination` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c06d0978a3ebff35ab60faadfb361704')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `MpesaMessage`");
        _db.execSQL("DROP TABLE IF EXISTS `CashTransaction`");
        _db.execSQL("DROP TABLE IF EXISTS `OfflineUser`");
        _db.execSQL("DROP TABLE IF EXISTS `SubRoutesResponse`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsMpesaMessage = new HashMap<String, TableInfo.Column>(8);
        _columnsMpesaMessage.put("transactionId", new TableInfo.Column("transactionId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMpesaMessage.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMpesaMessage.put("time", new TableInfo.Column("time", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMpesaMessage.put("amount", new TableInfo.Column("amount", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMpesaMessage.put("phonenumber", new TableInfo.Column("phonenumber", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMpesaMessage.put("firstname", new TableInfo.Column("firstname", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMpesaMessage.put("secondname", new TableInfo.Column("secondname", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMpesaMessage.put("printStatus", new TableInfo.Column("printStatus", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMpesaMessage = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMpesaMessage = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMpesaMessage = new TableInfo("MpesaMessage", _columnsMpesaMessage, _foreignKeysMpesaMessage, _indicesMpesaMessage);
        final TableInfo _existingMpesaMessage = TableInfo.read(_db, "MpesaMessage");
        if (! _infoMpesaMessage.equals(_existingMpesaMessage)) {
          return new RoomOpenHelper.ValidationResult(false, "MpesaMessage(com.komiut.conductor.model.MpesaMessage).\n"
                  + " Expected:\n" + _infoMpesaMessage + "\n"
                  + " Found:\n" + _existingMpesaMessage);
        }
        final HashMap<String, TableInfo.Column> _columnsCashTransaction = new HashMap<String, TableInfo.Column>(17);
        _columnsCashTransaction.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("selectedDepart", new TableInfo.Column("selectedDepart", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("selectedDest", new TableInfo.Column("selectedDest", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("regno", new TableInfo.Column("regno", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("passname", new TableInfo.Column("passname", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("passphone", new TableInfo.Column("passphone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("nopass", new TableInfo.Column("nopass", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("amount", new TableInfo.Column("amount", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("luggage", new TableInfo.Column("luggage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("amtGiven", new TableInfo.Column("amtGiven", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("stringTotal", new TableInfo.Column("stringTotal", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("stringChange", new TableInfo.Column("stringChange", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("offlineDataAccessDate", new TableInfo.Column("offlineDataAccessDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("uniqueID", new TableInfo.Column("uniqueID", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("cashId", new TableInfo.Column("cashId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCashTransaction.put("status", new TableInfo.Column("status", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCashTransaction = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCashTransaction = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCashTransaction = new TableInfo("CashTransaction", _columnsCashTransaction, _foreignKeysCashTransaction, _indicesCashTransaction);
        final TableInfo _existingCashTransaction = TableInfo.read(_db, "CashTransaction");
        if (! _infoCashTransaction.equals(_existingCashTransaction)) {
          return new RoomOpenHelper.ValidationResult(false, "CashTransaction(com.komiut.conductor.model.CashTransaction).\n"
                  + " Expected:\n" + _infoCashTransaction + "\n"
                  + " Found:\n" + _existingCashTransaction);
        }
        final HashMap<String, TableInfo.Column> _columnsOfflineUser = new HashMap<String, TableInfo.Column>(6);
        _columnsOfflineUser.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOfflineUser.put("user_email", new TableInfo.Column("user_email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOfflineUser.put("user_name", new TableInfo.Column("user_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOfflineUser.put("user_phone", new TableInfo.Column("user_phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOfflineUser.put("user_plate", new TableInfo.Column("user_plate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOfflineUser.put("fingerprint", new TableInfo.Column("fingerprint", "BLOB", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOfflineUser = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOfflineUser = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOfflineUser = new TableInfo("OfflineUser", _columnsOfflineUser, _foreignKeysOfflineUser, _indicesOfflineUser);
        final TableInfo _existingOfflineUser = TableInfo.read(_db, "OfflineUser");
        if (! _infoOfflineUser.equals(_existingOfflineUser)) {
          return new RoomOpenHelper.ValidationResult(false, "OfflineUser(com.komiut.conductor.model.OfflineUser).\n"
                  + " Expected:\n" + _infoOfflineUser + "\n"
                  + " Found:\n" + _existingOfflineUser);
        }
        final HashMap<String, TableInfo.Column> _columnsSubRoutesResponse = new HashMap<String, TableInfo.Column>(4);
        _columnsSubRoutesResponse.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubRoutesResponse.put("substage", new TableInfo.Column("substage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubRoutesResponse.put("departure", new TableInfo.Column("departure", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubRoutesResponse.put("destination", new TableInfo.Column("destination", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubRoutesResponse = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubRoutesResponse = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubRoutesResponse = new TableInfo("SubRoutesResponse", _columnsSubRoutesResponse, _foreignKeysSubRoutesResponse, _indicesSubRoutesResponse);
        final TableInfo _existingSubRoutesResponse = TableInfo.read(_db, "SubRoutesResponse");
        if (! _infoSubRoutesResponse.equals(_existingSubRoutesResponse)) {
          return new RoomOpenHelper.ValidationResult(false, "SubRoutesResponse(com.komiut.conductor.retrofit.SubRoutesResponse).\n"
                  + " Expected:\n" + _infoSubRoutesResponse + "\n"
                  + " Found:\n" + _existingSubRoutesResponse);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "c06d0978a3ebff35ab60faadfb361704", "981d0c34648ab3332be2d292463a200c");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "MpesaMessage","CashTransaction","OfflineUser","SubRoutesResponse");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `MpesaMessage`");
      _db.execSQL("DELETE FROM `CashTransaction`");
      _db.execSQL("DELETE FROM `OfflineUser`");
      _db.execSQL("DELETE FROM `SubRoutesResponse`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public MpesaMessageDao mpesaMessageDao() {
    if (_mpesaMessageDao != null) {
      return _mpesaMessageDao;
    } else {
      synchronized(this) {
        if(_mpesaMessageDao == null) {
          _mpesaMessageDao = new MpesaMessageDao_Impl(this);
        }
        return _mpesaMessageDao;
      }
    }
  }

  @Override
  public CashTransactionDao cashTransactionDao() {
    if (_cashTransactionDao != null) {
      return _cashTransactionDao;
    } else {
      synchronized(this) {
        if(_cashTransactionDao == null) {
          _cashTransactionDao = new CashTransactionDao_Impl(this);
        }
        return _cashTransactionDao;
      }
    }
  }

  @Override
  public OfflineUserDataDao offlineUserDataDao() {
    if (_offlineUserDataDao != null) {
      return _offlineUserDataDao;
    } else {
      synchronized(this) {
        if(_offlineUserDataDao == null) {
          _offlineUserDataDao = new OfflineUserDataDao_Impl(this);
        }
        return _offlineUserDataDao;
      }
    }
  }

  @Override
  public SubRoutesDao subRoutesDao() {
    if (_subRoutesDao != null) {
      return _subRoutesDao;
    } else {
      synchronized(this) {
        if(_subRoutesDao == null) {
          _subRoutesDao = new SubRoutesDao_Impl(this);
        }
        return _subRoutesDao;
      }
    }
  }
}
