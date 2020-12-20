package com.hok.forgod.pojo.Notifiction

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MyAppsNotificationManager private constructor(context: Context) {
    private val context: Context
    private val notificationManagerCompat: NotificationManagerCompat
    private val notificationManager: NotificationManager
    fun registerNotificationChannelChannel(
        channelId: String?,
        channelName: String?,
        channelDescription: String?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = channelDescription
            val notificationManager: NotificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun getNotification(
        targetNotificationActivity: Class<*>?,
        title: String?,
        priority: Int,
        autoCancel: Boolean,
        notificationId: Int
    ): Notification {
        val intent = Intent(context, targetNotificationActivity)
        val pendingIntent = PendingIntent.getActivity(context, 1, intent, 0)
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context,"hokid")
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.ic_btn_speak_now
                    )
                )
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId("123")
                .setAutoCancel(true)
        return builder.build()
    }

    fun cancelNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }

    companion object {
        private var instance: MyAppsNotificationManager? = null
        fun getInstance(context: Context): MyAppsNotificationManager? {
            if (instance == null) {
                instance = MyAppsNotificationManager(context)
            }
            return instance
        }
    }

    init {
        this.context = context
        notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}
