package com.example.restappimobile.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restappimobile.R
import com.example.restappimobile.activities.client.home.ClientHomeActivity
import com.example.restappimobile.activities.client.products.detail.ClientProductsDetailActivity
import com.example.restappimobile.activities.client.shopping_bag.ClientShoppingBagActivity
import com.example.restappimobile.activities.delivery.home.DeliveryHomeActivity
import com.example.restappimobile.activities.restaurant.home.RestaurantHomeActivity
import com.example.restappimobile.models.Category
import com.example.restappimobile.models.Product
import com.example.restappimobile.models.Rol
import com.example.restappimobile.utils.SharedPref
import com.google.gson.Gson


class OrderProductsAdapter (val context: Activity, val products: ArrayList<Product>): RecyclerView.Adapter<OrderProductsAdapter.OrderProductsViewHolder>(){


    val sharedPref = SharedPref(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductsViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_order_products,parent, false)
        return OrderProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: OrderProductsViewHolder, position: Int){
        val product = products  [position] //Devuelve cada categoria

        holder.textViewName.text = product.nombre


        if (product.cantidad != null) {
            holder.textViewQuantity.text = "${product.cantidad}"
        }
        Glide.with(context).load(product.image1).into(holder.imageViewProduct)
    }

    class OrderProductsViewHolder(view: View): RecyclerView.ViewHolder(view){

        val imageViewProduct : ImageView
        val textViewName : TextView
        val textViewQuantity : TextView

        init {
            imageViewProduct =view.findViewById(R.id.imageview_product)
            textViewName =view.findViewById(R.id.textview_name)
            textViewQuantity =view.findViewById(R.id.textview_quantity)
        }
    }


}