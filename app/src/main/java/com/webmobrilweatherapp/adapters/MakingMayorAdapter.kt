package com.webmobrilweatherapp.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.mayorwether.DataItems
import com.webmobrilweatherapp.model.mayorwether.Mayor
import java.text.SimpleDateFormat


class MakingMayorAdapter(
    private val context: Context,
    private val listmodel: List<DataItems>,
    private val listmodels: Mayor?,
) :
    RecyclerView.Adapter<MakingMayorAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MakingMayorAdapter.ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.makingmayorxml, parent, false)
        return MakingMayorAdapter.ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MakingMayorAdapter.ViewHolder, position: Int) {
         var date =listmodel.get(position)!!.monthYear
        var spf = SimpleDateFormat("yyyy-MM")
        val newDate = spf.parse(date)
        spf = SimpleDateFormat("MMM -yyyy")
        date = spf.format(newDate)
        Log.d("TAG", "datetime"+date)
        holder.datemayor.setText(date)
        holder.txtCity.setText(listmodels!!.city)
       /* Glide.with(context)
            .load(ApiConstants.IMAGE_URL + listmodel[position]!!.mayor!!.profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.IMgMayors)*/
    }
    override fun getItemCount(): Int {
        return listmodel.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var datemayor: TextView
        var txtCity: TextView
        var IMgMayors: ImageView

        init {

            datemayor = itemView.findViewById(R.id.datemayor)
            IMgMayors = itemView.findViewById(R.id.IMgMayors)
            txtCity = itemView.findViewById(R.id.txtCity)
        }

    }
}