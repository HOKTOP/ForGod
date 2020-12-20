package com.hok.forgod.pojo.Notifiction.Service

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log


class NotificationServicechecker():Service() {


    override fun onCreate() {
        Log.e("serviceee","onCreate servicer num1")
        super.onCreate()
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        object : CountDownTimer(100000, 4000) {
            override fun onTick(millisUntilFinished: Long) {
                sendBroadcast(Intent(this@NotificationServicechecker,MyReceiverservice::class.java))
            }

            override fun onFinish() {}
        }.start()

        return START_NOT_STICKY
    }

}