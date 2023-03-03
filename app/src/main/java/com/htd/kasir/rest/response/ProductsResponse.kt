package com.htd.kasir.rest.response

import com.google.gson.annotations.SerializedName
import com.htd.kasir.model.Penjualan
import com.htd.kasir.model.Product

data class ProductsResponse (
    @SerializedName("message")
    var message:String,
    @SerializedName("status")
    var status:String,
    @SerializedName("data")
    var data:ArrayList<Product>,
)