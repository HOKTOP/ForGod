package com.hok.forgod.ui

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.download.library.DownloadImpl
import com.download.library.DownloadListenerAdapter
import com.download.library.Extra
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.hok.forgod.R
import com.hok.forgod.pojo.Adaptors.OnCarItemClickListner
import com.hok.forgod.pojo.Adaptors.adaptorSoung
import com.hok.forgod.pojo.Notifiction.CreateNotification
import com.hok.forgod.pojo.Notifiction.Playable
import com.hok.forgod.pojo.Notifiction.Service.MyReceiverservice
import com.hok.forgod.pojo.Notifiction.Service.OnClearFromRecentService
import com.hok.forgod.pojo.ads.AppOpenManager
import com.hok.forgod.pojo.interfaces.ListLisenar
import com.hok.forgod.pojo.model.*
import com.hok.forgod.ui.mvactivity.MainActivityViewM
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layoutsound.*
import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

class MainActivity : BaseActivity(),ListLisenar, OnCarItemClickListner,Playable,View.OnClickListener,SeekBar.OnSeekBarChangeListener {
    lateinit var adaptorSosung: adaptorSoung//adptor this sour
    private var ListDownloaded: List<Downloaded>? = listOf() // list for save data this soura downloaded
    private var play_list: Boolean= false // status mod play all list
    lateinit var MainActivityViewModel: MainActivityViewM // vm this fragment
    lateinit  var mp: MediaPlayer// oop MediaPlayer
    private var lastusrl: String? = null // list url running
    private var position: Int? = null // positon the Reciter in this recyclerview
    private var ListTrak: List<Traklist> = listOf() // list sour this Reciter
    private var ListlsitReaders: List<Reciter> = listOf() // list Reciter
    var notificationManager: NotificationManager? = null  // oop for Notification class
    private val STORAGE_PERMISSON_CODE: Int = 1000 // id requst permisson
    private val arrprvid: ArrayList<String> = arrayListOf<String>() // arr for temp save url for downloaded
    private var ListDownloadedGET: HashMap<String,Downloaded>? = HashMap()
    ////=> HashMap for split dataDownloaded use mark kay ->url
    private lateinit var mInterstitialAd: InterstitialAd // oop ads
    private var appOpenManager: AppOpenManager? = null//ads
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //on Create activtie
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?)  {
        var settingapp = Settingapp()
        if (settingapp.themestting(null,this)){
            setTheme(R.style.AppTheme)
        }else{
            setTheme(R.style.AppThemee)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        play_button_main.setOnClickListener(this)
        play_button.setOnClickListener(this)
        pause_button_main.setOnClickListener(this)
        pause_button.setOnClickListener(this)
        Previoussoura.setOnClickListener(this)
        nextsoura.setOnClickListener(this)
        seekBar3.setOnSeekBarChangeListener(this)
        loop.setOnClickListener(this)
        playlist.setOnClickListener(this)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications
        ))

        mp = MediaPlayer()
        MainActivityViewModel =
            ViewModelProvider(this).get(MainActivityViewM::class.java)
        MainActivityViewModel.ReadDBDownloaded.observe(this, Observer {

            ListDownloaded = it
            creatarrforidDownloaded()

        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
            registerReceiver(broadcastReceiver, IntentFilter("TRACKS_TRACKS"))
            startService(Intent(this, OnClearFromRecentService::class.java))
        }
        setSupportActionBar(toolbar)
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)
        MobileAds.initialize(this) {}

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-8786215020718835/7057738492"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.show()
        MobileAds.initialize(
            this,
            object : OnInitializationCompleteListener {
                override fun onInitializationComplete(initializationStatus: InitializationStatus?) {}
            })
        appOpenManager =  AppOpenManager(this.application);


    }


    // ger listTrack form framgment 
    override fun paslisttrack(List: List<Traklist>,list2: List<Sura>,lsitReaders:List<Reciter>) {
        setupRecylersurs(List,list2)
        ListTrak = List
        ListlsitReaders = lsitReaders
        if (sliding_layout.panelHeight == 0){
            sliding_layout.panelHeight = 250
        }
        songs_title.text = List.get(0).nameReciter
    }


    ///menu setup function
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.barapp,menu)
        return true
    }

    //menu selected item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu ->{
                DrawerLayout.open()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    // Setup Recycler Sours
    private fun setupRecylersurs(list: List<Traklist>,list2: List<Sura>) {
        reySours.layoutManager = LinearLayoutManager(this)
        adaptorSosung = adaptorSoung(this)
        reySours.adapter = adaptorSosung
        adaptorSosung.addlist(list)
        adaptorSosung.addhach(ListDownloadedGET)
    }






    //on Click item on Recycler Sours

    override fun onItemClick(view: View, item: List<Traklist>?, list2: List<ReadersSelected>?, position: Int) {
        this.position = position
        lastusrl = ListTrak.get(this.position!!).url
        onTrackPlay()

        when (view.id) {
            R.id.play_audo -> {
                onTrackPlay()
            }
            R.id.dowinlod -> {
                this.position = position
                if (isOnline(this)) {
                    lastusrl = ListTrak.get(this.position!!).url
                    donlowdsura(ListTrak.get(this.position!!).url)
                } else {
                    Toast.makeText(
                        this,
                        "لا يوجد اتصال بي الانترنت",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            }
        }
    }



    //function Create Channel Notification
    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CreateNotification.CHANNEL_ID,
                "hok DEV", NotificationManager.IMPORTANCE_LOW
            )
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (notificationManager != null) {
                notificationManager!!.createNotificationChannel(channel)
            }
        }
    }






















    //FUNCTION FOR CREATE ARRAY LIST TO FILES ALready downloaded
    private fun creatarrforidDownloaded() {
        thread {
            if (ListDownloaded?.size !=0){
                for (i in ListDownloaded?.indices!!){
                    arrprvid.add(ListDownloaded?.get(i)?.url!!)
                }
            }
        }
        creatHACHMAPforidDownloaded()
    }















    //function Create hach Map to url db Downloaded
    private fun creatHACHMAPforidDownloaded() {
        thread {
            if (ListDownloadedGET?.size !=0){
                ListDownloadedGET?.clear()
            }
            if (ListDownloaded?.size !=0){
                for (i in ListDownloaded?.indices!!){
                    ListDownloadedGET?.put(ListDownloaded!![i].url,ListDownloaded!![i])
                }
            }
        }
    }




















    ////Function to get check and ask permission and call function  startdownload for download soura
    private fun donlowdsura(url: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //permission denied ,requset it
                requestPermissions(
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSON_CODE
                )
            } else {
                //if permission  alreadey grantd perfrom donlowd call this function startdownload()
                startdownload(url)
            }
        } else {
            Toast.makeText(this, "Permission denide!", Toast.LENGTH_SHORT).show()

        }
    }



















    //function  startdownload for download soura
    @SuppressLint("ResourceType")
    private fun startdownload(url: String) {
        var file = getSharedPreferences("filedownload", Context.MODE_PRIVATE)

        if (arrprvid.contains(url)){
            println("this file is or Ready exist")

        }else{
            println("he start download")
            DownloadImpl.getInstance()
                .with(this)
                .url(url)
                .setEnableIndicator(true)
                .setUniquePath(false)
                .setForceDownload(true)
                .setIcon(R.id.dowinlod)
                .target(File(filesDir, "/Downloaded/${file.getString("File","temp")}/"))
                .enqueue(object : DownloadListenerAdapter(){
                    override fun onStart(url: String?, userAgent: String?, contentDisposition: String?, mimetype: String?, contentLength: Long, extra: Extra?) {
                        super.onStart(url, userAgent, contentDisposition, mimetype, contentLength, extra)
                        CreateNotification.createNotificationdown(this@MainActivity,ListTrak.get(position!!).nameSoura,"Download started")
                    }
                    override fun onProgress(url: String?, downloaded: Long, length: Long, usedTime: Long) {
                        super.onProgress(url, downloaded, length, usedTime)
                        Log.i("Downloader", " progress:" + downloaded + " url:" + url);

                    }

                    override fun onResult(throwable: Throwable?, path: Uri?, url: String?, extra: Extra?): Boolean {
                        Log.i("Downloader", " path:" + path + " url:" + url + " length:" +  File(path!!.getPath()).length() + "extra:$extra");
                        CreateNotification.createNotificationdown(this@MainActivity,ListTrak.get(position!!).nameSoura,"Download finished")
                        adddata(url!!,path)
                        reySours.refreshDrawableState()
                        reySours.setHasFixedSize(true)
                        return super.onResult(throwable, path, url, extra)
                    }

                })
        }
    }





















    //function to add data process download in data db
    private fun adddata(url: String,path:Uri) {
        var list = mutableListOf<Downloaded>()
        var namerector = ListTrak.get(position!!).nameReciter
        var namesora = ListTrak.get(position!!).nameSoura
        var idprv = "${ListTrak.get(position!!).nameReciter}_${ListTrak.get(position!!).nameSoura}"
        list.add(Downloaded(null,idprv,namerector,namesora,url,path.toString()))
        MainActivityViewModel.insertdownloaddb(list)
        adaptorSosung.addhach(ListDownloadedGET)
    }






















    //override Function onRequestPermissionsResult
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_PERMISSON_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    startdownload(lastusrl!!)
                }else{
                    requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),STORAGE_PERMISSON_CODE)
                    Toast.makeText(this,"Permission denide!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }














    //functions [installplaymponline,playmponline] install mediaplayer  if we online
    private fun installplaymponline() {
        if (ListTrak?.size !=0){
            try {
                CreateNotification.createNotification(this, ListTrak.get(position!!),
                    R.drawable.ic_pause_black_24dp, position!!, ListTrak.size)
            }catch (e:Exception){
                Log.d("Error:Play:",e.message.toString())
            }
            try {
                play_button.visibility = View.GONE
                play_button_main.visibility = View.GONE
                pause_button.visibility = View.VISIBLE
                pause_button_main.visibility = View.VISIBLE
            } catch (e: Exception) {
                play_button.visibility = View.VISIBLE
                play_button_main.visibility = View.VISIBLE
                pause_button.visibility = View.GONE
                pause_button_main.visibility = View.GONE
            }

            if (mp == null) {
                mp = MediaPlayer()
            }
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()

            }
            try {
                if (ListDownloadedGET?.get(ListTrak.get(position!!).url)?.url ==ListTrak.get(position!!).url){
                    println("offlinemode")
                    println("offlinemode")
                    println("offlinemode")
                    plaympoffline()
                }else{
                    println("onlinemode")
                    println("onlinemode")
                    println("onlinemode")
                    if (isOnline(this)){
                        playmponline()
                    }else{
                        Toast.makeText(this,"لا يوجد اتصال بي الانترنت",Toast.LENGTH_SHORT).show()

                    }
                }

            } catch (e: Exception) {
                mp.start()
            }
        }else {
            Toast.makeText(
                this,
                "هذه السورة غير لست محمله ولا يوجد اتصال بي الانترنت",
                Toast.LENGTH_SHORT
            ).show()

        }

    }



















    //////////////functions[installplaympoffline,plaympoffline] install mediaplayer  if we offline///////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun installplaympoffline() {
        if (ListDownloadedGET?.size !=0){
            try {
                CreateNotification.createNotification(this, ListTrak.get(position!!),
                    R.drawable.ic_pause_black_24dp, position!!, ListTrak.size)
            }catch (e:Exception){
                Log.d("Error:Play:",e.message.toString())
            }
            try {
                play_button.visibility = View.GONE
                play_button_main.visibility = View.GONE
                pause_button.visibility = View.VISIBLE
                pause_button_main.visibility = View.VISIBLE
            } catch (e: Exception) {
                play_button.visibility = View.VISIBLE
                play_button_main.visibility = View.VISIBLE
                pause_button.visibility = View.GONE
                pause_button_main.visibility = View.GONE
            }
            if (mp == null) {
                mp = MediaPlayer()
            }
            if (mp.isPlaying) {
                mp.stop()
                mp.reset()
            }
            try {
                if (ListDownloadedGET?.get(ListTrak.get(position!!).url)?.url ==ListTrak.get(position!!).url){
                    println("offlinemode")
                    println("offlinemode")
                    println("offlinemode")
                    plaympoffline()
                }else{
                    println("onlinemode")
                    println("onlinemode")
                    println("onlinemode")
                    if (isOnline(this)){
                        playmponline()
                    }else{
                        Toast.makeText(this,"لا يوجد اتصال بي الانترنت",Toast.LENGTH_SHORT).show()

                    }
                }

            } catch (e: Exception) {
                mp.start()
            }
        }else {
            Toast.makeText(
                this,
                "هذه السورة  لست محمله ولا يوجد اتصال بي الانترنت",
                Toast.LENGTH_SHORT
            ).show()

        }

    }
    ///function play media player online
    private fun playmponline() {
        mp.reset()
        if (position != null) {
            mp.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                }
                setDataSource(ListTrak.get(position!!).url)
                prepare()
                start()
                initialiseSeebar()
            }
        }
    }
    ///function play media player offline
    private fun plaympoffline() {
        mp.reset()
        if (position != null) {
            mp.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                }
                setDataSource(this@MainActivity, Uri.parse(ListDownloadedGET?.get(ListTrak.get(position!!).url)?.patch))
                prepare()
                start()
                initialiseSeebar()
            }

        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////












    //action button Previous
    override fun onTrackPrevious() {


        if (position == 0) {
            position = ListTrak.size
            onTrackPlay()
        } else {
            position = position!! - 1
            onTrackPlay()

        }
        CreateNotification.createNotification(
            this, ListTrak.get(position!!),
            R.drawable.ic_pause_black_24dp, position!!, ListTrak.size
        )
    }


    //action button Play
    override fun onTrackPlay() {
        if (ListTrak.size !=0 ){
            songs_title.text = ListTrak.get(position!!).nameReciter
            songs_artist_name.text = ListTrak.get(position!!).nameSoura
            if (isOnline(this)){
                installplaymponline()
            }else{
                installplaympoffline()
            }
        }



    }


    //action button Pause
    override fun onTrackPause() {

        try {
            play_button.visibility = View.VISIBLE
            play_button_main.visibility = View.VISIBLE
            pause_button.visibility = View.GONE
            pause_button_main.visibility = View.GONE
        } catch (e: Exception) {
            play_button.visibility = View.GONE
            play_button_main.visibility = View.GONE
            pause_button.visibility = View.VISIBLE
            pause_button_main.visibility = View.VISIBLE
        }
        CreateNotification.createNotification(
            this, ListTrak.get(position!!),
            R.drawable.ic_play_arrow_black_24dp, position!!, ListTrak.size
        )
        mp.pause()
    }


    //action button Next
    override fun onTrackNext() {

        if (position !=null &&  isOnline(this)){
            if (position == ListTrak.size) {
                position = 0
                onTrackPlay()
            } else {
                position = position!! + 1
                onTrackPlay()

            }
        }else if(isOnline(this)){
            Toast.makeText(this,"يرجا اختيار سورة ",Toast.LENGTH_SHORT).show()

        }
        else{
            Toast.makeText(this,"لا يوجد اتصال بي الانترنت",Toast.LENGTH_SHORT).show()
        }

        CreateNotification.createNotification(
            this, ListTrak.get(position!!),
            R.drawable.ic_pause_black_24dp, position!!, ListTrak.size
        )
    }


    //if stop appliction
    override fun onStops() {
        try {
            mp.stop()
            mp.reset()
            mp.release()
        }catch (e:Exception){

        }
    }
