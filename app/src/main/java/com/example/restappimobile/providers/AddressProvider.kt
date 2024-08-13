package com.example.restappimobile.providers

import com.example.restappimobile.api.ApiRoutes
import com.example.restappimobile.models.Address
import com.example.restappimobile.models.Category
import com.example.restappimobile.models.ResponseHttp
import com.example.restappimobile.models.User
import com.example.restappimobile.routes.AddressRoutes
import com.example.restappimobile.routes.CategoriesRoutes
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class AddressProvider(val token:String) {

    private var addressRoutes : AddressRoutes? = null

    init {
        val api = ApiRoutes()
        addressRoutes = api.getAddressRoutes(token)
    }

   fun getAddress(idUser: String): Call<ArrayList<Address>>?{
        return addressRoutes?.getAddress(idUser,token)
    }

    fun create(address: Address): Call<ResponseHttp>? {
        return addressRoutes?.create(address, token)
    }
}