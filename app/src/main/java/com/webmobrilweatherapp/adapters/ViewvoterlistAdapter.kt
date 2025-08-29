package com.webmobrilweatherapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.uservotinglist.DataItem
 import com.webmobrilweatherapp.viewmodel.ApiConstants

class ViewvoterlistAdapter(
    private val context: Context,
    private val listmodel: List<DataItem?>,
) :
    RecyclerView.Adapter<ViewvoterlistAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewvoterlistAdapter.ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.viewvotesrecycle, parent, false)
        return ViewvoterlistAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewvoterlistAdapter.ViewHolder, position: Int) {
        //holder.textrain.setText(listmodel[position]!!.precipitation!!.precipitationName.toString())

        if (ApiConstants.IMAGE_URL_VOTING_LIST +listmodel[position]?.precipitation?.preciptionsImg!=null){
            Glide.with(context)
                .load(ApiConstants.IMAGE_URL_VOTING_LIST +listmodel[position]?.precipitation?.preciptionsImg)
                .placeholder(R.drawable.claudyicon2)
                .into(holder.imgviewvotingicon)
            Log.d("TAG", "imgdsdsds"+ApiConstants.IMAGE_URL_VOTING_LIST+listmodel.get(position)!!.precipitation!!.preciptionsImg)
        }else{
            holder.imgviewvotingicon.setImageResource(R.drawable.claudyicon2)

        }
        holder.textrain.setText(listmodel[position]?.precipitation?.precipitationName.toString())
        holder.textdavideviewvotes.setText(listmodel.get(position)!!.userName)

        var temp=listmodel.get(position)!!.tempValue
        holder.txtTem.setText(""+temp+"℃")
        if (listmodel.get(position)!!.isTemp!!.toString().equals("1")){
            holder.texttempviewvotes.setText("High temp")
        }else{
            holder.texttempviewvotes.setText("Low Temp")
        }
       /* Glide.with(context)
            .load(ApiConstants.IMAGE_URL + listmodel[position]!!.precipitation!!.preciptionsImg)
            .placeholder(R.drawable.claudyicon2)
            .into(holder.imgviewvotingicon)*/
    }
    override fun getItemCount(): Int {
        return listmodel.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textdavideviewvotes: TextView
        var texttempviewvotes: TextView
        var textrain: TextView
        var txtTem: TextView
        var imgviewvotingicon: ImageView

        init {

            textdavideviewvotes = itemView.findViewById(R.id.textdavideviewvotes)
            imgviewvotingicon = itemView.findViewById(R.id.imgviewvotingicon)
            texttempviewvotes = itemView.findViewById(R.id.texttempviewvotes)
            textrain = itemView.findViewById(R.id.textrain)
            txtTem = itemView.findViewById(R.id.txtTem)
        }

    }
}