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

class MemoActivity: AppCompatActivity() {
    val DISPLAY_MEMO = 1
    val CREATE_MEMO = 2

    internal lateinit var site: EditText
    internal lateinit var login: EditText
    internal lateinit var password: EditText

    var passwordEntry: PasswordEntry? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMemoBinding>(this, R.layout.activity_memo)
        site = binding.site
        login = binding.login
        password = binding.password

        readInfos()
    }

    private fun readInfos() {
        val intent = this.intent
        passwordEntry = PasswordEntry.readInfos(intent)

        this.site.setText(passwordEntry?.site, TextView.BufferType.EDITABLE)
        this.login.setText(passwordEntry?.login, TextView.BufferType.EDITABLE)
        this.password.setText(passwordEntry?.password, TextView.BufferType.EDITABLE)
    }

    fun cancel(view: View) {
        setResult(RESULT_CANCELED)
        finish()
    }

    fun update(view: View) {
        saveMemo()
        val intent = passwordEntry?.giveInfos()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun saveMemo() {
        passwordEntry?.update(
            this.site.text.toString(),
            this.login.text.toString(),
            this.password.text.toString()
        )
    }
}