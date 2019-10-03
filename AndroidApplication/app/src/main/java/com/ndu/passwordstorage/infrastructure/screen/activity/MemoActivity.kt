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
    companion object {
        const val DISPLAY_MEMO = 1
        const val CREATE_MEMO = 2
        const val EXCHANGE_DATA = "passwordEntry"
    }

    internal lateinit var site: EditText
    internal lateinit var login: EditText
    internal lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMemoBinding>(this, R.layout.activity_memo)
        site = binding.site
        login = binding.login
        password = binding.password
    }

    fun cancel(view: View) {
        setResult(RESULT_CANCELED)
        finish()
    }

    fun update(view: View) {
        val formDatas = getFormDatas()
        intent.putExtra(EXCHANGE_DATA, PasswordEntry(formDatas))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getFormDatas(): Triple<String, String, String> {
        return Triple(
            this.site.text.toString(),
            this.login.text.toString(),
            this.password.text.toString())
    }
}