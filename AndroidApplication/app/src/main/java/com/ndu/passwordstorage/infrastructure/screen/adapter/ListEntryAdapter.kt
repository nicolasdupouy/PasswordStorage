package com.ndu.passwordstorage.infrastructure.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.domain.PasswordEntry

class ListEntryAdapter(private val entryList: List<PasswordEntry>): RecyclerView.Adapter<ListEntryAdapter.ViewHolder>() {
    class ViewHolder(linearLayout: LinearLayout): RecyclerView.ViewHolder(linearLayout) {
        val siteTextView: TextView = linearLayout.findViewById(R.id.site)
        val loginTextView: TextView = linearLayout.findViewById(R.id.login)
        val passwordEditText: TextView = linearLayout.findViewById(R.id.password)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val linearLayout = LayoutInflater.from(parent.context).inflate(R.layout.adapter_list_entry, parent, false) as LinearLayout
        return ViewHolder(linearLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val passwordEntry = entryList[position]
        holder.siteTextView.text = passwordEntry.site
        holder.loginTextView.text = passwordEntry.login
        holder.passwordEditText.text = passwordEntry.password

    }

    override fun getItemCount(): Int = entryList.size
}