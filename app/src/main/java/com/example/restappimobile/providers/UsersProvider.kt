package com.example.restappimobile.providers


import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.restappimobile.api.ApiRoutes
import com.example.restappimobile.models.Category
import com.example.restappimobile.models.ResponseHttp
import com.example.restappimobile.models.User
import com.example.restappimobile.routes.UsersRoutes
import com.example.restappimobile.utils.SharedPref
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout.TabGravity
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UsersProvider(val token: String? = null) {

    val TAG = "UsersProvider"

    private var usersRoutes : UsersRoutes? = null
    private var usersRoutesToken : UsersRoutes? = null

    init {
        val api = ApiRoutes()
        usersRoutes = api.getUsersRoutes()

        if (token != null){
            usersRoutesToken = api.getUsersRoutesWithToken(token!!)
        }
    }

    fun createToken(user: User, context: Activity){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            val sharedPref = SharedPref(context)

            user.notificationToken = token

            sharedPref.save("user", user)

            updateNotificationToken(user)?.enqueue(object: Callback<ResponseHttp>{
                override fun onResponse(call: Call<ResponseHttp>,response: Response<ResponseHttp>){
                    if (response.body() == null){
                        Log.d(TAG, "Hubo un error al crear el token")
                    }
                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }

            })

            // Get new FCM registration token

            Log.d(TAG, "TOKEN DE NOTIFICACIONES $token")

        })
    }

    fun getDeliveryMen(): Call<ArrayList<User>>?{
        return usersRoutesToken?.getDeliveryMen(token!!)
    }

    fun  register(user: User): Call<ResponseHttp>?{
        return usersRoutes?.register(user)
    }

    fun  login(email: String, password: String): Call<ResponseHttp>?{
        return usersRoutes?.login(email,password)
    }

    fun  updateWithoutImage(user: User): Call<ResponseHttp>?{
        return usersRoutesToken?.updateWithoutImage(user, token!!)
    }

    fun  updateNotificationToken(user: User): Call<ResponseHttp>?{
        return usersRoutesToken?.updateNotificationToken(user, token!!)
    }


    fun update(file: File, user: User): Call<ResponseHttp>? {
        val reqFile = RequestBody.create(MediaType.parse("image/*"),file)
        val image = MultipartBody.Part.createFormData("image",file.name, reqFile)
        val requestBody = RequestBody.create(MediaType.parse("text/plain"), user.toJson())

        return usersRoutesToken?.update(image, requestBody, token!!)
    }
}