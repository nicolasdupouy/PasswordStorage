package com.ndu.passwordstorage.domain.dao

import com.ndu.passwordstorage.domain.PasswordEntry

interface PasswordDao {
    fun selectAll(): List<PasswordEntry>
    fun insert(passwordEntry: PasswordEntry): Boolean
    fun update(passwordEntry: PasswordEntry): Boolean
    fun delete(passwordEntry: PasswordEntry): Boolean
}