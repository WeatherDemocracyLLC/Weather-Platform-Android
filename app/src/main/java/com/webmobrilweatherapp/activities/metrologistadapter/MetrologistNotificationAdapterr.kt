package com.webmobrilweatherapp.activities.metrologistadapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.activities.metrologistactivity.InstaFollowingActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistForecastChallengeActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistInstaHomeMetrologistActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistPostActivity
import com.webmobrilweatherapp.model.notification.NotificationsItem


class MetrologistNotificationAdapterr(private val context: Context, private val ListModel:List<NotificationsItem>) :
    RecyclerView.Adapter<MetrologistNotificationAdapterr.ViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MetrologistNotificationAdapterr.ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.notificationrecycle, parent, false)
        return MetrologistNotificationAdapterr.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MetrologistNotificationAdapterr.ViewHolder, position: Int) {

        holder.notification_txt.setText(ListModel.get(position).message)


        holder.itemView.setOnClickListener(View.OnClickListener {



            if (ListModel.get(position).notificationType==6)
            {
                val intent = Intent(context, MetrologistForecastChallengeActivity::class.java)
                context.startActivity(intent)
            }
            else if(/*ListModel.get(position).notificationType==8||*/ListModel.get(position).notificationType==7)
            {
               CommonMethod.getInstance(context)
                    .savePreference(AppConstant.KEY_IntsUserIdMetrologist,ListModel.get(position).notificationData!!.requestFrom.toString())





                if (ListModel.get(position).notificationData?.userType.toString().equals("3"))
                {

                    var intent = Intent(context, InstaFollowingActivity::class.java)
                    intent.putExtra("userIds", ListModel.get(position).notificationData!!.requestFrom)
                    context.startActivity(intent)

                }

                else if(ListModel.get(position).notificationData?.userType.toString().equals("2"))
                {


                    var intent = Intent(context, MetrologistInstaHomeMetrologistActivity::class.java)
                    intent.putExtra("userIds",  ListModel.get(position).notificationData!!.requestFrom)
                    context.startActivity(intent)

                }



            }
            else if(ListModel.get(position).notificationType==10||ListModel.get(position).notificationType==9)
            {
                var a=1
                val intent = Intent(context, MetrologistPostActivity::class.java)
                intent.putExtra("PostId", ListModel.get(position).notificationData!!.postId);
                intent.putExtra("statusBarcolor",a.toString())

                context.startActivity(intent)
            }


        })

/*
        holder.accept.setOnClickListener(View.OnClickListener {

            selectItem.selectItem("1",ListModel.get(position).notificationData?.requestFrom.toString())

        })


        holder.reject.setOnClickListener(View.OnClickListener {
            selectItem.selectItem("2",ListModel.get(position).notificationData?.requestFrom.toString())
        })
*/


    }

    override fun getItemCount(): Int {
        return ListModel.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var notification_txt: TextView
        var accept: ImageView
        var reject: ImageView

        init {


            notification_txt=itemView.findViewById(R.id.notification_txt)
            accept=itemView.findViewById(R.id.right)
            reject=itemView.findViewById(R.id.cross)


        }
    }
}





