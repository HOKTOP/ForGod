package com.hok.forgod.pojo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Reciteren (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()

    var idkey: Int? = null
,
    @ColumnInfo()

    @SerializedName("id")
    @Expose
    var id: Int? = null
,
    @ColumnInfo()
    @SerializedName("name")
    @Expose
    var name: String? = null
,
    @ColumnInfo()
    @SerializedName("Server")
    @Expose
    var server: String? = null
,
    @ColumnInfo()
    @SerializedName("rewaya")
    @Expose
    var rewaya: String? = null
,
    @ColumnInfo()
    @SerializedName("count")
    @Expose
    var count: String? = null
,
    @ColumnInfo()
    @SerializedName("letter")
    @Expose
    var letter: String? = null
,
    @ColumnInfo()
    @SerializedName("suras")
    @Expose
    var suras: String? = null

)