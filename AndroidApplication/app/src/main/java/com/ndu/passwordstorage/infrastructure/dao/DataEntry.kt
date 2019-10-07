package com.ndu.passwordstorage.infrastructure.dao

import android.provider.BaseColumns

object DataEntry: BaseColumns {
    internal const val TABLE_NAME = "password"
    const val COLUMN_NAME_ID = "id"
    const val COLUMN_NAME_SITE = "site"
    const val COLUMN_NAME_LOGIN = "login"
    const val COLUMN_NAME_PASSWORD = "password"

    private val SQL_CREATE_CONSTRAINT = (" CONSTRAINT constraint_name UNIQUE ("
            + COLUMN_NAME_LOGIN
            + ", "
            + COLUMN_NAME_PASSWORD
            + ", "
            + COLUMN_NAME_SITE
            + ")")

    internal val SQL_CREATE_ENTRIES = (
            "CREATE TABLE " + TABLE_NAME
                    + "("
                    + COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_NAME_LOGIN + " TEXT NOT NULL, "
                    + COLUMN_NAME_PASSWORD + " TEXT NOT NULL, "
                    + COLUMN_NAME_SITE + " TEXT NOT NULL, "
                    + SQL_CREATE_CONSTRAINT
                    + ")")

    internal val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}