package com.example.firebaseauthmvvmdatabinding.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseauthmvvmdatabinding.repository.Repository

class MyViewModelFactory(private val application: Application, private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}