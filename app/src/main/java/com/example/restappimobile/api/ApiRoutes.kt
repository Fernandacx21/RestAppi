package com.example.restappimobile.api

import com.example.restappimobile.routes.*

class ApiRoutes {
    //Verificar que siempre sea la misma ip para que funcione
    val API_URL = "http://192.168.1.64:3000/api/"
    val  retrofit = RetrofitClient()

    fun getUsersRoutes(): UsersRoutes{
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }
    fun getUsersRoutesWithToken(token: String): UsersRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(UsersRoutes::class.java)
    }

    fun getCategoriesRoutes(token: String): CategoriesRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(CategoriesRoutes::class.java)
    }

    fun getAddressRoutes(token: String): AddressRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(AddressRoutes::class.java)
    }

    fun getOrdersRoutes(token: String): OrdersRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(OrdersRoutes::class.java)
    }

    fun getProductsRoutes(token: String): ProductsRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(ProductsRoutes::class.java)
    }

    fun getPaymentsRoutes(token: String): PaymentsRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(PaymentsRoutes::class.java)
    }

}