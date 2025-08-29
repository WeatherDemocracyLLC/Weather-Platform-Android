package com.webmobrilweatherapp.activities.metrologistactivity

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.smarteist.autoimageslider.SliderView
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.adapters.UsercommentAdapter
import com.webmobrilweatherapp.databinding.ActivityMetrologistPostBinding
import com.webmobrilweatherapp.fragment.profile.slideradapter.SliderProfileHomeAdapter
import com.webmobrilweatherapp.model.getcomment.CommentItem
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import java.text.SimpleDateFormat
import java.util.*


class MetrologistPostActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistPostBinding
    var accountViewModel: AccountViewModel? = null

    var postId=""

    lateinit var player: ExoPlayer
    lateinit var txtLikecomment: TextView
    lateinit var txtLike: TextView
    lateinit var txtMessage: EditText
    lateinit var sendButton: FloatingActionButton
    lateinit var recylerviewCommenst: RecyclerView
    private lateinit var dialogs: BottomSheetDialog
    lateinit var usercommentAdapter: UsercommentAdapter
    private var postions:Int = 0
    private  var backPressed="0"
    var statusBarColor="0"

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metrologist_post)
        statusBarColor= getIntent().getExtras()?.getString("statusBarcolor").toString()

        if (statusBarColor.equals("1"))
        {

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        }
        else
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.toolbaar))
        }

        postId= getIntent().getExtras()?.getString("PostId").toString()
        backPressed= intent.getStringExtra("backPressed").toString()

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_metrologist_post)
        player = ExoPlayer.Builder(this).build()

        getPost()

        binding.ImgLikes.setOnClickListener(View.OnClickListener {

            getrlike("2")
            binding.imgdisloke.visibility = View.VISIBLE
            binding.ImgLikes.visibility = View.GONE

        })


        binding.imgdisloke.setOnClickListener(View.OnClickListener {


            getrlike("1")
            binding.imgdisloke.visibility = View.GONE
            binding.ImgLikes.visibility = View.VISIBLE


        })


        binding.txtComment.setOnClickListener(View.OnClickListener {

            getpostUserComment(postId.toString())

        })


        binding.imgComment.setOnClickListener(View.OnClickListener {

            getpostUserComment(postId.toString())

        })

        binding.shareBtn.setOnClickListener(View.OnClickListener {

            shareProduct(postId)

        })

    }


    @RequiresApi(Build.VERSION_CODES.S)
    private fun getPost() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

        accountViewModel?.getPostById(postId,"Bearer " + CommonMethod.getInstance(this).getPreference(
            AppConstant.KEY_token_Metrologist))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success== true) {

                    binding.mainConsprev.visibility= View.VISIBLE
                    binding.txtUserNames.text=it.post!!.user!!.name
                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL + it.post!!.user!!.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.ImgProfile)

//video and Images setUp

                    if (it.post.postVideo=="")

                    {
                        if(it.post.post?.size ==1){
                            binding.ImagesSliderProfileHomeUser.visibility = View.GONE
                            binding.singleimageonly.visibility = View.VISIBLE
                            Glide.with(this)
                                .load(ApiConstants.IMAGE_URL +it.post.post[0])
                                .into(binding.singleimageonly)
                        }else{
                            binding.ImagesSliderProfileHomeUser.visibility= View.VISIBLE
                            binding.videoPlayer.visibility= View.GONE
                            setImageInSlider(it.post.post, binding.ImagesSliderProfileHomeUser)
                        }

                    }
                    else if(it.post.postVideo==null)
                    {if(it.post.post?.size ==1){
                        binding.ImagesSliderProfileHomeUser.visibility = View.GONE
                        binding.singleimageonly.visibility = View.VISIBLE
                        Glide.with(this)
                            .load(ApiConstants.IMAGE_URL +it.post.post[0])
                            .into(binding.singleimageonly)
                    }else{
                        binding.ImagesSliderProfileHomeUser.visibility= View.VISIBLE
                        binding.videoPlayer.visibility= View.GONE
                        setImageInSlider(it.post.post, binding.ImagesSliderProfileHomeUser)
                    }

                       /* binding.ImagesSliderProfileHomeUser.visibility= View.VISIBLE
                        binding.videoPlayer.visibility= View.GONE
                        setImageInSlider(it.post.post, binding.ImagesSliderProfileHomeUser)*/
                    }
                    else
                    {
                        binding.ImagesSliderProfileHomeUser.visibility= View.GONE
                        binding.videoPlayer.visibility= View.VISIBLE

                        var link="https://weatherdemocracy.com/storage/app/"+it.post.postVideo

                        binding.videoPlayer.setPlayer(player);
                        var mediaItem: MediaItem = MediaItem.fromUri(link)
                        player.setMediaItem(mediaItem)
                        player.prepare()
                        player.playWhenReady
                    }

