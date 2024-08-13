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
import com.example.restappimobile.activities.client.address.list.ClientAddressListActivity
import com.example.restappimobile.activities.client.products.list.ClientProductsListActivity
import com.example.restappimobile.models.Address
import com.example.restappimobile.models.Category
import com.example.restappimobile.utils.SharedPref
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.gson.Gson

class AddressAdapter (val context: Activity, val address: ArrayList<Address>): RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    val sharedPref = SharedPref(context)
    val gson = Gson()
    var prev = 0
    var positionAddressSesion = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun getItemCount(): Int {
        return address.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val a = address[position] //Devuelve cada una de las direcciones

        if(!sharedPref.getData("address").isNullOrBlank()){ //si se eligio una dirección
            val  adr = gson.fromJson(sharedPref.getData("address"), Address::class.java)

            if (adr.id == a.id){
                positionAddressSesion = position
                holder.imageViewCheck.visibility = View.VISIBLE
            }
        }


        holder.textViewAddress.text = a.address
        holder.textViewNeighborhood.text = a.neigborhood

        holder.itemView.setOnClickListener {
            (context as ClientAddressListActivity).resetValue(prev)
            (context as ClientAddressListActivity).resetValue(positionAddressSesion)
            prev = position // valor 1

            holder.imageViewCheck.visibility = View.VISIBLE
            saveAddress(a.toJson())
        }

    }

    private fun saveAddress(data: String){
        val ad = gson.fromJson(data, Address::class.java)
        sharedPref.save("address",ad)
    }

    class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewAddress: TextView
        val textViewNeighborhood: TextView
        val imageViewCheck: ImageView

        init {
            textViewAddress = view.findViewById(R.id.textview_address)
            textViewNeighborhood = view.findViewById(R.id.textview_neighborhood)
            imageViewCheck = view.findViewById(R.id.imageview_check)
        }
    }
}