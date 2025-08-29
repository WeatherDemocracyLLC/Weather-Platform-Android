package com.pusher.pusherchat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.PhotosViewActivity
import com.webmobrilweatherapp.model.getchat.DataItem
import com.webmobrilweatherapp.viewmodel.ApiConstants
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MessageUserAdapter(
    private val context: Context,
    private var list: ArrayList<DataItem>,private var type:Int
) :
    RecyclerView.Adapter<MessageUserAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder
    private var userid: Int? = 0
    private var ids = "0"

    //  private var list:ArrayList<MessageModels>?= ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.my_messageuser, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(type==3)
        {
            holder.chat1.setBackgroundResource(R.drawable.chat1shape)
            holder.chat2.setBackgroundResource(R.drawable.chat2shape)
        }
        else
        {
            holder.chat1.setBackgroundResource(R.drawable.chatshapegry)
            holder.chat2.setBackgroundResource(R.drawable.chatshape)
        }


        userid = CommonMethod.getInstance(context).getPreference(AppConstant.KEY_User_id)

        //    userid=ids.toInt()
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(context, R.color.toolbaar))
        circularProgressDrawable.start()
        var fromid = list.get(position).from
        /////////////////////////////////////////////////////////////////////////////
        holder.ImgMysender.setOnClickListener(View.OnClickListener {
            val postIntent = Intent(context, PhotosViewActivity::class.java)
            postIntent.putExtra("Photourl", ApiConstants.IMAGE_URL + list.get(position).message);
            context.startActivity(postIntent);


        })
        holder.ImgMyother.setOnClickListener(View.OnClickListener {

            val postIntent = Intent(context, PhotosViewActivity::class.java)
            postIntent.putExtra("Photourl", ApiConstants.IMAGE_URL + list.get(position).message);
            context.startActivity(postIntent);


        })
        ////////////////////////////////////////////////////////////////////////////
        if (userid == fromid) {
            if (list.get(position).messageType == 1) {
                holder.txtMyMessages.setText(list.get(position).message)

                var date = list.get(position).messageTime.toString()
                val upToNCharacters: String = date!!.substring(11, Math.min(date.length, 16))

                var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val newDate = spf.parse(date)
                spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
                val newDateString = spf.format(newDate)
                holder.txttimes.setText(newDateString
                )
                //holder.txttimes.setText(getDate(date)+" "+getTime(date))


                holder.txtMyMessageOthers.visibility = GONE
                holder.txttimesUser.visibility = GONE
                holder.cardviewSendUser.visibility = GONE
                holder.CardViewReciver.visibility = GONE
                //  holder.txtMyMessage.textAlignment = TEXT_ALIGNMENT_VIEW_END
                // holder.txtMyMessageOther.visibility=GONE
            } else {

                holder.txtMyMessageOthers.visibility = GONE
                holder.txttimesUser.visibility = GONE
                holder.txtMyMessages.visibility = GONE
                holder.txttimes.visibility = GONE
                holder.cardviewSendUser.visibility = VISIBLE
                holder.CardViewReciver.visibility = GONE
                holder.ImgTime.visibility = VISIBLE
                holder.ImgTimeReciver.visibility = VISIBLE
                var date = list.get(position).messageTime.toString()
                val imgTime: String = date!!.substring(11, Math.min(date.length, 16))

                var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val newDate = spf.parse(date)
                spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
                val newDateString = spf.format(newDate)
                holder.ImgTimeReciver.setText(newDateString
                )

               // holder.ImgTimeReciver.setText(getDate(date)+" "+getTime(date))

                Glide.with(context)
                    .load(ApiConstants.IMAGE_URL + list.get(position).message)
                    .placeholder(circularProgressDrawable)
                    .into(holder.ImgMysender)


            }
        } else {
            if (list.get(position).messageType == 1) {

                holder.txtMyMessageOthers.setText(list.get(position).message)
                var date = list.get(position).messageTime.toString()
                val upToNCharacters: String = date!!.substring(11, Math.min(date.length, 16))
                holder.txttimesUser.setText(date)


                var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val newDate = spf.parse(date)
                spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
                val newDateString = spf.format(newDate)
                holder.txttimesUser.setText(newDateString)
                //Toast.makeText(context, date.toString(), Toast.LENGTH_LONG).show()
              //  holder.txttimesUser.setText(getDate(date)+" "+getTime(date))
                holder.txtMyMessages.visibility = GONE
                holder.txttimes.visibility = GONE
                holder.cardviewSendUser.visibility = GONE
                holder.CardViewReciver.visibility = GONE
                //holder.txtMyMessageOther.textAlignment = TEXT_ALIGNMENT_VIEW_START

            } else {

                holder.txtMyMessageOthers.visibility = GONE
                holder.txttimesUser.visibility = GONE
                holder.txtMyMessages.visibility = GONE
                holder.txttimes.visibility = GONE
                holder.CardViewReciver.visibility = VISIBLE
                holder.ImgTime.visibility = VISIBLE
                holder.cardviewSendUser.visibility = GONE
                holder.ImgTimeReciver.visibility = GONE
                var date = list.get(position).messageTime.toString()
                val upToNCharacters: String = date!!.substring(11, Math.min(date.length, 16).toInt())
                var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val newDate = spf.parse(date)
                spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
                val newDateString = spf.format(newDate)
                holder.ImgTime.setText(newDateString)

               // Toast.makeText(context, list.get(position).message.toString(), Toast.LENGTH_LONG).show()

                Glide.with(context)
                    .load(ApiConstants.IMAGE_URL + list.get(position).message)
                    .placeholder(circularProgressDrawable)
                    .into(holder.ImgMyother)
            }
        }

    }
    fun add(dataItem: DataItem) {
        list!!.add(dataItem)
        notifyDataSetChanged()

    }

    fun getDate(dateAndTime:String):String{
        val sdf= SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateAndTime)
        return SimpleDateFormat("MM-dd-yyyy").format(sdf)
    }

    fun getTime(time:String): String {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.timeZone = (TimeZone.getTimeZone("IST"))
        val date = sdf.parse(time)
        return SimpleDateFormat("hh:mm ").format(date)

    }




    override fun getItemCount(): Int {

        return list.size
    }
    override fun getItemViewType(position: Int): Int {
        return list.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtMyMessages: TextView
        var txtMyMessageOthers: TextView
        var txttimesUser: TextView
        var txttimes: TextView
        var ImgTime: TextView
        var ImgTimeReciver: TextView
        var ImgMysender: ImageView
        var ImgMyother: ImageView
        var cardviewSendUser: CardView
        var CardViewReciver: CardView
        // private var timeText: TextView = view.txtMyMessageTime
        var chat1: ConstraintLayout
        var chat2: ConstraintLayout
        init {
            chat1=itemView.findViewById(R.id.chat1_constt)
            chat2=itemView.findViewById(R.id.chat2const)
            txtMyMessages = itemView.findViewById(R.id.txtMyMessages)
            txtMyMessageOthers = itemView.findViewById(R.id.txtMyMessageOthers)
            txttimesUser = itemView.findViewById(R.id.txttimesUser)
            ImgMysender = itemView.findViewById(R.id.ImgMysender)
            ImgMyother = itemView.findViewById(R.id.ImgMyother)
            txttimes = itemView.findViewById(R.id.txttimes)
            cardviewSendUser = itemView.findViewById(R.id.cardviewSendUser)
            CardViewReciver = itemView.findViewById(R.id.CardViewReciver)
            ImgTime = itemView.findViewById(R.id.ImgTime)
            ImgTimeReciver = itemView.findViewById(R.id.ImgTimeReciver)
        }
        /*fun bind(mess: MessageModels) {
            txtMyMessage.text = mess.msg
            //  timeText.text = DateUtils.fromMillisToTimeString(message.time)
        }*/
    }
}
