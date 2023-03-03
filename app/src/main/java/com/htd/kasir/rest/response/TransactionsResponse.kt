package com.htd.kasir.rest.response

import com.google.gson.annotations.SerializedName
import com.htd.kasir.model.Penjualan

data class TransactionsResponse (
    @SerializedName("message")
    var message:String,
    @SerializedName("status")
    var status:String,
    @SerializedName("data")
    var data:ArrayList<Penjualan>,
)