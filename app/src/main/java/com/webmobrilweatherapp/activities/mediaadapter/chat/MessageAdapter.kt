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
import com.webmobrilweatherapp.activities.MetrologistPhotoViewActivity
import com.webmobrilweatherapp.model.getchat.DataItem
import com.webmobrilweatherapp.viewmodel.ApiConstants
import java.text.SimpleDateFormat

class MessageAdapter(private val context: Context, private var list: ArrayList<DataItem>,private var type:Int) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder
    private var useridMetrologist = 0
    private var  ids = "0"
    //  private var list:ArrayList<MessageModels>?= ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ids =
            CommonMethod.getInstance(context).getPreference(AppConstant.KEY_User_idMetrologist)

        useridMetrologist=ids.toInt()
        /* return if (viewType == VIEW_TYPE_MY_MESSAGE) {
             ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_message, parent, false))
         } else {
             ViewHolder(LayoutInflater.from(context).inflate(R.layout.other_message, parent, false))
         }*/
        var itemView: View =LayoutInflater.from(context).inflate(R.layout.my_message, parent, false)
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



        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(context, R.color.toolbaar))
        circularProgressDrawable.start()


            var fromid=list.get(position).from

        /////////////////////////////////////////////////////////////////////////////


        holder.imgsendImagreciver.setOnClickListener(View.OnClickListener {
            val postIntent = Intent(context, MetrologistPhotoViewActivity::class.java)
            postIntent.putExtra("Photourl", ApiConstants.IMAGE_URL + list.get(position).message);
            context.startActivity(postIntent);


        })


        holder.imgsendImageUser.setOnClickListener(View.OnClickListener {

            val postIntent = Intent(context, MetrologistPhotoViewActivity::class.java)
            postIntent.putExtra("Photourl", ApiConstants.IMAGE_URL + list.get(position).message);
            context.startActivity(postIntent);

        })


        ////////////////////////////////////////////////////////////////////////////
            if (useridMetrologist==fromid) {
                if (list.get(position).messageType==1){
                    holder.txtMyMessage.setText(list.get(position).message)
                    var date = list.get(position).messageTime!!.toString()
                    val upToNCharacters: String = date!!.substring(11, Math.min(date.length, 16))
                    var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val newDate = spf.parse(date)
                    spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
                    val newDateString = spf.format(newDate)
                    holder.txttimes.setText(newDateString)
                    holder.txtMyMessageOther.visibility= GONE
                    holder.txttimesUser.visibility= GONE
                }else{
                    holder.txtMyMessageOther.visibility= GONE
                    holder.txttimesUser.visibility= GONE
                    holder.txtMyMessage.visibility= GONE
                    holder.txttimes.visibility= GONE
                    holder.CardViewUserImg.visibility= VISIBLE
                    holder.ImgTimeUser.visibility= VISIBLE
                    var date = list.get(position).messageTime.toString()
                    val upToNCharacters: String = date!!.substring(11, Math.min(date.length, 16).toInt())
                    var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val newDate = spf.parse(date)
                    spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
                    val newDateString = spf.format(newDate)
                    holder.ImgTimeUser.setText(newDateString)
                    holder.CardViewIMgReciver.visibility= GONE
                    holder.ImgTime.visibility= GONE
                    Glide.with(context)
                        .load(ApiConstants.IMAGE_URL +list.get(position).message)
                        .placeholder(circularProgressDrawable)
                        .into(holder.imgsendImageUser)
                }
            }else {
                if (list.get(position).messageType==1){
                    holder.txtMyMessageOther.setText(list.get(position).message)
                    var date = list.get(position).messageTime.toString()
                     val upToNCharacters: String = date!!.substring(11, Math.min(date.length, 16))

                    var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val newDate = spf.parse(date)
                    spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
                    val newDateString = spf.format(newDate)
                     holder.txttimesUser.setText(newDateString)
                    holder.txtMyMessage.visibility= GONE
                    holder.txttimes.visibility= GONE

                }else{
                    holder.txtMyMessageOther.visibility= GONE
                    holder.txttimesUser.visibility= GONE
                    holder.txtMyMessage.visibility= GONE
                    holder.txttimes.visibility= GONE
                    holder.CardViewUserImg.visibility= GONE
                    holder.ImgTimeUser.visibility= VISIBLE
                    holder.CardViewIMgReciver.visibility= VISIBLE
                    holder.ImgTime.visibility= VISIBLE

                    var date = list.get(position).messageTime.toString()
                  //  var upToNCharacters: String = date!!.substring(11, Math.min(date.length, 16).toInt())
                    var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val newDate = spf.parse(date)
                    spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
                    val newDateString = spf.format(newDate)
                    holder.ImgTime.setText(newDateString)
                    Glide.with(context)
                        .load(ApiConstants.IMAGE_URL +list.get(position).message)
                        .placeholder(circularProgressDrawable)
                        .into(holder.imgsendImagreciver)
                }


            }

    }
    fun add(dataItem: DataItem) {
        list.add(dataItem)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {

          return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtMyMessage: TextView
        var txtMyMessageOther: TextView
        var txttimes: TextView
        var txttimesUser: TextView
        var ImgTime: TextView
        var ImgTimeUser: TextView
        var imgsendImagreciver: ImageView
        var imgsendImageUser: ImageView
        var CardViewUserImg: CardView
        var CardViewIMgReciver: CardView
        var chat1:ConstraintLayout
        var chat2:ConstraintLayout
        init {
            chat1=itemView.findViewById(R.id.chat1_const)
            chat2=itemView.findViewById(R.id.layoutMyMessage)
            txtMyMessage = itemView.findViewById(R.id.txtMyMessage)
            txtMyMessageOther = itemView.findViewById(R.id.txtMyMessageOther)
            txttimes = itemView.findViewById(R.id.txttimes)
            txttimesUser = itemView.findViewById(R.id.txttimesUser)
            imgsendImageUser = itemView.findViewById(R.id.imgsendImageUser)
            imgsendImagreciver = itemView.findViewById(R.id.imgsendImagreciver)
            CardViewUserImg = itemView.findViewById(R.id.CardViewUserImg)
            CardViewIMgReciver = itemView.findViewById(R.id.CardViewIMgReciver)
            ImgTime = itemView.findViewById(R.id.ImgTime)
            ImgTimeUser = itemView.findViewById(R.id.ImgTimeUser)
        }
    }
}
