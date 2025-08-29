package com.webmobrilweatherapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.InstaHomeActivity
import com.webmobrilweatherapp.activities.InstaHomemetrologistActivity
import com.webmobrilweatherapp.activities.metrologistactivity.InstaFollowingActivity
import com.webmobrilweatherapp.model.Followers.FollowerListItem
import com.webmobrilweatherapp.viewmodel.ApiConstants


class FollowersAdapter
    (private val context: Context,var user:Int, private  val List:List<FollowerListItem>):
    RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

/*    interface SelectItem{
        fun selectItem(id:String)
    }*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersAdapter.ViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.metrologistsearchprofile,parent,false)
        return  FollowersAdapter.ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: FollowersAdapter.ViewHolder, position: Int)
    {
        /*holder.itemView.setOnClickListener(View.OnClickListener {
            selectItem.selectItem("5")
        })*/
        holder.imguseraddline.visibility=View.GONE
        holder.pf_name.text=List.get(position).name
        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + List?.get(position)?.profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.pf_image)

        holder.pf_add.text=List.get(position).city


        holder.itemView.setOnClickListener {
            CommonMethod.getInstance(context)
                .savePreference(AppConstant.KEY_IntsUserId, List.get(position).id.toString())
            if (user==1)
            {
                if (List.get(position).userType.toString().equals("2"))
                {
                    var ids = List.get(position).id
                    var type = List.get(position).userType

                    Log.e("user Type1 ==", user.toString());
                    Log.e("user Type1 ==", type.toString());


                        var intent = Intent(context, InstaHomeActivity::class.java)
                    intent.putExtra("userIds", ids)
                    intent.putExtra("type", type)
                    context.startActivity(intent)
                }
                else
                {

                    Log.e("user Type2==", user.toString());
                    var ids = List.get(position).id
                    var type = List.get(position).userType
                    var intent = Intent(context, InstaHomemetrologistActivity::class.java)
                    intent.putExtra("userId", ids)
                    intent.putExtra("type", type)
                    context.startActivity(intent)
                }
            }
            else
            {
                Log.e("user Type3==", user.toString());

                CommonMethod.getInstance(context).getPreference(AppConstant.KEY_IntsUserIdMetrologist, List.get(position).id.toString())
                var ids = List.get(position).id
                var intent = Intent(context, InstaFollowingActivity::class.java)
                intent.putExtra("userIds", ids)
                context.startActivity(intent)

            }


        }

    }
    override fun getItemCount(): Int {
        return List.size;
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var pf_image: ImageView
        var pf_name: TextView
        var pf_add:TextView
        var imguseraddline:ImageView
        init {
            pf_image = itemView.findViewById(R.id.imgsearchlogopic)
            pf_name = itemView.findViewById(R.id.Christian)
            pf_add=itemView.findViewById(R.id.Liveinneapolis)
            imguseraddline=itemView.findViewById(R.id.imguseraddline)
        }

    }
}

