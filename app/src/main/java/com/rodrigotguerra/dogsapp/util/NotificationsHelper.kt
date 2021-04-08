package com.rodrigotguerra.dogsapp.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rodrigotguerra.dogsapp.R
import com.rodrigotguerra.dogsapp.view.MainActivity

class NotificationsHelper(val context: Context) {

    companion object {
        private const val CHANNEL_ID = "Dogs Channel id"
        private const val CHANNEL_DESCRIPTION = "Channel description"
        private const val NOTIFICATION_ID = 321
        private const val INTENT_REQUEST_CODE = 0
    }

    fun createNotification() {
        createNotificationChannel()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, INTENT_REQUEST_CODE, intent, 0)
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.dog_transparent)
        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(R.drawable.dog_icon)
                .setLargeIcon(icon).setContentTitle("Dogs retrieved")
                .setContentText("This notification has some content")
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(icon).bigLargeIcon(null))
                .setContentIntent(pendingIntent).setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}