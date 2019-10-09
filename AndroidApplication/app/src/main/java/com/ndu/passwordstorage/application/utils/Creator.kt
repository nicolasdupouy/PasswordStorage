package com.ndu.passwordstorage.application.utils

import android.content.Context
import com.ndu.passwordstorage.domain.dao.PasswordDao

interface Creator {
    fun getPasswordDao(context: Context): PasswordDao
}