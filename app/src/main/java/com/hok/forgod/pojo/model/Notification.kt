package com.hok.forgod.pojo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Notification (
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("bady")
    @Expose
    var bady: String? = null,

    @SerializedName("image")
    @Expose
    var image: String? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null

)