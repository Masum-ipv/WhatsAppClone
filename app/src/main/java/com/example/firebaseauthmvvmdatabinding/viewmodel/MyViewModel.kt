package com.example.firebaseauthmvvmdatabinding.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.firebaseauthmvvmdatabinding.model.ChatGroup
import com.example.firebaseauthmvvmdatabinding.model.ChatMessage
import com.example.firebaseauthmvvmdatabinding.repository.Repository

class MyViewModel(private val application: Application, private val repository: Repository) :
    AndroidViewModel(application) {

    fun signUpAnonymousUser() {
        repository.firebaseAnonymousAuth(application)
    }

    fun getCurrentUserId(): String? {
        return repository.getCurrentUserId()
    }

    fun signOut() {
        repository.signOut()
    }

    fun getChatGroupList(): MutableLiveData<List<ChatGroup>> {
        return repository.getChatGroupMutableLiveData()
    }

    fun getMessagesLiveData(groupName: String): MutableLiveData<List<ChatMessage>> {
        return repository.getMessagesLiveData(groupName)
    }

    fun createNewChatGroup(groupName: String) {
        repository.createNewChatGroup(groupName)
    }

    fun sendMessage(message: String, groupName: String) {
        repository.sendMessage(message, groupName)
    }

}