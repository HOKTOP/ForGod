package com.hok.forgod.pojo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Suors")
class Sura(
    @SerializedName("id")
    @Expose
    var id: String? = null
    ,
    @SerializedName("name")
    @Expose
    var name: String? = null

)