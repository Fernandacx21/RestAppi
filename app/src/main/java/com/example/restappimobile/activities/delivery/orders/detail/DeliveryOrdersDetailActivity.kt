package com.example.restappimobile.activities.delivery.orders.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restappimobile.R
import com.example.restappimobile.activities.delivery.orders.map.DeliveryOrdersMapActivity
import com.example.restappimobile.activities.restaurant.home.RestaurantHomeActivity
import com.example.restappimobile.adapters.OrderProductsAdapter
import com.example.restappimobile.models.Category
import com.example.restappimobile.models.Order
import com.example.restappimobile.models.ResponseHttp
import com.example.restappimobile.models.User
import com.example.restappimobile.providers.OrdersProvider
import com.example.restappimobile.providers.UsersProvider
import com.example.restappimobile.utils.SharedPref
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryOrdersDetailActivity : AppCompatActivity() {

    val TAG = "DeliveryOrdersDetail"
    var order: Order? = null
    val gson = Gson()

    var toolbar: Toolbar? = null
    var textViewClient: TextView? = null
    var textViewAddress: TextView? = null
    var textViewDate: TextView? = null
    var textViewTotal: TextView? = null
    var textViewStatus: TextView? = null
    var recyclerViewProducts: RecyclerView? = null
    var buttonUpdate: Button? = null
    var buttonGoToMap: Button? = null

    var adapter: OrderProductsAdapter? = null

    var usersProvider: UsersProvider? = null
    var ordersProvider: OrdersProvider? = null
    var user: User? = null
    var sharedPref : SharedPref? = null


    var idDelivery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_orders_detail)

        sharedPref = SharedPref(this)

        order = gson.fromJson(intent.getStringExtra("order"), Order::class.java)

        getUserFromSession()

        usersProvider = UsersProvider(user?.sessionToken!!)
        ordersProvider = OrdersProvider(user?.sessionToken!!)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        toolbar?.title = "Orden #${order?.id}"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textViewClient = findViewById(R.id.textview_client)
        textViewAddress = findViewById(R.id.textview_address)
        textViewDate = findViewById(R.id.textview_date)
        textViewTotal = findViewById(R.id.textview_total)
        textViewStatus = findViewById(R.id.textview_status)
        buttonUpdate = findViewById(R.id.btn_update_status)
        buttonGoToMap = findViewById(R.id.btn_update_go_to_map)

        recyclerViewProducts = findViewById(R.id.recyclerview_products)
        recyclerViewProducts?.layoutManager = LinearLayoutManager(this)

        adapter = OrderProductsAdapter(this, order?.products!!)
        recyclerViewProducts?.adapter = adapter

        textViewClient?.text = "${order?.client?.nombre} ${order?.client?.apellidos}"
        textViewAddress?.text = order?.address?.address
        textViewDate?.text = "${order?.timestamp}"
        textViewStatus?.text = order?.status

        Log.d(TAG,"Orden: ${order.toString()}")

        getTotal()


        if (order?.status == "DESPACHADO"){
            buttonUpdate?.visibility = View.VISIBLE
        }

        if (order?.status == "EN CAMINO"){
            buttonGoToMap?.visibility = View.VISIBLE
        }


        buttonUpdate?.setOnClickListener { updateOrder() }
        buttonGoToMap?.setOnClickListener { goToMap() }
    }

    private fun updateOrder(){
        //order?.idDelivery = idDelivery

        ordersProvider?.updateToOnTheWay(order!!)?.enqueue(object: Callback<ResponseHttp>{
            override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                if (response.body() != null){
                    Toast.makeText(this@DeliveryOrdersDetailActivity, "Entrega Iniciada", Toast.LENGTH_LONG).show()

                    if (response.body()?.isSuccess == true){
                        Toast.makeText(this@DeliveryOrdersDetailActivity, "Repartidor asignado correctamente", Toast.LENGTH_LONG).show()
                        goToMap()
                    }else{
                        Toast.makeText(this@DeliveryOrdersDetailActivity, "No se pudo asignar el repartidor", Toast.LENGTH_LONG).show()

                    }

                }else{
                    Toast.makeText(this@DeliveryOrdersDetailActivity, "No hubo respuesta del servidor", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                Toast.makeText(this@DeliveryOrdersDetailActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun goToMap(){
        val i = Intent(this, DeliveryOrdersMapActivity::class.java)
        i.putExtra("order", order?.toJson())
        startActivity(i)
    }


    private fun getUserFromSession(){
        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()){
            //SI EL USUARIO EXISTE EN SESIÓN
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }

    }

    private fun getTotal() {
        var total = 0.0

        for (p in order?.products!!) {
            total = total + (p.precio * p.cantidad!!)
        }
        textViewTotal?.text = "${total}$"
    }
}