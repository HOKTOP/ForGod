package com.hok.forgod.pojo.Notifiction

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.hok.forgod.R
import com.hok.forgod.pojo.Notifiction.Service.NotificationActionService
import com.hok.forgod.pojo.model.Traklist
import com.squareup.picasso.Picasso


object CreateNotification {
    const val CHANNEL_ID = "channel1"
    const val CHANNEL_ID_2 = "channel2"
    const val ACTION_PREVIUOS = "actionprevious"
    const val ACTION_PLAY = "actionplay"
    const val ACTION_NEXT = "actionnext"
    const val ACTION_STOP = "actionstop"
    var notification: Notification? = null

    @JvmStatic


    fun createNotification(context: Context, track: Traklist, playbutton: Int, pos: Int, size: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            val mediaSessionCompat = MediaSessionCompat(context, "tag")
            val icon = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
            val pendingIntentPrevious: PendingIntent?
            val drw_previous: Int
            if (pos == 0) {
                pendingIntentPrevious = null
                drw_previous = 0
            } else {
                val intentPrevious = Intent(context, NotificationActionService::class.java)
                    .setAction(ACTION_PREVIUOS)
                pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                    intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT)
                drw_previous = R.drawable.ic_skip_previous_black_24dp
            }
            val intentPlay = Intent(context, NotificationActionService::class.java)
                .setAction(ACTION_PLAY)
            val pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                intentPlay, PendingIntent.FLAG_UPDATE_CURRENT)
            val pendingIntentNext: PendingIntent?
            val drw_next: Int
            if (pos == size) {
                pendingIntentNext = null
                drw_next = 0
            } else {
                val intentNext = Intent(context, NotificationActionService::class.java)
                    .setAction(ACTION_NEXT)
                pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                    intentNext, PendingIntent.FLAG_UPDATE_CURRENT)
                drw_next = R.drawable.ic_skip_next_black_24dp
            }

            //create notification
            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_note)
                .setContentTitle(track.nameSoura)
                .setContentText(track.nameReciter)
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true) //show notification for only first time
                .setShowWhen(false)
                .addAction(drw_previous, "Previous", pendingIntentPrevious)
                .addAction(playbutton, "Play", pendingIntentPlay)
                .addAction(drw_next, "Next", pendingIntentNext)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mediaSessionCompat.sessionToken))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            notificationManagerCompat.notify(1, notification!!)
        }
    }
    @JvmStatic
    fun createNotificationdown(context: Context, name:String,stuts:String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            val mediaSessionCompat = MediaSessionCompat(context, "tag")
            val icon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_download_white)
            val pendingIntentPrevious: PendingIntent?
            val drw_previous: Int

            //create notification
            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_dwonload)
                .setContentTitle("Download..$name")
                .setContentText(stuts)
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true) //show notification for only first time
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            notificationManagerCompat.notify(1, notification!!)
            sound(context)
        }
    }

    fun createNotificationserver(context: Context, id:String,title:String,bady:String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            var id = 1911


            //create notification
            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("$title")
                .setContentText(bady)
                .setOnlyAlertOnce(true) //show notification for only first time
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            notificationManagerCompat.notify(1, notification!!)
            sound(context)
        }
    }

    private fun sound(context: Context) {

        try {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val  r = RingtoneManager.getRingtone(context,alarmSound)
            r.play()
        }catch (e:Exception){

        }
    }


}