package com.example.restappimobile.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class User(
   @SerializedName("id_costumer") val id_costumer: String? = null,
   @SerializedName("nombre") var nombre : String,
   @SerializedName("apellidos") var apellidos: String,
   @SerializedName("telefono") var telefono: String,
   @SerializedName("email") val email: String,
   @SerializedName("password") val password: String,
   @SerializedName("image") var image: String? = null,
   @SerializedName("session_token") val sessionToken: String? = null,
   @SerializedName("notification_token") var notificationToken: String? = null,
   @SerializedName("is_available") val isAvailable: Boolean? = null,
   @SerializedName("roles") val roles:ArrayList<Rol>? = null
) {

   override fun toString(): String {
      return "$nombre $apellidos"
   }

   fun toJson(): String{
      return Gson().toJson(this)
   }
}