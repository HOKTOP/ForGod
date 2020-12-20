package com.hok.forgod.pojo.Adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hok.forgod.R
import com.hok.forgod.pojo.model.Adkar
import java.util.ArrayList

class AdkarAdapter(private val mAdkarArrayList: ArrayList<Adkar>) : RecyclerView.Adapter<AdkarAdapter.AdkarViewHolder>() {

    class AdkarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mDouaa: TextView
        var mTimes: TextView

        init {
            mDouaa = itemView.findViewById(R.id.douaa)
            mTimes = itemView.findViewById(R.id.times)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AdkarViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.adkar, viewGroup, false)
        return AdkarViewHolder(v)
    }

    override fun onBindViewHolder(adkarViewHolder: AdkarViewHolder, i: Int) {
        val (douaa, times) = mAdkarArrayList[i]
        adkarViewHolder.mDouaa.text = douaa
        adkarViewHolder.mTimes.text = times
    }

    override fun getItemCount(): Int {
        return mAdkarArrayList.size
    }

}