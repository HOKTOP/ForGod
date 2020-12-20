package com.hok.forgod.pojo.RetrofitServics

import com.hok.forgod.pojo.model.*
import retrofit2.Call
import retrofit2.http.*

interface api {

    @GET("/api/_arabic.json")
    fun getReaders() : Call<Reciters>

    @GET("/api/_english.json")
    fun getReadersen() : Call<Reciters>

    @GET("/al-qraunService/api/soura.json")
    fun getSours() : Call<List<Sura>>

    @Headers("Accept: application/json")
    @GET("timingsByCity")
    fun gettime(@Query("city") city:String, @Query("country") country:String,@Query("method") method:Int ):Call<Timesalat>

    @GET("/notification.json")
    fun getnotification() : Call<Notification>
}