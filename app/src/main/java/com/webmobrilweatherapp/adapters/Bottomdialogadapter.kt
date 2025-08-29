package com.webmobrilweatherapp.adapters

import android.content.Context
import com.webmobrilweatherapp.beans.bottomdialog.DataItem
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.webmobrilweatherapp.R
import android.widget.TextView

class Bottomdialogadapter(private val context: Context, private val listModels: List<DataItem>) :
    RecyclerView.Adapter<Bottomdialogadapter.Myviewholder>() {
     var bottomInterface: BottonInterface? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottomdialog, parent, false)
        return Myviewholder(view)
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        holder.textnoprecipitation.text = listModels[position].precipitationName
        holder.itemView.setOnClickListener {
            this.bottomInterface?.setOnItemClick(listModels[position])
        }
        //bottomInterface!!.setValues(listModels)
    }

    fun setbottomInterface(bottomInterface: BottonInterface) {
        this.bottomInterface = bottomInterface
    }

    interface BottonInterface {
        fun setOnItemClick(dataItem: DataItem)
    }

    override fun getItemCount(): Int {
        return listModels.size
    }

    inner class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textnoprecipitation: TextView

        init {
            textnoprecipitation = itemView.findViewById(R.id.textnoprecipitation)
        }
    }
}