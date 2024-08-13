package com.example.restappimobile.api

import com.example.restappimobile.routes.*

class MercadoPagoApiRoutes {
    //Verificar que siempre sea la misma ip para que funcione
    val API_URL = "https://api.mercadopago.com/"
    val  retrofit = RetrofitClient()

    fun getMercadoPagoRoutes(): MercadoPagoRoutes{
        return retrofit.getClient(API_URL).create(MercadoPagoRoutes::class.java)
    }


}