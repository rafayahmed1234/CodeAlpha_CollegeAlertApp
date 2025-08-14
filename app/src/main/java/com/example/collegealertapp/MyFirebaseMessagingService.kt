package com.example.collegealertapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCMService"
        private const val CHANNEL_ID = "college_alerts"
        private const val CHANNEL_NAME = "College Alerts"
        private const val CHANNEL_DESCRIPTION = "Notifications for college events and alerts"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
//            handleDataMessage(remoteMessage.data)
        }

        // Check if message contains a notification payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
//            showNotification(
//                title = it.title ?: "College Alert",
//                body = it.body ?: "",
//                data = remoteMessage.data
//            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

        // Send token to your server
        sendRegistrationToServer(token)
    }

//    private fun handleDataMessage(data: Map<String, String>) {
//        val title = data["title"] ?: "College Alert"
//        val body = data["body"] ?: data["message"] ?: ""
//        val type = data["type"] ?: "notice"
//        val eventDate = data["eventDate"] ?: ""
//        val location = data["location"] ?: ""
//        val imageUrl = data["imageUrl"] ?: ""
//        val priority = data["priority"] ?: "normal"
//
//        // Create NotificationItem object
//        val notificationItem = NotificationItem(
//            id = data["id"] ?: System.currentTimeMillis().toString(),
//            title = title,
//            message = body,
//            type = type,
//            timestamp = System.currentTimeMillis(),
//            imageUrl = imageUrl,
//            isRead = false,
//            eventDate = eventDate,
//            location = location,
//            priority = priority,
//            category = data["category"] ?: "",
//            detailsUrl = data["detailsUrl"] ?: ""
//        )
//
//        // Save notification to local storage or database
//        saveNotificationToLocal(notificationItem)
//
//        // Show notification
//        showNotification(title, body, data)
//    }

//    private fun saveNotificationToLocal(notification: NotificationItem) {
//        // Save to SharedPreferences or Room Database
//        val sharedPref = getSharedPreferences("notifications", Context.MODE_PRIVATE)
//        val editor = sharedPref.edit()
//
//        // For simplicity, we'll use SharedPreferences
//        // In production, use Room Database for better data management
//        val notificationsJson = sharedPref.getString("notifications_list", "[]")
//        // Here you would parse JSON, add new notification, and save back
//
//        Log.d(TAG, "Notification saved: ${notification.title}")
//    }

//    private fun showNotification(title: String, body: String, data: Map<String, String>) {
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Create notification channel for Android O and above
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                CHANNEL_NAME,
//                NotificationManager.IMPORTANCE_HIGH
//            ).apply {
//                description = CHANNEL_DESCRIPTION
//                enableLights(true)
//                enableVibration(true)
//            }
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        // Create intent for notification click
//        val intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            // Add extra data if needed
//            data.forEach { (key, value) ->
//                putExtra(key, value)
//            }
//        }
//
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            Random.nextInt(),
//            intent,
//            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        // Build notification
//        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_notification) // Make sure this icon exists
//            .setContentTitle(title)
//            .setContentText(body)
//            .setAutoCancel(true)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setDefaults(NotificationCompat.DEFAULT_ALL)
//            .setContentIntent(pendingIntent)
//            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
//
//        // Show notification
//        notificationManager.notify(Random.nextInt(), notificationBuilder.build())
//    }

    private fun sendRegistrationToServer(token: String) {
        // Send token to your backend server
        Log.d(TAG, "Token sent to server: $token")

        // Here you would make an API call to send token to your server
        // Example:
        // ApiClient.sendTokenToServer(token)
    }
}

