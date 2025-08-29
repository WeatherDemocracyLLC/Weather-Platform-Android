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
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.InstaHomeActivity
import com.webmobrilweatherapp.model.topfivemetrologist.DataItem
import com.webmobrilweatherapp.viewmodel.ApiConstants

class UsertopfivemetrologistAdapter(
    private val context: Context,
    private val listModel: List<DataItem>
) : RecyclerView.Adapter<UsertopfivemetrologistAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder
    // private var datatm: List<TodayItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.topfivemetrologist, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Christian.setText(listModel.get(position).name)
        var date = listModel.get(position).createdAt.toString()
        val upToNCharacters: String = date!!.substring(0, Math.min(date.length, 10))
        holder.month_name.setText(upToNCharacters)
        var city = listModel.get(position).city
        holder.location_name.setText(city)
        Log.i(TAG, "onBindViewHolder: " + listModel.get(position).city)
        var citys: String = "";
        citys = listModel.get(position).city.toString()
        Glide.with(context)
            .load(ApiConstants.IMAGE_URL +listModel.get(position).profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.imgsearchlogopic)
        if (citys.equals("null")) {
            holder.Liveinneapolis.visibility = GONE
        } else {
            holder.Liveinneapolis.visibility = VISIBLE
            holder.Liveinneapolis.setText("Live in " + city)
        }
        holder.itemView.setOnClickListener {
            var ids = listModel.get(position).id
            CommonMethod.getInstance(context)
                .savePreference(AppConstant.KEY_IntsUserId, listModel.get(position).id.toString())
            var intent = Intent(context, InstaHomeActivity::class.java)
            intent.putExtra("userIds", ids)
            context.startActivity(intent)


        }
        //  holder.Liveinneapolis.setText("Live in "+city)
    }

    override fun getItemCount(): Int {
        return listModel.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Christian: TextView
        var month_name: TextView
        var Liveinneapolis: TextView
        var location_name: TextView
        var imgsearchlogopic: ImageView

        init {
            Christian = itemView.findViewById(R.id.Christian)
            Liveinneapolis = itemView.findViewById(R.id.Liveinneapolis)
            month_name = itemView.findViewById(R.id.month_name)
            location_name = itemView.findViewById(R.id.location_name)
            imgsearchlogopic = itemView.findViewById(R.id.imgsearchlogopic)

        }
    }
}