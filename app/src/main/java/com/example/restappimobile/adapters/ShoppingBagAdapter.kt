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


class ShoppingBagAdapter (val context: Activity, val products: ArrayList<Product>): RecyclerView.Adapter<ShoppingBagAdapter.ShoppingBagViewHolder>(){


    val sharedPref = SharedPref(context)

    init {
        (context as ClientShoppingBagActivity).setTotal(getTotal())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingBagViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_shopping_bag,parent, false)
        return ShoppingBagViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ShoppingBagViewHolder, position: Int){
        val product = products  [position] //Devuelve cada categoria

        holder.textViewName.text = product.nombre
        holder.textViewCounter.text = "${product.cantidad}"

        if (product.cantidad != null) {
            holder.textViewPrice.text = "${product.precio * product.cantidad!!}$"
        }
        Glide.with(context).load(product.image1).into(holder.imageViewProduct)

        holder.imageViewAdd.setOnClickListener { addItem (product, holder) }
        holder.imageViewRemove.setOnClickListener { removeItem (product, holder) }
        holder.imageViewDelete.setOnClickListener { deleteItem (position) }

        //holder.itemView.setOnClickListener { goToDetail(product) }

    }
    private fun getTotal(): Double {
        var total = 0.0
        for (p in products) {
            if (p.cantidad != null) {
                total = total + (p.cantidad!! * p.precio)
            }
        }
        return total
    }

    private fun getIndexOf(idProduct: String): Int {
        var pos = 0

        for (p in products) {
            if (p.id == idProduct) {
                return pos
            }
            pos++
        }
        return -1
    }

    private fun deleteItem(position: Int) {
        products.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, products.size)
        sharedPref.save("order", products)
        (context as ClientShoppingBagActivity).setTotal(getTotal())
    }

    private fun addItem(product: Product, holder: ShoppingBagViewHolder) {

        val index = getIndexOf(product.id!!)
        product.cantidad = product.cantidad!! + 1
        products[index].cantidad = product.cantidad

        holder.textViewCounter?.text = "${product?.cantidad}"
        holder.textViewPrice?.text = "${product.cantidad!! * product.precio}$"

        sharedPref.save("order", products)
        (context as ClientShoppingBagActivity).setTotal(getTotal())
    }
    private fun removeItem(product: Product, holder: ShoppingBagViewHolder) {
        if (product.cantidad!! > 1) {
            val index = getIndexOf(product.id!!)
            product.cantidad = product.cantidad!! - 1
            products[index].cantidad = product.cantidad

            holder.textViewCounter?.text = "${product?.cantidad}"
            holder.textViewPrice?.text = "${product.cantidad!! * product.precio}$"

            sharedPref.save("order", products)
            (context as ClientShoppingBagActivity).setTotal(getTotal())
        }
    }

    private fun goToDetail(product: Product) {
            val i = Intent(context, ClientProductsDetailActivity::class.java)
            i.putExtra("product", product.toJson())
            context.startActivity(i)
        }


    class ShoppingBagViewHolder(view: View): RecyclerView.ViewHolder(view){

        val textViewName : TextView
        val textViewPrice : TextView
        val textViewCounter : TextView
        val imageViewProduct : ImageView
        val imageViewAdd : ImageView
        val imageViewRemove : ImageView
        val imageViewDelete : ImageView

        init {
            textViewName =view.findViewById(R.id.textview_name)
            textViewPrice =view.findViewById(R.id.textview_price)
            textViewCounter =view.findViewById(R.id.textview_counter)
            imageViewProduct =view.findViewById(R.id.imageview_product)
            imageViewAdd  =view.findViewById(R.id.imageview_add)
            imageViewRemove =view.findViewById(R.id.imageview_remove)
            imageViewDelete =view.findViewById(R.id.imageview_delete)
        }
    }


}