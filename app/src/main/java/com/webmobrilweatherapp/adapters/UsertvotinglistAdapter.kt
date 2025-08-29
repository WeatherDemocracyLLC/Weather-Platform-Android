package com.webmobrilweatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.uservotinglist.DataItem
import com.webmobrilweatherapp.model.uservotinglist.User
import com.webmobrilweatherapp.viewmodel.ApiConstants
import java.util.*
import kotlin.collections.ArrayList

class UsertvotinglistAdapter(
    private val context: Context,
    private val listModel: ArrayList<DataItem>,
    private val listmodels: User?,
    var adapterInterface: AdapterInterface
) : RecyclerView.Adapter<UsertvotinglistAdapter.ViewHolder>(), Filterable {
    private val originalList = ArrayList(listModel!!)
    lateinit var viewHolder: ViewHolder

    private var listData = ArrayList<DataItem>()
    // private var datatm: List<TodayItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.votinglistrecycle, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.typeofspeed.setText(listModel[position]?.precipitation?.precipitationName.toString())

        var temp=listModel.get(position)!!.tempValue
        holder.temperatureindegree.setText(""+temp+"℃")
         holder.votername.setText(listmodels?.name.toString())
        holder.locationname.setText(listmodels?.city.toString())
        if (listModel.get(position).isTemp!!.equals("2")) {
            holder.tvspeed.setText("High")
        } else if (listModel.get(position).isTemp!!.equals("1")) {
            holder.tvspeed.setText("Low")
        }
        if (ApiConstants.IMAGE_URL_VOTING_LIST + listModel[position].precipitation?.preciptionsImg!=null){
            Glide.with(context)
                .load(ApiConstants.IMAGE_URL_VOTING_LIST + listModel[position]?.precipitation!!.preciptionsImg)
                .placeholder(R.drawable.claudyicon2)
                .into(holder.cloudyicon)
        }else{
            holder.cloudyicon.setImageResource(R.drawable.claudyicon2)

        }
        if (ApiConstants.IMAGE_URL + listmodels!!.profileImage!=null){
            Glide.with(context)
                .load(ApiConstants.IMAGE_URL + listmodels!!.profileImage)
                .placeholder(R.drawable.edit_profileicon)
                .into(holder.votinglistimage)
        }else{
            holder.cloudyicon.setImageResource(R.drawable.edit_profileicon)

        }

        //  holder.Liveinneapolis.setText("Live in "+city)

        holder.constraintLayout.setOnClickListener {
            adapterInterface.onItemClick(
                listModel[position]?.id.toString(),
                listModel[position]?.precipitation?.precipitationName.toString()

            )
            //mcontect.startActivity(Intent(mcontect, SelectTravelDate::class.java))
        }
    }

    override fun getItemCount(): Int {
        return listModel.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var votername: TextView
        var cloudyicon: ImageView
        var locationname: TextView
        var typeofspeed: TextView
        var tvspeed: TextView
        var temperatureindegree: TextView
        var votinglistimage: ImageView
        var constraintLayout: RelativeLayout

        init {
            votername = itemView.findViewById(R.id.votername)
            cloudyicon = itemView.findViewById(R.id.cloudyicon)
            locationname = itemView.findViewById(R.id.locationname)
            typeofspeed = itemView.findViewById(R.id.typeofspeed)
            tvspeed = itemView.findViewById(R.id.tvspeed)
            temperatureindegree = itemView.findViewById(R.id.temperatureindegree)
            constraintLayout = itemView.findViewById(R.id.constraintLayout)
            votinglistimage = itemView.findViewById(R.id.votinglistimage)

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
                listModel.clear()
                if (constraint.isNullOrBlank()) {
                    listModel.addAll(originalList)
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
                        if (item?.userName!!.toLowerCase(Locale.ROOT)
                                .contains(constraint)
                        )
                            listModel.add(item)
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
                    it.values = listModel
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // no need to use "results" filtered list provided by this method.
                if (listModel.isNullOrEmpty())
                    Toast.makeText(context, "No Data", Toast.LENGTH_LONG).show()
                notifyDataSetChanged()

            }
        }

    }
}