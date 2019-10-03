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
    constructor(t: Triple<String, String, String>): this(-1, t.first, t.second, t.third)

}