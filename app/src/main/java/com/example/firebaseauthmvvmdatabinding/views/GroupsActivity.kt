package com.example.firebaseauthmvvmdatabinding.views

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauthmvvmdatabinding.R
import com.example.firebaseauthmvvmdatabinding.databinding.ActivityGroupsBinding
import com.example.firebaseauthmvvmdatabinding.repository.Repository
import com.example.firebaseauthmvvmdatabinding.viewmodel.MyViewModel
import com.example.firebaseauthmvvmdatabinding.viewmodel.MyViewModelFactory
import com.example.firebaseauthmvvmdatabinding.views.adapter.GroupAdapter

class GroupsActivity : AppCompatActivity() {
    lateinit var viewModel: MyViewModel
    lateinit var binding: ActivityGroupsBinding
    lateinit var recyclerView: RecyclerView
    lateinit var groupCreateDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_groups)

        val repository = Repository()
        val factory = MyViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, factory)[MyViewModel::class.java]
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Setup an observer to listen for changes in a "Live Data" object
        viewModel.getChatGroupList().observe(this, Observer {
            binding.recyclerView.adapter = GroupAdapter(it)
        })
    }

    fun showDialog() {
        groupCreateDialog = Dialog(this)
        groupCreateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val view = LayoutInflater.from(this)
            .inflate(R.layout.group_create_dialog, null)
        groupCreateDialog.setContentView(view)
        groupCreateDialog.show()

        val submit: Button = view.findViewById(R.id.button)
        val editText: EditText = view.findViewById(R.id.editText)
        submit.setOnClickListener {
            val groupName = editText.text.toString()
            viewModel.createNewChatGroup(groupName)
            groupCreateDialog.dismiss()
        }
    }
}