package com.ndu.passwordstorage.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.List;

public class DbHelperImpl extends SQLiteOpenHelper implements DbHelper {
    public DbHelperImpl(Context context) {
        super(context, DataContract.DATABASE_NAME, null, DataContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DataContract.DataEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DataContract.DataEntry.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public List<PasswordEntry> getEntries() {
        return null;
    }

    @Override
    public void insertEntry(PasswordEntry passwordEntry) {

    }

    @Override
    public void updateEntry(PasswordEntry passwordEntry) {

    }

    @Override
    public void deleteEntry(PasswordEntry passwordEntry) {

    }
}
