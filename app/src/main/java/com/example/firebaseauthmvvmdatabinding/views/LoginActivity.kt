package com.example.firebaseauthmvvmdatabinding.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauthmvvmdatabinding.R
import com.example.firebaseauthmvvmdatabinding.databinding.ActivityLoginBinding
import com.example.firebaseauthmvvmdatabinding.repository.Repository
import com.example.firebaseauthmvvmdatabinding.viewmodel.MyViewModel
import com.example.firebaseauthmvvmdatabinding.viewmodel.MyViewModelFactory

class LoginActivity : AppCompatActivity() {
    lateinit var viewModel: MyViewModel
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val repository = Repository()
        val factory = MyViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, factory)[MyViewModel::class.java]
        binding.vModel = viewModel
    }
}