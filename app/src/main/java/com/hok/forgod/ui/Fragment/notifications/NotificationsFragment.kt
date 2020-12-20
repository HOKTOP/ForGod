package com.hok.forgod.ui.Fragment.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.hok.forgod.R
import com.hok.forgod.pojo.model.Timings
import com.hok.forgod.ui.AdkaralMassaa
import com.hok.forgod.ui.MainActivity
import com.hok.forgod.ui.ismaaallh
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.activity_menuapp.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import java.io.IOException

class NotificationsFragment : Fragment(), LocationListener,View.OnClickListener {
    /*


 ____   ____         _____     ____    ____             _____        ______    ____      ____
|    | |    |   ____|\    \   |    |  |    |        ___|\    \   ___|\     \  |    |    |    |
|    | |    |  /     /\    \  |    |  |    |       |    |\    \ |     \     \ |    |    |    |
|    |_|    | /     /  \    \ |    | /    //       |    | |    ||     ,_____/||    |    |    |
|    .-.    ||     |    |    ||    |/ _ _//        |    | |    ||     \--'\_|/|    |    |    |
|    | |    ||     |    |    ||    |\    \'        |    | |    ||     /___/|  |    |    |    |
|    | |    ||\     \  /    /||    | \    \        |    | |    ||     \____|\ |\    \  /    /|
|____| |____|| \_____\/____/ ||____|  \____\       |____|/____/||____ '     /|| \ ___\/___ / |
|    | |    | \ |    ||    | /|    |   |    |      |    /    | ||    /_____/ | \ |   ||   | /
|____| |____|  \|____||____|/ |____|   |____|      |____|____|/ |____|     | /  \|___||___|/
  \(     )/       \(    )/      \(       )/          \(    )/     \( |_____|/     \(    )/
   '     '         '    '        '       '            '    '       '    )/         '    '
                                                                        '


    *|
     */
    private val PERMISSIONCODE: Int= 191
    private lateinit var notificationsViewModel: NotificationsViewModel
    lateinit var locationManager: LocationManager
    lateinit var location: Location
    var data:Timings = Timings()
    var handler = Handler(Looper.getMainLooper())
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var snackbar:Snackbar
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        root.adkarmassaa.setOnClickListener(this)
        root.adkarssunrise.setOnClickListener(this)
        root.asmaaallh.setOnClickListener(this)
        root.spha.setOnClickListener(this)
        root.QiblaFinder.setOnClickListener(this)
        root.clcretorzkatk.setOnClickListener(this)
        root.wallpapors.setOnClickListener(this)
        root.aboutus.setOnClickListener(this)
        root.shareapp.setOnClickListener(this)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //CHECK Permission
        permissioncheck()

        //
        notificationsViewModel.salattimes.observe(viewLifecycleOwner, Observer {
            data = it
            updatetime(it)
            if (it.fajr !=null){


                StyleableToast.makeText(requireContext(),
                    getText(R.string.prayertimesloaded) as String?, Toast.LENGTH_LONG,R.style.mytoast).show()

            }
        })
        MobileAds.initialize(requireContext()) {}
        mInterstitialAd = InterstitialAd(requireContext())
        mInterstitialAd.adUnitId = "ca-app-pub-8786215020718835/7057738492"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.show()

    }


    private fun updatetime(it: Timings?) {
        fajr.text = it?.fajr
        duhur.text = it?.dhuhr
        asr.text = it?.asr
        maghrib.text = it?.maghrib
        isha.text = it?.isha
    }

    private fun permissioncheck() {
        if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSIONCODE)

        }else{
            //Check if GPS WORKING IF He work get location if dose't call method work gps
            check2()
        }
    }

    @SuppressLint("MissingPermission")
    private fun check2() {

        if(!checkHighAccuracyLocationMode(requireContext())){
            buildAlertMessageNoGps()
        }else{
            fusedLocationClient.lastLocation.addOnSuccessListener  {
                if (MainActivity().isOnline(requireContext()) && it !=null){
                    onLocationChanged(it)
                }else{
                    StyleableToast.makeText(requireContext(),
                        getText(R.string.internetfirst) as String?,Toast.LENGTH_LONG,R.style.mytoast).show()
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONCODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                //Check if GPS WORKING IF He work get location if dose't call method run gps
                StyleableToast.makeText(requireContext(),
                    getText(R.string.locationloading) as String?,Toast.LENGTH_LONG,R.style.mytoast).show()
                if (checkHighAccuracyLocationMode(requireContext())){
                    permissioncheck()
                }else{
                    permissioncheck()
                    buildAlertMessageNoGps()
                }

            }else{
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSIONCODE)

            }
        }

    }



    /////////method run gps////////////
    private fun buildAlertMessageNoGps() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(requireContext())
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id -> check()})
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id ->  dialog.cancel() })
        val alert: android.app.AlertDialog? = builder.create()
        alert?.show()
    }

    private fun check() {
        if (!checkHighAccuracyLocationMode(requireContext())){
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    ////////method check if gps work or dose't//////////
    private  fun checkHighAccuracyLocationMode(context: Context): Boolean {
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
            } catch (e: Settings.SettingNotFoundException) {
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

    override fun onLocationChanged(location: Location) {
        gecooder(location)
    }
    private fun gecooder(location: Location) {
        var city:String?=null
        var country:String?=null
        try {
            var geocoder = Geocoder(requireContext())
            var address:List<Address> = listOf()
            address = geocoder.getFromLocation(location.latitude,location.longitude,1)
            city=address[0].locality
            country=address[0].countryName
            city0.text = city

        }catch (e: IOException){
            println("ERORR:${e}")

        }
        if (city != null && country != null) {
            notificationsViewModel.getsalattimes(city,country)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.adkarmassaa -> {
                var intent = Intent(requireContext(), AdkaralMassaa::class.java)
                intent.putExtra("kay", 0)
                startActivity(intent)
            }
            R.id.adkarssunrise -> {

                var intent = Intent(requireContext(), AdkaralMassaa::class.java)
                intent.putExtra("kay", 1)
                startActivity(intent)
            }
            R.id.asmaaallh -> {

                var intent = Intent(requireContext(), ismaaallh::class.java)
                intent.putExtra("kay", 1)
                startActivity(intent)
            }
            R.id.spha -> {

                var intent = Intent(requireContext(), com.hok.forgod.ui.spha::class.java)
                startActivity(intent)
            }

            R.id.QiblaFinder -> {

                var intent = Intent(requireContext(), com.hok.forgod.ui.QiblaFinder::class.java)
                startActivity(intent)
            }

            R.id.clcretorzkatk -> {

                var intent = Intent(requireContext(), com.hok.forgod.ui.ZakatCalculator::class.java)
                startActivity(intent)
            }
            R.id.wallpapors -> {

                var intent = Intent(requireContext(), com.hok.forgod.ui.Wallpapers::class.java)
                startActivity(intent)
            }
            R.id.aboutus -> {

                var intent = Intent(requireContext(), com.hok.forgod.ui.Settingapp::class.java)
                startActivity(intent)
            }

            R.id.shareapp -> {
                val intentShare = Intent(Intent.ACTION_SEND)
                intentShare.type = "text/plain"
                intentShare.putExtra(Intent.EXTRA_SUBJECT, "My Subject Here ... ")
                intentShare.putExtra(
                    Intent.EXTRA_TEXT, "${resources.getText(R.string.share)} \n https://play.google.com/store/apps/details?id=com.hok.forgod"
                )

                startActivity(Intent.createChooser(intentShare, "Shared the text ..."))


            }
        }
    }


}