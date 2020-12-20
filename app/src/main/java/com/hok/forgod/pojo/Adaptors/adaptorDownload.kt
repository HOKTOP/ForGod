package com.hok.forgod.pojo.Adaptors

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SectionIndexer
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hok.forgod.R
import com.hok.forgod.pojo.Notifiction.CreateNotification
import com.hok.forgod.pojo.Notifiction.Service.NotificationActionService
import com.hok.forgod.pojo.model.Downloaded
import com.hok.forgod.pojo.model.ReadersSelected
import com.hok.forgod.pojo.model.Sura
import kotlinx.android.synthetic.main.layout_download.view.*
import kotlinx.android.synthetic.main.layout_sour.view.*

class  adaptorDownload(private var clickListner:OnCarItemClickListnerDownload): RecyclerView.Adapter<adaptorDownload.DownloadViewHolder>(){
    var context:Context? =null
    var list = listOf<Downloaded>()
    var pendingIntentPrevious: PendingIntent? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptorDownload.DownloadViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_download,parent,false)
        context = parent.context
        return DownloadViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: adaptorDownload.DownloadViewHolder, position: Int) {
        var data = list.get(position)
        holder.souraname.text = data.nameSora
        holder.recitename.text = data.nameReciter
        holder.initialize(list,clickListner)
    }


    inner class DownloadViewHolder(item2:View) :RecyclerView.ViewHolder(item2) {
       var souraname = item2.findViewById<TextView>(R.id.name_soura)
       var recitename = item2.findViewById<TextView>(R.id.name_rectiors)
        fun initialize(item: List<Downloaded>?, action:OnCarItemClickListnerDownload) {
            itemView.play.setOnClickListener {
                action.on1ItemClick(itemView,item,adapterPosition)
            }
        }
    }

    fun  adddata(it:List<Downloaded>){
        list = it
        notifyDataSetChanged()
    }

    interface OnCarItemClickListnerDownload {
        fun on1ItemClick(view: View, item: List<Downloaded>?,  position: Int)
    }

}