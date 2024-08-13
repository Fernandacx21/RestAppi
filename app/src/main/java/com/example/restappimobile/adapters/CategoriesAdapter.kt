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
import com.example.restappimobile.activities.client.products.list.ClientProductsListActivity
import com.example.restappimobile.models.Category
import com.example.restappimobile.utils.SharedPref

class CategoriesAdapter (val context: Activity, val categories: ArrayList<Category>): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    val sharedPref = SharedPref(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_categories, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = categories[position] //Devuelve cada categoria

        holder.textViewCategory.text = category.nombre
        Glide.with(context).load(category.image).into(holder.imageViewCategory)

        holder.itemView.setOnClickListener { goToProducts(category) }

    }

    private fun goToProducts(category: Category) {
        val i = Intent(context, ClientProductsListActivity::class.java)
        i.putExtra("idCategory", category.id)
        context.startActivity(i)
    }

    class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewCategory: TextView
        val imageViewCategory: ImageView

        init {
            textViewCategory = view.findViewById(R.id.textview_category)
            imageViewCategory = view.findViewById(R.id.image_view_category)
        }
    }
}