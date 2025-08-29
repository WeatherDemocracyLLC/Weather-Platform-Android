package com.webmobrilweatherapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.net.Uri
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.smarteist.autoimageslider.SliderView
import com.webmobrilweatherapp.BottomInterface
import com.webmobrilweatherapp.LikeInterface
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.fragment.profile.slideradapter.SliderProfileHomeAdapter
import com.webmobrilweatherapp.model.homepage.DataItem
import com.webmobrilweatherapp.viewmodel.ApiConstants
import java.text.SimpleDateFormat
import java.util.*


class ProfilefragmenthomeAdapter(
    private val context: Context,
    private val listmodels: List<DataItem>,
    private val bottomInterface: BottomInterface,
    private val likeInterface: LikeInterface,
    var uri: String = ""
) : RecyclerView.Adapter<ProfilefragmenthomeAdapter.ViewHolder>() {


    lateinit var viewHolder: ViewHolder
    var clicked = false
    var doubleClick: Boolean? = false
    val layoutInflater = null
    var count = 0
    var likees: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.profilefragmenthomeadapter, parent, false)

        return ViewHolder(itemView)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun plust(cnt: String, pid: Int, liketype: String) {
        Log.e("tete", cnt)
        listmodels.get(pid).count = cnt.toInt()
        listmodels.get(pid).likedbyme = 1
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun plustcomment(cnt: String, pid: Int) {
        Log.e("tete", cnt)
        listmodels.get(pid).postCommentCount = cnt.toInt()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun nigative(cnt: String, pid: Int, liketype: String) {
        Log.e("teteses", cnt)
        listmodels.get(pid).count = cnt.toInt()
        listmodels.get(pid).likedbyme = 2
        notifyDataSetChanged()
    }

    @SuppressLint("NewApi", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.shareBtn.setOnClickListener(View.OnClickListener {
            shareProduct(listmodels.get(position).id.toString())
        })



        holder.itemView.setOnClickListener(View.OnClickListener {
            var a = 0
            val postIntent = Intent(context, PostActivity::class.java)
            postIntent.putExtra("PostId", listmodels.get(position).id.toString());
            postIntent.putExtra("statusBarcolor", a.toString())
            context.startActivity(postIntent);
        })


        val imageList = listmodels.get(position).post
        val videoURL = listmodels.get(position).postVideo

        if (listmodels.get(position).postVideo == "") {
            holder.ImagesSliderProfileHomeUser.visibility = View.VISIBLE
            holder.videoPlayer.visibility = View.GONE
            holder.video_icon.visibility = View.GONE

            if (imageList != null) {
                if (imageList.isEmpty()) {
                    holder.ImagesSliderProfileHomeUser.visibility = View.GONE
                } else {
                    if (imageList.size == 1) {
                        holder.ImagesSliderProfileHomeUser.visibility = View.GONE
                        holder.singleimageonly.visibility = View.VISIBLE
                        Glide.with(context)
                            .load(ApiConstants.IMAGE_URL + imageList[0])
                            .into(holder.singleimageonly)

                    } else {
                        holder.ImagesSliderProfileHomeUser.visibility = View.VISIBLE
                        setImageInSlider(imageList, holder.ImagesSliderProfileHomeUser)
                    }

                }
            }

        } else if (listmodels.get(position).postVideo == null) {
            holder.ImagesSliderProfileHomeUser.visibility = View.VISIBLE
            holder.videoPlayer.visibility = View.GONE
            holder.video_icon.visibility = View.GONE

            if (imageList != null) {
                if (imageList.isEmpty()) {
                    holder.ImagesSliderProfileHomeUser.visibility = View.GONE
                } else {
                    if (imageList.size == 1) {
                        holder.ImagesSliderProfileHomeUser.visibility = View.GONE
                        holder.singleimageonly.visibility = View.VISIBLE
                        Glide.with(context)
                            .load(ApiConstants.IMAGE_URL + imageList[0])
                            .into(holder.singleimageonly)

                    } else {
                        holder.ImagesSliderProfileHomeUser.visibility = View.VISIBLE
                        setImageInSlider(imageList, holder.ImagesSliderProfileHomeUser)
                    }
                }
            }
        } else {
            holder.ImagesSliderProfileHomeUser.visibility = View.GONE
            holder.videoPlayer.visibility = View.VISIBLE
            holder.video_icon.visibility = View.VISIBLE

            var link = "https://weatherdemocracy.com/storage/app/" + videoURL

            Glide.with(context)
                .load(link)
                .centerCrop()
                .placeholder(R.color.black)
                .into(holder.videoPlayer);


            /*    holder.videoPlayer.setOnClickListener(View.OnClickListener {

                    val videoIntent = Intent(context, VideoplayerActivity::class.java)
                    videoIntent.putExtra("Videolink", link);
                    context.startActivity(videoIntent);


                })*/

        }
        holder.txtComment.setText(listmodels.get(position).postCommentCount.toString() + " Comments")

        if(listmodels.get(position).likedbyme==1){

            if(listmodels.get(position).count==1){
                holder.TxtLike.setText("You liked this post | ")
            }
            else{
                holder.TxtLike.setText("You and "+(listmodels.get(position).count?.minus(1)).toString() + " other liked this post |")
            }
        }
        else{
            holder.TxtLike.setText("Liked by "+listmodels.get(position).count.toString() + " person |")
        }





        holder.imgComment.setOnClickListener {
            var positioon = listmodels.get(position).id
            bottomInterface.selectImage(positioon.toString())
        }


        holder.txtComment.setOnClickListener {
            var positioon = listmodels.get(position).id
            bottomInterface.selectImage(positioon.toString())
        }
        if (listmodels.get(position).likedbyme!!.equals(1)) {
            holder.imgdisloke.visibility = GONE
            holder.ImgLikes.visibility = VISIBLE
        } else {
            holder.imgdisloke.visibility = VISIBLE
            holder.ImgLikes.visibility = GONE

        }


        /* holder.laytoutLikes.setOnClickListener {
             if (!clicked) {
                 // holder.ImgLikes.visibility=GONE
                 val postid = listmodels.get(position).id
                 var liketype = 1
                 holder.imgdisloke.visibility = GONE
                 holder.ImgLikes.visibility = VISIBLE
                 var counts=listmodels.get(position).count
                 count= counts!! +1
                 likeInterface.selectposition(postid.toString(), liketype.toString())
                 holder.TxtLike.setText(count)
                 clicked = true
             } else {
                 holder.imgdisloke.visibility = VISIBLE
                 holder.ImgLikes.visibility = GONE
                 val postid = listmodels.get(position).id
                 var liketypes = 2
                 var counts=listmodels.get(position).count
                 count= counts!! -1
                 likeInterface.selectposition(postid.toString(), liketypes.toString())
                 // holder.imgdisloke.visibility= GONE
                 holder.TxtLike.setText(count)
                 clicked = false
             }
         }*/
        holder.imgdisloke.setOnClickListener {
            val postid = listmodels.get(position).id.toString()
            var liketype = 1
            holder.imgdisloke.visibility = View.GONE
            holder.ImgLikes.visibility = View.VISIBLE
            likeInterface.selectposition(
                postid.toString(),
                liketype.toString(), position
            )
        }
        holder.ImgLikes.setOnClickListener {
            val postid = listmodels.get(position).id
            var liketype = 2
            holder.imgdisloke.visibility = View.VISIBLE
            holder.ImgLikes.visibility = View.GONE
            likeInterface.selectposition(
                postid.toString(),
                liketype.toString(),
                position
            )
        }

        holder.txtComments.setText(listmodels.get(position).description)

////////////////////////////////////////////////////////////////////////////////
        /*
                val df = SimpleDateFormat("yyyy dd MM HH:mm:ss", Locale.ENGLISH)
                df.timeZone = TimeZone.getTimeZone("UTC")
                val date = df.parse(listmodels.get(position).createdAt.toString())
                df.timeZone = TimeZone.getDefault()

                val formattedDate = df.format(date)*/

////////////////////////////////////////////////////////////////////////////

        var date = getTime(listmodels.get(position).createdAt.toString())

        var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val newDate = spf.parse(date)
        spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
        val newDateString = spf.format(newDate)
        holder.txtdates.setText(newDateString.toString())


        holder.txtUserNames.setText(listmodels.get(position).user?.name)
        //holder.TxtLike.setText(listmodels.get(position).count!!).toString()

        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + listmodels.get(position).user?.profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.ImgProfile)

        val gDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }


            override fun onDoubleTap(e: MotionEvent): Boolean {
                Log.i("TAG", "onDoubleTap: ")
                return true
            }
        })
