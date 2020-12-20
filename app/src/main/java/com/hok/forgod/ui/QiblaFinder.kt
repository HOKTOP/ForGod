package com.hok.forgod.ui


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.hok.forgod.R
import com.hok.forgod.pojo.service.Compass
import com.hok.forgod.pojo.service.GPSTracker
import java.util.*


class QiblaFinder : BaseActivity() {
    lateinit var compass: Compass
    lateinit var arrowViewQiblat: ImageView
    lateinit var imageDial: ImageView
    lateinit var text_up: TextView
    lateinit var text_down: TextView
    private var currentAzimuth = 0f
    var prefs: SharedPreferences? = null
    var gps: GPSTracker? = null
     override fun onCreate(savedInstanceState: Bundle?) {
         var settingapp = Settingapp()
         if (settingapp.themestting(null,this)){
             setTheme(R.style.AppTheme)
         }else{
             setTheme(R.style.AppThemee)
         }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qibla_finder)
        prefs = getSharedPreferences("qibla", MODE_PRIVATE)
        gps = GPSTracker(this)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        arrowViewQiblat = findViewById(R.id.main_image_qibla)
        imageDial = findViewById(R.id.main_image_dial)
        text_up = findViewById(R.id.text_up)
        text_down = findViewById(R.id.text_down)
        arrowViewQiblat!!.visibility = View.INVISIBLE
        arrowViewQiblat!!.visibility = View.GONE
        setupCompass()
    }

     override fun onStart() {
        super.onStart()
        Log.d(TAG, "start compass")
        if (compass != null) {
            compass.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (compass != null) {
            compass.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        if (compass != null) {
            compass.start()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "stop compass")
        if (compass != null) {
            compass.stop()
        }
    }

    private fun setupCompass() {
        val permission_granted = GetBoolean("permission_granted")
        if (permission_granted) {
            bearing
        } else {
            text_up!!.text = ""
            text_down.setText(getResources().getString(R.string.permission_not_garanted))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1
                )
            } else {
                bearing
            }
        }
        compass = Compass(this)
        val cl: Compass.CompassListener = object : Compass.CompassListener {
            override fun onNewAzimuth(azimuth: Float) {
                adjustGambarDial(azimuth)
                adjustArrowQiblat(azimuth)
            }
        }
        compass.setListener(cl)
    }

    fun adjustGambarDial(azimuth: Float) {
        val an: Animation = RotateAnimation(
            -currentAzimuth,
            -azimuth,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        currentAzimuth = azimuth
        an.duration = 500
        an.repeatCount = 0
        an.fillAfter = true
        imageDial!!.startAnimation(an)
    }

    fun adjustArrowQiblat(azimuth: Float) {
        val QiblaDegree = GetFloat("QiblaDegree")
        val an: Animation = RotateAnimation(
            -currentAzimuth + QiblaDegree,
            -azimuth,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        currentAzimuth = azimuth
        an.duration = 500
        an.repeatCount = 0
        an.fillAfter = true
        arrowViewQiblat!!.startAnimation(an)
        if (QiblaDegree > 0) {
            arrowViewQiblat!!.visibility = View.VISIBLE
        } else {
            arrowViewQiblat!!.visibility = View.INVISIBLE
            arrowViewQiblat!!.visibility = View.GONE
        }
    }

    // Get the location manager
    /*float QiblaDegree = GetFloat("QiblaDegree");
    if(QiblaDegree > 0.0001){
        text_down.setText(getResources().getString(R.string.coord) +" "+getResources().getString(R.string.lastlocation));
        text_up.setText(getResources().getString(R.string.qibladirection) +" " + QiblaDegree + " " + getResources().getString(R.string.fromnorth));
        arrowViewQiblat .setVisibility(View.VISIBLE);
    }else
    {
        fetch_GPS();
    }*/
    @get:SuppressLint("MissingPermission")
    val bearing: Unit
        get() {
            // Get the location manager
            fetch_GPS()
            /*float QiblaDegree = GetFloat("QiblaDegree");
            if(QiblaDegree > 0.0001){
                text_down.setText(getResources().getString(R.string.coord) +" "+getResources().getString(R.string.lastlocation));
                text_up.setText(getResources().getString(R.string.qibladirection) +" " + QiblaDegree + " " + getResources().getString(R.string.fromnorth));
                arrowViewQiblat .setVisibility(View.VISIBLE);
            }else
            {
                fetch_GPS();
            }*/
        }

   override fun onRequestPermissionsResult(
       requestCode: Int,
       permissions: Array<out String>,
       grantResults: IntArray
   ) {
        when (requestCode) {
            1 -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission was granted, yay! Do the
                    SaveBoolean("permission_granted", true)
                    bearing
                    //text_down.setText(getResources().getString(R.string.permissiongaranted));
                    //arrowViewQiblat.setVisibility(INVISIBLE);
                    //arrowViewQiblat.setVisibility(View.GONE);
                } else {
                    finish()
                }
                return
            }
        }
    }

    fun SaveBoolean(key: String?, bb: Boolean?) {
        val edit = prefs!!.edit()
        edit.putBoolean(key, bb!!)
        edit.apply()
    }

    fun GetBoolean(key: String?): Boolean {
        return prefs!!.getBoolean(key, false)
    }

    fun SaveFloat(key: String?, ff: Float?) {
        val edit = prefs!!.edit()
        edit.putFloat(key, ff!!)
        edit.apply()
    }

    fun GetFloat(key: String?): Float {
        return prefs!!.getFloat(key, 0f)
    }

  override  fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        fetch_GPS()
        return true
    }

    fun fetch_GPS() {
        var result = 0.0
        gps = GPSTracker(this)
        if (gps!!.canGetLocation()) {
            val latitude: Double = gps!!.getLatitude()
            val longitude: Double = gps!!.getLongitude()
            text_down.setText(
                getResources().getString(R.string.coord)
                    .toString() + "\n" + getResources().getString(R.string.latitude) + ": " + latitude + getResources().getString(
                    R.string.longitude
                ) + ": " + longitude
            )
            Log.e("TAG", "GPS is on")
            val lat_saya: Double = gps!!.getLatitude()
            val lon_saya: Double = gps!!.getLongitude()
            if (lat_saya < 0.001 && lon_saya < 0.001) {
                arrowViewQiblat!!.visibility = View.INVISIBLE
                arrowViewQiblat!!.visibility = View.GONE
                text_up!!.text = ""
                text_down.setText(getResources().getString(R.string.locationunready))
            } else {
                val longitude2 =
                    39.826209 // Kaabah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                val latitude2 =
                    Math.toRadians(21.422507) // Kaabah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                val latitude1 = Math.toRadians(lat_saya)
                val longDiff = Math.toRadians(longitude2 - lon_saya)
                val y = Math.sin(longDiff) * Math.cos(latitude2)
                val x =
                    Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(
                        latitude1
                    ) * Math.cos(latitude2) * Math.cos(longDiff)
                result = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360
                val result2 = result.toFloat()
                SaveFloat("QiblaDegree", result2)
                text_up.setText(getString(R.string.qibladirection)
                        .toString() + " " + result2 + " " + getResources().getString(R.string.fromnorth)
                )
                arrowViewQiblat!!.visibility = View.VISIBLE
            }
        } else {
            gps!!.showSettingsAlert()
            arrowViewQiblat!!.visibility = View.INVISIBLE
            arrowViewQiblat!!.visibility = View.GONE
            text_up!!.text = ""
            text_down.setText(getResources().getString(R.string.gpsplz))
        }
    }

    fun QiblaTips(v: View?) {
        val mContext: Context = this
        val alertDialog =
            AlertDialog.Builder(mContext, R.style.AlertDialogCustom)
        alertDialog.setTitle(mContext.resources.getString(R.string.consignes))
        alertDialog.setMessage(mContext.resources.getString(R.string.qiblatips))
        alertDialog.setPositiveButton(
            mContext.resources.getString(R.string.ok)
        ) { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }

    companion object {
        private const val TAG = "QiblaFinder"
    }
}
