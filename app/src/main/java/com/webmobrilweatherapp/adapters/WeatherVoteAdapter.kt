package com.webmobrilweatherapp.adapters

import android.content.Context
 import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
 import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.myweathervotepersentage.VotesItem

class WeatherVoteAdapter(
    private val context: Context,
    private val listmodel: List<VotesItem?>,
) :
    RecyclerView.Adapter<WeatherVoteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherVoteAdapter.ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.weathervoterecycle, parent, false)
        return WeatherVoteAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherVoteAdapter.ViewHolder, position: Int) {
        holder.textStorm.setText(listmodel[position]!!.precipitation!!.precipitationName)
        holder.textapr.setText(listmodel.get(position)!!.weatherdate.toString())

        var temp=listmodel.get(position)!!.tempValue
        holder.textnum.setText(""+temp+"℃")
         if (listmodel.get(position)!!.isTemp.toString().equals("1")){
            holder.texthigh.setText("High")
        }else{
            holder.texthigh.setText("Low")
        }
        var accuraci=listmodel.get(position)!!.voteAccurate!!.toInt()
        if (accuraci!=2){
            holder.imgEmogi.setImageResource(R.drawable.ic_happyicon)
        }else{
            holder.imgEmogi.setImageResource(R.drawable.sadimg)
        }


    }

    override fun getItemCount(): Int {
        return listmodel.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textapr: TextView
        var textnum: TextView
        var textStorm: TextView
        var texthigh: TextView
        var imgEmogi: ImageView

        init {

            textapr = itemView.findViewById(R.id.textapr)
            texthigh = itemView.findViewById(R.id.texthigh)
            textnum = itemView.findViewById(R.id.textnum)
            textStorm = itemView.findViewById(R.id.textStorm)
            imgEmogi = itemView.findViewById(R.id.imgEmogi)
        }

    }
}