package com.webmobrilweatherapp.activities.metrologistadapter


import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.TendaysWeatherApi.DailyForecastsItem
import java.text.SimpleDateFormat
import java.util.*

class Matro_SevenDaysAdapter(private val context: Context, val list: List<DailyForecastsItem>):
    RecyclerView.Adapter<Matro_SevenDaysAdapter.ViewHolder>() {

    interface SelectItem{
        fun selectItem(id:String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Matro_SevenDaysAdapter.ViewHolder {

        var itemView: View = LayoutInflater.from(context).inflate(R.layout.metro_sevendays,parent,false)
        return  Matro_SevenDaysAdapter.ViewHolder(itemView)

    }


    override fun onBindViewHolder(holder: Matro_SevenDaysAdapter.ViewHolder, position: Int) {
        // date.setText("Last updated  "+indianTime);

        /*SimpleDateFormat utcFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String utcTime = utcFormatter.format(mPostItems.get(position).getTime());*/

        //    txtTime.setText(splited[1]);
        for (i in 0..6) {

            val splited = list.get(position).date!!.split("T")

            var d=splited[0].toString()
            val stringDate =d
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = formatter.parse(stringDate)
            val calender = Calendar.getInstance()
            calender.setTime(date)
            val dayToday: String = DateFormat.format("EEEE",calender) as String

            holder.date.text = dayToday

            var tempMinimum = list.get(position).temperature!!.minimum!!.value
            var tempMaximum = list.get(position).temperature!!.maximum!!.value
            var MinC = (tempMinimum!! - 32) * 5 / 9
            var MaxC = (tempMaximum!! - 32) * 5 / 9


            ////////////////////////////////////////////////////////////////////////////////////////////



            /*holder.temp.text =
                MinC.toString().substring(0, 2) + "℃" + "/" + MaxC.toString().substring(0, 2) + "℃"*/
////////////////////////////////////////////////////
            var subString =
                MinC.toString()
                    .split(".")

            var tempValue=MinC.toString().split(".")

            var t=tempValue[1].toString().substring(0,1)





            var a=t.toInt()

            if (5<a) {

                var b=subString[0].toInt()+1
                // binding.text.setText("" + b + "\u2103")
                holder.temp.text =
                    ""+ b + "℃" + "/"
            }
            else{
                // Toast.makeText(context,it[0].temperature?.metric?.value.toString()+""+tempValue.toString(), Toast.LENGTH_LONG).show()
                //  binding.text.setText("" + subString + "\u2103")

                holder.temp.text =
                    ""+ subString[0] + "℃" + "/"
            }
            //////////////////////////////////////////////////////////////////////////////////

            var subString2 =
                MaxC.toString().toString()
                    .split(".")

            var tempValue2=MaxC.toString().split(".")
            var t2=tempValue2[1].toString().substring(0,1)

            var a2=t2.toInt()

            if (5<=a2) {

                var b2=subString2[0].toInt()+1
                // binding.text.setText("" + b + "\u2103")
                holder.temp2.text =
                    ""+ b2 + "℃"
            }
            else{
                // Toast.makeText(context,it[0].temperature?.metric?.value.toString()+""+tempValue.toString(), Toast.LENGTH_LONG).show()
                //  binding.text.setText("" + subString + "\u2103")

                holder.temp2.text =
                    ""+ subString2[0] + "℃"

            }



            ////////////////////////////////////////////////////////////////////////////////////////////
            val isNight: Boolean
            val cal: Calendar = Calendar.getInstance()
            val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
            isNight = if (hour < 6 || hour > 18) {
                true
            } else {
                false
            }

            if (isNight == true) {
                holder.iconPhrases.text = list.get(position).night!!.iconPhrase
            } else {
                holder.iconPhrases.text = list.get(position).day!!.iconPhrase

            }

        }
    }

    override fun getItemCount(): Int {

        return 7 ;

    }
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)
    {

        var iconPhrases: TextView
        var date:TextView
        var temp:TextView
        var temp2:TextView

        init {
            iconPhrases = itemView.findViewById(R.id.IconPhrase)
            date=itemView.findViewById(R.id.date)
            temp=itemView.findViewById(R.id.temp)
            temp2=itemView.findViewById(R.id.temp2)

        }
    }
}




