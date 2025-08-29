package com.webmobrilweatherapp.activities.metrologistadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.mediaadapter.CounterInterface
import com.smarteist.autoimageslider.SliderViewAdapter
import java.util.*

class SliderImageMetrologistAdapter(
    private val context: Context,
    val counterInterface: CounterInterface
) :
    SliderViewAdapter<SliderImageMetrologistAdapter.VH>() {
    private var mSliderItems = ArrayList<String>()

    fun renewItems(sliderItems: ArrayList<String>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: String) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): VH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.shopimage_holder, null)
        return VH(inflate)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        //load image into view
        Glide.with(context)
            .load(mSliderItems[position])
            .into(viewHolder.imageView)

        counterInterface.onSliderChangeListner(position)

    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class VH(itemView: View) : ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageSlider)

    }
}