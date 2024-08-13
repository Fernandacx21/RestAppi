package com.example.restappimobile.activities.client.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.restappimobile.R
import com.example.restappimobile.activities.MainActivity
import com.example.restappimobile.fragments.client.ClientCategoriesFragment
import com.example.restappimobile.fragments.client.ClientOrdersFragment
import com.example.restappimobile.fragments.client.ClientProfileFragment
import com.example.restappimobile.models.User
import com.example.restappimobile.providers.UsersProvider
import com.example.restappimobile.utils.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class ClientHomeActivity : AppCompatActivity() {

    private val TAG = "ClientHomeActivity"
    //
    var sharedPref: SharedPref? = null

    var bottomNavigation: BottomNavigationView? = null
    var usersProviders: UsersProvider? = null
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_home)

        sharedPref = SharedPref(this)
        /*buttonlogout = findViewById(R.id.btn_logout)
        buttonlogout?.setOnClickListener { logout() }*/

        openFragment(ClientCategoriesFragment())

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.item_home -> {
                    openFragment(ClientCategoriesFragment())
                    true
                }
                R.id.item_orders -> {
                    openFragment(ClientOrdersFragment())
                    true
                }
                R.id.item_profile -> {
                    openFragment(ClientProfileFragment())
                    true
                }
            else -> false
            }
        }

        getUserFromSession()

        usersProviders = UsersProvider(token = user?.sessionToken!!)
        createToken()
    }

    private fun createToken(){
        usersProviders?.createToken(user!!, this)
    }

    private fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }


    private fun getUserFromSession(){
        val sharedPref = SharedPref(this)
        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()){
            //SI EL USUARIO EXISTE EN SESIÓN
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }

    }

}