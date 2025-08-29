package com.webmobrilweatherapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.Followers.FollowerListItem
import com.webmobrilweatherapp.viewmodel.ApiConstants

class ForecastFollowerAdapter(private  val context: Context,private  val selectItem: ForecastFollowerAdapter.SelectItem,private var List: List<FollowerListItem>):
    RecyclerView.Adapter<ForecastFollowerAdapter.ViewHolder>()
{

    interface SelectItem{
        fun selectItem(id:String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastFollowerAdapter.ViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.forecastfollowers_item,parent,false)
        return  ForecastFollowerAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ForecastFollowerAdapter.ViewHolder, position: Int)
    {

        holder.itemView.setOnClickListener(View.OnClickListener {
            selectItem.selectItem(List.get(position).id.toString())
        })

        holder.pf_name.text=List.get(position).name
        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + List?.get(position)?.profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.pf_image)
    }
    override fun getItemCount(): Int {

        return  List.size;

    }
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

        var pf_image: ImageView
        var pf_name: TextView


        init {
            pf_image = itemView.findViewById(R.id.votinglistimage)
            pf_name = itemView.findViewById(R.id.votername)
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: MutableList<FollowerListItem>) {
        List = list
        notifyDataSetChanged()
    }

}

