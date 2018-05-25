package com.ndu.passwordstorage.data

import com.ndu.passwordstorage.model.PasswordEntry

interface PasswordDatabase {
    fun select(): List<PasswordEntry>
    fun insert(passwordEntry: PasswordEntry): Boolean
    fun update(passwordEntry: PasswordEntry): Boolean
    fun delete(passwordEntry: PasswordEntry): Boolean
    fun closeDatabase()
}
