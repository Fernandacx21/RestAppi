package com.example.restappimobile.providers

import com.example.restappimobile.api.MercadoPagoApiRoutes
import com.example.restappimobile.models.MercadoPagoCardTokenBody
import com.example.restappimobile.routes.MercadoPagoRoutes
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call

class MercadoPagoProvider {

    var mercadoPagoRoutes : MercadoPagoRoutes? = null

    init {
        val api = MercadoPagoApiRoutes()
        mercadoPagoRoutes = api.getMercadoPagoRoutes()
    }

    fun getInstallments(bin: String, amount: String): Call<JsonArray>?{
        return mercadoPagoRoutes?.getInstallments(bin, amount)
    }

    fun createCardToken(mercadoPagoCardTokenBody: MercadoPagoCardTokenBody): Call<JsonObject>? {
        return mercadoPagoRoutes?.createCardToken(mercadoPagoCardTokenBody)
    }

}