package com.example.demo

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM", "From: ${remoteMessage.from}")

        remoteMessage.notification?.let {
            Log.d("FCM", "Title: ${it.title}")
            Log.d("FCM", "Body: ${it.body}")
        }

        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FCM", "Data: ${remoteMessage.data}")
        }
        showNotification("hello",remoteMessage.toString())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCM", "New Token: $token")

        // Send token to your backend if required
    }

    private fun showNotification(title: String, body: String) {

        val channelId = "default_channel"

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentText(body)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(body)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()



}

}