//
                    binding.txtComments.setText(it.post!!.description.toString())
                    binding.txtComment.setText(it.post.postCommentCount.toString()+" Comments")

                    binding.TxtLike.setText( it.post.count.toString() + " Likes |")
                  //  binding.txtdates.setText(getDate(it.post!!.createdAt.toString())+" "+getTime(it.post!!.createdAt.toString()))

                    var date = getTime(it.post!!.createdAt.toString())

                    var spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val newDate = spf.parse(date)
                    spf = SimpleDateFormat("MMM dd, yyyy hh:mm a")
                    val newDateString = spf.format(newDate)
                    binding.txtdates.setText(newDateString.toString())

                    if (it.post!!.likedbyme!!.equals(1)){
                        binding.imgdisloke.visibility = View.GONE
                        binding.ImgLikes.visibility = View.VISIBLE
                    } else {

                        binding.imgdisloke.visibility = View.VISIBLE
                        binding.ImgLikes.visibility = View.GONE
                    }

                    getpostUserCommentmetroonpage(postId.toString())

                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
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
    private fun setImageInSlider(images: List<String?>?, imageSlider: SliderView) {
        val adapter = SliderProfileHomeAdapter(this)
        adapter.renewItems(images as ArrayList<String>)
        imageSlider.setSliderAdapter(adapter)
        //  binding.txtCounter.setText("1/"+imageList.size)

        imageSlider.setCurrentPageListener {
            val position = imageSlider.currentPagePosition
            //  binding.txtCounter.setText((position+1).toString()+"/"+imageList.size)
            Log.i("TAG", "setImageInSlider: " + position)

        }
        
        
    }


    //********************************************Like Api********************************************//
    
    private fun getrlike(liketypes:String) {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        accountViewModel?.getrlike(
            postId, liketypes, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                if (!this.isFinishing) {
                    //  ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {

                        //   getPost()
                        // ListItem.get(UserPostId).count
                        //  Toast.makeText(this, it.post_count.toString(), Toast.LENGTH_LONG).show()
                        binding.TxtLike.setText( it.post_count.toString() + " Likes |")

                    } else {

                        // Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()

                    }
                }

            }
    }
    //*************************************************************************************************//

    //*****************************************************Comment Api***********************************************

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getpostUserComment(UserPostId: String) {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getpostUserComment(
            UserPostId,
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    showBottomSheetDialog()
                    usercommentAdapter = UsercommentAdapter(
                        this,it.comment as List<CommentItem>)
                    val layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    recylerviewCommenst.layoutManager = layoutManager
                    recylerviewCommenst.adapter = usercommentAdapter
                    var likecount=it.likeCouunt.toString()
                    var commentcount=it.commentCount.toString()
                    txtLike.setText(likecount  +" Likes |")
                    txtLikecomment.setText(commentcount  +" Comments")

                    //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getpostUserCommentmetroonpage(UserPostId: String) {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getpostUserComment(
            UserPostId,
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                  //  showBottomSheetDialog()
                    usercommentAdapter = UsercommentAdapter(
                        this,it.comment as List<CommentItem>)
                    val layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.recylerviewCommenstmetroonpage.layoutManager = layoutManager
                    binding.recylerviewCommenstmetroonpage.adapter = usercommentAdapter
                    var likecount=it.likeCouunt.toString()
                    var commentcount=it.commentCount.toString()
                 //   txtLike.setText(likecount  +" Likes |")
                  //  txtLikecomment.setText(commentcount  +" Comments")

                    //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showBottomSheetDialog() {
        dialogs = BottomSheetDialog(this,R.style.DialogStyle)
        dialogs.setContentView(R.layout.commentdialog)
        // dialogs.window!!.setBackgroundDrawableResource(R.drawable.dialog_curved_bg_inset)
        txtLikecomment = dialogs.findViewById(R.id.txtLikecomment)!!
        txtLike = dialogs.findViewById(R.id.txtLike)!!
        recylerviewCommenst = dialogs.findViewById(R.id.recylerviewCommenst)!!
        txtMessage = dialogs.findViewById(R.id.txtMessage)!!
        sendButton = dialogs.findViewById(R.id.sendButton)!!
        txtMessage.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }
        /*val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recylerviewCommenst.layoutManager = layoutManager
        recylerviewCommenst.adapter = UsercommentAdapter(requireContext())*/
        sendButton.setOnClickListener {
            if (isValidation()) {
                postcomments()
                dialogs.hide()

            }

        }
        // initRecyclerview()
        dialogs.show()

    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun postcomments() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.postcomments(
            postId,
            txtMessage.text.toString(),
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                if (!this.isFinishing) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        //profilefragmenthomeAdapter.plustcomment(it.postCount.toString(),postions)
                        // getpostUserComment(postId)
                        getPost()
                        txtMessage.setText("")
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
    }

    private fun isValidation(): Boolean {
        if (txtMessage.text.isNullOrEmpty()) {
            txtMessage.requestFocus()
            Toast.makeText(this,"Please Enter Comment", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    //***************************************************************************************************************//
//*************************************************Share Post(Deep Linking)*****************************************//

    private fun shareProduct(uri:String) {
        //  ProgressBarUtils.hideProgressDialog()
        ProgressD.showLoading(this,getResources().getString(R.string.logging_in))


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
                "&apn=" + this.getPackageName() +
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
                        val clipboard =this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("AngleFood", shortLink.toString())
                        clipboard.setPrimaryClip(clip)
                        val intent = Intent()
                        intent.action = Intent.ACTION_SEND
                        //intent.putExtra(Intent.EXTRA_STREAM, uri)
                        intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
                        intent.type = "text/plain"
                        CommonMethod.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE,"1")
                        this.startActivity(intent)
                    } else {
                        ProgressD.hideProgressDialog()

                        // binding.ivLoader.visibility=View.GONE

                    }
                })

    }

    override fun onBackPressed() {
        var chngevalue=0
        if (backPressed.equals("1")){
            player.stop()
            statusBarColor=chngevalue.toString()

            startActivity(Intent(this, MetrilogistHomeActivity::class.java))
        }
        else{
            player.stop()
            statusBarColor=chngevalue.toString()

            super.onBackPressed()
        }
    }
    override fun onDestroy() {
        player.stop()
        super.onDestroy()
    }


    override fun onPause() {
        player.stop()
        super.onPause()
    }


}