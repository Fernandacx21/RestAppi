package com.example.restappimobile.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class Order (
    @SerializedName ("id") val id: String? = null,
    @SerializedName ("id_cliente") val idCliente: String,
    @SerializedName ("id_delivery") var idDelivery: String? = null,
    @SerializedName ("id_direccion") val idDireccion: String,
    @SerializedName ("status") val status: String? = "PAGADO",
    @SerializedName ("timestamp") val timestamp: String? = null,
    @SerializedName ("products") val products: ArrayList<Product>,
    @SerializedName ("productos") val productos: ArrayList<Product>,
    @SerializedName ("client") val client : User? = null,
    @SerializedName("delivery") val delivery: User? = null,
    @SerializedName ("address") val address: Address? = null,
    @SerializedName ("lat") var lat: Double? = null,
    @SerializedName ("lng") var lng: Double? = null

) {

    fun toJson(): String {
        return Gson().toJson(this)
    }

    override fun toString(): String {
        return "Order(id=$id, idCliente='$idCliente', idDelivery=$idDelivery, idDireccion='$idDireccion', status=$status, timestamp='$timestamp', products=$products,productos=$productos, client=$client, delivery=$delivery, address=$address)"
    }


}