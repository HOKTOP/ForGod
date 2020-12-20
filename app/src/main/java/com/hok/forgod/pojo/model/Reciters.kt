package com.hok.forgod.pojo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hok.forgod.pojo.model.Reciter


class Reciters {
    @SerializedName("reciters")
    @Expose
    var reciters: List<Reciter>? = null

}