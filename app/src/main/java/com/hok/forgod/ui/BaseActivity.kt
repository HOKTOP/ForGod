package com.hok.forgod.ui

import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hok.forgod.pojo.Notifiction.MyAppsNotificationManager
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity

open class BaseActivity : LocaleAwareCompatActivity() {
    private var notificatsionManager: MyAppsNotificationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    fun getMyAppsNotificationManager(): MyAppsNotificationManager? {
        return notificatsionManager
    }
}
