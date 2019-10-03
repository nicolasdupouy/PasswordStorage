package com.ndu.passwordstorage.domain

data class PasswordEntry(
    val id: Int,
    val site: String,
    val login: String,
    val password: String) {
}