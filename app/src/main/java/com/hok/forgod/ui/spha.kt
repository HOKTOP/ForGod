package com.hok.forgod.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hok.forgod.R
import kotlinx.android.synthetic.main.activity_spha.*
import java.util.*

class spha : BaseActivity() {

    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        var settingapp = Settingapp()
        if (settingapp.themestting(null,this)){
            setTheme(R.style.AppTheme)
        }else{
            setTheme(R.style.AppThemee)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spha)
        counter.setOnClickListener {
            counter_text.setText("${count++}")
        }
        reset.setOnClickListener {
            count = 0

            counter_text.setText("$count")


        }
    }
}