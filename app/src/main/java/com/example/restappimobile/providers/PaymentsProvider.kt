package com.example.restappimobile.providers

import com.example.restappimobile.api.ApiRoutes
import com.example.restappimobile.models.Category
import com.example.restappimobile.models.MercadoPagoPayment
import com.example.restappimobile.models.ResponseHttp
import com.example.restappimobile.routes.CategoriesRoutes
import com.example.restappimobile.routes.PaymentsRoutes
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class PaymentsProvider(val token:String) {

    private var paymentsRoutes : PaymentsRoutes? = null

    init {
        val api = ApiRoutes()
        paymentsRoutes = api.getPaymentsRoutes(token)
    }

    fun create(mercadoPagoPayment: MercadoPagoPayment): Call<ResponseHttp>? {
        return paymentsRoutes?.createPayment(mercadoPagoPayment, token)
    }
}