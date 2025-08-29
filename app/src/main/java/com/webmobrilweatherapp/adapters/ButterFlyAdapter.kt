package com.webmobrilweatherapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.model.butterflySpecies.DataItem
import com.webmobrilweatherapp.viewmodel.ApiConstants


class ButterFlyAdapter(private val context: Context, private val List:List<DataItem>,private  val selectItem: ButterFlyAdapter.SelectItem):
    RecyclerView.Adapter<ButterFlyAdapter.ViewHolder>() {

        interface SelectItem{
        fun selectItem(id:String)
    }

    var checkedPosition = 0
    var ButterflyId="0"
   var s=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButterFlyAdapter.ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.butterfly_item, parent, false)
        ButterflyId= CommonMethod.getInstance(context).getPreference(AppConstant.Key_ID_Butterfly,"0")

        return ButterFlyAdapter.ViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ButterFlyAdapter.ViewHolder, position: Int) {
        /*holder.itemView.setOnClickListener(View.OnClickListener {
            selectItem.selectItem("5")
        })*/

        Log.e("statusBind",List.get(position).is_selected.toString())
      /*  holder.Ulysses_cardview.setOnClickListener(View.OnClickListener {
            //selectItem.selectItem(List.get(position).id.toString())

           // Toast.makeText(context,"Hello Javatpoint",Toast.LENGTH_SHORT).show();
        })*/


        if(List.get(position).id.toString()==ButterflyId)
        {

    if(s==0)
        {

    List.get(position).is_selected=true

         }

        }

        if(List.get(position).is_selected==true)
        {
            holder.tick.visibility = View.VISIBLE
        }
        else
        {
            holder.tick.visibility = View.GONE

        }


        holder.Ulysses_cardview.setOnClickListener(View.OnClickListener {
          s=1
            for (i in List.indices){

                holder.tick.visibility=View.VISIBLE
                if (List.get(position).id==List.get(i).id)
                {    Log.e("rt",List.get(position).id.toString()+"----"+List.get(i).id)
                    List.get(position).is_selected=true
                    selectItem.selectItem(List.get(position).id.toString())
                    //Toast.makeText(context,List.get(position).id.toString(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.e("rt0",List.get(position).id.toString()+"----"+List.get(i).id)
                    List.get(i).is_selected=false
                }
            }
            notifyDataSetChanged()
        })

        holder.name__.text = List.get(position).butterfliesTitle
        Glide.with(context)
            .load(ApiConstants.im_url + List?.get(position)?.butterfliesImage)
            .into(holder.bt_image)


    }

    override fun getItemCount(): Int
    {
        return List.size;

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name__: TextView
        var bt_image: ImageView
        var Ulysses_cardview: CardView
        var tick: ImageView

        var checkedPosition = 0

        init {
            name__ = itemView.findViewById(R.id.name_)
            bt_image = itemView.findViewById(R.id.img1)
            Ulysses_cardview = itemView.findViewById(R.id.Ulysses_cardview)
            tick = itemView.findViewById(R.id.uly_tick1)

        }
       /* private fun bind(DataItem datum)
        {
            if(checkedPosition==-1)
            {


            }
        }*/
/////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}