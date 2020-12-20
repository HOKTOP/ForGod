package com.hok.forgod.ui.mvactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hok.forgod.pojo.RetrofitServics.serviceRetrofit
import com.hok.forgod.pojo.model.Data
import com.hok.forgod.pojo.model.Datatimesalat
import com.hok.forgod.pojo.model.Timesalat
import com.hok.forgod.pojo.model.Timings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MVMenuapp :ViewModel(){
    var salattimes = MutableLiveData<Timings>()

    fun getsalattimes(city:String,Country:String){
        var get = serviceRetrofit().gettimesalat().gettime(city,Country,5)
        get.enqueue(object :Callback<Timesalat>{
            override fun onFailure(call: Call<Timesalat>, t: Throwable) {
                println("erorr api:${t.message}")
            }

            override fun onResponse(call: Call<Timesalat>, response: Response<Timesalat>) {
                salattimes.value = response.body()?.data?.timings

            }


        })
    }
}