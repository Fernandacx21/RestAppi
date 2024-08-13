package com.example.restappimobile.routes

import com.example.restappimobile.models.MercadoPagoPayment
import com.example.restappimobile.models.ResponseHttp
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PaymentsRoutes {

    @POST("payments/create")
    fun createPayment(
        @Body mercadoPagoPayment: MercadoPagoPayment,
        @Header ("Authorization") token: String
    ):Call<ResponseHttp>

}