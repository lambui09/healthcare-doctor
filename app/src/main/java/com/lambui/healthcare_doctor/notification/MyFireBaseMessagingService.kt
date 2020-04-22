package com.lambui.healthcare_doctor.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFireBaseMessagingService : FirebaseMessagingService() {
    companion object{
        const val TAG = "FireBaseService"
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("Firebase", token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.i("Firebase ..... ", remoteMessage.data["title"])
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from)
        //check if message contains a data payload
        if (remoteMessage.data.isNotEmpty()){
            Log.d(TAG, "Message data payload: " + remoteMessage.getData())
        }
        //Check if message contains a notification payload.
        if (remoteMessage.notification != null){
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification?.body)
        }
    }
}