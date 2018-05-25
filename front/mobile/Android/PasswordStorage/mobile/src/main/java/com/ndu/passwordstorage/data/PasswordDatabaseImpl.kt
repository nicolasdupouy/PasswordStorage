package com.ndu.passwordstorage.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

import com.ndu.passwordstorage.model.PasswordEntry

import java.util.ArrayList

class PasswordDatabaseImpl(context: Context) : SQLiteOpenHelper(context, DataContract.DATABASE_NAME, null, DataContract.DATABASE_VERSION), PasswordDatabase {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(DataContract.DataEntry.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL(DataContract.DataEntry.SQL_DELETE_ENTRIES)
        onCreate(sqLiteDatabase)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    override fun select(): List<PasswordEntry> {
        val entries = ArrayList<PasswordEntry>()

        val readableDatabase = readableDatabase

        val columns = arrayOf(BaseColumns._ID, DataContract.DataEntry.COLUMN_NAME_KEY, DataContract.DataEntry.COLUMN_NAME_LOGIN, DataContract.DataEntry.COLUMN_NAME_PASSWORD, DataContract.DataEntry.COLUMN_NAME_SITE)

        val cursor = readableDatabase.query(
                DataContract.DataEntry.TABLE_NAME,
                columns,
                null, null, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val key = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_NAME_KEY))
            val site = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_NAME_SITE))
            val login = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_NAME_LOGIN))
            val password = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_NAME_PASSWORD))

            entries.add(PasswordEntry[id, key, site, login, password])
        }
        cursor.close()

        return entries
    }

    override fun insert(passwordEntry: PasswordEntry): Boolean {
        val writableDatabase = writableDatabase

        val values = ContentValues()
        values.put(DataContract.DataEntry.COLUMN_NAME_KEY, passwordEntry.key)
        values.put(DataContract.DataEntry.COLUMN_NAME_SITE, passwordEntry.site)
        values.put(DataContract.DataEntry.COLUMN_NAME_LOGIN, passwordEntry.login)
        values.put(DataContract.DataEntry.COLUMN_NAME_PASSWORD, passwordEntry.password)

        val insert = writableDatabase.insert(
                DataContract.DataEntry.TABLE_NAME, null,
                values)
        return insert != -1.toLong()
    }

    override fun update(passwordEntry: PasswordEntry): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put(DataContract.DataEntry.COLUMN_NAME_SITE, passwordEntry.site)
        values.put(DataContract.DataEntry.COLUMN_NAME_LOGIN, passwordEntry.login)
        values.put(DataContract.DataEntry.COLUMN_NAME_PASSWORD, passwordEntry.password)

        val selection = DataContract.DataEntry.COLUMN_NAME_KEY + " = ?"
        val selectionArgs = arrayOf<String>(passwordEntry.key)

        val update = db.update(
                DataContract.DataEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs)

        return update == 1
    }

    override fun delete(passwordEntry: PasswordEntry): Boolean {
        val db = writableDatabase

        val selection = DataContract.DataEntry.COLUMN_NAME_KEY + " = ?"
        val selectionArgs = arrayOf<String>(passwordEntry.key)

        val delete = db.delete(DataContract.DataEntry.TABLE_NAME, selection, selectionArgs)

        return delete == 1
    }

    override fun closeDatabase() {
        close()
    }
}
