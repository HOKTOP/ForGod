package com.hok.forgod.pojo.Notifiction.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hok.forgod.pojo.Notifiction.CreateNotification

class NotificationActionService : BroadcastReceiver() {
    lateinit  var Bundl :Bundle
    var TAG = "oldNOtification"
    override fun onReceive(context: Context, intent: Intent) {
        context.sendBroadcast(Intent("TRACKS_TRACKS")
            .putExtra("actionname", intent.action))

    }
}