package com.hok.forgod.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hok.forgod.R
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import kotlinx.android.synthetic.main.activity_settingapp.*
import org.intellij.lang.annotations.Language
import java.util.*


class Settingapp : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (themestting(null,this)){
            setTheme(R.style.AppTheme)
        }else{
            setTheme(R.style.AppThemee)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settingapp)
        languages.setOnClickListener(this)
        if (themestting(null,this)){
            switchnightmode.isChecked =true
        }else{
            switchnightmode.isChecked =false
        }
        switchnightmode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                themestting(true,this)
            }else{
                themestting(false,this)

            }
        }
    }


    public fun themestting(boolean: Boolean?=false,context: Context):Boolean{
        var data = context.getSharedPreferences("Theme",MODE_PRIVATE).edit()
        if (boolean==null){
            var data2 = context.getSharedPreferences("Theme", MODE_PRIVATE)
            return data2.getBoolean("darkmode",false)
        }else{
            data.putBoolean("darkmode",boolean!!)
            data.commit()
            restartApp()
        }
        var data2 = context.getSharedPreferences("Theme", MODE_PRIVATE)
        return data2.getBoolean("darkmode",false)

    }
    private fun restartApp() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val mPendingIntentId: Int = 1
        val mPendingIntent = PendingIntent.getActivity(
            applicationContext,
            mPendingIntentId,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val mgr =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr[AlarmManager.RTC, System.currentTimeMillis() + 100] = mPendingIntent
        System.exit(0)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.languages ->{
                var languageRange = AlertDialog.Builder(this)
                var View = layoutInflater.inflate(R.layout.dlglanguages,null)
                var arrido = View.findViewById<RadioButton>(R.id.ar_radioButton)
                var enrido = View.findViewById<RadioButton>(R.id.en_radioButton)
                var grouprido = View.findViewById<RadioGroup>(R.id.radioButtongroup)
                if (selectlanguages(this).equals("ar" +
                            "")){
                    arrido.isChecked =true
                }else{
                    enrido.isChecked =true

                }
                grouprido.setOnCheckedChangeListener { group, checkedId ->
                    when(checkedId){
                        R.id.ar_radioButton ->{
                            selectlanguages(this,"ar")
                            updateLocale(Locale("ar"))

                        }
                        R.id.en_radioButton ->{
                            selectlanguages(this,"en")
                            updateLocale(Locale("en"))



                        }
                    }

                }
                languageRange.setView(View)
                var alertDialog:AlertDialog = languageRange.create()
                alertDialog.show()


            }
        }
    }

    fun selectlanguages(context: Context,languagecode:String?=null) :String{
        var sharedPreferences = context.getSharedPreferences("languages", MODE_PRIVATE)
        if (languagecode != null){
            var edits = sharedPreferences.edit()
            edits.putString("language",languagecode)
            edits.commit()
        }
        if (Locale.getDefault()!=Locale("ar")){
            return   sharedPreferences.getString("language","en")!!
        }else{
            return sharedPreferences.getString("language","ar")!!
        }
    }

}