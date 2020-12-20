package com.hok.forgod.ui.mvactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hok.forgod.pojo.databass.Repository
import com.hok.forgod.pojo.databass.Room
import com.hok.forgod.pojo.model.Downloaded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewM(appliction:Application) :AndroidViewModel(appliction){

    private val Reposetrydb: Repository
    val ReadDBDownloaded: LiveData<List<Downloaded>>
    init {
        val userdo = Room.getDatabase(appliction).wordDao()
        Reposetrydb= Repository(userdo)
        ReadDBDownloaded = Reposetrydb.allDownloaded
    }

    fun insertdownloaddb(downloaded: List<Downloaded>){
        viewModelScope.launch(Dispatchers.IO) {
            for (i in downloaded.indices){
                Reposetrydb.insertDownloaded(downloaded[i])
            }
        }
    }
}