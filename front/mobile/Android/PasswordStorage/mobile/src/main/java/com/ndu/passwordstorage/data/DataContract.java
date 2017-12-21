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

        private static final String SQL_CREATE_CONSTRAINT = " CONSTRAINT constraint_name UNIQUE ("
                + DataEntry.COLUMN_NAME_LOGIN
                + ", "
                + DataEntry.COLUMN_NAME_PASSWORD
                + ", "
                + DataEntry.COLUMN_NAME_SITE
                + ")";

        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DataEntry.TABLE_NAME
                        + "("
                        + DataEntry._ID + " INTEGER PRIMARY KEY,"
                        + DataEntry.COLUMN_NAME_KEY + " TEXT NOT NULL, "
                        + DataEntry.COLUMN_NAME_LOGIN + " TEXT NOT NULL, "
                        + DataEntry.COLUMN_NAME_PASSWORD + " TEXT NOT NULL, "
                        + DataEntry.COLUMN_NAME_SITE + " TEXT NOT NULL, "
                        + SQL_CREATE_CONSTRAINT
                        + ")";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME;
    }
}
