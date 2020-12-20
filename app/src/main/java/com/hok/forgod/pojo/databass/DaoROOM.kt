package com.hok.forgod.pojo.databass

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hok.forgod.pojo.model.Downloaded
import com.hok.forgod.pojo.model.ReadersSelected
import com.hok.forgod.pojo.model.Reciter
import com.hok.forgod.pojo.model.Reciteren

@Dao
interface  DaoROOM{

    //Query databass table reciter
    @Query("SELECT * from reciter ORDER BY name ASC")
    fun getallnamereciter(): LiveData<List<Reciter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reciter: Reciter)


    @Query("DELETE FROM reciter")
    suspend fun deleteAll()


    /////EN

    //Query databass table Suors

    @Query("SELECT * FROM READERSSELECTED WHERE idSoura = :id")
    fun loadAllSoura(id: Int?):LiveData<List<ReadersSelected>>

    @Query("SELECT * from ReadersSelected ")
    fun gettableSura():LiveData<List<ReadersSelected>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertsuora(sura: ReadersSelected)
    @Query("DELETE FROM ReadersSelected")
    suspend fun deleteAllsours()

    ///Downloaded
    //inSRT DATA
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownloaded(Downloaded:Downloaded)
    //getalldata
    @Query("SELECT *FROM Downloaded")
    fun getalldatadownloaded():LiveData<List<Downloaded>>
    @Query("SELECT * FROM Downloaded WHERE privetid = :id")
    fun CHECKIFDOWNLOADED(id: String?):LiveData<List<Downloaded>>

}