
package com.webmobrilweatherapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.model.NewsItem
import com.webmobrilweatherapp.model.newapimodel.newnesapi.Seometa



class NewsAdapter(
    private val context: Context,private var listmodels: List<NewsItem>
) : RecyclerView.Adapter<NewsAdapter.Myviewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.newsrow, parent, false)
        return Myviewholder(view)
    }

    override fun getItemCount(): Int {
        Log.d("TAG", "dfghjklhgfdfsgh: ${listmodels.size}")
        return  listmodels.size
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {

        holder.textlorem.text = listmodels[position].title
        holder.textdecription.text = listmodels[position].createdate

        holder.textlink.setOnClickListener {
            var providename = listmodels[position].providername
            var newUrl = "https://" + providename + listmodels[position].url
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(newUrl)
            context.startActivity(openURL)
        }


        /*holder.itemView.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(listmodels.get(position).url)
            context.startActivity(openURL)
        }*/
        Log.d("TAG", "wesrtyuioytrewarty: ${listmodels[position].seometa?.ogimage}")
            Glide.with(context)
                .load(listmodels[position].seometa?.ogimage)
                .placeholder(R.drawable.edit_profileicon)
                .into(holder.instapic1)


    }



    inner class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      var   textlorem:TextView
      var   instapic1:ImageView
      var   textdecription:TextView
      var   textLinkUrl:TextView
      var   textlink:TextView

        init {
            textlorem=itemView.findViewById(R.id.textlorem)
            instapic1=itemView.findViewById(R.id.instapic1)
            textdecription=itemView.findViewById(R.id.textdecription)
            textLinkUrl=itemView.findViewById(R.id.textLinkUrl)
            textlink=itemView.findViewById(R.id.textlink)
        }
    }
}
data class NewsModel(
    val articles: List<Article>
)

data class Article(
    val title: String?,
    val createdate: String?,
    val providername: String?,
    val url: String?,
    val seometa: Seometa?
)
