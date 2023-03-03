package com.htd.kasir.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCrm {
    const val BASE_URL = "https://crm.htd-official.com"
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}