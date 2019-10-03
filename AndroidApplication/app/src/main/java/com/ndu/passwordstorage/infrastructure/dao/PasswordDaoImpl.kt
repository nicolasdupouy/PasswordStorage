package com.ndu.passwordstorage.infrastructure.dao

import com.ndu.passwordstorage.domain.PasswordEntry
import com.ndu.passwordstorage.domain.dao.PasswordDao

class PasswordDaoImpl() :PasswordDao {
    override fun selectAll(): List<PasswordEntry> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(passwordEntry: PasswordEntry): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(passwordEntry: PasswordEntry): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(passwordEntry: PasswordEntry): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}