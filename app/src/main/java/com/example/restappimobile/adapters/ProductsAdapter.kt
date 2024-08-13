package com.example.restappimobile.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restappimobile.R
import com.example.restappimobile.activities.client.home.ClientHomeActivity
import com.example.restappimobile.activities.client.products.detail.ClientProductsDetailActivity
import com.example.restappimobile.activities.delivery.home.DeliveryHomeActivity
import com.example.restappimobile.activities.restaurant.home.RestaurantHomeActivity
import com.example.restappimobile.models.Category
import com.example.restappimobile.models.Product
import com.example.restappimobile.models.Rol
import com.example.restappimobile.utils.SharedPref
import com.google.gson.Gson


class ProductsAdapter (val context: Activity, val products: ArrayList<Product>): RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>(){


    val sharedPref = SharedPref(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_product,parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int){
        val product = products  [position] //Devuelve cada categoria

        holder.textViewName.text = product.nombre
        holder.textViewPrice.text = "${product.precio}$"
        Glide.with(context).load(product.image1).into(holder.imageViewProduct)

        holder.itemView.setOnClickListener { goToDetail(product) }

    }

    private fun goToDetail(product: Product) {

            val i = Intent(context, ClientProductsDetailActivity::class.java)
            i.putExtra("product", product.toJson())
            context.startActivity(i)
        }


    class ProductsViewHolder(view: View): RecyclerView.ViewHolder(view){

        val textViewName : TextView
        val textViewPrice : TextView
        val imageViewProduct : ImageView

        init {
            textViewName =view.findViewById(R.id.textview_name)
            textViewPrice =view.findViewById(R.id.textview_price)
            imageViewProduct =view.findViewById(R.id.image_view_product)
        }
    }


}