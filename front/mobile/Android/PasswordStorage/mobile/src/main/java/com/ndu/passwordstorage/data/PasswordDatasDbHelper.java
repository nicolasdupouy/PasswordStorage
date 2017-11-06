package com.ndu.passwordstorage.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PasswordDatasDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PasswordDatas.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PasswordDatasContract.PasswordDataEntry.TABLE_NAME
                    + "("
                    + PasswordDatasContract.PasswordDataEntry._ID + " INTEGER PRIMARY KEY,"
                    + PasswordDatasContract.PasswordDataEntry.COLUMN_NAME_KEY + " INTEGER,"
                    + PasswordDatasContract.PasswordDataEntry.COLUMN_NAME_LOGIN + "TEXT,"
                    + PasswordDatasContract.PasswordDataEntry.COLUMN_NAME_PASSWORD + "TEXT,"
                    + PasswordDatasContract.PasswordDataEntry.COLUMN_NAME_SITE + "TEXT"
                    + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PasswordDatasContract.PasswordDataEntry.TABLE_NAME;

    public PasswordDatasDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
