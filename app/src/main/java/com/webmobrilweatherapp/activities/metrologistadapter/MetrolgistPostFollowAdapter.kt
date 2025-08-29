package com.webmobrilweatherapp.activities.metrologistadapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.fragment.imageslidermetrologist.SliderMetrologistAdapter
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.BottomInterfacemretologist
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.LikeInterfaceMetrologist
import com.webmobrilweatherapp.model.userpost.User
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.smarteist.autoimageslider.SliderView
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistPostActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class   MetrolgistPostFollowAdapter(
    private val context: Context,
    private val listmodel: List<com.webmobrilweatherapp.model.userpost.DataItem>,
    private val listmodels: User?,
    private val bottomInterfacemretologist: BottomInterfacemretologist,
    private val likeInterfaceMetrologist: LikeInterfaceMetrologist
) : RecyclerView.Adapter<MetrolgistPostFollowAdapter.ViewHolder>() {
    var clicked = false
    lateinit var viewHolder: ViewHolder
    // private var datatm: List<TodayItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.postfragmentmetrologist, parent, false)

        return ViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun plust(cnt:String, pid:Int,liketype: String){
        Log.e("tete",cnt)
        listmodel.get(pid).count=cnt.toInt()
        listmodel.get(pid).likedbyme=1
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun plustcomment(cnt: String, pid: Int) {
        Log.e("tete", cnt)
        listmodel.get(pid).postCommentCount = cnt.toInt()
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun nigative(cnt: String, pid: Int, liketype: String) {
        Log.e("teteses", cnt)
        listmodel.get(pid).count = cnt.toInt()
        listmodel.get(pid).likedbyme=2
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageList = listmodel.get(position).post
        Log.i("TAG", "onBindViewHolder: " + imageList?.size)


        holder.itemView.setOnClickListener(View.OnClickListener {

            var a=0
            val postIntent = Intent(context, MetrologistPostActivity::class.java)
            postIntent.putExtra("PostId", listmodel.get(position).id.toString());
            postIntent.putExtra("statusBarcolor",a.toString())
            context.startActivity(postIntent);

        })


        val videoURL=listmodel.get(position).postVideo


        if (listmodel.get(position).postVideo=="") {
            holder.ImagesSliderProfilePostMetrologist.visibility=View.VISIBLE
            holder.videoPlayer.visibility=View.GONE
            holder.video_icon.visibility=View.GONE
            if (imageList != null) {
                if (imageList?.isEmpty()!!) {
                    holder.ImagesSliderProfilePostMetrologist.visibility = View.GONE
                } else {
                    if(imageList.size==1){
                        holder.ImagesSliderProfilePostMetrologist.visibility = View.GONE
                        holder.singleimageonly.visibility = View.VISIBLE
                        Glide.with(context)
                            .load(ApiConstants.IMAGE_URL +imageList[0])
                            .into(holder.singleimageonly)

                    }else{
                        holder.ImagesSliderProfilePostMetrologist.visibility = View.VISIBLE

                        setImageInSlider(imageList, holder.ImagesSliderProfilePostMetrologist)
                    }


                }
            }
        }


        else  if (listmodel.get(position).postVideo==null) {
            holder.ImagesSliderProfilePostMetrologist.visibility=View.VISIBLE
            holder.videoPlayer.visibility=View.GONE
            holder.video_icon.visibility=View.GONE
            if (imageList != null) {
                if (imageList?.isEmpty()!!) {
                    holder.ImagesSliderProfilePostMetrologist.visibility = View.GONE
                } else {
                    if(imageList.size==1){
                        holder.ImagesSliderProfilePostMetrologist.visibility = View.GONE
                        holder.singleimageonly.visibility = View.VISIBLE
                        Glide.with(context)
                            .load(ApiConstants.IMAGE_URL +imageList[0])
                            .into(holder.singleimageonly)

                    }else{
                        holder.ImagesSliderProfilePostMetrologist.visibility = View.VISIBLE

                        setImageInSlider(imageList, holder.ImagesSliderProfilePostMetrologist)
                    }


                    /* holder.ImagesSliderProfilePostMetrologist.visibility = View.VISIBLE

                     setImageInSlider(imageList, holder.ImagesSliderProfilePostMetrologist)
 */
                }
            }

        }


        else
        {
            holder.ImagesSliderProfilePostMetrologist.visibility=View.GONE
            holder.videoPlayer.visibility=View.VISIBLE
            holder.video_icon.visibility=View.VISIBLE

            var link="https://weatherdemocracy.com/storage/app/"+videoURL

            Glide.with(context)
                .load(link)
                .centerCrop()
                .placeholder(R.color.black)
                .into(holder.videoPlayer);

        }


            holder.txtComment.setText(listmodel.get(position).postCommentCount.toString() + " Comments")

            holder.txtComments.setText(listmodel.get(position).description)


        var date = getTime(listmodel.get(position).createdAt.toString())

        var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val newDate = spf.parse(date)
        spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
        val newDateString = spf.format(newDate)
        holder.txtdates.setText(newDateString.toString())
            holder.txtUserNames.setText(listmodels?.name.toString())
            var countlike = listmodel.get(position).count.toString()
            holder.TxtLike.setText(countlike + " Likes |")
            Glide.with(context)
                .load(ApiConstants.IMAGE_URL + listmodels?.profileImage)
                .placeholder(R.drawable.edit_profileicon)
                .into(holder.ImgProfile)

            holder.imgComment.setOnClickListener {
                var positioon = listmodel.get(position).id
                bottomInterfacemretologist.selectImage(positioon.toString())
            }


            holder.txtComment.setOnClickListener {
                var positioon = listmodel.get(position).id
                bottomInterfacemretologist.selectImage(positioon.toString())
            }
            if (listmodel.get(position).likedbyme == 1) {
                holder.Imgdislike.visibility = View.GONE
                holder.ImgLikes.visibility = View.VISIBLE
            } else {
                holder.Imgdislike.visibility = View.VISIBLE
                holder.ImgLikes.visibility = View.GONE
            }
            /* holder.LayoutLikeMetrologist.setOnClickListener {
            if (!clicked) {
                // holder.ImgLikes.visibility=GONE
                val postid = listmodel.get(position).id
                var liketype = 1
                holder.Imgdislike.visibility = View.GONE
                holder.ImgLikes.visibility = View.VISIBLE
                likeInterfaceMetrologist.selectposition(postid.toString(), liketype.toString(),position)
                clicked = true
            } else {
                holder.Imgdislike.visibility = View.VISIBLE
                holder.ImgLikes.visibility = View.GONE
                val postid = listmodel.get(position).id
                var liketypes = 2
                likeInterfaceMetrologist.selectposition(postid.toString(), liketypes.toString(),position)
                // holder.imgdisloke.visibility= GONE
                clicked = false

            }

        }*/




        holder.Imgdislike.setOnClickListener {
            val postid = listmodel.get(position).id
            var liketype = 1
            holder.Imgdislike.visibility = View.GONE
            holder.ImgLikes.visibility = View.VISIBLE
            likeInterfaceMetrologist.selectposition(
                postid.toString(),
                liketype.toString(),position
            )
        }
        holder.ImgLikes.setOnClickListener {
            val postid = listmodel.get(position).id
            var liketype = 2
            holder.Imgdislike.visibility = View.VISIBLE
            holder.ImgLikes.visibility = View.GONE
            likeInterfaceMetrologist.selectposition(
                postid.toString(),
                liketype.toString(),
                position
            )
        }


        // this.viewHolder=holder
        //holder.Names.text=listModels.get(0).name
    }

    override fun getItemCount(): Int {
        Log.i("TAG", "getItemCount: " + listmodel.size)
        return listmodel.size
    }
    fun getTime(time:String): String {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.timeZone = (TimeZone.getTimeZone("IST"))
        val date = sdf.parse(time)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ImagesSliderProfilePostMetrologist: SliderView
        var txtComments: TextView
        var txtdates: TextView
        var txtUserNames: TextView
        var txtComment: TextView
        var TxtLike: TextView
        var ImgProfile: ImageView
        var ImgLikes: ImageView
        var singleimageonly: ImageView
        var Imgdislike: ImageView
        var imgComment: ImageView
        var LayoutLikeMetrologist: ConstraintLayout
        var videoPlayer:ImageView
        var video_icon:ImageView
        init {

            ImagesSliderProfilePostMetrologist =
                itemView.findViewById(R.id.ImagesSliderProfilePostMetrologist)
            txtComments = itemView.findViewById(R.id.txtComments)
            txtdates = itemView.findViewById(R.id.txtdates)
            txtUserNames = itemView.findViewById(R.id.txtUserNames)
            ImgProfile = itemView.findViewById(R.id.ImgProfile)
            singleimageonly = itemView.findViewById(R.id.singleimageonly)
            TxtLike = itemView.findViewById(R.id.TxtLike)
            LayoutLikeMetrologist = itemView.findViewById(R.id.LayoutLikeMetrologist)
            ImgLikes = itemView.findViewById(R.id.ImgLikes)
            Imgdislike = itemView.findViewById(R.id.Imgdislike)
            imgComment = itemView.findViewById(R.id.imgComment)
            txtComment = itemView.findViewById(R.id.txtComment)
            videoPlayer=itemView.findViewById(R.id.video_player)
            video_icon=itemView.findViewById((R.id.video_icon))

        }
    }

    private fun setImageInSlider(images: List<String?>?, imageSlider: SliderView) {
        val adapter = SliderMetrologistAdapter(context)
        adapter.renewItems(images as ArrayList<String>)
        imageSlider.setSliderAdapter(adapter)
        //  binding.txtCounter.setText("1/"+imageList.size)

        imageSlider.setCurrentPageListener {
            val position = imageSlider.currentPagePosition
            //  binding.txtCounter.setText((position+1).toString()+"/"+imageList.size)
            Log.i("TAG", "setImageInSlider: " + position)

        }
    }

    fun getDate(dateAndTime:String):String{
        val sdf= SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateAndTime)
        return SimpleDateFormat("MM-dd-yyyy").format(sdf)
    }


}