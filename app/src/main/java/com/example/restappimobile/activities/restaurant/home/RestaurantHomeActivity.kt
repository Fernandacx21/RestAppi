package com.example.restappimobile.activities.restaurant.home

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
import com.example.restappimobile.fragments.restaurant.RestaurantCategoryFragment
import com.example.restappimobile.fragments.restaurant.RestaurantOrdersFragment
import com.example.restappimobile.fragments.restaurant.RestaurantProductFragment
import com.example.restappimobile.models.User
import com.example.restappimobile.utils.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class RestaurantHomeActivity : AppCompatActivity() {

    private val TAG = "RestaurantHomeActivity"
    //
    var sharedPref: SharedPref? = null

    var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_home)

        sharedPref = SharedPref(this)
        /*buttonlogout = findViewById(R.id.btn_logout)
        buttonlogout?.setOnClickListener { logout() }*/

        openFragment(RestaurantOrdersFragment())

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.item_home -> {
                    openFragment(RestaurantOrdersFragment())
                    true
                }
                R.id.item_category -> {
                    openFragment(RestaurantCategoryFragment())
                    true
                }
                R.id.item_product -> {
                    openFragment(RestaurantProductFragment())
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
    }

    private fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }


    private fun logout(){
        sharedPref?.remove("user")
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun getUserFromSession(){
        val sharedPref = SharedPref(this)
        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()){
            //SI EL USUARIO EXISTE EN SESIÓN
            val user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
            Log.d(TAG,"Usuario: $user")
        }

    }

}