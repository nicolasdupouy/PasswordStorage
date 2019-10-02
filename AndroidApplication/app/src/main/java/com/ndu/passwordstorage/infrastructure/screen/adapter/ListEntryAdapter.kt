package com.ndu.passwordstorage.infrastructure.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ndu.passwordstorage.R

class ListEntryAdapter(private val entryList: List<String>): RecyclerView.Adapter<ListEntryAdapter.ViewHolder>() {
    class ViewHolder(linearLayout: LinearLayout): RecyclerView.ViewHolder(linearLayout) {
        val myTextView: TextView = linearLayout.findViewById<TextView>(R.id.my_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val linearLayout = LayoutInflater.from(parent.context).inflate(R.layout.my_text_view, parent, false) as LinearLayout
        return ViewHolder(linearLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myTextView.text = entryList[position]
    }

    override fun getItemCount(): Int = entryList.size
}