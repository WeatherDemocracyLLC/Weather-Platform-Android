package com.webmobrilweatherapp.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
 import com.webmobrilweatherapp.R
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient

class LocationAdapter(
    var mContext: Context,
    val onClick: (prediction: AutocompletePrediction) -> Unit
) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder>(), Filterable {
    private var mResultList: ArrayList<AutocompletePrediction>? = arrayListOf()
    private val placesClient: PlacesClient = Places.createClient(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_place_suggestions, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mResultList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
        holder.itemView.setOnClickListener {
            onClick(mResultList?.get(position)!!)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tv_address_title: TextView
        lateinit var tv_address_sub_title: TextView
        fun onBind(position: Int) {
            val res = mResultList?.get(position)
            tv_address_title = itemView.findViewById(R.id.tv_address_title)


            itemView.apply {

                tv_address_title.text = res?.getPrimaryText(null)

              //  tv_address_sub_title.text = res?.getSecondaryText(null)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                mResultList = getPredictions(constraint)
                if (mResultList != null) {
                    results.values = mResultList
                    results.count = mResultList!!.size
                }
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getPredictions(constraint: CharSequence): ArrayList<AutocompletePrediction>? {
        val result: ArrayList<AutocompletePrediction> = arrayListOf()
        val token = AutocompleteSessionToken.newInstance()
        val request =
            FindAutocompletePredictionsRequest.builder() // Call either setLocationBias() OR setLocationRestriction().
                //.setLocationBias(bounds)
                //.setCountries("SG","LK")
                //.setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .setQuery(constraint.toString())
                .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                result.addAll(response.autocompletePredictions)
                notifyDataSetChanged()
            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {';'
                    Log.e("TAG", "Place not found: " + exception.statusCode)
                   // mContext.showToastLong("Place not found")
                  Toast.makeText(mContext,"Place not found",Toast.LENGTH_SHORT).show();
                }
            }
        return result
    }
}