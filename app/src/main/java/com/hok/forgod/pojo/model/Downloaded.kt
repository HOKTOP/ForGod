package com.hok.forgod.pojo.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Downloaded(
    @PrimaryKey(autoGenerate = true)
    var id:Int?,
    @ColumnInfo()
    var privetid:String,
    @ColumnInfo()
    var nameReciter:String,
    @ColumnInfo()
    var nameSora:String,
    @ColumnInfo()
    var url: String,
    @ColumnInfo()
    var patch:String
)