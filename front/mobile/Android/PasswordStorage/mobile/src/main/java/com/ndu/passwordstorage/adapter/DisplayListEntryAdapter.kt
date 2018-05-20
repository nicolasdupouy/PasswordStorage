package com.ndu.passwordstorage.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast

import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.model.PasswordEntry
import com.ndu.passwordstorage.screen.DisplayListActivity

class DisplayListEntryAdapter(private val displayListActivity: DisplayListActivity, passwordEntries: List<PasswordEntry>) : ArrayAdapter<PasswordEntry>(displayListActivity, 0, passwordEntries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: DisplayListEntryViewHolder
        if (convertView != null) {
            viewHolder = convertView.tag as DisplayListEntryViewHolder
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_display_list_entry, parent, false)
            viewHolder = DisplayListEntryViewHolder(convertView!!)
            convertView.tag = viewHolder
        }

        val passwordEntry = getItem(position)
        viewHolder.fill(this.displayListActivity, this, passwordEntry, position)

        return convertView
    }


    fun displayAlert(entryItem: PasswordEntry) {
        val items = arrayOf<CharSequence>("delete", "show password")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose an action")
        builder.setItems(items) { dialog, item -> Toast.makeText(context, items[item].toString() + " " + entryItem.toString(), Toast.LENGTH_SHORT).show() }
        val alert = builder.create()
        alert.show()
    }
}
