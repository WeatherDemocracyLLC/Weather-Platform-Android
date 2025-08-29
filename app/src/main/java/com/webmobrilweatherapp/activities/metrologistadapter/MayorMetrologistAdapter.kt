package com.webmobrilweatherapp.activities.metrologistadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.R

class MayorMetrologistAdapter(private val context: Context) :
    RecyclerView.Adapter<MayorMetrologistAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder
    // private var datatm: List<TodayItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.profilemetrologist, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // this.viewHolder=holder
        //holder.Names.text=listModels.get(0).name
    }

    override fun getItemCount(): Int {
        return 2
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}