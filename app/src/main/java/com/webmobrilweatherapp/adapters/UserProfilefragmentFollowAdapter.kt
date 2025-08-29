package com.webmobrilweatherapp.adapters

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
import com.webmobrilweatherapp.BottomInterface
import com.webmobrilweatherapp.LikeInterface
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.fragment.profile.slideradapter.SliderProfileUserAdapter
import com.webmobrilweatherapp.model.userpost.DataItem
import com.webmobrilweatherapp.model.userpost.User
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.smarteist.autoimageslider.SliderView
import com.webmobrilweatherapp.activities.PostActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserProfilefragmentFollowAdapter(
    private val context: Context,
    private val listmodel: List<DataItem>, private val listmoedls: User?,private val bottomInterface: BottomInterface,
     private val likeInterface: LikeInterface
) : RecyclerView.Adapter<UserProfilefragmentFollowAdapter.ViewHolder>() {
    private val listModels: MutableList<DataItem>? = null
    var clicked = false
    lateinit var viewHolder: ViewHolder
    // private var datatm: List<TodayItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.profilefragmentfollow, parent, false)

        return ViewHolder(itemView)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun plust(cnt: String, pid: Int, liketype: String) {
        Log.e("tete", cnt)
        listmodel.get(pid).count = cnt.toInt()
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
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener(View.OnClickListener {
               var a=1
            val postIntent = Intent(context, PostActivity::class.java)
            postIntent.putExtra("PostId", listmodel.get(position).id.toString());
            postIntent.putExtra("statusBarcolor",a.toString())
            context.startActivity(postIntent);
        })



        val imageList = listmodel.get(position).post
        val videoURL=listmodel.get(position).postVideo

        Log.i("TAG", "onBindViewHolder: " + imageList?.size)

        if (listmodel.get(position).postVideo=="") {
            holder.ImagesSliderProfilePostUser.visibility=View.VISIBLE
            holder.videoPlayer.visibility=View.GONE
            holder.video_icon.visibility=View.GONE
            if (imageList != null) {


                if (imageList?.isEmpty()!!) {

                    holder.ImagesSliderProfilePostUser.visibility = View.GONE
                } else {
                    if(imageList.size==1){
                        holder.ImagesSliderProfilePostUser.visibility = View.GONE
                        holder.singleimageonly.visibility = View.VISIBLE
                        Glide.with(context)
                            .load(ApiConstants.IMAGE_URL +imageList[0])
                            .into(holder.singleimageonly)

                    }else{
                        holder.ImagesSliderProfilePostUser.visibility = View.VISIBLE
                        setImageInSlider(imageList, holder.ImagesSliderProfilePostUser)
                    }
                 //   holder.ImagesSliderProfilePostUser.visibility = View.VISIBLE
                  //  setImageInSlider(imageList, holder.ImagesSliderProfilePostUser)
                }
            }
        }

      else  if (listmodel.get(position).postVideo==null) {

            holder.ImagesSliderProfilePostUser.visibility=View.VISIBLE
            holder.videoPlayer.visibility=View.GONE
            holder.video_icon.visibility=View.GONE


            if (imageList != null) {


                if (imageList?.isEmpty()!!) {

                    holder.ImagesSliderProfilePostUser.visibility = View.GONE
                } else {
                    if(imageList.size==1){
                        holder.ImagesSliderProfilePostUser.visibility = View.GONE
                        holder.singleimageonly.visibility = View.VISIBLE
                        Glide.with(context)
                            .load(ApiConstants.IMAGE_URL +imageList[0])
                            .into(holder.singleimageonly)

                    }else{
                        holder.ImagesSliderProfilePostUser.visibility = View.VISIBLE
                        setImageInSlider(imageList, holder.ImagesSliderProfilePostUser)
                    }
                   // holder.ImagesSliderProfilePostUser.visibility = View.VISIBLE
                  //  setImageInSlider(imageList, holder.ImagesSliderProfilePostUser)
                }
            }
        }
        else
        {
            holder.ImagesSliderProfilePostUser.visibility=View.GONE
            holder.videoPlayer.visibility=View.VISIBLE
            holder.video_icon.visibility=View.VISIBLE

            var link="https://weatherdemocracy.com/storage/app/"+videoURL

            Glide.with(context)
                .load(link)
                .centerCrop()
                .placeholder(R.color.black)
                .into(holder.videoPlayer);



          /*  holder.videoPlayer.setOnClickListener(View.OnClickListener {

                val videoIntent = Intent(context, VideoplayerActivity::class.java)
                videoIntent.putExtra("Videolink", link);
                context.startActivity(videoIntent);


            })*/
        }
        var date = getTime(listmodel.get(position).createdAt.toString())

        var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val newDate = spf.parse(date)
        spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
        val newDateString = spf.format(newDate)
        holder.txtdates.setText(newDateString.toString())

        holder.txtPostComments.setText(listmodel.get(position).postCommentCount.toString()+" Comments")
        holder.imgComment.setOnClickListener {
            var positioon = listmodel.get(position).id
            bottomInterface.selectImage(positioon.toString())
        }
        holder.txtPostComments.setOnClickListener {
            var positioon = listmodel.get(position).id
            bottomInterface.selectImage(positioon.toString())
        }
        holder.txtComments.setText(listmodel.get(position).description)
        holder.txtUserNames.setText(listmoedls?.name.toString())
        var countlike = listmodel.get(position).count.toString()
        holder.TxtLike.setText(countlike + " Likes |")
        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + listmoedls?.profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.ImgProfile)


        if (listmodel.get(position).likedbyme!!.equals(1)){
            holder.imgdisloke.visibility = View.GONE
            holder.ImgLikes.visibility = View.VISIBLE
        } else {
            holder.imgdisloke.visibility = View.VISIBLE
            holder.ImgLikes.visibility = View.GONE
        }

       /* holder.layoutUserPost.setOnClickListener {
            if (!clicked) {
                // holder.ImgLikes.visibility=GONE
                val postid = listmodel.get(position).id
                var liketype = 1
                holder.imgdisloke.visibility = View.GONE
                holder.ImgLikes.visibility = View.VISIBLE
                likeInterface.selectposition(postid.toString(), liketype.toString(),position)
                clicked = true
            } else {
                holder.imgdisloke.visibility = View.VISIBLE
                holder.ImgLikes.visibility = View.GONE
                val postid = listmodel.get(position).id
                var liketypes = 2
               likeInterface.selectposition(postid.toString(), liketypes.toString(),position)
                // holder.imgdisloke.visibility= GONE
                clicked = false
            }
        }*/

        holder.imgdisloke.setOnClickListener {
            val postid = listmodel.get(position).id
            var liketype = 1
            holder.imgdisloke.visibility = View.GONE
            holder.ImgLikes.visibility = View.VISIBLE
            likeInterface.selectposition(
                postid.toString(),
                liketype.toString(),position
            )
        }
        holder.ImgLikes.setOnClickListener {
            val postid = listmodel.get(position).id
            var liketype = 2
            holder.imgdisloke.visibility = View.VISIBLE
            holder.ImgLikes.visibility = View.GONE
            likeInterface.selectposition(
                postid.toString(),
                liketype.toString(),
                position)
        }

    }
    override fun getItemCount(): Int {
        Log.i("TAG", "getItemCount: " + listmodel.size)
        return listmodel.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ImagesSliderProfilePostUser: SliderView
        var txtComments: TextView
        var txtdates: TextView
        var txtUserNames: TextView
        var txtPostComments: TextView
        var TxtLike: TextView
        var ImgProfile: ImageView
        var singleimageonly: ImageView
        var imgdisloke: ImageView
        var ImgLikes: ImageView
        var layoutUserPost: ConstraintLayout
        var imgComment: ImageView
        var videoPlayer:ImageView
        var video_icon:ImageView

        init {
            ImagesSliderProfilePostUser =
                itemView.findViewById<SliderView>(R.id.ImagesSliderProfilePostUser)
            txtComments = itemView.findViewById(R.id.txtComments)
            txtdates = itemView.findViewById(R.id.txtdates)
            txtUserNames = itemView.findViewById(R.id.txtUserNames)
            ImgProfile = itemView.findViewById(R.id.ImgProfile)
            TxtLike = itemView.findViewById(R.id.TxtLike)
            imgdisloke = itemView.findViewById(R.id.imgdisloke)
            ImgLikes = itemView.findViewById(R.id.ImgLikes)
            singleimageonly = itemView.findViewById(R.id.singleimageonlypff)
            layoutUserPost = itemView.findViewById(R.id.layoutUserPost)
            imgComment = itemView.findViewById(R.id.imgComment)
             txtPostComments = itemView.findViewById(R.id.txtPostComments)
            videoPlayer=itemView.findViewById(R.id.video_player)
            video_icon=itemView.findViewById((R.id.video_icon))
        }
    }

    private fun setImageInSlider(images: List<String?>?, imageSlider: SliderView) {
        val adapter = SliderProfileUserAdapter(context)
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

    fun getTime(time:String): String {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.timeZone = (TimeZone.getTimeZone("IST"))
        val date = sdf.parse(time)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
    }

}