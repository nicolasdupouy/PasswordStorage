package com.ndu.passwordstorage.domain

import android.content.Intent
import com.ndu.passwordstorage.infrastructure.dao.DataEntry

data class PasswordEntry(
    val id: Int,
    var site: String,
    var login: String,
    var password: String
) {
    fun giveInfos(): Intent {
        val intent = Intent()
        putInfos(intent)

        return intent
    }

    fun putInfos(intent: Intent) {
        intent.putExtra(DataEntry.COLUMN_NAME_ID, this.id)
        intent.putExtra(DataEntry.COLUMN_NAME_SITE, this.site)
        intent.putExtra(DataEntry.COLUMN_NAME_LOGIN, this.login)
        intent.putExtra(DataEntry.COLUMN_NAME_PASSWORD, this.password)
    }

    fun update(site: String, login: String, password: String) {
        this.site = site
        this.login = login
        this.password = password
    }

    /*fun update(passwordEntry: PasswordEntry) {
        this.site = passwordEntry.site
        this.login = passwordEntry.login
        this.password = passwordEntry.password
    }*/

    companion object {
        fun readInfos(intent: Intent?): PasswordEntry? {
            intent?.let {
                return PasswordEntry(
                    intent.getIntExtra(DataEntry.COLUMN_NAME_ID, -1),
                    intent.getStringExtra(DataEntry.COLUMN_NAME_SITE),
                    intent.getStringExtra(DataEntry.COLUMN_NAME_LOGIN),
                    intent.getStringExtra(DataEntry.COLUMN_NAME_PASSWORD)
                )
            }
            return null
        }
    }
}