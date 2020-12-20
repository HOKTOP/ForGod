package com.hok.forgod.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.hok.forgod.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.*
import kotlin.concurrent.timerTask

class activity_splash_screen : AppCompatActivity() {
    var context:Context ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var Sherad = getSharedPreferences("start", Context.MODE_PRIVATE)
        var get = Sherad.getString("start","0")
        if (get.equals("start")){
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }else{

            var anime : Animation
            var logo2anim : Animation
            var logoanim : Animation
            context = this
            anime = AnimationUtils.loadAnimation(this,R.anim.splashanimion)
            background.startAnimation(anime)
            logo2anim = AnimationUtils.loadAnimation(applicationContext,R.anim.logo2)
            logo2.startAnimation(logo2anim)
            logoanim = AnimationUtils.loadAnimation(applicationContext,R.anim.logo)

            logo.startAnimation(logoanim)
            Timer().schedule(timerTask {
                val start = Intent(context,MainActivity::class.java)
                startActivity(start)
            },8000)

        }
    }
}