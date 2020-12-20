package com.hok.forgod.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hok.forgod.R
import java.util.*

class ZakatCalculator : BaseActivity() {
   lateinit var ToUnderline: TextView
    lateinit    var GoldPrice: EditText
    lateinit  var TotalPrice:EditText
    lateinit var Result1: TextView
    lateinit var Result2:TextView
    var InputGold = 0f
    var InputTotal:kotlin.Float = 0f
    var k = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        var settingapp = Settingapp()
        if (settingapp.themestting(null,this)){
            setTheme(R.style.AppTheme)
        }else{
            setTheme(R.style.AppThemee)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zakat_calculator)
        ToUnderline = findViewById(R.id.underlinelink)
        ToUnderline.paintFlags = ToUnderline.paintFlags or Paint.UNDERLINE_TEXT_FLAG

    }
    fun LocalPriceGold(v: View?) {
        val intent = Intent(this, GoldPrice::class.java)
        startActivity(intent)
    }

    fun ZakatInfo(v: View?) {
        k = 1
        setContentView(R.layout.zakatinfo)
    }

    @SuppressLint("SetTextI18n")
    fun CalculateZakat(v: View?) {
        GoldPrice = findViewById(R.id.inputgoldprice)
        TotalPrice = findViewById(R.id.inputtotalprice)
        Result1 = findViewById(R.id.zakatresult1)
        Result2 = findViewById(R.id.zakatresult2)
        if (GoldPrice.text.toString() == "" || TotalPrice.text
                .toString() == ""
        ) return
        InputGold = GoldPrice.text.toString().toFloat()
        InputTotal = TotalPrice.text.toString().toFloat()
        val nissab = InputGold * 85.05
        val result = getString(R.string.nissab) + " " + nissab
        val zakat: String
        if (nissab > InputTotal) {
            zakat = getString(R.string.nozakat)
            Result1.text = zakat
            Result2.text = result
        } else {
            val zakatprice = (InputTotal * 0.025).toFloat()
            zakat = getString(R.string.zakatprice) + " " + zakatprice
            Result1.text = zakat
            Result2.text = result
        }
    }

    override fun onBackPressed() {
        if (k == 1) {
            k = 0
            //super.onBackPressed();
            setContentView(R.layout.activity_zakat_calculator)
        } else {
            super.onBackPressed()
        }
    }
}