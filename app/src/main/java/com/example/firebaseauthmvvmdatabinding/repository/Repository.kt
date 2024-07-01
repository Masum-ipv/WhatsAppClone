package com.example.firebaseauthmvvmdatabinding.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.firebaseauthmvvmdatabinding.model.ChatGroup
import com.example.firebaseauthmvvmdatabinding.model.ChatMessage
import com.example.firebaseauthmvvmdatabinding.views.GroupsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class Repository {
    private var auth = Firebase.auth
    private val chatGroupMutableLiveData: MutableLiveData<List<ChatGroup>> = MutableLiveData()
    private val messageMutableLiveData: MutableLiveData<List<ChatMessage>> = MutableLiveData()
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.reference


    // Authentication
    fun firebaseAnonymousAuth(context: Context) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAGY", "signInAnonymously:success")
                    var intent = Intent(context, GroupsActivity::class.java)
                    //this activity will become the start of a new task on this history stack.
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else {
                    Log.e("TAGY", "signInAnonymously:failure", task.exception)
                }
            }
    }

    // Getting current User Id
    fun getCurrentUserId(): String? {
        return auth.uid
    }

    // Signout User
    fun signOut() {
        auth.signOut()
    }

    // Getting chat group list from Firebase real-time DB
    fun getChatGroupMutableLiveData(): MutableLiveData<List<ChatGroup>> {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var groupList = ArrayList<ChatGroup>()
                for (chatSnapShot in snapshot.children) {
                    val group = ChatGroup(chatSnapShot.key)
                    groupList.add(group)
                }
                chatGroupMutableLiveData.postValue(groupList)
                Log.d("TAGY", "Group Size: " + groupList.size)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAGY", "Group List Database Error: " + error.toException())
            }
        }
        )
        return chatGroupMutableLiveData
    }

    // Getting Messages Live Data
    fun getMessagesLiveData(groupName: String): MutableLiveData<List<ChatMessage>> {
        val groupReference = reference.child(groupName)
        groupReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var messageList = ArrayList<ChatMessage>()
                for (chatSnapShot in snapshot.children) {
                    Log.d("TAGY", "chatSnapShot value: " + chatSnapShot.value)
                    val message: ChatMessage? = chatSnapShot.getValue(ChatMessage::class.java)
                    Log.d("TAGY", "Message: " + message!!.text)
                    messageList.add(message)
                }
                messageMutableLiveData.postValue(messageList)
                Log.d("TAGY", "Message Size: " + messageList.size)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return messageMutableLiveData
    }

    // Add new chat group
    fun createNewChatGroup(groupName: String) {
        reference.child(groupName).setValue(groupName)
    }

    // Send message to chat group
    fun sendMessage(messageText: String, chatGroup: String) {

        var ref: DatabaseReference = database.getReference(chatGroup)

        var chatMessage = ChatMessage(
            FirebaseAuth.getInstance().currentUser!!.uid,
            messageText,
            System.currentTimeMillis()
        )

        val randomKey = ref.push().key.toString()
        ref.child(randomKey).setValue(chatMessage)
    }

}