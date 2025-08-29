package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.mediaadapter.CounterInterface
import com.webmobrilweatherapp.activities.metrologistadapter.SliderImageMetrologistAdapter
import com.webmobrilweatherapp.databinding.ActivityCreatepostMetrologistBinding
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.smarteist.autoimageslider.SliderView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CreatepostMetrologistActivity : AppCompatActivity(), CounterInterface {

    lateinit var binding: ActivityCreatepostMetrologistBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    var accountViewModel: AccountViewModel? = null

    private var useridMetrologist = "0"
    private var imageList = ArrayList<String>()
    var imageLists: ArrayList<MultipartBody.Part> = ArrayList()

    var VideoUrl=""
    var keyy=0
    var checkbol=false;
    private var userid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        setContentView(R.layout.activity_metrologist_photo_view)
        Log.e("screen time", "coming 1 time")

        VideoUrl= getIntent().getExtras()?.getString("VideoPath").toString()

        keyy= getIntent().getExtras()?.getInt("key")!!

        binding = DataBindingUtil.setContentView(this, R.layout.activity_createpost_metrologist)


        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)


        if(keyy==1)
        {
            binding.ImagesSlider.visibility= View.GONE
            binding.videoView.visibility= View.VISIBLE

            videoUpload();

        }
        else
        {


            binding.ImagesSlider.visibility= View.VISIBLE
            binding.videoView.visibility= View.GONE
            photoUpload();

        }


        binding.vectorback.setOnClickListener {
            if (!imageList.isNullOrEmpty()) {
                imageList = ArrayList()
            }
            onBackPressed()

        }


    }

    private fun photoUpload() {


        binding.post.setOnClickListener {


            if (isValidation()) {
                if(checkbol){

                }else{
                    checkbol=true;
                    getCreatePost()
                }


            }

        }

        imageList = intent.extras?.getSerializable("DATA") as ArrayList<String>

        getuserprofileMetrologist()

        val view = binding.root

        setContentView(view)
       // setImageInSlider(imageList, binding.ImagesSlider)

        ///////////////////////////////////////////////////////////////


        binding.imageSet.setImageURI(Uri.parse(imageList.get(0).toString()))

        if(imageList.size==1)
        {
            binding.imageSet.visibility=View.VISIBLE
            binding.ImagesSlider.visibility=View.GONE
            binding.imageSet.setImageURI(Uri.parse(imageList.get(0).toString()))

        } else{

            binding.ImagesSlider.visibility=View.VISIBLE
            binding.imageSet.visibility=View.GONE
            setImageInSlider(imageList, binding.ImagesSlider)

        }
        /////////////////////////////////////////////////////////////
    }

    private fun videoUpload() {

        binding.post.setOnClickListener {
            if (isValidation()) {
                getCreateVideoPost()

            }

        }
        getuserprofileMetrologist()

        val uri = Uri.parse(VideoUrl)
        binding.videoView.setVideoURI(uri)
        val mediaController = MediaController(this)


        // sets the anchor view
        // anchor view for the videoView

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(binding.videoView)

        // sets the media player to the videoView

        // sets the media player to the videoView
        mediaController.setMediaPlayer(binding.videoView)

        // sets the media controller to the videoView
        // sets the media controller to the videoView
        binding.videoView.setMediaController(mediaController)
        // starts the video

        // starts the video
        binding.videoView.start()

    }





    private fun isValidation(): Boolean {
        if (binding.editwriteheresomthing.text.isNullOrEmpty()) {
            binding.editwriteheresomthing.requestFocus()
            showToastMessage("Please Enter Post Content")
            return false
        }
        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()

    }

    private fun getCreatePost() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        imageLists.clear()
        for (image in imageList) {
            val file = File(image)
            val ImageBody = RequestBody.create(("image/*").toMediaTypeOrNull(), file)
            val part = MultipartBody.Part.createFormData(
                "post_image[]",
                file.getName(),
                ImageBody
            )
            imageLists.add(part)
        }

        val decription: RequestBody =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.editwriteheresomthing.text.toString()
            )
        accountViewModelMetrologist?.getCreatePostMetrologist(
            imageLists, decription, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                checkbol=false;
                if (it != null && it.success == true) {
                    checkbol=false;
                    val i = Intent(this, MetrilogistHomeActivity::class.java)
                    i.putExtra("details", "1")
                    startActivity(i)
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                } else {
                    checkbol=false;
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = SliderImageMetrologistAdapter(this, this)
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        binding.txtCounter.setText("1/" + imageList.size)

        imageSlider.setCurrentPageListener {
            val position = imageSlider.currentPagePosition
            binding.txtCounter.setText((position + 1).toString() + "/" + imageList.size)
            Log.i("TAG", "setImageInSlider: " + position)

        }
    }


    override fun onSliderChangeListner(position: Int) {
        Log.i("TAG", "onSliderChangeListner: " + position)

    }


    private fun getuserprofileMetrologist() {
        //  ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        useridMetrologist =CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getuserprofileMetrologist(
            useridMetrologist.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this, {
                //ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    binding.textdavidpost.setText(it.data?.name)
                    var names = it.data?.name
                    binding.davidcreatepostshaw.setText("@" + names)
                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL + it.data?.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.imgmaskpivcreatepost)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            })
    }
    private fun getCreateVideoPost() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        imageLists.clear()

        //var uri=Uri.parse(image)
        // val imgPath = FileUtils.getPath(this@CreatePostActivity,uri)
        val file = File(VideoUrl)
        val ImageBody = RequestBody.create(("image/*").toMediaTypeOrNull(), file)
        val part = MultipartBody.Part.createFormData("post_video", file.getName(), ImageBody)
        val decription: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            binding.editwriteheresomthing.text.toString()
        )


        accountViewModel?.getCreateVideoPost(
            part, decription, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()

                if (it != null && it.success == true) {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    val i = Intent(this, MetrilogistHomeActivity::class.java)
                    i.putExtra("details", "2")
                    startActivity(i)

                }


                else {

                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

                }
            }
    }
//**************************************************************************************************************//

}