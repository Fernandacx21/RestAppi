package com.example.restappimobile.response

import com.google.gson.annotations.SerializedName

data class responseGetUser(@SerializedName("nombre") var nombre: String,
                           @SerializedName("apellidos") var apellidos: String,
                           @SerializedName("direcciones") var direcciones: Array<String>,
                           @SerializedName("telefono") var telefono: Number,
                           @SerializedName("email") var email: String,
                           @SerializedName("password") var password: String)