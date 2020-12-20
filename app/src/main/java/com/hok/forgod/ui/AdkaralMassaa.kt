package com.hok.forgod.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hok.forgod.R
import com.hok.forgod.pojo.Adaptors.AdkarAdapter
import com.hok.forgod.pojo.model.Adkar
import kotlinx.android.synthetic.main.activity_adkaral_massaa.*
import java.util.*

class AdkaralMassaa : BaseActivity() {
    lateinit var mRecyclerView: RecyclerView
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    var content: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        var settingapp = Settingapp()
        if (settingapp.themestting(null,this)){
            setTheme(R.style.AppTheme)
        }else{
            setTheme(R.style.AppThemee)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adkaral_massaa)
        var getintnted= intent.getIntExtra("kay",0)
        if (getintnted ==0){
            asdkarallmsaaa()
        }else if (getintnted==1){
            asdkaralsbah()
        }
    }

    private fun asdkarallmsaaa() {
        adkarmassaatitle.text = getString(R.string.menu_sunset)
        val AdkarMassaaList: ArrayList<Adkar> = ArrayList<Adkar>()
        AdkarMassaaList.add(Adkar(getString(R.string.adkarmassaa1), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarmassaa2), getString(R.string.time4)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarmassaa3), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarmassaa4), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah3), getString(R.string.time7)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah6), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah7), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarmassaa5), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah9), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarmassaa6), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah11), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah12), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah13), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah14), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah15), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah16), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah17), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah18), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah19), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah20), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah21), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah22), getString(R.string.time10)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah23), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah25), getString(R.string.time100)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah26), getString(R.string.time100)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah28), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarmassaa7), getString(R.string.time1)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah29), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah30), getString(R.string.time3)))
        AdkarMassaaList.add(Adkar(getString(R.string.adkarsabah31), getString(R.string.time3)))
        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        var mAdapter = AdkarAdapter(AdkarMassaaList)
        mRecyclerView.setLayoutManager(mLayoutManager)
        mRecyclerView.setAdapter(mAdapter)
    }
    private fun asdkaralsbah() {
        adkarmassaatitle.text = getString(R.string.menu_sunrise)

        val AdkarSabahList = ArrayList<Adkar>()
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah1), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah2), getString(R.string.time4)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah3), getString(R.string.time7)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah4), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah5), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah6), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah7), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah8), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah9), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah10), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah11), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah12), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah13), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah14), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah15), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah16), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah17), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah18), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah19), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah20), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah21), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah22), getString(R.string.time10)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah23), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah24), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah25), getString(R.string.time100)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah26), getString(R.string.time100)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah27), getString(R.string.time100)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah28), getString(R.string.time1)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah29), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah30), getString(R.string.time3)))
        AdkarSabahList.add(Adkar(getString(R.string.adkarsabah31), getString(R.string.time3)))
        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        var mAdapter = AdkarAdapter(AdkarSabahList)
        mRecyclerView.setLayoutManager(mLayoutManager)
        mRecyclerView.setAdapter(mAdapter)
    }
}