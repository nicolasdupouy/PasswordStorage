package com.ndu.passwordstorage.data

import android.provider.BaseColumns

object DataContract {

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "PasswordDatas.db"

    object DataEntry : BaseColumns {
        const internal val TABLE_NAME = "password"
        const internal val COLUMN_NAME_KEY = "key"
        const internal val COLUMN_NAME_SITE = "site"
        const internal val COLUMN_NAME_LOGIN = "login"
        const internal val COLUMN_NAME_PASSWORD = "password"

        const internal val SQL_CREATE_CONSTRAINT = """" CONSTRAINT constraint_name UNIQUE ("
                DataEntry.COLUMN_NAME_LOGIN
                ", "
                DataEntry.COLUMN_NAME_PASSWORD
                ", "
                DataEntry.COLUMN_NAME_SITE)"""

        const internal val SQL_CREATE_ENTRIES = (
                "CREATE TABLE " + DataEntry.TABLE_NAME
                        + "("
                        + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                        + DataEntry.COLUMN_NAME_KEY + " TEXT NOT NULL, "
                        + DataEntry.COLUMN_NAME_LOGIN + " TEXT NOT NULL, "
                        + DataEntry.COLUMN_NAME_PASSWORD + " TEXT NOT NULL, "
                        + DataEntry.COLUMN_NAME_SITE + " TEXT NOT NULL, "
                        + SQL_CREATE_CONSTRAINT
                        + ")")

        const internal val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME
    }
}
