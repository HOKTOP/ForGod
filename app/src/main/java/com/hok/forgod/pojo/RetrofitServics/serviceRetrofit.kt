package com.hok.forgod.pojo.RetrofitServics

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class  serviceRetrofit() {
    fun creatlinkService(): api {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://mp3quran.net")
            .build()
        return retrofit.create(api::class.java)
    }
    fun creatlinkService2(): api {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://hoktopprg.000webhostapp.com")
            .build()
        return retrofit.create(api::class.java)
    }
    fun gettimesalat(): api {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.aladhan.com/v1/")
            .build()
        return retrofit.create(api::class.java)
    }
    fun Notification(): api {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.8")
            .build()
        return retrofit.create(api::class.java)
    }
}