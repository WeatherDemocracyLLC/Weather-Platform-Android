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
import com.webmobrilweatherapp.model.challengebyme.DataItem
import com.webmobrilweatherapp.viewmodel.ApiConstants

class challenegbyme_Adpater(private val context: Context,private  val selectItem: challenegbyme_Adpater.SelectItem, private  val List:List<DataItem>):
    RecyclerView.Adapter<challenegbyme_Adpater.ViewHolder>() {

    interface SelectItem{
        fun selectItem(challengeByMeName:String,meTemp:String,meTemptypr:String,location:String,date:String,mePreception:String,CompetetorName:String,comptTemp:String,comptTempType:String,comptPrecption:String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): challenegbyme_Adpater.ViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.challengeby_me_item,parent,false)
        return  challenegbyme_Adpater.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: challenegbyme_Adpater.ViewHolder, position: Int)
    {

        holder.voter_name.setText(List.get(position).compititor?.name)
        val TemperatureMeasurementStr = List.get(position).voteTempValue.toString() + "\u2103"
        holder.temprature.setText(TemperatureMeasurementStr)
        holder.date.setText(List.get(position).voteDate)
        if(List.get(position).isChallengeAccept==0)
        {
            holder.status.setText("Pending")
        }
        else
        {
            holder.status.setText("Accept")
        }


        if(List.get(position).isTemp==0)
        {
            holder.tv_speed.text="Low"
        }
        else
        {
            holder.tv_speed.text="High"
        }

        holder.locationName.setText(List.get(position)?.city.toString())
        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + List?.get(position)?.compititor?.profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.votingListImage)

        holder.itemView.setOnClickListener(View.OnClickListener {

            if(List.get(position).isChallengeAccept!=0)
            {
                selectItem.selectItem(List.get(position)?.competitorId.toString(),List.get(position)?.voteTempValue.toString(),List.get(position)?.isTemp.toString(),List.get(position)?.city.toString(),List.get(position)?.voteDate.toString(),List.get(position)?.precipitationId.toString(),List.get(position)?.compititor?.name.toString(),List.get(position)?.voteTempValueByCompetitor.toString(),List.get(position)?.isTempByCompetitor.toString(),List.get(position)?.precipitationIdByCompetitor.toString())

            }
        })
//challengeByMeName:String,meTemp:String,meTemptypr:String,location:String,date:String,mePreception:String,CompetetorName:String,comptTemp:String,comptTempType:String,comptPrecption:String

    }
    override fun getItemCount(): Int {

        return  List.size;

    }
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)
    {

        var voter_name: TextView
        var temprature:TextView
        var tv_speed:TextView
        var date:TextView
        var status:TextView
        var locationName:TextView
        var votingListImage: ImageView
        init {
            voter_name=itemView.findViewById(R.id.votername)
            votingListImage=itemView.findViewById(R.id.votinglistimage)
            temprature=itemView.findViewById(R.id.temperatureindegree)
            date=itemView.findViewById(R.id.date)
            tv_speed=itemView.findViewById(R.id.tvspeed)
            locationName=itemView.findViewById(R.id.locationname)
            status=itemView.findViewById(R.id.status_)

        }
    }
}




