package com.webmobrilweatherapp.activities.metrologistadapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.*
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
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.fragment.imageslidermetrologist.SliderHomeMetrologistAdapter
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.BottomInterfacemretologist
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.LikeInterfaceMetrologist
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.smarteist.autoimageslider.SliderView
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistPostActivity
import com.webmobrilweatherapp.model.homepage.DataItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ProfilefragmentMetrologisthomeAdapter(
    private val context: Context,
    private val listmodels: List<DataItem>,
    private val bottomInterfacemretologist: BottomInterfacemretologist,
    private val likeInterfaceMetrologist: LikeInterfaceMetrologist
) : RecyclerView.Adapter<ProfilefragmentMetrologisthomeAdapter.ViewHolder>() {
    lateinit var viewHolder: ViewHolder
    var clicked = false
    var mListener = ""
    val layoutInflater = null
    var finalCount = 0
    var like = "0"
    var liketypes = 0
    var disLike = "0"



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.profilefragmenthomemetrologistadapter, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun plust(cnt: String, pid: Int, liketype: String) {
        Log.e("tete", cnt)
        listmodels.get(pid).count = cnt.toInt()
        listmodels.get(pid).likedbyme=1
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
        listmodels.get(pid).likedbyme=2
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageList = listmodels.get(position).post
        Log.i("TAG", "onBindViewHolder: " + imageList?.size)
        holder.shareBtn.setOnClickListener(View.OnClickListener {

            shareProduct(listmodels.get(position).id.toString())
        })


        holder.itemView.setOnClickListener(View.OnClickListener {
            var a=1
            val postIntent = Intent(context, MetrologistPostActivity::class.java)
            postIntent.putExtra("PostId", listmodels.get(position).id.toString());
            postIntent.putExtra("statusBarcolor",a.toString())
            context.startActivity(postIntent);

        })


        if (listmodels.get(position).postVideo=="")
        {
            holder.ImagesSliderProfileHomeMetrologist.visibility=View.VISIBLE
            holder.videoPlayer.visibility=View.GONE
            holder.video_icon.visibility=View.GONE
            if (imageList != null) {
                if (imageList?.isEmpty()!!) {
                    holder.ImagesSliderProfileHomeMetrologist.visibility = View.GONE
                } else {
                    if(imageList.size==1){
                        holder.ImagesSliderProfileHomeMetrologist.visibility = View.GONE
                        holder.singleimageonly.visibility = View.VISIBLE
                        Glide.with(context)
                            .load(ApiConstants.IMAGE_URL +imageList[0])
                            .into(holder.singleimageonly)

                    }else{
                        holder.ImagesSliderProfileHomeMetrologist.visibility = View.VISIBLE
                        setImageInSlider(imageList, holder.ImagesSliderProfileHomeMetrologist)
                    }
                  //  holder.ImagesSliderProfileHomeMetrologist.visibility = View.VISIBLE
                   // setImageInSlider(imageList, holder.ImagesSliderProfileHomeMetrologist)
                }
            }

        }

        else if(listmodels.get(position).postVideo==null)
        {
            holder.ImagesSliderProfileHomeMetrologist.visibility=View.VISIBLE
            holder.videoPlayer.visibility=View.GONE
            holder.video_icon.visibility=View.GONE

            if (imageList != null) {
                if (imageList?.isEmpty()!!) {
                    holder.ImagesSliderProfileHomeMetrologist.visibility = View.GONE
                } else {
                    if(imageList.size==1){
                        holder.ImagesSliderProfileHomeMetrologist.visibility = View.GONE
                        holder.singleimageonly.visibility = View.VISIBLE
                        Glide.with(context)
                            .load(ApiConstants.IMAGE_URL +imageList[0])
                            .into(holder.singleimageonly)

                    }else{
                        holder.ImagesSliderProfileHomeMetrologist.visibility = View.VISIBLE
                        setImageInSlider(imageList, holder.ImagesSliderProfileHomeMetrologist)
                    }
                   // holder.ImagesSliderProfileHomeMetrologist.visibility = View.VISIBLE
                  //  setImageInSlider(imageList, holder.ImagesSliderProfileHomeMetrologist)
                }
            }

        }
        else
        {
            holder.ImagesSliderProfileHomeMetrologist.visibility=View.GONE
            holder.videoPlayer.visibility=View.VISIBLE
            holder.video_icon.visibility=View.VISIBLE


            var link="https://weatherdemocracy.com/storage/app/"+listmodels.get(position).postVideo

            Glide.with(context)
                .load(link)
                .centerCrop()
                .placeholder(R.color.black)
                .into(holder.videoPlayer);
        }

        holder.txtComment.setText(listmodels.get(position).postCommentCount.toString() + " Comments")
        holder.txtComments.setText(listmodels.get(position).description)

        var date = getTime(listmodels.get(position).createdAt.toString())

        var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val newDate = spf.parse(date)
        spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
        val newDateString = spf.format(newDate)
        holder.txtdates.setText(newDateString.toString())
        var countlike = listmodels.get(position).count.toString()
        holder.TxtLike.setText(countlike + "Likes ")
        holder.Usernames.setText(listmodels.get(position).user?.name)
        Glide.with(context)
            .load(ApiConstants.IMAGE_URL + listmodels.get(position).user?.profileImage)
            .placeholder(R.drawable.edit_profileicon)
            .into(holder.ImgProfile)
        holder.imgComment.setOnClickListener {
            var postID = listmodels.get(position).id
            bottomInterfacemretologist.selectImage(postID.toString())
        }
        holder.txtComment.setOnClickListener {
            var postID = listmodels.get(position).id
            bottomInterfacemretologist.selectImage(postID.toString())
        }

        if (listmodels.get(position).likedbyme == 1) {
            holder.Imgdislike.visibility = View.GONE
            holder.ImgLikes.visibility = View.VISIBLE
        } else {
            holder.Imgdislike.visibility = View.VISIBLE
            holder.ImgLikes.visibility = View.GONE
        }
        /* holder.layoutLikess.setOnClickListener {
         if (!clicked) {
             // holder.ImgLikes.visibility=GONE
             val postid = listmodels.get(position).id
             liketypes = 1
             holder.Imgdislike.setClickable(true)
             holder.ImgLikes.setClickable(false)
             holder.Imgdislike.visibility = View.GONE
             holder.ImgLikes.visibility = View.VISIBLE
             likeInterfaceMetrologist.selectposition(postid.toString(), liketypes.toString(),position)
             clicked = true
         } else {
             val postid = listmodels.get(position).id
             liketypes = 2
             holder.Imgdislike.setClickable(false)
             holder.ImgLikes.setClickable(true)
             holder.Imgdislike.visibility = View.VISIBLE
             holder.ImgLikes.visibility = View.GONE
             likeInterfaceMetrologist.selectposition(postid.toString(), liketypes.toString(),position)
             // holder.imgdisloke.visibility= GONE
             clicked = false
         }
     }*/


        holder.Imgdislike.setOnClickListener {
            val postid = listmodels.get(position).id
            var liketype = 1
            holder.Imgdislike.visibility = View.GONE
            holder.ImgLikes.visibility = View.VISIBLE
            likeInterfaceMetrologist.selectposition(
                postid.toString(),
                liketype.toString(),position
            )

        }
        holder.ImgLikes.setOnClickListener {
            val postid = listmodels.get(position).id
            var liketype = 2
            holder.Imgdislike.visibility = View.VISIBLE
            holder.ImgLikes.visibility = View.GONE
            likeInterfaceMetrologist.selectposition(
                postid.toString(),
                liketype.toString(),
                position
            )
        }

        /*holder.layoutLikess.setOnClickListener {
            if (!clicked) {
                // holder.ImgLikes.visibility=GONE
                val postid = listmodels.get(position).id
                var liketype = 1
                holder.Imgdislike.visibility = View.GONE
                holder.ImgLikes.visibility = View.VISIBLE
                likeInterfaceMetrologist.selectposition(postid.toString(), liketype.toString(),position)
                clicked = true
            } else {
                holder.Imgdislike.visibility = View.VISIBLE
                holder.ImgLikes.visibility = View.GONE
                val postid = listmodels.get(position).id
                var liketypes = 2
                likeInterfaceMetrologist.selectposition(postid.toString(), liketypes.toString(),position)
                // holder.imgdisloke.visibility= GONE
                clicked = false

            }

        }*/
    }
    fun getTime(time:String): String {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.timeZone = (TimeZone.getTimeZone("IST"))
        val date = sdf.parse(time)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
    }
    override fun getItemCount(): Int {
        return listmodels.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ImagesSliderProfileHomeMetrologist: SliderView
        var txtComments: TextView
        var txtdates: TextView
        var Usernames: TextView
        var txtComment: TextView
        var TxtLike: TextView
        var ImgProfile: ImageView
        var ImgLikes: ImageView
        var singleimageonly: ImageView
        var Imgdislike: ImageView
        var imgComment: ImageView
        var layoutLikess: ConstraintLayout
        var LayoutImagesSlider: ConstraintLayout
        var videoPlayer:ImageView
        var video_icon:ImageView
        var shareBtn:ImageView
        init {
            ImagesSliderProfileHomeMetrologist =
                itemView.findViewById<SliderView>(R.id.ImagesSliderProfileHomeMetrologist)
            txtComments = itemView.findViewById(R.id.txtComments)
            txtdates = itemView.findViewById(R.id.txtdates)
            Usernames = itemView.findViewById(R.id.Usernames)
            ImgProfile = itemView.findViewById(R.id.ImgProfile)
            TxtLike = itemView.findViewById(R.id.TxtLike)
            ImgLikes = itemView.findViewById(R.id.ImgLikes)
            singleimageonly = itemView.findViewById(R.id.singleimageonly)

            Imgdislike = itemView.findViewById(R.id.Imgdislike)
            layoutLikess = itemView.findViewById(R.id.layoutLikess)
            imgComment = itemView.findViewById(R.id.imgComment)
            LayoutImagesSlider = itemView.findViewById(R.id.LayoutImagesSlider)
            txtComment = itemView.findViewById(R.id.txtComment)
            videoPlayer=itemView.findViewById(R.id.video_player)
            video_icon=itemView.findViewById((R.id.video_icon))
            shareBtn=itemView.findViewById(R.id.share_btn)
        }
    }

    private fun setImageInSlider(images: List<String?>?, imageSlider: SliderView) {
        val adapter = SliderHomeMetrologistAdapter(context)
        adapter.renewItems(images as ArrayList<String>)
        imageSlider.setSliderAdapter(adapter)
        //  binding.txtCounter.setText("1/"+imageList.size)
        imageSlider.setCurrentPageListener {
            val position = imageSlider.currentPagePosition
            //  binding.txtCounter.setText((position+1).toString()+"/"+imageList.size)
            Log.i("TAG", "setImageInSlider: " + position)

        }
    }
    private fun shareProduct(uri:String) {
        //  ProgressBarUtils.hideProgressDialog()
        ProgressD.showLoading(context,context.getResources().getString(R.string.logging_in))

        // binding.ivLoader.visibility=View.VISIBLE
        var shareLink = "https://weatherdemocracy.com/post?id="+uri+"&"+"userType="+3
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