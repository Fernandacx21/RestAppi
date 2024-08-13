package com.example.restappimobile.routes

import com.example.restappimobile.models.MercadoPagoCardTokenBody
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MercadoPagoRoutes {

    @GET("v1/payment_methods/installments?access_token=TEST-6480206115598771-112212-c78fb2ce7d3b852b225f05b5aeacffe8-329793296")
    fun getInstallments(@Query("bin") bin: String, @Query("amount") amount: String): Call<JsonArray>



    @POST("v1/card_tokens?public_key=TEST-72bca123-5476-46d3-a6e7-4f503852ff41")
    fun createCardToken(@Body body: MercadoPagoCardTokenBody): Call<JsonObject>
}