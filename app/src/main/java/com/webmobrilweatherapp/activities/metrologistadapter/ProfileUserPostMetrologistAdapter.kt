package com.webmobrilweatherapp.activities.metrologistadapter


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.webmobrilweatherapp.DeleteBottomInterface
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.fragment.imageslidermetrologist.SliderMetrologistAdapter
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.BottomInterfacemretologist
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.LikeInterfaceMetrologist
import com.webmobrilweatherapp.model.userpost.User
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.smarteist.autoimageslider.SliderView
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistPostActivity
import com.webmobrilweatherapp.model.userpost.DataItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class   ProfileUserPostMetrologistAdapter(
    private val context: Context,
    private var listmodel: List<DataItem>,
    private var listmodels: User?,
    private val deleteBottomInterface: DeleteBottomInterface,
    private val bottomInterfacemretologist: BottomInterfacemretologist,
    private val likeInterfaceMetrologist: LikeInterfaceMetrologist,

) : RecyclerView.Adapter<ProfileUserPostMetrologistAdapter.ViewHolder>() {
    var clicked = false
    lateinit var viewHolder: ViewHolder

    // private var datatm: List<TodayItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.profilefragmentmetrologist, parent, false)

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
        holder.share_btn.setOnClickListener(View.OnClickListener {

            shareProduct(listmodel.get(position).id.toString())
        })


        val imageList = listmodel.get(position).post
        val videoURL=listmodel.get(position).postVideo
        holder.itemView.setOnClickListener(View.OnClickListener {

            var a=1;
            val postIntent = Intent(context, MetrologistPostActivity::class.java)
            postIntent.putExtra("PostId", listmodel.get(position).id.toString());
            postIntent.putExtra("statusBarcolor",a.toString())
            context.startActivity(postIntent);

        })
        if (listmodel.get(position).postVideo=="")
        {

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
                        val constraintSet = ConstraintSet()
                        constraintSet.clone(holder.LayoutLikeMetrologist)
                        constraintSet.connect(R.id.ImagesSliderProfilePostMetrologist,ConstraintSet.TOP,R.id.ImagesSliderProfilePostMetrologist,ConstraintSet.TOP,0)
                        holder.ImagesSliderProfilePostMetrologist.visibility = View.VISIBLE

                        setImageInSlider(imageList, holder.ImagesSliderProfilePostMetrologist)
                    }



                }
            }

        }

        else if(listmodel.get(position).postVideo==null)
        {

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
                        val constraintSet = ConstraintSet()
                        constraintSet.clone(holder.LayoutLikeMetrologist)
                        constraintSet.connect(R.id.ImagesSliderProfilePostMetrologist,ConstraintSet.TOP,R.id.ImagesSliderProfilePostMetrologist,ConstraintSet.TOP,0)
                        holder.ImagesSliderProfilePostMetrologist.visibility = View.VISIBLE

                        setImageInSlider(imageList, holder.ImagesSliderProfilePostMetrologist)
                    }

                   /* val constraintSet = ConstraintSet()
                    constraintSet.clone(holder.LayoutLikeMetrologist)
                    constraintSet.connect(R.id.ImagesSliderProfilePostMetrologist,ConstraintSet.TOP,R.id.ImagesSliderProfilePostMetrologist,ConstraintSet.TOP,0)
                    holder.ImagesSliderProfilePostMetrologist.visibility = View.VISIBLE

                    setImageInSlider(imageList, holder.ImagesSliderProfilePostMetrologist)*/

                }
            }

        }
        else
        {
            holder.ImagesSliderProfilePostMetrologist.visibility = View.GONE
            holder.videoPlayer.visibility = View.VISIBLE
            holder.video_icon.visibility = View.VISIBLE

            var link = "https://weatherdemocracy.com/storage/app/" + listmodel.get(position).postVideo

            Glide.with(context)
                .asBitmap()
                .apply(RequestOptions().placeholder(R.color.black).centerCrop())
                .load(link)
                .into(holder.videoPlayer);

        }


        holder.txtComment.setText(listmodel.get(position).postCommentCount.toString()+" Comments")
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
        holder.ImgDelete.setOnClickListener {
            deleteBottomInterface.selectImagedelete(position.toString())
            notifyDataSetChanged()
        }

        if (listmodel.get(position).likedbyme ==1) {
            holder.Imgdislike.visibility = View.GONE
            holder.ImgLikes.visibility = View.VISIBLE
        } else {
            holder.Imgdislike.visibility = View.VISIBLE
            holder.ImgLikes.visibility = View.GONE
        }
        /*holder.LayoutLikeMetrologist.setOnClickListener {
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
    fun getTime(time:String): String {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.timeZone = (TimeZone.getTimeZone("IST"))
        val date = sdf.parse(time)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
    }
    override fun getItemCount(): Int {
        Log.i("TAG", "getItemCount: " + listmodel.size)
        return listmodel.size
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
        var ImgDelete: ImageView
        var LayoutLikeMetrologist: ConstraintLayout
        var videoPlayer:ImageView
        var video_icon:ImageView
        var share_btn:ImageView
        init {
            ImagesSliderProfilePostMetrologist =
                itemView.findViewById(R.id.ImagesSliderProfilePostMetrologist)
            txtComments = itemView.findViewById(R.id.txtComments)
            txtdates = itemView.findViewById(R.id.txtdates)
            txtUserNames = itemView.findViewById(R.id.txtUserNames)
            ImgProfile = itemView.findViewById(R.id.ImgProfile)
            TxtLike = itemView.findViewById(R.id.TxtLike)
            LayoutLikeMetrologist = itemView.findViewById(R.id.LayoutLikeMetrologist)
            ImgLikes = itemView.findViewById(R.id.ImgLikes)
            singleimageonly = itemView.findViewById(R.id.singleimageonly)

            Imgdislike = itemView.findViewById(R.id.Imgdislike)
            imgComment = itemView.findViewById(R.id.imgComment)
            ImgDelete = itemView.findViewById(R.id.ImgDelete)
            txtComment = itemView.findViewById(R.id.txtComment)
            videoPlayer=itemView.findViewById(R.id.video_player)
            video_icon=itemView.findViewById(R.id.video_icon)
            share_btn=itemView.findViewById(R.id.share_btn)
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