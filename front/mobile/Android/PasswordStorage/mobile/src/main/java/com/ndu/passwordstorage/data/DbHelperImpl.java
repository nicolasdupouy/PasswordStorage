package com.ndu.passwordstorage.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.ArrayList;
import java.util.Collections;
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
        List<PasswordEntry> entries = new ArrayList<>();

        SQLiteDatabase readableDatabase = getReadableDatabase();

        String[] columns = {
                DataContract.DataEntry._ID,
                DataContract.DataEntry.COLUMN_NAME_KEY,
                DataContract.DataEntry.COLUMN_NAME_LOGIN,
                DataContract.DataEntry.COLUMN_NAME_PASSWORD,
                DataContract.DataEntry.COLUMN_NAME_SITE
        };

        Cursor cursor = readableDatabase.query(
                DataContract.DataEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DataContract.DataEntry._ID));
            String key = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_NAME_KEY));
            String site = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_NAME_SITE));
            String login = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_NAME_LOGIN));
            String password = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_NAME_PASSWORD));

            entries.add(PasswordEntry.get(id, key, site, login, password));
        }
        cursor.close();

        return entries;
    }

    @Override
    public boolean insertEntry(PasswordEntry passwordEntry) {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataContract.DataEntry.COLUMN_NAME_KEY, passwordEntry.getKey());
        values.put(DataContract.DataEntry.COLUMN_NAME_SITE, passwordEntry.getSite());
        values.put(DataContract.DataEntry.COLUMN_NAME_LOGIN, passwordEntry.getLogin());
        values.put(DataContract.DataEntry.COLUMN_NAME_PASSWORD, passwordEntry.getPassword());

        long insert = writableDatabase.insert(
                DataContract.DataEntry.TABLE_NAME,
                null,
                values);
        return insert != -1;
    }

    @Override
    public boolean updateEntry(PasswordEntry passwordEntry) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataContract.DataEntry.COLUMN_NAME_SITE, passwordEntry.getSite());
        values.put(DataContract.DataEntry.COLUMN_NAME_LOGIN, passwordEntry.getLogin());
        values.put(DataContract.DataEntry.COLUMN_NAME_PASSWORD, passwordEntry.getPassword());

        String selection = DataContract.DataEntry.COLUMN_NAME_KEY + " = ?";
        String[] selectionArgs = {passwordEntry.getKey()};

        int update = db.update(
                DataContract.DataEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return update == 1;
    }

    @Override
    public boolean deleteEntry(PasswordEntry passwordEntry) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = DataContract.DataEntry.COLUMN_NAME_KEY + " = ?";
        String[] selectionArgs = {passwordEntry.getKey()};

        int delete = db.delete(DataContract.DataEntry.TABLE_NAME, selection, selectionArgs);

        return delete == 1;
    }
}
