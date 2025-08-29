package com.webmobrilweatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.getcomment.CommentItem
import com.webmobrilweatherapp.viewmodel.ApiConstants
import java.text.SimpleDateFormat
import java.util.*

class UsercommentAdapter(private val context: Context,private val DataItem:List<CommentItem>) :
    RecyclerView.Adapter<UsercommentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsercommentAdapter.ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.usercommentxml, parent, false)
        return UsercommentAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UsercommentAdapter.ViewHolder, position: Int) {
        holder.TxtUserNamess.setText(DataItem.get(position).user?.name)
         holder.txtCommentes.setText(DataItem!!.get(position).comment)

        holder.txtCommentdate.setText(getDate(DataItem.get(position).createdAt.toString())+" "+getTime(DataItem.get(position).createdAt.toString()))


        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + DataItem.get(position).user?.profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.usersImages)

    }

    override fun getItemCount(): Int {
        return DataItem.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var usersImages:ImageView
       var TxtUserNamess:TextView
       var txtCommentes:TextView
       var txtCommentdate:TextView
        init {
            usersImages=itemView.findViewById(R.id.usersImages)
            TxtUserNamess=itemView.findViewById(R.id.TxtUserNamess)
            txtCommentes=itemView.findViewById(R.id.txtCommentes)
            txtCommentdate=itemView.findViewById(R.id.txtCommentdate)

        }

    }

    fun getDate(dateAndTime:String):String{
        val sdf= SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateAndTime)
        return SimpleDateFormat("MM-dd-yyyy").format(sdf)
    }

    fun getTime(time:String): String {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.timeZone = (TimeZone.getTimeZone("IST"))
        val date = sdf.parse(time)
        return SimpleDateFormat("hh:mm aa").format(date)
    }
}