package com.example.firebaseauthmvvmdatabinding.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauthmvvmdatabinding.BR
import com.example.firebaseauthmvvmdatabinding.R
import com.example.firebaseauthmvvmdatabinding.databinding.RowChatBinding
import com.example.firebaseauthmvvmdatabinding.model.ChatMessage

class ChatAdapter(val chatMessageList: List<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RowChatBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_chat,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return chatMessageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Data Binding in Recyclerview
        holder.binding.setVariable(BR.chatMessage, chatMessageList[position])
        holder.binding.executePendingBindings()
    }
}