package com.htd.kasir.model

import com.google.gson.annotations.SerializedName

class LoginModel {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("user_id")
    var userId: String? = null
}