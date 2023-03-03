package com.htd.kasir.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("shortname")
    var shortname: String,
    @SerializedName("code")
    var code: String,
    @SerializedName("stock")
    var stock: String,
    @SerializedName("pic")
    var pic: String,
    @SerializedName("created_at")
    var createdAt: String,
)
