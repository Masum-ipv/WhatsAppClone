package com.example.firebaseauthmvvmdatabinding.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

data class ChatMessage(
    var currentUserId: String? = "",
    var text: String? = "",
    var time: Long? = 0
) {

    fun checkIsMine(): Boolean {
        var loggedInUser: String? = FirebaseAuth.getInstance().currentUser?.uid
        Log.d("TAGY", "currentUserId $currentUserId")
        Log.d("TAGY", "loggedInUser $loggedInUser")
        Log.d("TAGY", "text $text")
        Log.d("TAGY", "time $time")

        if (currentUserId.equals(loggedInUser)) {
            Log.d("TAGY", "Is Mine ture")
            return true
        }
        Log.d("TAGY", "Is Mine false")
        return false
    }

    fun convertTime(): String {
        val sdf = SimpleDateFormat("HH:mm")
        val date = Date(time!!)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}