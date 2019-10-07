package com.ndu.passwordstorage.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PasswordEntry(
    val id: Int,
    var site: String,
    var login: String,
    var password: String
): Parcelable {
    constructor(t: Triple<String, String, String>): this(UNDEFINED_ID, t.first, t.second, t.third)

    companion object {
        const val UNDEFINED_ID = -1
    }
}

