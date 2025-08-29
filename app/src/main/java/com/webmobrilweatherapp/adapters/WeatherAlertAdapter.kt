package com.webmobrilweatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.WeatherAlert.DataItem
import java.text.SimpleDateFormat
import java.util.*

class WeatherAlertAdapter(private val context: Context,private val ListModel:List<DataItem>) :
    RecyclerView.Adapter<WeatherAlertAdapter.ViewHolder>() {

    interface SelectItem{
        fun selectItem(status:String,requestFrom:String)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherAlertAdapter.ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.weather_alert_item, parent, false)
        return WeatherAlertAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherAlertAdapter.ViewHolder, position: Int) {
        holder.weatherAlert.setText("Weather alert sent by "+ListModel.get(position).user?.name.toString())
        holder.title.setText("Title "+ListModel.get(position).title.toString())
        holder.message.setText("Message "+ListModel.get(position).message.toString())
        var date = getTime(ListModel.get(position).createdAt.toString())

        var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val newDate = spf.parse(date)
        spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
        val newDateString = spf.format(newDate)
        holder.timeAlert.setText("Time "+newDateString)

    }

    fun getTime(time:String): String {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.timeZone = (TimeZone.getTimeZone("IST"))
        val date = sdf.parse(time)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
    }

    override fun getItemCount(): Int {
        return ListModel.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var weatherAlert:TextView
        var title:TextView
        var message:TextView
       var timeAlert:TextView
        init {

            weatherAlert=itemView.findViewById(R.id.weatherAlert_txt)
            title=itemView.findViewById(R.id.title)
            message=itemView.findViewById(R.id.message)
            timeAlert=itemView.findViewById(R.id.timeAlert)

        }
    }
}





