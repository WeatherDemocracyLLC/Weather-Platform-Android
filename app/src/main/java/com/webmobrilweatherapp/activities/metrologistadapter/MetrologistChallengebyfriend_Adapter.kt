package com.webmobrilweatherapp.activities.metrologistadapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.viewmodel.ApiConstants

class MetrologistChallengebyfriend_Adapter(private  val context: Context, private  val selectItem: MetrologistChallengebyfriend_Adapter.SelectItem, private val challengeItem: MetrologistChallengebyfriend_Adapter.ChallengeItem, private  val List:List<com.webmobrilweatherapp.model.challengebyfriends.DataItem>):
    RecyclerView.Adapter<MetrologistChallengebyfriend_Adapter.ViewHolder>() {

    interface SelectItem{
        fun selectItem(id:String,temp:String,city:String,precption:String)
    }


    interface ChallengeItem{
        fun challengeItem(challengeByMeName:String,meTemp:String,meTemptypr:String,location:String,date:String,mePreception:String,CompetetorName:String,comptTemp:String,comptTempType:String,comptPrecption:String)
    }


    var prec=""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetrologistChallengebyfriend_Adapter.ViewHolder {

        var itemView: View = LayoutInflater.from(context).inflate(R.layout.challenegebyfriend_item,parent,false)
        return  MetrologistChallengebyfriend_Adapter.ViewHolder(itemView)

    }


    override fun onBindViewHolder(holder: MetrologistChallengebyfriend_Adapter.ViewHolder, position: Int)
    {

        holder.voter_name.setText(List.get(position).compititor1?.name)
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
            .load(ApiConstants.IMAGE_URL + List?.get(position)?.compititor1?.profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.votingListImage)


        holder.itemView.setOnClickListener(View.OnClickListener {
            if(List.get(position).isTemp==0)
            {

                prec="Low"
            }
            else
            {


                prec="High"

            }
            if(List.get(position).isChallengeAccept==0)
            {
                selectItem.selectItem(List.get(position)?.id.toString(),List.get(position).voteTempValue.toString(),List.get(position).city.toString(),prec)

            }
            holder.itemView.setOnClickListener(View.OnClickListener {

                if(List.get(position).isChallengeAccept!=0)
                {
                    challengeItem.challengeItem(List.get(position)?.competitorId.toString(),List.get(position)?.voteTempValue.toString(),List.get(position)?.isTemp.toString(),List.get(position)?.city.toString(),List.get(position)?.voteDate.toString(),List.get(position)?.precipitationId.toString(),List.get(position)?.compititor1?.name.toString(),List.get(position)?.voteTempValueByCompetitor.toString(),List.get(position)?.isTempByCompetitor.toString(),List.get(position)?.precipitationIdByCompetitor.toString())

                }
                else
                {
                    selectItem.selectItem(List.get(position)?.id.toString(),List.get(position).voteTempValue.toString(),List.get(position).city.toString(),prec)

                }
            })
        })

    }
    override fun getItemCount(): Int {

        return  List.size;
    }
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

        var voter_name: TextView
        var temprature: TextView
        var tv_speed: TextView
        var date: TextView
        var status: TextView
        var locationName: TextView
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




