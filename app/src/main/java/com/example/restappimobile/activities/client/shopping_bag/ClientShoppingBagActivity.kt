package com.example.restappimobile.activities.client.shopping_bag

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restappimobile.R
import com.example.restappimobile.activities.client.address.create.ClientAddressCreateActivity
import com.example.restappimobile.activities.client.address.list.ClientAddressListActivity
import com.example.restappimobile.adapters.ShoppingBagAdapter
import com.example.restappimobile.models.Product
import com.example.restappimobile.utils.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ClientShoppingBagActivity : AppCompatActivity() {

    var recyclerViewShoppingBag : RecyclerView? = null
    var textViewTotal : TextView? = null
    var buttonNext : Button? = null
    var toolbar : Toolbar?  = null

    var adapter : ShoppingBagAdapter? = null
    var sharedPref: SharedPref? = null
    var gson = Gson()
    var selectedProducts = ArrayList<Product>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_shopping_bag)

        sharedPref = SharedPref(this)


        recyclerViewShoppingBag = findViewById(R.id.recyclerview_shopping_bag)
        textViewTotal = findViewById(R.id.textview_total)
        buttonNext = findViewById(R.id.btn_next)
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        toolbar?.title = "Tu orden"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerViewShoppingBag?.layoutManager = LinearLayoutManager(this)
        getProductsFromSharedPref()

        buttonNext?.setOnClickListener { goToAddressList() }

    }
    private fun goToAddressList() {
        val i = Intent(this, ClientAddressListActivity::class.java)
        startActivity(i)
    }

    fun setTotal(total: Double) {
        textViewTotal?.text = "${total}$"
    }

    private fun getProductsFromSharedPref() {

        if (!sharedPref?.getData("order").isNullOrBlank()) { //Valida si existe una orden en SharedPref
            val type = object: TypeToken<ArrayList<Product>>() {}.type
            selectedProducts = gson.fromJson(sharedPref?.getData("order"), type)

            adapter = ShoppingBagAdapter(this,selectedProducts)
            recyclerViewShoppingBag?.adapter = adapter
        }
    }

}