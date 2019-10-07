package com.ndu.passwordstorage.infrastructure.screen.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.databinding.ActivityMemoBinding
import com.ndu.passwordstorage.domain.PasswordEntry
import com.ndu.passwordstorage.infrastructure.screen.extention.readPasswordEntry
import com.ndu.passwordstorage.infrastructure.screen.extention.writePasswordEntry

class MemoActivity: AppCompatActivity() {
    companion object {
        const val DISPLAY_MEMO = 1
        const val CREATE_MEMO = 2
        const val EXCHANGE_DATA = "passwordEntry"
    }

    private var currentId: Int = PasswordEntry.UNDEFINED_ID
    internal lateinit var site: EditText
    internal lateinit var login: EditText
    internal lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMemoBinding>(this, R.layout.activity_memo)
        site = binding.site
        login = binding.login
        password = binding.password

        readInfos()
    }

    private fun readInfos() {
        val passwordEntry = this.intent.readPasswordEntry()
        passwordEntry?.let {
            currentId = passwordEntry.id
            displayGivenEntry(passwordEntry)
        }
    }

    private fun displayGivenEntry(passwordEntry: PasswordEntry) {
        this.site.setText(passwordEntry.site, TextView.BufferType.EDITABLE)
        this.login.setText(passwordEntry.login, TextView.BufferType.EDITABLE)
        this.password.setText(passwordEntry.password, TextView.BufferType.EDITABLE)
    }

    fun cancel(view: View) {
        setResult(RESULT_CANCELED)
        finish()
    }

    fun update(view: View) {
        intent.writePasswordEntry(getCurrentVersion())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getCurrentVersion(): PasswordEntry {
        val formDatas = getFormDatas()
        if (currentId != PasswordEntry.UNDEFINED_ID) {
            return PasswordEntry(currentId, formDatas.first, formDatas.second, formDatas.third)
        } else {
            return PasswordEntry(formDatas)
        }
    }

    private fun getFormDatas(): Triple<String, String, String> {
        return Triple(
            this.site.text.toString(),
            this.login.text.toString(),
            this.password.text.toString())
    }
}