/////////////////////////////////////////////

    //On click item media view
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.play_button ->{
                if (position !=null &&  isOnline(this)){
                    onTrackPlay()
                }else if(isOnline(this)){
                    Toast.makeText(this,"يرجا اختيار سورة ",Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(this,"لا يوجد اتصال بي الانترنت",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.play_button_main ->{
                if (position !=null &&  isOnline(this)){
                    onTrackPlay()
                }else if(isOnline(this)){
                    Toast.makeText(this,"يرجا اختيار سورة ",Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(this,"لا يوجد اتصال بي الانترنت",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.pause_button ->{
                onTrackPause()
            }
            R.id.pause_button_main ->{
                onTrackPause()
            }
            R.id.nextsoura ->{
                onTrackNext()
            }
            R.id.Previoussoura ->{
                onTrackPrevious()
            }

            R.id.loop ->{

                if(!lastusrl.equals(null)){
                    if (mp.isLooping){
                        mp.isLooping = false
                        loop.setImageResource(R.drawable.ic_loopoff)
                    }else {
                        mp.isLooping =true
                        loop.setImageResource(R.drawable.ic_loopon)
                    }
                }
            }

            R.id.playlist ->{
                if (!lastusrl.equals(null)){
                    if (play_list){
                        play_list = false
                        playlist.setImageResource(R.drawable.ic_playlist_off)

                    }else{
                        play_list =true
                        playlist.setImageResource(R.drawable.ic_playlist_on)
                    }
                }
            }

        }
    }



    //if fragment Destroy
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager!!.cancelAll()
        }

        mp.stop()
        mp.reset()

    }



    //function test if we online
    @SuppressLint("NewApi")
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }


    //broadcast Receiver to Notification
    var broadcastReceiver: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.extras!!.getString("actionname")
            when (action) {
                CreateNotification.ACTION_PREVIUOS -> onTrackPrevious()
                CreateNotification.ACTION_PLAY -> if (mp.isPlaying) {
                    onTrackPause()
                } else {
                    onTrackPlay()
                }
                CreateNotification.ACTION_NEXT -> onTrackNext()
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////seekbarFunctios/////////////////////////////
    private fun  initialiseSeebar(){
        seekBar3.max = mp.duration
        val  handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(object :Runnable{
            override fun run() {
                try {
                    endTime.text = (mp.duration/1000).toString()+"SC"
                    seekBar3.progress = mp.currentPosition
                    StartTime.text = (mp.currentPosition/1000).toString()+"SC"
                    var iftimeend = mp.duration/1000 == mp.currentPosition/1000

                    if (iftimeend){
                        play_listfun()
                    }
                    handler.postDelayed(this,1000)
                }catch (e:Exception){
                    try {
                        seekBar3.progress =0
                    }catch (e:java.lang.Exception){}
                }
            }
        },0)
    }
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser)  mp?.seekTo(progress)
    }
    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }
    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    //funtion play all list
    private fun play_listfun() {
        try {
            play_button.visibility = View.VISIBLE
            play_button_main.visibility = View.VISIBLE
            pause_button.visibility = View.GONE
            pause_button_main.visibility = View.GONE
            /////
            CreateNotification.createNotification(
                this, ListTrak.get(position!!),
                R.drawable.ic_play_arrow_black_24dp, position!!, ListTrak.size
            )

        } catch (e: Exception) {
            play_button.visibility = View.GONE
            play_button_main.visibility = View.GONE
            pause_button.visibility = View.VISIBLE
            pause_button_main.visibility = View.VISIBLE
        }
        if (play_list){
            position = position!!+1
            CreateNotification.createNotification(
                this, ListTrak.get(position!!),
                R.drawable.ic_pause_black_24dp, position!!, ListTrak.size
            )
            onTrackPlay()
        }
    }


}
