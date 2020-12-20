package com.hok.forgod.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hok.forgod.R
import java.util.*

class ismaaallh : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var settingapp = Settingapp()
        if (settingapp.themestting(null,this)){
            setTheme(R.style.AppTheme)
        }else{
            setTheme(R.style.AppThemee)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ismaaallh)
    }
}