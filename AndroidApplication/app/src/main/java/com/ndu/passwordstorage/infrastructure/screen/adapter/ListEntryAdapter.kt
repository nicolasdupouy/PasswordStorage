package com.ndu.passwordstorage.infrastructure.screen.adapter

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
    class ViewHolder(private val mainActivity: MainActivity, val listEntryAdapter: ListEntryAdapter, binding: AdapterListEntryBinding): RecyclerView.ViewHolder(binding.root) {
        private val passwordEntryLayout: LinearLayout = binding.passwordEntryLayout
        val siteTextView: TextView = binding.site
        val loginTextView: TextView = binding.login
        val passwordEditText: TextView = binding.password
        private val optionButton: ImageButton = binding.optionButton

        init {
            this.setMainClickListener()
            this.setLongClickListener()
            this.setOptionButtonClickListener()

        }

        private fun setMainClickListener() {
            this.passwordEntryLayout.setOnClickListener { mainActivity.displayMemo(this.adapterPosition) }
        }

        private fun setLongClickListener() {
            this.passwordEntryLayout.setOnLongClickListener { false }
        }

        private fun setOptionButtonClickListener() {
            optionButton.setOnClickListener { v ->
                    Toast.makeText(mainActivity.applicationContext, "setOptionButtonClickListener pos=${this.adapterPosition}", Toast.LENGTH_SHORT).show()
                    val entryItem = listEntryAdapter.entryList.get(this.adapterPosition)
                    mainActivity.displayAlert(entryItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterListEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mainActivity, this, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val passwordEntry = entryList[position]
        holder.siteTextView.text = passwordEntry.site
        holder.loginTextView.text = passwordEntry.login
        holder.passwordEditText.text = passwordEntry.password
    }

    override fun getItemCount(): Int = entryList.size
}