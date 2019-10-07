package com.ndu.passwordstorage.infrastructure.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ndu.passwordstorage.domain.PasswordEntry
import com.ndu.passwordstorage.domain.dao.PasswordDao
import java.util.ArrayList

class PasswordDaoImpl(context: Context) :
    SQLiteOpenHelper(context, DataContract.DATABASE_NAME, null, DataContract.DATABASE_VERSION),
    PasswordDao {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.execSQL(DataEntry.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase?.execSQL(DataEntry.SQL_DELETE_ENTRIES)
        onCreate(sqLiteDatabase)
    }

    override fun selectAll(): List<PasswordEntry> {
        val entries = ArrayList<PasswordEntry>()

        val readableDatabase = readableDatabase

        val columns = arrayOf(
            DataEntry.COLUMN_NAME_ID,
            DataEntry.COLUMN_NAME_LOGIN,
            DataEntry.COLUMN_NAME_PASSWORD,
            DataEntry.COLUMN_NAME_SITE
        )

        val cursor = readableDatabase.query(
            DataEntry.TABLE_NAME,
            columns,
            null, null, null, null, null
        )
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DataEntry.COLUMN_NAME_ID))
            val site = cursor.getString(cursor.getColumnIndex(DataEntry.COLUMN_NAME_SITE))
            val login = cursor.getString(cursor.getColumnIndex(DataEntry.COLUMN_NAME_LOGIN))
            val password =cursor.getString(cursor.getColumnIndex(DataEntry.COLUMN_NAME_PASSWORD))

            entries.add(PasswordEntry(id, site, login, password))
        }
        cursor.close()

        return entries
    }

    override fun insert(passwordEntry: PasswordEntry): Boolean {
        val writableDatabase = writableDatabase

        val values = ContentValues()
        values.put(DataEntry.COLUMN_NAME_SITE, passwordEntry.site)
        values.put(DataEntry.COLUMN_NAME_LOGIN, passwordEntry.login)
        values.put(DataEntry.COLUMN_NAME_PASSWORD, passwordEntry.password)

        val insert = writableDatabase.insert(
            DataEntry.TABLE_NAME,
            null,
            values
        )
        return insert.toInt() != -1
    }

    override fun update(passwordEntry: PasswordEntry): Boolean {
        val values = ContentValues()
        values.put(DataEntry.COLUMN_NAME_SITE, passwordEntry.site)
        values.put(DataEntry.COLUMN_NAME_LOGIN, passwordEntry.login)
        values.put(DataEntry.COLUMN_NAME_PASSWORD, passwordEntry.password)

        val selection = DataEntry.COLUMN_NAME_ID + " = ?"
        val selectionArgs = arrayOf(passwordEntry.id.toString())

        val update = writableDatabase.update(
            DataEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )

        return update == 1
    }

    override fun delete(passwordEntry: PasswordEntry): Boolean {
        val selection = DataEntry.COLUMN_NAME_ID + " = ?"
        val selectionArgs = arrayOf(passwordEntry.id.toString())

        val delete = writableDatabase.delete(DataEntry.TABLE_NAME, selection, selectionArgs)

        return delete == 1
    }
}