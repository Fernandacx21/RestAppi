package com.example.restappimobile.fragments.restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restappimobile.R
import com.example.restappimobile.adapters.OrdersClientAdapter
import com.example.restappimobile.adapters.OrdersRestaurantAdapter
import com.example.restappimobile.models.Order
import com.example.restappimobile.models.User
import com.example.restappimobile.providers.OrdersProvider
import com.example.restappimobile.utils.SharedPref
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RestaurantOrdersStatusFragment : Fragment() {

    var myView : View? = null
    var ordersProvider: OrdersProvider? = null
    var user: User? = null
    var sharedPref: SharedPref? = null

    var recyclerViewOrders: RecyclerView? = null
    var adapter: OrdersRestaurantAdapter? = null

    var status = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_restaurant_orders_status, container, false)

        sharedPref = SharedPref(requireActivity())

        status = arguments?.getString("status")!!

        getUserFromSession()
        ordersProvider = OrdersProvider(user?.sessionToken!!)

        recyclerViewOrders = myView?.findViewById(R.id.recyclerview_orders)
        recyclerViewOrders?.layoutManager = LinearLayoutManager(requireContext())

        getOrders()

        return myView
    }

    private fun getOrders(){
        ordersProvider?.getOrdersByStatus(status)?.enqueue(object : Callback<ArrayList<Order>> {
            override fun onResponse(call: Call<ArrayList<Order>>, response: Response<ArrayList<Order>>) {

                if (response.body() != null){
                    val orders = response.body()
                    adapter = OrdersRestaurantAdapter(requireActivity(),orders!!)
                    recyclerViewOrders?.adapter = adapter
                }

            }

            override fun onFailure(call: Call<ArrayList<Order>>, t: Throwable) {
                Toast.makeText(requireActivity(),"Error: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getUserFromSession(){
        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()){
            //SI EL USUARIO EXISTE EN SESIÓN
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }

    }

}