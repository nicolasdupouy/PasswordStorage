package com.ndu.passwordstorage.model

import android.content.Intent

import com.ndu.passwordstorage.data.DataContract

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

class PasswordEntry private constructor() {
    private var id: Int = 0
    var key: String? = null
        private set
    var site: String? = null
        private set
    var login: String? = null
        private set
    var password: String? = null
        private set

    fun update(site: String, login: String, password: String) {
        this.site = site
        this.login = login
        this.password = password
    }

    fun update(passwordEntry: PasswordEntry) {
        this.site = passwordEntry.site
        this.login = passwordEntry.login
        this.password = passwordEntry.password
    }

    fun giveInfos(): Intent {
        val intent = Intent()
        putInfos(intent)

        return intent
    }

    fun putInfos(intent: Intent) {
        intent.putExtra(DataContract.DataEntry.COLUMN_NAME_KEY, this.key)
        intent.putExtra(DataContract.DataEntry.COLUMN_NAME_SITE, this.site)
        intent.putExtra(DataContract.DataEntry.COLUMN_NAME_LOGIN, this.login)
        intent.putExtra(DataContract.DataEntry.COLUMN_NAME_PASSWORD, this.password)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as PasswordEntry?

        if (if (site != null) site != that!!.site else that!!.site != null) return false
        if (if (login != null) login != that.login else that.login != null) return false
        return if (password != null) password == that.password else that.password == null
    }

    override fun hashCode(): Int {
        var result = if (site != null) site!!.hashCode() else 0
        result = 31 * result + if (login != null) login!!.hashCode() else 0
        result = 31 * result + if (password != null) password!!.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return (this.site + "/"
                + this.login + "/"
                + this.password)
    }

    companion object {

        private val random = Random(1)

        operator fun get(id: Int, key: String, site: String, login: String, password: String): PasswordEntry {
            val pe = PasswordEntry()
            pe.id = id
            pe.key = key
            pe.site = site
            pe.login = login
            pe.password = password

            return pe
        }

        operator fun get(passwordEntry: PasswordEntry): PasswordEntry {
            val pe = PasswordEntry()
            pe.id = passwordEntry.id
            pe.key = passwordEntry.key
            pe.site = passwordEntry.site
            pe.login = passwordEntry.login
            pe.password = passwordEntry.password

            return pe
        }

        fun makeNew(site: String, login: String, password: String): PasswordEntry {
            val passwordEntry = PasswordEntry.makeNew()
            passwordEntry.update(site, login, password)

            return passwordEntry
        }

        fun makeNew(): PasswordEntry {
            val passwordEntry = PasswordEntry()
            passwordEntry.key = createKey()

            return passwordEntry
        }

        private fun createKey(): String {
            val locale = Locale("en_US")
            Locale.setDefault(locale)

            val pattern = "yyyy-MM-dd HH:mm:ss Z"
            val formatter = SimpleDateFormat(pattern, locale)
            return formatter.format(Date()) + random.nextInt()
        }

        fun readInfos(intent: Intent): PasswordEntry {
            val passwordEntry = PasswordEntry()
            passwordEntry.key = intent.getStringExtra(DataContract.DataEntry.COLUMN_NAME_KEY)
            passwordEntry.site = intent.getStringExtra(DataContract.DataEntry.COLUMN_NAME_SITE)
            passwordEntry.login = intent.getStringExtra(DataContract.DataEntry.COLUMN_NAME_LOGIN)
            passwordEntry.password = intent.getStringExtra(DataContract.DataEntry.COLUMN_NAME_PASSWORD)

            return passwordEntry
        }
    }
}
