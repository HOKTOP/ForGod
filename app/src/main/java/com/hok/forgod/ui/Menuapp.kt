package com.hok.forgod.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hok.forgod.R
import com.hok.forgod.pojo.model.Timings
import com.hok.forgod.ui.mvactivity.MVMenuapp
import kotlinx.android.synthetic.main.activity_menuapp.*
import kotlinx.coroutines.Runnable
import java.io.IOException


class Menuapp : AppCompatActivity(),LocationListener {

    var permissioncode= 151
    lateinit var locationManager: LocationManager
    lateinit var location: Location
    lateinit var mv:MVMenuapp
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuapp)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mv = ViewModelProvider(this).get(MVMenuapp::class.java)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),permissioncode)
            return
        }
        if(checkHighAccuracyLocationMode(this)){
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
            onLocationChanged(location!!)
        }else {
            Toast.makeText(this,"Please turn on locate",Toast.LENGTH_SHORT).show()
        }


        mv.salattimes.observe(this, Observer {
            updatetime(it)
        })
    }


    private fun updatetime(it: Timings?) {
        fajr.text = it?.fajr
        duhur.text = it?.dhuhr
        asr.text = it?.asr
        maghrib.text = it?.maghrib
        isha.text = it?.isha
    }



    fun checkHighAccuracyLocationMode(context: Context): Boolean {
        var locationMode = 0
        val locationProviders: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Equal or higher than API 19/KitKat
            try {
                locationMode = Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.LOCATION_MODE
                )
                if (locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                    return true
                }
            } catch (e: SettingNotFoundException) {
                e.printStackTrace()
            }
        } else {
            //Lower than API 19
            locationProviders = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED
            )
            if (locationProviders.contains(LocationManager.GPS_PROVIDER) && locationProviders.contains(
                    LocationManager.NETWORK_PROVIDER
                )
            ) {
                return true
            }
        }
        return false
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
       if (requestCode == permissioncode){
           if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){


           }else{
               Toast.makeText(this,"Permission was not granted", Toast.LENGTH_SHORT).show()
               requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),permissioncode)

           }
       }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onLocationChanged(location: Location) {
        println("longitude:${location.longitude}")
        println("latitude:${location.latitude}")
        gecooder(location)
    }

    private fun gecooder(location: Location) {
        var city:String?=null
        var country:String?=null
        try {
            var geocoder = Geocoder(this)
            var address:List<Address> = listOf()
            address = geocoder.getFromLocation(location.latitude,location.longitude,1)
            city=address[0].locality
            country=address[0].countryName
            city0.text = city

        }catch (e:IOException){
            println("ERORR:${e}")

        }
        if (city != null && country != null) {
                mv.getsalattimes(city,country)
        }

    }


}