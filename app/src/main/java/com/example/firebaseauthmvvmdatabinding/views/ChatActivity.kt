package com.example.firebaseauthmvvmdatabinding.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauthmvvmdatabinding.R
import com.example.firebaseauthmvvmdatabinding.databinding.ActivityChatBinding
import com.example.firebaseauthmvvmdatabinding.repository.Repository
import com.example.firebaseauthmvvmdatabinding.viewmodel.MyViewModel
import com.example.firebaseauthmvvmdatabinding.viewmodel.MyViewModelFactory
import com.example.firebaseauthmvvmdatabinding.views.adapter.ChatAdapter

class ChatActivity : AppCompatActivity() {
    lateinit var viewModel: MyViewModel
    lateinit var binding: ActivityChatBinding
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)

        val repository = Repository()
        val factory = MyViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, factory)[MyViewModel::class.java]
        binding.myViewModel = viewModel

        //RecyclerView with DataBinding
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Getting the group name from the clicked item in the GroupsActivity
        var groupName = intent.getStringExtra("GROUP_NAME").toString()
        binding.textView.text = groupName
        viewModel.getMessagesLiveData(groupName).observe(this, Observer {
            binding.recyclerView.adapter = ChatAdapter(it)

            //Scroll to the latest message added:
//            var latestPosition = binding.recyclerView.adapter.itemCount -1
            var latestPosition = ChatAdapter(it).itemCount - 1
            if (latestPosition > 0) {
                binding.recyclerView.smoothScrollToPosition(latestPosition)
            }
        })

        binding.button.setOnClickListener {
            var msg = binding.editText.text.toString()
            viewModel.sendMessage(msg, groupName)
            binding.editText.text.clear()
        }
    }
}