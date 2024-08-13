package com.example.restappimobile.models

import com.google.gson.Gson

class Category(
    val id: String? = null,
    val nombre :String,
    val image :String? = null
){
    override fun toString(): String {
        return nombre
    }
    fun toJson(): String{
        return Gson().toJson(this)
    }
}

