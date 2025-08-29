package com.webmobrilweatherapp.activities.metrologistadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.metrologist.viewuservote.TodayItem
import com.webmobrilweatherapp.viewmodel.ApiConstants
import java.util.*
import kotlin.collections.ArrayList

class MetrologistDayAdapter(
    private val context: Context,
    private val listModeles: ArrayList<TodayItem>,
    var adapterInterface: AdapterInterface,var vartxt:TextView
) : RecyclerView.Adapter<MetrologistDayAdapter.ViewHolder>() {
    private val originalList = ArrayList(listModeles!!)
    lateinit var viewHolder: ViewHolder
    // private var datatm: List<TodayItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.mertologistdayrecycle, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // this.viewHolder=holder
        //holder.Names.text=listModels.get(0).name
        vartxt.visibility=View.GONE

        holder.Names.setText(listModeles.get(position).name)
        holder.txtHighrani.setText(listModeles.get(position).isTemp)
        var temp=listModeles.get(position)!!.tempValue
        holder.txtcelcalcius.setText(""+temp+"℃")
        holder.txtRaini.setText(listModeles.get(position).precipitationName)
        holder.txtCity.setText(listModeles.get(position).city)
        if (ApiConstants.IMAGE_URL_VOTING_LIST +listModeles[position]?.preciptionsImg!=null){
            Glide.with(context)
                .load(ApiConstants.IMAGE_URL_VOTING_LIST +listModeles[position]?.preciptionsImg)
                .placeholder(R.drawable.claudyicon2)
                .into(holder.imgVotingList)
         }else{
            holder.imgVotingList.setImageResource(R.drawable.claudyicon2)

        }
        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + listModeles.get(position).profileImage)
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
        return listModeles.size
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
                listModeles!!.clear()
                if (constraint.isNullOrBlank()) {
                    listModeles!!.addAll(originalList)
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
                            listModeles!!.add(item)
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
                    it.values = listModeles
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // no need to use "results" filtered list provided by this method.
                if (listModeles.isNullOrEmpty())
/*
                    Toast.makeText(context, "No Item", Toast.LENGTH_LONG).show()
*/
                    txt.visibility=View.VISIBLE

                notifyDataSetChanged()

            }
        }

    }
}