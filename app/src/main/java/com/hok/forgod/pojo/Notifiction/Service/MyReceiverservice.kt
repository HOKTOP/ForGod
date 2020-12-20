package com.hok.forgod.pojo.Notifiction.Service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.hok.forgod.pojo.Notifiction.CreateNotification
import com.hok.forgod.ui.MainActivity


class MyReceiverservice :   BroadcastReceiver() {
    var TAG = "BroadcastRSSSSeceiver"
    override fun onReceive(context: Context, intent: Intent) {
        Log.e(TAG, "start broadcast")
        context.startService(Intent(context,MyService::class.java))

    }

    }
