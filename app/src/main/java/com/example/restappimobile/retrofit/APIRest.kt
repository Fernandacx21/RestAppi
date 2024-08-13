package com.example.restappimobile.retrofit

import com.example.restappimobile.response.responseGetUser

import retrofit2.Response
import retrofit2.http.GET

interface APIRest {

    @GET("api/v1/user/getAllUsers")
    suspend fun getUsers(): Response<responseGetUser>

}