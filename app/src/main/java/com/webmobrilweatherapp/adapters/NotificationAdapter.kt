package com.webmobrilweatherapp.adapters

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
import com.webmobrilweatherapp.model.notification.NotificationsItem
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class NotificationAdapter(
    private val context: Context,
    private val ListModel: List<NotificationsItem>,
    private val selectItem: NotificationAdapter.SelectItem
) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    interface SelectItem {
        fun selectItem(status: String, requestFrom: String)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.notificationrecycle, parent, false)
        return NotificationAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {

        holder.notification_txt.setText(ListModel.get(position).message)


        // Define the original format of your input date string
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));  // Parse it as UTC


        try {
            // Parse the input string into a Date object
            val date: Date? = inputFormat.parse(ListModel.get(position).createdAt.toString())

            // Define the new format you want to display the date as
            val outputFormat = SimpleDateFormat("d MMMM yyyy hh:mm a", Locale.getDefault())

            // Format the date into the desired format
            val formattedDate = date?.let { outputFormat.format(it) }

            // Set this formatted date to your TextView or any other UI component
            holder.txt_datetimenoti.text = formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
        }
     //   holder.txt_datetimenoti.setText(ListModel.get(position).createdAt)

//
//        val dateString = ListModel.get(position).createdAt // "2025-04-04 10:00:40"
//        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//        formatter.timeZone = TimeZone.getTimeZone("UTC") // Optional: Adjust if needed
//
//
//        try {
//            val date = formatter.parse(dateString)
//            val prettyTime = PrettyTime()
//            val ago = prettyTime.format(date)
//            holder.txt_datetimenoti.text = ago // Output: "3 days ago", "moments ago", etc.
//        } catch (e: Exception) {
//            e.printStackTrace()
//            holder.txt_datetimenoti.text = dateString // fallback
//        }



        holder.itemView.setOnClickListener(View.OnClickListener {

            if (ListModel.get(position).notificationType == 6 || ListModel.get(position).notificationType == 16) {
                val intent = Intent(context, ForecastScreen::class.java)
                intent.putExtra("notificationType",ListModel.get(position).notificationType.toString())
                context.startActivity(intent)
            } else if (ListModel.get(position).notificationType == 7) {
                CommonMethod.getInstance(context)
                    .savePreference(
                        AppConstant.KEY_IntsUserId,
                        ListModel.get(position).notificationData!!.requestFrom.toString()
                    )

//                var intent = Intent(context, InstaHomeActivity::class.java)
//                intent.putExtra("userIds", ListModel.get(position).notificationData!!.requestFrom)
//                //  intent.putExtra("type", type)
//                context.startActivity(intent)

                if (ListModel.get(position).notificationData!!.userType.toString().equals("2")) {
                    var intent = Intent(context, InstaHomeActivity::class.java)
                    intent.putExtra("userIds", ListModel.get(position).notificationData!!.requestFrom)
                    //  intent.putExtra("type", type)
                    context.startActivity(intent)


//                    var intent = Intent(context, InstaHomeActivity::class.java)
//                    intent.putExtra("userIds", ListModel.get(position).senderId.toString())
//                    intent.putExtra("type", ListModel.get(position).notificationData!!.userType)
//                    context.startActivity(intent)
                } else if (ListModel.get(position).notificationData!!.userType.toString()
                        .equals("3")
                ) {


                    var intent = Intent(context, InstaHomemetrologistActivity::class.java)
                    intent.putExtra(
                        "userId",
                        ListModel.get(position).notificationData!!.requestFrom
                    )
                    intent.putExtra("type", ListModel.get(position).notificationData!!.userType)
                    context.startActivity(intent)
                }

            } else if (ListModel.get(position).notificationType == 10 || ListModel.get(position).notificationType == 9) {
                val intent = Intent(context, PostActivity::class.java)
                intent.putExtra("PostId", ListModel.get(position).notificationData!!.postId);
                context.startActivity(intent)
            } else if (ListModel.get(position).notificationType == 8 || ListModel.get(position).notificationType == 11) {

                val intent = Intent(context, InstaHomeActivity::class.java)
                intent.putExtra("userIds", ListModel.get(position).senderId?.toIntOrNull() ?: 0)
                //  intent.putExtra("type", ListModel.get(position).notificationType)
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
        var txt_datetimenoti: TextView
        var accept: ImageView
        var reject: ImageView

        init {

            notification_txt = itemView.findViewById(R.id.notification_txt)
            txt_datetimenoti = itemView.findViewById(R.id.txt_datetimenoti)
            accept = itemView.findViewById(R.id.right)
            reject = itemView.findViewById(R.id.cross)

        }
    }
}





