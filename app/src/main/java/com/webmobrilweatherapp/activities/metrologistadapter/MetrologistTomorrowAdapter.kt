package com.webmobrilweatherapp.activities.metrologistadapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.metrologist.viewuservote.TomorrowItem
import com.webmobrilweatherapp.viewmodel.ApiConstants
import java.util.*
import kotlin.collections.ArrayList

class MetrologistTomorrowAdapter(
    private val context: Context,
    private val listModels: ArrayList<TomorrowItem>,
    var adapterInterface: AdapterInterface,var vartxt:TextView
) : RecyclerView.Adapter<MetrologistTomorrowAdapter.ViewHolder>() {
    private val originalList = ArrayList(listModels!!)
    lateinit var viewHolder: ViewHolder


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.mertologistdayrecycle, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        vartxt.visibility=View.GONE

        // this.viewHolder=holder
        //holder.Names.text=listModels.get(0).name
        holder.Names.setText(listModels.get(position).name)
        holder.txtHighrani.setText(listModels.get(position).isTemp)
        var temp=listModels.get(position)!!.tempValue
        holder.txtcelcalcius.setText(""+temp+"℃")
        Log.i(TAG, "onBindViewHolder " + listModels.get(position).isTemp)
        holder.txtRaini.setText(listModels.get(position).precipitationName)
        holder.txtCity.setText(listModels.get(position).city)
        if (ApiConstants.IMAGE_URL_VOTING_LIST +listModels[position]?.preciptionsImg!=null){
            Glide.with(context)
                .load(ApiConstants.IMAGE_URL_VOTING_LIST +listModels[position]?.preciptionsImg)
                .placeholder(R.drawable.claudyicon2)
                .into(holder.imgVotingList)
        }else{
            holder.imgVotingList.setImageResource(R.drawable.claudyicon2)

        }
        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + listModels.get(position).profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.IMgGirl)
    }

    public interface AdapterInterface {
        fun onItemClick(
            id: String,
            name: String
        )
    }

    override fun getItemCount(): Int {
        return listModels.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Names: TextView
        var txtHighrani: TextView
        var txtRaini: TextView
        var txtCity: TextView
        var txtcelcalcius: TextView
        var imgVotingList: ImageView
        var IMgGirl: ImageView

        init {
            Names = itemView.findViewById(R.id.Names)
            txtHighrani = itemView.findViewById(R.id.txtHighrani)
            txtRaini = itemView.findViewById(R.id.txtRaini)
            txtCity = itemView.findViewById(R.id.txtCity)
            txtcelcalcius = itemView.findViewById(R.id.txtcelcalcius)
            imgVotingList = itemView.findViewById(R.id.imgVotingList)
            IMgGirl = itemView.findViewById(R.id.IMgGirl)

        }

    }

    fun getFilter(txt:TextView): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                listModels!!.clear()
                if (constraint.isNullOrBlank()) {
                    listModels!!.addAll(originalList)
                }
                /*else {

                    val searchResults =
                        originalList.filter {
                            it.name!!.lowercase().contains(
                                constraint.toString().lowercase(
                                    Locale.ROOT
                                )
                            )
                        }


                    list!!.addAll(searchResults)
                }*/
                else {
                    for (item in originalList) {
                        if (item.name!!.toLowerCase(Locale.ROOT)
                                .contains(constraint)
                        )
                            listModels!!.add(item)
                    }
                    /*  val searchResults =
                          originalList.filter {
                              it.name!!.toLowerCase(Locale.ROOT).contains(
                                  constraint.toString().toLowerCase(
                                      Locale.ROOT
                                  )
                              )
                              || it.iataCode!!.lowercase().contains(
                                  constraint.toString().lowercase(
                                      Locale.ROOT
                                  )
                              )
                          }*/
                    //  list!!.addAll(searchResults)
                }

                return filterResults.also {
                    it.values = listModels
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // no need to use "results" filtered list provided by this method.
                if (listModels.isNullOrEmpty())
                    txt.visibility=View.VISIBLE
                notifyDataSetChanged()

            }
        }

    }
}