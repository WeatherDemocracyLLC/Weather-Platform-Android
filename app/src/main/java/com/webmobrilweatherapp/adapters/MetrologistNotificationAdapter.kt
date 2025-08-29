package com.webmobrilweatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.R

class MetrologistNotificationAdapter(private val context: Context) :
    RecyclerView.Adapter<MetrologistNotificationAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder
    var position = 0


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MetrologistNotificationAdapter.ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.notificationrecycle, parent, false)
        return MetrologistNotificationAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MetrologistNotificationAdapter.ViewHolder,
        position: Int
    ) {
        this.viewHolder = holder
        this.position = position

    }

    override fun getItemCount(): Int {
        return 10
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}