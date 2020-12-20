package com.hok.forgod.pojo.Adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.hok.forgod.R
import com.hok.forgod.pojo.model.Downloaded
import com.hok.forgod.pojo.model.ReadersSelected
import com.hok.forgod.pojo.model.Sura
import com.hok.forgod.pojo.model.Traklist
import kotlinx.android.synthetic.main.layout_sour.view.*

class  adaptorSoung(private var clickListner:OnCarItemClickListner): RecyclerView.Adapter<adaptorSoung.ReadersViewHolder>(){
    var context:Context? =null
    private var mSectionPositions: ArrayList<Int>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): adaptorSoung.ReadersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_sour,parent,false)
        context = parent.context
        return ReadersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listdataSura.size
    }
    var position = 0
    override fun onBindViewHolder(holder: adaptorSoung.ReadersViewHolder, position: Int) {
        var list = listdataSura?.get(position)
        holder.name.text = "${list?.nameSoura}"
        holder.idsoura.text = list?.idSoura.toString()
        holder.dowinlod.setImageResource(R.drawable.ic_baseline_cloud_download_24)
        if (ListDownloadedGET!!.containsKey(list?.url)){
            holder.dowinlod.setImageResource(R.drawable.ic_baseline_cloud_done_24)
        }
        holder.initialize(listdataSura, clickListner)
        this.position = position


    }
    private var listdataSura: List<Traklist> = listOf()
    private var ListDownloadedGET: HashMap<String, Downloaded>? = HashMap() ////=> HashMap for split dataDownloaded use mark kay ->url

    inner class ReadersViewHolder(item2:View) :RecyclerView.ViewHolder(item2) {
        var dowinlod = item2.findViewById<ImageView>(R.id.dowinlod)
        var name = item2.findViewById<TextView>(R.id.name_soura)
        var idsoura = item2.findViewById<Button>(R.id.idsoura)

        fun initialize(item: List<Traklist>?, action:OnCarItemClickListner) {
            itemView.setOnClickListener {
                if (item != null) {
                    action.onItemClick(it,item,null,adapterPosition)
                    notifyDataSetChanged()

                }
            }
            itemView.dowinlod.setOnClickListener {
                if (item != null) {
                    action.onItemClick(it,item,null,adapterPosition)
                    notifyDataSetChanged()

                }
            }
        }
    }

    fun addlist(list: List<Traklist>){

        listdataSura= list

        notifyDataSetChanged()
    }
    fun addhach(hach: HashMap<String, Downloaded>?){

        ListDownloadedGET= hach

        notifyDataSetChanged()
    }
}


interface OnCarItemClickListner{
    fun onItemClick(view: View,item: List<Traklist>?,list2:List<ReadersSelected>?, position: Int)
}