package com.hok.forgod.pojo.Adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.SectionIndexer
import androidx.recyclerview.widget.RecyclerView
import com.hok.forgod.R
import com.hok.forgod.pojo.model.Reciter
import kotlinx.android.synthetic.main.layout_readers.view.*
import java.util.*
import kotlin.collections.ArrayList


class  adaptorReaders(private var clickListner:OnCarItemClickListner2?) :RecyclerView.Adapter<adaptorReaders.ViewHOLDER>(),
    SectionIndexer,Filterable {
    var context: Context? = null
    var arrayfristkay = arrayListOf<String>()
    var arrayfristpoition = arrayListOf<Int>()
    var filterList = listOf<Reciter>()
    lateinit var holder :ViewHOLDER
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHOLDER {

        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_readers, parent, false)
        context = parent.context
        return ViewHOLDER(view)
    }

    override fun getItemCount(): Int {
        if (filterList.size>0){
            return filterList.size
        }
        return readerlist.size
    }

    override fun onBindViewHolder(holder: adaptorReaders.ViewHOLDER, position: Int) {
        if (filterList.size > 0) {
            val list = filterList[position]
            holder.name.text = list.name
            holder.cont.text = "${list.count}"
            holder.rewya.text = list.rewaya
            clickListner?.let { holder.initialize(filterList, it,position) }

        }else{
            val list = readerlist[position]
            holder.name.text = list.name
            holder.cont.text = "${list.count}"
            holder.rewya.text = list.rewaya
            clickListner?.let { holder.initialize(readerlist, it,position) }

        }
    }

    private var readerlist = emptyList<Reciter>()
    var name = null

    inner class ViewHOLDER(item: View) : RecyclerView.ViewHolder(item) {
        var name = item.name_Readers
        var cont = item.contSours
        var rewya = item.rwya

        fun initialize(item: List<Reciter>, action: OnCarItemClickListner2,position: Int) {
            itemView.setOnClickListener {
                action.onItemClick(it, item, position)
            }

        }

    }
    fun setdata(reader: List<Reciter>) {
        readerlist = reader
        notifyDataSetChanged()
        filterList = readerlist
    }

    override fun getSections(): Array<String> {
        for (i  in readerlist.indices){
            var kay = readerlist.get(i).name?.get(0)
            if (!arrayfristkay.contains(kay.toString().replace("ا", "أ").replace("إ", "أ").replace("آ", "أ"))){
                arrayfristkay.add(kay.toString().replace("ا", "أ").replace("إ", "أ").replace("آ", "أ"))
                arrayfristpoition.add(i)
            }else{

            }
        }
        return arrayfristkay.toTypedArray()
    }
    override fun getSectionForPosition(position: Int): Int {
        return 0
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        return arrayfristpoition.get(sectionIndex)
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = readerlist
                } else {
                    val resultList = mutableListOf<Reciter>()
                    for (row in readerlist) {
                        if (row.name?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT))!!) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as  List<Reciter>
                notifyDataSetChanged()
            }

        }
    }

}

interface OnCarItemClickListner2 {
    fun onItemClick(view: View, item: List<Reciter>, position: Int)
}