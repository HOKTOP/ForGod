package com.hok.forgod.pojo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReadersSelected(

    @PrimaryKey()
    @ColumnInfo
    var idkey: Int?=null
    ,
    @ColumnInfo
    var nameReader: String
    ,
    @ColumnInfo
    var nameSoura: String
    ,
    @ColumnInfo
    var idSoura: String
    ,
    @ColumnInfo
    var urlSoura: String






)