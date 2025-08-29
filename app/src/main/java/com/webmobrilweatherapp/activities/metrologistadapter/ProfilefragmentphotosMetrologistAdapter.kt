package com.webmobrilweatherapp.activities.metrologistadapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.MetrologistPhotoViewActivity
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.IMAGE_URL

class ProfilefragmentphotosMetrologistAdapter(
    private val context: Context,
    private val ListModel: List<String>
) : RecyclerView.Adapter<ProfilefragmentphotosMetrologistAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder
    // private var datatm: List<TodayItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.profilefragmentphotosmetrologist, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(context, R.color.toolbaar))
        circularProgressDrawable.start()
        Glide.with(context)
            .load(IMAGE_URL + ListModel.get(position))
            .placeholder(circularProgressDrawable)
            .into(holder.ImgGallery)
        // this.viewHolder=holder
        //holder.Names.text=listModels.get(0).name


        holder.ImgGallery.setOnClickListener(View.OnClickListener {
            // selectItem.selectItem(IMAGE_URL + ListModel.get(position).toString())

            val postIntent = Intent(context, MetrologistPhotoViewActivity::class.java)
            postIntent.putExtra("Photourl", IMAGE_URL + ListModel.get(position).toString());
            context.startActivity(postIntent);


        })
    }

    override fun getItemCount(): Int {
        return ListModel.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ImgGallery: ImageView

        init {
            ImgGallery = itemView.findViewById(R.id.ImgGallery)

        }
    }
}