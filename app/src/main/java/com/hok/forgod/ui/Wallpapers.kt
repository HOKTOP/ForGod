package com.hok.forgod.ui

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hok.forgod.R
import com.hok.forgod.pojo.Adaptors.ImageAdapter
import com.hok.forgod.pojo.model.ImageItem
import com.hok.forgod.pojo.service.NetworkConnectivity
import com.hok.forgod.pojo.service.WallpaperDialog
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class Wallpapers : BaseActivity(), ImageAdapter.OnItemClickListener,
    WallpaperDialog.WallpaperDialogListener {
    lateinit var mRecyclerView: RecyclerView
    lateinit var mImageAdapter: ImageAdapter
    private var mImageListt: ArrayList<ImageItem>? = null
    private var mRequestQueue: RequestQueue? = null
    var progressload: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        var settingapp = Settingapp()
        if (settingapp.themestting(null,this)){
            setTheme(R.style.AppTheme)
        }else{
            setTheme(R.style.AppThemee)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpapers)
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(GridLayoutManager(this, 2))
        mImageListt = ArrayList<ImageItem>()
        if (NetworkConnectivity.isNetworkStatusAvailable(applicationContext)) {
            Toast.makeText(
                applicationContext,
                getString(R.string.internetfound),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                applicationContext,
                getString(R.string.internetlost),
                Toast.LENGTH_SHORT
            ).show()
        }
        mRequestQueue = Volley.newRequestQueue(this)
        progressload = findViewById(R.id.progressBar)

        //--------ADS
        parseJSON()
    }

    fun randomUnique(max: Int): ArrayList<Int?> {
        val list = ArrayList<Int?>()
        for (i in 1 until max) {
            list.add(i)
        }
        Collections.shuffle(list)
        return list
    }

    private fun parseJSON() {
        val url = "https://api.npoint.io/e99d032bbebe11c2e76f"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            object : Response.Listener<JSONObject?>{
                override fun onResponse(response: JSONObject?) {
                    try {
                        val jsonArray = response?.getJSONArray("pics")
                        val list = randomUnique(jsonArray?.length()!!)
                        for (i in 0..19) {
                            val hit = jsonArray?.getJSONObject(list[i]!!)
                            val imageUrl = hit.getString("imageurl")
                            mImageListt!!.add(ImageItem(imageUrl))
                        }
                        mImageAdapter = ImageAdapter(this@Wallpapers, mImageListt!!)
                        mRecyclerView.setAdapter(mImageAdapter)
                        mImageAdapter.setOnItemClickListener(this@Wallpapers)
                        progressload!!.visibility = View.GONE
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }, object : Response.ErrorListener {
                override   fun onErrorResponse(error: VolleyError) {
                    error.printStackTrace()
                }
            })
        mRequestQueue?.add(request)
    }

    override fun onItemClick(position: Int) {
        openDialog(position)
    }

    var getposition = 0

    //making target global variable actually make difference to handle onBitmapLoaded correctly (it wasnt called on first load also on odd number of loads)
    lateinit var target : com.squareup.picasso.Target
    override   fun onYesClicked() {
        val clickedItem: ImageItem = mImageListt!![getposition]
        val wallpaperManager = WallpaperManager.getInstance(this)
        target = object :  com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                try {
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(
                        applicationContext,
                        resources.getString(R.string.wallpaperset),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    Toast.makeText(
                        applicationContext,
                        resources.getString(R.string.wallpapertryagain),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override   fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.wallpaperfailed),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override    fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.wallpaperloading),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val url: String = clickedItem.imageUrl
        Picasso.get().load(url).resize(1080, 1920).centerCrop().into(target)
    }

    fun openDialog(p: Int) {
        val dialog = WallpaperDialog()
        dialog.show(supportFragmentManager, "Wallpaper Dialog")
        getposition = p
    }
}
