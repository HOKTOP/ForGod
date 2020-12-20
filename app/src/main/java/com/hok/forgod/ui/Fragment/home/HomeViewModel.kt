package com.hok.forgod.ui.Fragment.home

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.hok.forgod.pojo.RetrofitServics.serviceRetrofit
import com.hok.forgod.pojo.databass.Repository
import com.hok.forgod.pojo.databass.Room
import com.hok.forgod.pojo.model.*
import com.hok.forgod.ui.Settingapp
import com.zeugmasolutions.localehelper.LocaleAwareApplication
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import com.zeugmasolutions.localehelper.LocaleHelper
import com.zeugmasolutions.localehelper.Locales
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.util.*

class HomeViewModel(appliction:Application) : AndroidViewModel(appliction) {
    var context: Context = appliction.applicationContext
    var surslist:MutableList<Sura> = mutableListOf<Sura>()
  private  var surslistSurafilterrecycler:MutableList<Sura> = mutableListOf<Sura>()
    var RecitersResponse = MutableLiveData<List<Reciter>>()
    var TrakLIVE = MutableLiveData<List<Traklist>>()
    var SuraResponse = MutableLiveData<List<Sura>>()
    var Surafilterrecycler = MutableLiveData<List<Sura>>()
    val ReadDBRciters: LiveData<List<Reciter>>
    val ReadDBDownloaded: LiveData<List<Downloaded>>
    lateinit var CheckDBDownloaded: LiveData<List<Downloaded>>
   private val Reposetrydb:Repository
    private var  listTrack:MutableList<Traklist> = mutableListOf<Traklist>()
lateinit var ReaderData:Call<Reciters>
    init {
        //install db and call
        val userdo = Room.getDatabase(appliction).wordDao()
        Reposetrydb= Repository(userdo)
        ReadDBRciters = Reposetrydb.allname
        ReadDBDownloaded = Reposetrydb.allDownloaded
    }
    //get data form api
    fun  getReaderonlien(){
        if (Settingapp().selectlanguages(context).equals("ar")){
            ReaderData = serviceRetrofit().creatlinkService().getReaders()

        }else{
            ReaderData = serviceRetrofit().creatlinkService().getReadersen()

        }
        ReaderData.enqueue(object : Callback<Reciters>{
            override fun onFailure(call: Call<Reciters>, t: Throwable) {
                Log.d("Erorr connect","${t.message}")

            }

            override fun onResponse(call: Call<Reciters>, response: Response<Reciters>) {
                RecitersResponse.value = response.body()?.reciters
                if (ReadDBRciters.value?.size ==0){
                    addReaderdb(response.body()?.reciters!!)
                }else if (!response.body()?.reciters?.get(0)?.name?.equals(ReadDBRciters.value?.get(0)?.name)!!)
                {

                    viewModelScope.launch {
                        Reposetrydb.deletdatareciter()
                        addReaderdb(response.body()?.reciters!!)
                    }

                }
            }

        })
    }
    //get data form local json
    fun  getSoraoffonlien(){
        var input:InputStream? = null
        var Json:String
        try {
            if(Settingapp().selectlanguages(context).equals("ar")){
                input = context.assets.open("soura.json")
            }else{
                input = context.assets.open("souraEN.json")
            }
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            Json = String(buffer)
            var jsonArray = JSONArray(Json)
            for (i:Int  in 0.. jsonArray.length()){
                var jsonobject = jsonArray.getJSONObject(i)
                surslist.add(Sura(jsonobject.get("id").toString(),jsonobject.get("name").toString()))
            }
        }catch (e:Exception){}finally {
            input?.close()
        }

        SuraResponse.value = surslist
    }
    //filter sours show in recycler
    fun filterrecycler(position:Int) : List<Sura>{
        viewModelScope.launch {
            var reciter =  ReadDBRciters.value?.get(position)?.suras?.split(",")?.map { it.toInt() }
            if (surslistSurafilterrecycler.size >0){
                surslistSurafilterrecycler.clear()
            }
            for (i in SuraResponse.value?.indices!!){
                if (reciter?.contains(SuraResponse.value?.get(i)?.id?.toInt())!!){
                    surslistSurafilterrecycler.add(surslist[i])
                }
            }
        }
        Surafilterrecycler.value = surslistSurafilterrecycler
      return Surafilterrecycler.value!!
    }
    //add data to Reader table
    fun addReaderdb(reciter: List<Reciter>){
        viewModelScope.launch(Dispatchers.IO){
            for (i in reciter.indices){
                Reposetrydb.insert(reciter[i])
            }

        }
    }
    fun addReaderdben(reciter: List<Reciter>){
        viewModelScope.launch(Dispatchers.IO){
            for (i in reciter.indices){
            }

        }
    }
    //fun Creator Traklist
    fun Traklist(positionReciter: Int,listSura: List<Sura>,ListReaders:List<Reciter>){
        viewModelScope.launch {
            if (listTrack.size != 0){
                listTrack.clear()
            }
            for (i in listSura.indices){
                if (listSura.get(i).id?.toInt()!!  >=0 && listSura.get(i).id?.toInt()!!  < 10) {
                    listTrack.add(Traklist(listSura[i].id!!.toInt(),listSura[i].name.toString(),ListReaders?.get(positionReciter)?.name!!,"${ListReaders!![positionReciter].server}/00${listSura.get(i).id}.mp3"))
                }else if (listSura.get(i).id?.toInt()!!  >=10 && listSura.get(i).id?.toInt()!!  < 100){
                    listTrack.add(Traklist(listSura[i].id!!.toInt(),listSura[i].name.toString(),ListReaders?.get(positionReciter)?.name!!,"${ListReaders!![positionReciter].server}/0${listSura.get(i).id}.mp3"))
                }
                else if (listSura.get(i).id?.toInt()!!  >=100){
                    listTrack.add(Traklist(listSura[i].id!!.toInt(),listSura[i].name.toString(),ListReaders?.get(positionReciter)?.name!!,"${ListReaders!![positionReciter].server}/${listSura.get(i).id}.mp3"))


                }
            }
            TrakLIVE.value = listTrack
        }

    }
    //Insert data downloaded in db
    fun insertdownloaddb(downloaded: List<Downloaded>){
        viewModelScope.launch(Dispatchers.IO) {
            for (i in downloaded.indices){
                Reposetrydb.insertDownloaded(downloaded[i])
            }
        }
    }
    // check if this data downloaded in db
    fun checkdownloaddb(privetid: String):Boolean{
        Reposetrydb.checkdatadownloaded(privetid)
        CheckDBDownloaded = Reposetrydb.checkDownloaded!!
        if (CheckDBDownloaded.value != null){
            return true
        }else{
            return false
        }
    }
}