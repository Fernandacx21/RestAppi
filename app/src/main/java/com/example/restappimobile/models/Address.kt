package com.example.restappimobile.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class Address (
    @SerializedName ("id") val id: String? = null,
    @SerializedName ("id_user") val idUser: String,
    @SerializedName ("direccion") val address: String,
    @SerializedName ("colonia") val neigborhood: String,
    @SerializedName ("lat") val lat: Double,
    @SerializedName ("lng") val lng: Double,
) {
    override fun toString(): String {
        return "Address(id=$id, idUser='$idUser', direccion='$address', neigborhood='$neigborhood', lat=$lat, lng=$lng)"
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}