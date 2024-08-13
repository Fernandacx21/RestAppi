package com.example.restappimobile.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class Product (
    @SerializedName("id") val id: String? = null,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("image1") val image1: String? = null,
    @SerializedName("image2") val image2: String? = null,
    @SerializedName("image3") val image3: String? = null,
    @SerializedName("id_category") val idCategory : String,
    @SerializedName("precio") val precio: Double,
    @SerializedName("cantidad") var cantidad: Int? = null,

){

    fun toJson(): String{
        return Gson().toJson(this)
    }

    override fun toString(): String {
        return "Product(id='$id', nombre='$nombre', descripcion='$descripcion', image1='$image1', image2='$image2', image3='$image3', idCategory='$idCategory', precio=$precio, cantidad=$cantidad)"
    }


}