// Set it to the view
// Set it to the view

        holder.ImagesSliderProfileHomeUser.setOnTouchListener { v, event ->
            gDetector.onTouchEvent(
                event
            )
        }


        // this.viewHolder=holder
        //holder.Names.text=listModels.get(0).name
    }

    override fun getItemCount(): Int {
        Log.i("TAG", "getItemCount: " + listmodels.size)
        return listmodels.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ImagesSliderProfileHomeUser: SliderView
        var txtComments: TextView
        var txtdates: TextView
        var txtUserNames: TextView
        var txtComment: TextView
        var TxtLike: TextView
        var ImgProfile: ImageView
        var imgComment: ImageView
        var ImgLikes: ImageView
        var singleimageonly: ImageView
        var imgdisloke: ImageView
        var laytoutLikes: ConstraintLayout
        var clickLayout: ConstraintLayout

        var shareBtn: ImageView

        var videoPlayer: ImageView
        var video_icon: ImageView

        init {
            ImagesSliderProfileHomeUser =
                itemView.findViewById<SliderView>(R.id.ImagesSliderProfileHomeUser)
            txtComments = itemView.findViewById(R.id.txtComments)
            txtdates = itemView.findViewById(R.id.txtdates)
            txtUserNames = itemView.findViewById(R.id.txtUserNames)
            ImgProfile = itemView.findViewById(R.id.ImgProfile)
            imgComment = itemView.findViewById(R.id.imgComment)
            TxtLike = itemView.findViewById(R.id.TxtLike)
            ImgLikes = itemView.findViewById(R.id.ImgLikes)
            singleimageonly = itemView.findViewById(R.id.singleimageonly)
            imgdisloke = itemView.findViewById(R.id.imgdisloke)
            laytoutLikes = itemView.findViewById(R.id.laytoutLikes)
            txtComment = itemView.findViewById(R.id.txtComment)
            shareBtn = itemView.findViewById(R.id.share_btn)
            videoPlayer = itemView.findViewById(R.id.video_player)
            video_icon = itemView.findViewById((R.id.video_icon))
            clickLayout = itemView.findViewById(R.id.clicklayout)
        }
    }


    private fun setImageInSlider(images: List<String?>?, imageSlider: SliderView) {
        val adapter = SliderProfileHomeAdapter(context)
        adapter.renewItems(images as ArrayList<String>)
        imageSlider.setSliderAdapter(adapter)
        //  binding.txtCounter.setText("1/"+imageList.size)

        imageSlider.setCurrentPageListener {
            val position = imageSlider.currentPagePosition
            //  binding.txtCounter.setText((position+1).toString()+"/"+imageList.size)
            Log.i("TAG", "setImageInSlider: " + position)

        }
    }

    fun getDate(dateAndTime: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateAndTime)
        return SimpleDateFormat("MM-dd-yyyy").format(sdf)
    }

    fun getTime(time: String): String {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.timeZone = (TimeZone.getTimeZone("IST"))
        val date = sdf.parse(time)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
    }


    private fun shareProduct(uri: String) {
        //  ProgressBarUtils.hideProgressDialog()
        ProgressD.showLoading(context, context.getResources().getString(R.string.logging_in))

        // binding.ivLoader.visibility=View.VISIBLE
        var shareLink = "https://weatherdemocracy.com/post?id=" + uri + "&" + "userType=" + 2
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse(shareLink)) /// add website name
            .setDomainUriPrefix("https://weatherdemocracy.page.link/?") /// add subdomain name
            // Open links with this app on Android
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            .setIosParameters(DynamicLink.IosParameters.Builder("com.Weather-Democracy").build())
            .buildDynamicLink()
        val dynamicLinkUri = dynamicLink.uri
        Log.e("dynamicLink", "" + dynamicLink.uri)
        val sharelinktext = dynamicLinkUri
        "" +
                "&apn=" + context.getPackageName() +
                "&st=" + "" +
                "&sd=" +
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
                        val clipboard =
                            context.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("AngleFood", shortLink.toString())
                        clipboard.setPrimaryClip(clip)
                        val intent = Intent()
                        intent.action = Intent.ACTION_SEND
                        //intent.putExtra(Intent.EXTRA_STREAM, uri)
                        intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
                        intent.type = "text/plain"
                        CommonMethod.getInstance(context)
                            .savePreference(AppConstant.KEY_ID_SHARE, "1")
                        context.startActivity(intent)
                    } else {
                        ProgressD.hideProgressDialog()

                        // binding.ivLoader.visibility=View.GONE

                    }
                })

    }


}