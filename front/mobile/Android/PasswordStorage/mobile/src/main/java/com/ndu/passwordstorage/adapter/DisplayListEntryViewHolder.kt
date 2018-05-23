package com.ndu.passwordstorage.adapter

import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.model.PasswordEntry
import com.ndu.passwordstorage.screen.DisplayListActivity

class DisplayListEntryViewHolder internal constructor(convertView: View) {
    private val linearLayout: ConstraintLayout
    private val site: TextView
    private val login: TextView
    private val password: EditText
    private val optionButton: ImageButton
    private var position: Int = 0

    init {
        this.linearLayout = convertView.findViewById(R.id.passwordEntryLayout)
        this.site = convertView.findViewById(R.id.site)
        this.login = convertView.findViewById(R.id.login)
        this.password = convertView.findViewById(R.id.password)
        optionButton = convertView.findViewById(R.id.optionButton)
    }

    internal fun fill(displayListActivity: DisplayListActivity, displayListEntryAdapter: DisplayListEntryAdapter, passwordEntry: PasswordEntry, position: Int) {
        this.position = position
        this.fillFields(passwordEntry)
        this.setMainClickListener(displayListActivity)
        this.setLongClickListener()
        this.setOptionButtonClickListener(displayListEntryAdapter)
    }

    private fun fillFields(passwordEntry: PasswordEntry) {
        this.site.setText(passwordEntry.site, TextView.BufferType.NORMAL)
        this.login.setText(passwordEntry.login, TextView.BufferType.NORMAL)
        this.password.setText(passwordEntry.password, TextView.BufferType.NORMAL)
    }

    private fun setMainClickListener(displayListActivity: DisplayListActivity) {
        this.linearLayout.setOnClickListener { v -> displayListActivity.displayMemo(this.position) }
    }

    private fun setLongClickListener() {
        this.linearLayout.setOnLongClickListener { v -> false }
    }

    private fun setOptionButtonClickListener(displayListEntryAdapter: DisplayListEntryAdapter) {
        optionButton.setOnClickListener { v ->
            val entryItem = displayListEntryAdapter.getItem(position)
            displayListEntryAdapter.displayAlert(entryItem!!)
        }
    }
}
