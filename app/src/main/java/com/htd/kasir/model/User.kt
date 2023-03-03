package com.htd.kasir.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id:String,
    @SerializedName("name")
    var name:String,
    @SerializedName("username")
    var username:String,
    @SerializedName("password")
    var password:String
)