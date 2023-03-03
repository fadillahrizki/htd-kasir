package com.htd.kasir.rest

import com.htd.kasir.rest.response.ProductsResponse
import com.htd.kasir.rest.response.TransactionsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @FormUrlEncoded
    @POST("/index.php?r=api/business/findByToken")
    fun getBusiness(
        @Field("token") token: String,
        @Field("note") note: String
    ): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @FormUrlEncoded
    @POST("/index.php?r=api/auth/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @GET("/index.php?r=api/application/index")
    fun application(@Header("Authorization") token: String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @GET("/index.php?r=api/products/index")
    fun products(@Header("Authorization") token: String): Call<ProductsResponse>

    @Headers("X-MINIPOS-APP: minipos")
    @GET("/index.php?r=api/transactions/index")
    fun transactions(@Header("Authorization") token: String): Call<TransactionsResponse>

    @Headers("X-MINIPOS-APP: minipos")
    @GET("/index.php?r=api/transactions/view")
    fun transaction(@Header("Authorization") token: String,@Query("id") transactionId : String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @POST("/index.php?r=api/transactions/delete-item")
    fun deleteItem(@Header("Authorization") token: String,@Query("id") transactionItemId : String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @POST("/index.php?r=api/transactions/pay")
    fun pay(@Header("Authorization") token: String,@Query("id") transactionItemId : String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @FormUrlEncoded
    @POST("/index.php?r=api/transactions/mass-pay")
    fun massPay(@Header("Authorization") token: String,@Query("id") transactionId : String, @Field("total") total: String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @FormUrlEncoded
    @POST("/index.php?r=api/transactions/create&status=order")
    fun order(@Header("Authorization") token: String, @Field("items") items: Any, @Field("notes") notes: String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @FormUrlEncoded
    @POST("/index.php?r=api/transactions/create&status=pay")
    fun orderPay(@Header("Authorization") token: String, @Field("items") items: Any, @Field("notes") notes: String, @Field("paytotal") paytotal: String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @FormUrlEncoded
    @POST("/index.php?r=api/transactions/update")
    fun appendOrderItems(@Header("Authorization") token: String,@Query("id") transactionId : String, @Field("items") items: Any): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @GET("/index.php?r=api/transactions/omset")
    fun omset(@Header("Authorization") token: String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @GET("/index.php?r=api/withdrawals/index")
    fun withdrawals(@Header("Authorization") token: String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @FormUrlEncoded
    @POST("/index.php?r=api/withdrawals/create")
    fun createWithdrawal(@Header("Authorization") token: String,@Field("amount") amount: String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @GET("/index.php?r=api/withdrawals/delete")
    fun deleteWithdrawal(@Header("Authorization") token: String,@Query("id") withdrawalId : String): Call<Any>

    @Headers("X-MINIPOS-APP: minipos")
    @FormUrlEncoded
    @POST("/index.php?r=api/withdrawals/total")
    fun totalWithdrawal(@Header("Authorization") token: String,@Field("amount") amount: String): Call<Any>

}