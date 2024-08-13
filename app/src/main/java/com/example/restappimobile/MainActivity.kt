package com.example.restappimobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.restappimobile.databinding.ActivityMainBinding
import com.example.restappimobile.response.responseGetUser
import com.example.restappimobile.retrofit.APIRest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var btn: Button ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.mostrarBtn)

        btn!!.setOnClickListener { getUsers() }

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            var call = getRetrofit().create(APIRest::class.java).getUsers()
            var users = call.body()
            runOnUiThread{
                if(call.isSuccessful){
                    Log.i("Usuarios:", users.toString())
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this@MainActivity, "Ocurrio un error", Toast.LENGTH_LONG).show();
    }

}