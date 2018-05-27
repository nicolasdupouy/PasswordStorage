package com.ndu.passwordstorage.screen

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.model.PasswordEntry
import kotlinx.android.synthetic.main.activity_memo.*

class MemoActivity : AppCompatActivity() {

    private lateinit var passwordEntry: PasswordEntry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        readInfos()
    }

    private fun readInfos() {
        val intent = this.intent
        passwordEntry = PasswordEntry.readInfos(intent)

        site.setText(passwordEntry.site, TextView.BufferType.EDITABLE)
        login.setText(passwordEntry.login, TextView.BufferType.EDITABLE)
        password.setText(passwordEntry.password, TextView.BufferType.EDITABLE)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun cancel(view: View) {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    fun update(view: View) {
        saveMemo()
        val intent = passwordEntry.giveInfos()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun saveMemo() {
        passwordEntry.update(
                site.text.toString(),
                login.text.toString(),
                password.text.toString())
    }

    companion object {

        val DISPLAY_MEMO = 1
        val CREATE_MEMO = 2
    }
}
