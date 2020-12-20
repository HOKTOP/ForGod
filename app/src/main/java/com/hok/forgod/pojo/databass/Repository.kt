package com.hok.forgod.pojo.databass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hok.forgod.pojo.model.Downloaded
import com.hok.forgod.pojo.model.ReadersSelected
import com.hok.forgod.pojo.model.Reciter
import com.hok.forgod.pojo.model.Reciteren

class Repository(private val DAO:DaoROOM) {

    val allname: LiveData<List<Reciter>> = DAO.getallnamereciter()
    val allDownloaded: LiveData<List<Downloaded>> = DAO.getalldatadownloaded()
    var checkDownloaded: LiveData<List<Downloaded>>? = null


    //    val allsuors: LiveData<List<Sura>> = DAO.gettableSura()
    //insertReaders
    suspend fun insert(word: Reciter) {
        DAO.insert(word)
    }
    suspend fun deletdatareciter(){
        DAO.deleteAll()
    }
    suspend fun insertDownloaded(Download: Downloaded) {
        DAO.insertDownloaded(Download)
    }
    fun checkdatadownloaded(priviteid: String):LiveData<List<Downloaded>>?{
        checkDownloaded =  DAO.CHECKIFDOWNLOADED(priviteid)
        if (checkDownloaded !=null){
            return checkDownloaded!!
        }else{
            return null
        }
    }

//    //insertsuora
//    suspend fun insertsours(soura: Sura) {
//        DAO.insertsuora(soura)
//    }
//    suspend fun deletsuoer() {
//        DAO.deleteAllsours()
//    }
}