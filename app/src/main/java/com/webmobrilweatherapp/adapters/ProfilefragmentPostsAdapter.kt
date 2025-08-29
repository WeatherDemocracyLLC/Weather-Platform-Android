package com.webmobrilweatherapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.fragment.profile.slideradapter.SliderProfileUserAdapter
import com.webmobrilweatherapp.model.userpost.User
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.smarteist.autoimageslider.SliderView
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.collections.ArrayList

import com.webmobrilweatherapp.*

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.model.userpost.DataItem
import java.text.SimpleDateFormat

class ProfilefragmentPostsAdapter(
    private val context: Context,
    private val listmodel: List<DataItem>,
    private val listmoedls: User?,
    private val bottomInterface: BottomInterface,
    private val deleteBottomInterface: DeleteBottomInterface,
    private val likeInterface: LikeInterface,
) : RecyclerView.Adapter<ProfilefragmentPostsAdapter.ViewHolder>() {
    private val listModels: MutableList<DataItem>? = null
    var clicked = false
    var count=0
    lateinit var viewHolder: ViewHolder
    // private var datatm: List<TodayItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.profilefragmenthome, parent, false)

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.Share.setOnClickListener(View.OnClickListener {

            shareProduct(listmodel.get(position).id.toString())
        })

        holder.itemView.setOnClickListener(View.OnClickListener {
           var a=0
            val postIntent = Intent(context, PostActivity::class.java)
            postIntent.putExtra("PostId", listmodel.get(position).id.toString());
            postIntent.putExtra("statusBarcolor",a.toString())

            context.startActivity(postIntent);

        })

        val imageList = listmodel.get(position).post
        val videoURL=listmodel.get(position).postVideo




        if (listmodel.get(position).postVideo=="")
        {
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
        else if(listmodel.get(position).postVideo==null)
        {
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
        else {


            holder.ImagesSliderProfilePostUser.visibility = View.GONE
            holder.videoPlayer.visibility = View.VISIBLE
            holder.video_icon.visibility = View.VISIBLE

            var link = "https://weatherdemocracy.com/storage/app/" + videoURL

            /* holder.videoPlayer.webViewClient = WebViewClient()
            holder.videoPlayer.settings.javaScriptCanOpenWindowsAutomatically = true
            holder.videoPlayer.settings.javaScriptEnabled = true
            holder.videoPlayer.settings.setSupportZoom(true)
            holder.videoPlayer.settings.mediaPlaybackRequiresUserGesture = false

            holder.videoPlayer.loadUrl(link)*/


            /*    val player = ExoPlayer.Builder(context).build()
            holder.videoPlayer.setPlayer(player);
            val mediaItem: MediaItem = MediaItem.fromUri(link)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady*/


            /*     holder.videoPlayer.setOnTouchListener(OnTouchListener { v, event ->
                player.playWhenReady = !player.playWhenReady
                false
            })*/



            Glide.with(context)
                .asBitmap()
                .apply(RequestOptions().placeholder(R.color.black).centerCrop())
                .load(link)
                .into(holder.videoPlayer);



         /*   holder.videoPlayer.setOnClickListener(View.OnClickListener {

                val videoIntent = Intent(context, VideoplayerActivity::class.java)
                videoIntent.putExtra("Videolink", link);
                context.startActivity(videoIntent);


            })*/
        }




            Log.i("TAG", "onBindViewHolder: " + imageList?.size)
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
            }else {

                holder.imgdisloke.visibility = View.VISIBLE
                holder.ImgLikes.visibility = View.GONE

            }


            /*holder.layoutUserPost.setOnClickListener {
                if (!clicked) {
                    // holder.ImgLikes.visibility=GONE
                    val postid = listmodel.get(position).id
                    var liketype = 1
                    holder.imgdisloke.visibility = View.GONE
                    holder.ImgLikes.visibility = View.VISIBLE
                    var counts=listmodel.get(position).count
                    count= counts!! +1
                    likeInterface.selectposition(postid.toString(), liketype.toString(),position)
                    holder.TxtLike.setText(count)
                    clicked = true
                } else {
                    holder.imgdisloke.visibility = View.VISIBLE
                    holder.ImgLikes.visibility = View.GONE
                    val postid = listmodel.get(position).id
                    var liketypes = 2

                    likeInterface.selectposition(postid.toString(), liketypes.toString(),position)
                    // holder.imgdisloke.visibility= GONE
                    holder.TxtLike.setText(count)
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



            holder.ImgDelete.setOnClickListener {
                deleteBottomInterface.selectImagedelete(position.toString())
                listModels?.removeAt(position)
                notifyDataSetChanged()


            }



    }

    private fun shareImageandText(bitmap: Uri)
    {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, bitmap)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        context.startActivity(intent);
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
        var imgdisloke: ImageView
        var ImgLikes: ImageView
        var singleimageonly: ImageView
        var layoutUserPost: ConstraintLayout
        var imgComment: ImageView
        var ImgDelete: ImageView
       // var videoPlayer:StyledPlayerView
        var videoPlayer:ImageView
        var Share:ImageView
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
            singleimageonly = itemView.findViewById(R.id.singleimageonly)

            ImgLikes = itemView.findViewById(R.id.ImgLikes)
            layoutUserPost = itemView.findViewById(R.id.layoutUserPost)
            imgComment = itemView.findViewById(R.id.imgComment)
            ImgDelete = itemView.findViewById(R.id.ImgDelete)
            txtPostComments = itemView.findViewById(R.id.txtPostComments)
            videoPlayer=itemView.findViewById(R.id.video_player)
            Share=itemView.findViewById(R.id.share_button)
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


    /**
     * Returns the visible region of the video surface on the screen.
     * if some is cut off, it will return less than the @videoSurfaceDefaultHeight
     */

    // Remove the old player

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

    private fun shareProduct(uri:String) {
        //  ProgressBarUtils.hideProgressDialog()
        ProgressD.showLoading(context,context.getResources().getString(R.string.logging_in))

        // binding.ivLoader.visibility=View.VISIBLE
        var shareLink = "https://weatherdemocracy.com/post?id="+uri+"&"+"userType="+2
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse(shareLink)) /// add website name
            .setDomainUriPrefix("https://weatherdemocracy.page.link/?") /// add subdomain name
            // Open links with this app on Android
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            .setIosParameters(DynamicLink.IosParameters.Builder("com.Weather-Democracy").build())
            .buildDynamicLink()
        val dynamicLinkUri = dynamicLink.uri
        Log.e("dynamicLink", "" + dynamicLink.uri)
        val sharelinktext =dynamicLinkUri
        ""  +
                "&apn=" + context.getPackageName() +
                "&st=" + "" +
                "&sd="  +
                "&si="
        Log.e("description", "manuallink$dynamicLinkUri")
        val shortLinkTask: Task<ShortDynamicLink> = FirebaseDynamicLinks.getInstance()
            .createDynamicLink() //.setLongLink(dynamicLink.getUri())
            .setLongLink(Uri.parse(sharelinktext.toString())) // manually
            .buildShortDynamicLink()
            .addOnCompleteListener(
                Activity(),
                OnCompleteListener<ShortDynamicLink> { task ->
                    if (task.isSuccessful) {
                        ProgressD.hideProgressDialog()
                        //     binding.ivLoader.visibility=View.GONE

                        val shortLink = task.result.shortLink
                        val flowchartLink = task.result.previewLink
                        Log.e("main ", "short link " + shortLink.toString())
                        // share app dialog
                        val clipboard =context.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("AngleFood", shortLink.toString())
                        clipboard.setPrimaryClip(clip)
                        val intent = Intent()
                        intent.action = Intent.ACTION_SEND
                        //intent.putExtra(Intent.EXTRA_STREAM, uri)
                        intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
                        intent.type = "text/plain"
                        CommonMethod.getInstance(context).savePreference(AppConstant.KEY_ID_SHARE,"1")
                        context.startActivity(intent)
                    } else {
                        ProgressD.hideProgressDialog()

                        // binding.ivLoader.visibility=View.GONE

                    }
                })

    }
}

