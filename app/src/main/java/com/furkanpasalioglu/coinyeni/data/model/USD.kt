package com.furkanpasalioglu.coinyeni.data.model


import com.google.gson.annotations.SerializedName

data class USD(
    @SerializedName("alis")
    val alis: String,
    @SerializedName("degisim")
    val degisim: String,
    @SerializedName("satis")
    val satis: String
)