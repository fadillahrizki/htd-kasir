package com.htd.kasir.model

import com.google.gson.annotations.SerializedName

data class Penjualan(
    @SerializedName("user")
    var user:User,
    @SerializedName("total")
    var total:String,
    @SerializedName("paytotal")
    var paytotal:String,
    @SerializedName("return_total")
    var returnTotal:String,
    @SerializedName("status")
    var status:String,
    @SerializedName("inv_code")
    var invoice:String,
    @SerializedName("notes")
    var notes:String,
    @SerializedName("created_at")
    var date:String,
)
