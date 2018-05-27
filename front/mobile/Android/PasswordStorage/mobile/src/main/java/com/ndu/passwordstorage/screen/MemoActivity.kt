package com.ndu.passwordstorage.screen

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.model.PasswordEntry

class MemoActivity : AppCompatActivity() {

    private lateinit var site: EditText
    private lateinit  var login: EditText
    private lateinit  var password: EditText

    private var passwordEntry: PasswordEntry? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)
        site = findViewById(R.id.site)
        login = findViewById(R.id.login)
        password = findViewById(R.id.password)

        readInfos()
    }

    private fun readInfos() {
        val intent = this.intent
        passwordEntry = PasswordEntry.readInfos(intent)

        this.site.setText(passwordEntry!!.site, TextView.BufferType.EDITABLE)
        this.login.setText(passwordEntry!!.login, TextView.BufferType.EDITABLE)
        this.password.setText(passwordEntry!!.password, TextView.BufferType.EDITABLE)
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
        val intent = passwordEntry!!.giveInfos()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun saveMemo() {
        passwordEntry!!.update(
                this.site.text.toString(),
                this.login.text.toString(),
                this.password.text.toString())
    }

    companion object {

        val DISPLAY_MEMO = 1
        val CREATE_MEMO = 2
    }
}
