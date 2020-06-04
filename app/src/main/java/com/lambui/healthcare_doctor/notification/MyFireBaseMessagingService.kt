package com.lambui.healthcare_doctor.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.EXTRA_HAS_NOTIFICATION
import com.lambui.healthcare_doctor.data.source.repositories.TokenRepository
import com.lambui.healthcare_doctor.ui.main.MainActivity
import com.lambui.healthcare_doctor.ui.splash.SplashActivity
import kotlinx.coroutines.channels.Channel
import org.koin.android.ext.android.inject


class MyFireBaseMessagingService : FirebaseMessagingService() {
    private val tokenRepository: TokenRepository by inject()

    companion object {
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
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData())
        }
        //Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification?.body)
        }
        if (tokenRepository.isHasLogIn()) {
            super.onMessageReceived(remoteMessage)
            sendMessage(remoteMessage)
        }
    }

    private fun sendMessage(remoteMessage: RemoteMessage) {
        Log.d("#####", ">>>>>>>>>>>>>send notification<<<<<<<<<<<")
        val intent: Intent = if (tokenRepository.isHasLogIn()) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, SplashActivity::class.java)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra(EXTRA_HAS_NOTIFICATION, true)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_logo)
            .setColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
            .setContentTitle(remoteMessage.data["title"])
            .setContentText(remoteMessage.data["body"])
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            Log.d("#####", "Send notification Android SDK > 26")
            Log.d("#####", "bug bug bug")
            val channel = NotificationChannel(
                "default_channel_id", "Default channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(
            System.currentTimeMillis().toInt()/* ID of notification */,
            notificationBuilder.build()
        )
    }
}