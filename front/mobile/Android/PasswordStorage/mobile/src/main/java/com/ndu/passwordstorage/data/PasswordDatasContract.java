package com.ndu.passwordstorage.data;

import android.provider.BaseColumns;

public final class PasswordDatasContract {

    public static abstract class PasswordDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "password";
        public static final String COLUMN_NAME_KEY = "key";
        public static final String COLUMN_NAME_SITE = "site";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
