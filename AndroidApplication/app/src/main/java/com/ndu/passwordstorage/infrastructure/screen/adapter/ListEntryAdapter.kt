package com.ndu.passwordstorage.infrastructure.screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ndu.passwordstorage.databinding.AdapterListEntryBinding
import com.ndu.passwordstorage.domain.PasswordEntry

class ListEntryAdapter(private val entryList: List<PasswordEntry>): RecyclerView.Adapter<ListEntryAdapter.ViewHolder>() {
    class ViewHolder(binding: AdapterListEntryBinding): RecyclerView.ViewHolder(binding.root) {
        val siteTextView: TextView = binding.site
        val loginTextView: TextView = binding.login
        val passwordEditText: TextView = binding.password
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterListEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val passwordEntry = entryList[position]
        holder.siteTextView.text = passwordEntry.site
        holder.loginTextView.text = passwordEntry.login
        holder.passwordEditText.text = passwordEntry.password
    }

    override fun getItemCount(): Int = entryList.size
}