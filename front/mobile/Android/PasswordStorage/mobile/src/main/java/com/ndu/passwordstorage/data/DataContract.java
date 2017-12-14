package com.ndu.passwordstorage.data;

import android.provider.BaseColumns;

public final class DataContract {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "PasswordDatas.db";

    public static abstract class DataEntry implements BaseColumns {
        static final String TABLE_NAME = "password";
        public static final String COLUMN_NAME_KEY = "key";
        public static final String COLUMN_NAME_SITE = "site";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_PASSWORD = "password";

        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DataContract.DataEntry.TABLE_NAME
                        + "("
                        + DataContract.DataEntry._ID + " INTEGER PRIMARY KEY,"
                        + DataContract.DataEntry.COLUMN_NAME_KEY + " TEXT,"
                        + DataContract.DataEntry.COLUMN_NAME_LOGIN + " TEXT,"
                        + DataContract.DataEntry.COLUMN_NAME_PASSWORD + " TEXT,"
                        + DataContract.DataEntry.COLUMN_NAME_SITE + " TEXT"
                        + ")";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DataContract.DataEntry.TABLE_NAME;
    }
}
