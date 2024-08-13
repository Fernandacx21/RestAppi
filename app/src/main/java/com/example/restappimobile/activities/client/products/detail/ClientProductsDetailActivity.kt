package com.example.restappimobile.activities.client.products.detail

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.withStyledAttributes
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.restappimobile.R
import com.example.restappimobile.models.Product
import com.example.restappimobile.utils.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.system.exitProcess

class ClientProductsDetailActivity : AppCompatActivity() {

    val TAG = "ProductsDetail"
    var product: Product? = null
    val gson = Gson()

    var imageSlider: ImageSlider? = null
    var textViewName: TextView? = null
    var textViewDescription: TextView? = null
    var textViewPrice: TextView? = null
    var textViewCounter: TextView? = null
    var imageViewAdd: ImageView? = null
    var imageViewRemove: ImageView? = null
    var buttonAdd: Button? = null

    var counter = 1
    var productPrice = 0.0

    var sharedPref: SharedPref? = null
    var selectedProducts = ArrayList<Product>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_products_detail)

        product = gson.fromJson(intent.getStringExtra("product"), Product::class.java)
        sharedPref = SharedPref(this)

        imageSlider = findViewById(R.id.imageslider)
        textViewName = findViewById(R.id.textview_name)
        textViewDescription = findViewById(R.id.textview_description)
        textViewPrice = findViewById(R.id.textview_price)
        textViewCounter = findViewById(R.id.textview_counter)
        imageViewAdd = findViewById(R.id.imageview_add)
        imageViewRemove = findViewById(R.id.imageview_remove)
        buttonAdd = findViewById(R.id.btn_add_product)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(product?.image1, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(product?.image2, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(product?.image3, ScaleTypes.CENTER_CROP))

        imageSlider?.setImageList(imageList)

        textViewName?.text = product?.nombre
        textViewDescription?.text = product?.descripcion
        textViewPrice?.text = "${product?.precio}$"

        imageViewAdd?.setOnClickListener { addItem() }
        imageViewRemove?.setOnClickListener { removeItem() }
        buttonAdd?.setOnClickListener { addToBag() }

        getProductsFromSharedPref()
    }

    private fun addToBag() {
        val index = getIndexOf(product?.id!!) // Indice del producto si es que existe en sharedPref

        if (index == -1) { // Este producto no existe aun en sharedPref
            if (product?.cantidad == null) {
                product?.cantidad = 1
            }
            selectedProducts.add(product!!)
        }
        else { // Ya existe el producto en sharedPref
            selectedProducts[index].cantidad = counter
        }
        sharedPref?.save("order", selectedProducts)
        Toast.makeText(this, "Producto agregado", Toast.LENGTH_LONG).show()
    }

    private fun getProductsFromSharedPref() {

        if (!sharedPref?.getData("order").isNullOrBlank()) { //Valida si existe una orden en SharedPref
            val type = object: TypeToken<ArrayList<Product>>() {}.type
            selectedProducts = gson.fromJson(sharedPref?.getData("order"), type)
            val index = getIndexOf(product?.id!!)

            if (index != -1) {
                product?.cantidad = selectedProducts[index].cantidad
                textViewCounter?.text = "${product?.cantidad}"

                productPrice = product?.precio!! * product?.cantidad!!
                textViewPrice?.text = "${productPrice}$"
                buttonAdd?.setText("Editar orden")
                buttonAdd?.backgroundTintList = ColorStateList.valueOf(Color.BLUE)
                buttonAdd?.setTextColor(Color.WHITE)
            }

            for (p in selectedProducts) {
                Log.d(TAG, "Shared pref: $p")
            }
        }
    }

    //Comparar si un producto ya existe en sharedPref y editar la cantidad del producto
    private fun getIndexOf(idProduct: String): Int {
        var pos = 0

        for (p in selectedProducts) {
            if (p.id == idProduct) {
                return pos
            }
            pos++
        }
        return -1
    }

    private fun addItem() {
        counter++
        productPrice = product?.precio!! * counter
        product?.cantidad = counter
        textViewCounter?.text = "${product?.cantidad}"
        textViewPrice?.text = "${productPrice}$"
    }
    private fun removeItem() {
        if (counter > 1) {
            counter--
            productPrice = product?.precio!! * counter
            product?.cantidad = counter
            textViewCounter?.text = "${product?.cantidad}"
            textViewPrice?.text = "${productPrice}$"
        }
    }
}