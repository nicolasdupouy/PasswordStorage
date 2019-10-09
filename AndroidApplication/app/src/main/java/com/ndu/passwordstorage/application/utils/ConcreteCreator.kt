package com.ndu.passwordstorage.application.utils

import android.content.Context
import com.ndu.passwordstorage.domain.dao.PasswordDao
import com.ndu.passwordstorage.infrastructure.dao.PasswordDaoImpl

object ConcreteCreator : Creator {
    override fun getPasswordDao(context: Context): PasswordDao {
        return PasswordDaoImpl(context)
    }
}