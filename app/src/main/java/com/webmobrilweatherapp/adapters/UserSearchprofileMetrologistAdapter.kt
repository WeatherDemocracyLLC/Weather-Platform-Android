package com.webmobrilweatherapp.adapters

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.InstaHomemetrologistActivity
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.model.usersearching.Datum
import com.webmobrilweatherapp.viewmodel.ApiConstants
import java.util.*
import kotlin.collections.ArrayList

class UserSearchprofileMetrologistAdapter(
    private val context: Context,
    private val listModel: ArrayList<Datum>,
    var adapterInterface: AdapterInterface, var vartxt:TextView
) : RecyclerView.Adapter<UserSearchprofileMetrologistAdapter.ViewHolder>() {
    private val originalList = ArrayList(listModel!!)
    lateinit var viewHolder: ViewHolder
    // private var datatm: List<TodayItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.metrologistsearchprofile, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        vartxt.visibility=View.GONE
        holder.Christian.setText(listModel.get(position).name)
        Log.i(TAG, "nameeeeeeeeadddd: " + listModel.get(position).name)
        var city = listModel.get(position).city
        Log.i(TAG, "onBindViewHolder: " + listModel.get(position).city)
        var citys: String = "";
        citys = listModel.get(position).city.toString()

        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + listModel.get(position).profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.imgsearchlogopic)
        if (citys.equals("null")) {
            holder.Liveinneapolis.visibility = GONE
        } else {
            holder.Liveinneapolis.visibility = VISIBLE
            holder.Liveinneapolis.setText("Live in " + city)
        }
        //  holder.Liveinneapolis.setText("Live in "+city)

        holder.itemView.setOnClickListener {
            var ids = listModel.get(position).id
            var intent = Intent(context, InstaHomemetrologistActivity::class.java)
          //  Log.d(TAG, "bsdkr: ${ listModel.get(position).id}")
            intent.putExtra("userId",ids)
            intent.putExtra("type", 0)
            context.startActivity(intent)
        }
        var likebyme = listModel.get(position).likebyme
        if (likebyme == true) {
            holder.imguserfloow.visibility = VISIBLE
            holder.imguseraddline.visibility = GONE
        } else {
            holder.imguserfloow.visibility = GONE
            holder.imguseraddline.visibility = VISIBLE
        }
        holder.instagramProfileHomeMterologist.setOnClickListener {
            adapterInterface.onItemClick(
                listModel.get(position).id.toString(),
                listModel.get(position).name.toString()

            )

            CommonMethod.getInstance(context)
                .savePreference(AppConstant.KEY_IntsUserId, listModel.get(position).id.toString())
            var ids = listModel.get(position).id
            var intent = Intent(context, InstaHomemetrologistActivity::class.java)
            intent.putExtra("userId",ids)
            intent.putExtra("type", 3)
            context.startActivity(intent)
            //mcontect.startActivity(Intent(mcontect, SelectTravelDate::class.java))
        }
    }

    public interface AdapterInterface {
        fun onItemClick(
            id: String,
            name: String
        )
    }

    override fun getItemCount(): Int {
        return listModel.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var Christian: TextView
        var Liveinneapolis: TextView
        var instagramProfileHomeMterologist: ConstraintLayout
        var imgsearchlogopic: ImageView
        var imguserfloow: ImageView
        var imguseraddline: ImageView

        init {
            Christian = itemView.findViewById(R.id.Christian)
            Liveinneapolis = itemView.findViewById(R.id.Liveinneapolis)
            imgsearchlogopic = itemView.findViewById(R.id.imgsearchlogopic)
            imguserfloow = itemView.findViewById(R.id.imguserfloow)
            imguseraddline = itemView.findViewById(R.id.imguseraddline)
            instagramProfileHomeMterologist =
                itemView.findViewById(R.id.instagramProfileHomeMterologist)

        }
    }

    fun getFilter(txt:TextView): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                listModel!!.clear()
                if (constraint.isNullOrBlank()) {
                    listModel!!.addAll(originalList)
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
                            listModel!!.add(item)
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
                txt.visibility=View.VISIBLE
                notifyDataSetChanged()

            }
        }

    }
}