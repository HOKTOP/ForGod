package com.hok.forgod.pojo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.shockwave.pdfium.PdfDocument.Meta


 class Datatimesalat {
     @SerializedName("timings")
     @Expose
     var timings: Timings? = null

 }