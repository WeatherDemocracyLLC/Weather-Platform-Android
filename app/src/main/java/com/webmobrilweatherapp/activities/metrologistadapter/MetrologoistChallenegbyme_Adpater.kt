package com.webmobrilweatherapp.activities.metrologistadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.R

class MetrologoistChallenegbyme_Adpater(private val context: Context):
    RecyclerView.Adapter<MetrologoistChallenegbyme_Adpater.ViewHolder>() {


    interface SelectItem{
        fun selectItem(id:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetrologoistChallenegbyme_Adpater.ViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.challengeby_me_item,parent,false)
        return  MetrologoistChallenegbyme_Adpater.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MetrologoistChallenegbyme_Adpater.ViewHolder, position: Int)
    {
        /*holder.itemView.setOnClickListener(View.OnClickListener {
            selectItem.selectItem("5")
        })*/
    }
    override fun getItemCount(): Int {

        return  10;

    }
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)
    {


    }
}




