package com.webmobrilweatherapp.adapters

import android.content.Context
import com.webmobrilweatherapp.model.InstaPostData
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.webmobrilweatherapp.R
import android.widget.TextView

class InstaPostAdapter(
    private val context: Context,
    private val listModelsdata: List<InstaPostData>
) : RecyclerView.Adapter<InstaPostAdapter.Myviewholder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Myviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.instarow, parent, false)
        return Myviewholder(view)
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {

    }
    override fun getItemCount(): Int {
        return listModelsdata.size
    }

    inner class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var instalogopic: ImageView
        var instapic1: ImageView
        var imglikeicon: ImageView
        var imgcommenticon: ImageView
        var textnamedavid: TextView
        var textlorem: TextView

        init {
            instalogopic = itemView.findViewById(R.id.instalogopic)
            instapic1 = itemView.findViewById(R.id.instapic1)
            imglikeicon = itemView.findViewById(R.id.imglikeicon)
            imgcommenticon = itemView.findViewById(R.id.imgcommenticon)
            textnamedavid = itemView.findViewById(R.id.textnamedavid)
            textlorem = itemView.findViewById(R.id.textlorem)
        }
    }
}