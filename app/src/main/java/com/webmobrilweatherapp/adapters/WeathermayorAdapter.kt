package com.webmobrilweatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.weathermayorlist.DataItem
import com.webmobrilweatherapp.model.weathermayorlist.Mayor
import com.webmobrilweatherapp.viewmodel.ApiConstants
import java.util.*
import kotlin.collections.ArrayList

class WeathermayorAdapter(private val context: Context, private val listmodel: ArrayList<DataItem>,private val listmodel1: Mayor?,var adapterInterface:AdapterInterface
) :
    RecyclerView.Adapter<WeathermayorAdapter.ViewHolder>(),Filterable {
    private val originalList = ArrayList(listmodel!!)
    lateinit var viewHolder: ViewHolder


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.weathermayorrecycle, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.month_name.setText(listmodel.get(position)!!.monthYear.toString())
        var persentage=listmodel.get(position).voteAccuracy!!.toInt()
        holder.ProgressBar.setProgress(persentage)
        holder.txtpersentage.setText(""+persentage+"%")
        holder.image_name.setText(listmodel[position].mayor!!.name)
        holder.image_name2.setText(listmodel[position].mayor!!.name)
        holder.location_name.setText(listmodel[position].mayor!!.city)

        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + listmodel[position].mayor!!.profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.image)

    }

    override fun getItemCount(): Int {
        return listmodel.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image_name: TextView
        var location_name: TextView
        var month_name: TextView
        var image_name2: TextView
        var txtpersentage: TextView
        var image: ImageView
        var ProgressBar: ProgressBar

        init {

            image_name = itemView.findViewById(R.id.image_name)
            image_name2 = itemView.findViewById(R.id.image_name2)
            location_name = itemView.findViewById(R.id.location_name)
            month_name = itemView.findViewById(R.id.month_name)
            image = itemView.findViewById(R.id.image)
            txtpersentage = itemView.findViewById(R.id.txtpersentage)
            ProgressBar = itemView.findViewById(R.id.ProgressBar)
        }

    }
    public interface AdapterInterface {
        fun onItemClick(
            id: String,
            name: String
        )
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                listmodel.clear()
                if (constraint.isNullOrBlank()) {
                    listmodel.addAll(originalList)
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
                        if (item?.mayor!!.city!!.toLowerCase(Locale.ROOT)
                                .contains(constraint)
                        )
                            listmodel.add(item)
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
                    it.values = listmodel
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // no need to use "results" filtered list provided by this method.
                if (listmodel.isNullOrEmpty())
                    Toast.makeText(context, "No Data", Toast.LENGTH_LONG).show()
                notifyDataSetChanged()

            }
        }

    }
}