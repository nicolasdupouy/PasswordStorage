package com.ndu.passwordstorage.infrastructure.screen.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ndu.passwordstorage.databinding.AdapterListEntryBinding
import com.ndu.passwordstorage.domain.PasswordEntry
import com.ndu.passwordstorage.infrastructure.screen.activity.MainActivity

class ListEntryAdapter(private val mainActivity: MainActivity, private val entryList: List<PasswordEntry>): RecyclerView.Adapter<ListEntryAdapter.ViewHolder>() {
    class ViewHolder(private val mainActivity: MainActivity, binding: AdapterListEntryBinding): RecyclerView.ViewHolder(binding.root) {
        private val passwordEntryLayout: LinearLayout = binding.passwordEntryLayout
        val siteTextView: TextView = binding.site
        val loginTextView: TextView = binding.login
        val passwordEditText: TextView = binding.password

        init {
            this.setMainClickListener()
            this.setLongClickListener()
        }

        private fun setMainClickListener() {
            this.passwordEntryLayout.setOnClickListener { v -> mainActivity.displayMemo(this.adapterPosition) }
        }

        private fun setLongClickListener() {
            this.passwordEntryLayout.setOnLongClickListener { v -> false }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterListEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mainActivity, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val passwordEntry = entryList[position]
        holder.siteTextView.text = passwordEntry.site
        holder.loginTextView.text = passwordEntry.login
        holder.passwordEditText.text = passwordEntry.password
    }

    override fun getItemCount(): Int = entryList.size
}