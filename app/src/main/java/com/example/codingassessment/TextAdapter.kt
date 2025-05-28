package com.example.codingassessment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codingassessment.databinding.ItemTextLayoutBinding

class TextAdapter(var msgText: List<String>, var timestampText: List<String>) :
    RecyclerView.Adapter<TextAdapter.TextViewHolder>() {

    inner class TextViewHolder(val binding: ItemTextLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTextLayoutBinding.inflate(layoutInflater, parent, false)
        return TextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextViewHolder, index: Int) {
        holder.binding.message = msgText[index]
        holder.binding.timestamp = timestampText[index]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return timestampText.size
    }
}