package com.webmobrilweatherapp.activities.metrologistadapter

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.metrologistactivity.InstaFollowingActivity
import com.webmobrilweatherapp.model.topfivemetrologist.DataItem

class MetrologisttopfiveAdapter(
    private val context: Context,
    private val listModel: List<DataItem>
) : RecyclerView.Adapter<MetrologisttopfiveAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder
    // private var datatm: List<TodayItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.metrologistsearchprofile, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Christian.setText(listModel.get(position).name)
        var city = listModel.get(position).city
        Log.i(TAG, "onBindViewHolder: " + listModel.get(position).city)
        var citys: String = "";
        citys = listModel.get(position).city.toString()
        if (citys.equals("null")) {
            holder.Liveinneapolis.visibility = GONE
        } else {

            holder.Liveinneapolis.visibility = VISIBLE
            holder.Liveinneapolis.setText("Live in " + city)
        }
        holder.itemView.setOnClickListener {
            var ids = listModel.get(position).id
            var intent = Intent(context, InstaFollowingActivity::class.java)
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
        var Liveinneapolis: TextView

        init {
            Christian = itemView.findViewById(R.id.Christian)
            Liveinneapolis = itemView.findViewById(R.id.Liveinneapolis)

        }
    }
}