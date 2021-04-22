package com.example.gotukang.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class InitializeFCM: FirebaseMessagingService() {
    private var fcmToken = ""

    init {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {task ->
            if(!task.isSuccessful) {
                return@addOnCompleteListener
            }
            this.fcmToken = task.result ?: ""
        }
    }

    fun getFcmToken(): String{
        return fcmToken
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        this.fcmToken = newToken
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("NOTIFICATION", "Message Notification Body: ${remoteMessage.data["message"]}")
        }
        remoteMessage.notification?.let { notification ->
            Log.d("NOTIFICATION", "Message Notification Body: ${notification.body}")
        }
    }
}