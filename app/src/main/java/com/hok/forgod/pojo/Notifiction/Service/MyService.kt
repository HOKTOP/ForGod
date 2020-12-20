package com.hok.forgod.pojo.Notifiction.Service

import android.app.Notification.CATEGORY_SERVICE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hok.forgod.R
import com.hok.forgod.pojo.Notifiction.CreateNotification
import com.hok.forgod.pojo.RetrofitServics.serviceRetrofit
import com.hok.forgod.pojo.model.Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MyService : FirebaseMessagingService() {
    val tag = "message reciverd"
    var sharedPreferences:SharedPreferences.Editor?=null
    var dataserviceedit:SharedPreferences.Editor?=null
    var dataservice:SharedPreferences?=null

    override fun onCreate() {
        sharedPreferences = getSharedPreferences("Servicea", MODE_PRIVATE).edit()
        dataserviceedit = getSharedPreferences("Service", MODE_PRIVATE).edit()
        dataservice = getSharedPreferences("Service", MODE_PRIVATE)
        Log.e("serviceee","onCreate")

        super.onCreate()
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        CreateNotification.createNotificationserver(this,p0.notification?.channelId.toString(),p0.notification?.title.toString(),p0.notification?.body.toString())
    }
    override fun onDestroy() {
        sharedPreferences?.putBoolean("servicer",false)?.commit()

        super.onDestroy()
    }
}
