package com.ndu.passwordstorage.infrastructure.screen.extention

import android.content.Intent
import com.ndu.passwordstorage.domain.PasswordEntry
import com.ndu.passwordstorage.infrastructure.screen.activity.MemoActivity

fun Intent.writePasswordEntry(passwordEntry: PasswordEntry) {
    putExtra(MemoActivity.EXCHANGE_DATA, passwordEntry)
}

fun Intent.readPasswordEntry(): PasswordEntry? {
    return getParcelableExtra(MemoActivity.EXCHANGE_DATA)
}