package com.example.firebaseauthmvvmdatabinding.views.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauthmvvmdatabinding.R
import com.example.firebaseauthmvvmdatabinding.databinding.ItemCardBinding
import com.example.firebaseauthmvvmdatabinding.model.ChatGroup
import com.example.firebaseauthmvvmdatabinding.views.ChatActivity

class GroupAdapter(val chatGroupList: List<ChatGroup>) :
    RecyclerView.Adapter<GroupAdapter.MyViewHolder>() {


    class MyViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(group: ChatGroup) {
            binding.groupNameTV.text = group.groupName

            binding.root.setOnClickListener {
                val position = adapterPosition
                Log.d("TAGY", "Chat Group: $position ${group.groupName}")

                var intent = Intent(it.context, ChatActivity::class.java)
                intent.putExtra("GROUP_NAME", group.groupName)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_card,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return chatGroupList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(chatGroupList[position])
    }

}