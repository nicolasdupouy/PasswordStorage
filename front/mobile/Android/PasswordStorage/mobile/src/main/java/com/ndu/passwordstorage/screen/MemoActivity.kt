package com.ndu.passwordstorage.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.model.PasswordEntry

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

class MemoActivity : AppCompatActivity() {

    @BindView(R.id.site)
    internal var site: EditText? = null
    @BindView(R.id.login)
    internal var login: EditText? = null
    @BindView(R.id.password)
    internal var password: EditText? = null

    private var passwordEntry: PasswordEntry? = null
    private var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)
        unbinder = ButterKnife.bind(this)

        readInfos()
    }

    private fun readInfos() {
        val intent = this.intent
        passwordEntry = PasswordEntry.readInfos(intent)

        this.site!!.setText(passwordEntry!!.site, TextView.BufferType.EDITABLE)
        this.login!!.setText(passwordEntry!!.login, TextView.BufferType.EDITABLE)
        this.password!!.setText(passwordEntry!!.password, TextView.BufferType.EDITABLE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
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
                this.site!!.text.toString(),
                this.login!!.text.toString(),
                this.password!!.text.toString())
    }

    companion object {

        val DISPLAY_MEMO = 1
        val CREATE_MEMO = 2
    }
}
