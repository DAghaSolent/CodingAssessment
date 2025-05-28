package com.example.codingassessment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextAdapter(var msgText: List<String>, var timestampText: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewMessage = itemView.findViewById(R.id.messageText) as TextView
        val textViewTimestamp = itemView.findViewById(R.id.timestampText) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedLayout = layoutInflater.inflate(R.layout.item_text_layout, parent, false)
        return TextViewHolder(inflatedLayout)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, index: Int) {

        val textViewHolder = holder as TextViewHolder

        textViewHolder.textViewMessage.text = msgText[index]
        textViewHolder.textViewTimestamp.text = timestampText[index]
    }

    override fun getItemCount(): Int {
        return timestampText.size
    }
}