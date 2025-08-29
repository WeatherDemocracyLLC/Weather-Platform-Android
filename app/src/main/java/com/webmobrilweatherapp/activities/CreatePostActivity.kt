package com.webmobrilweatherapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.mediaadapter.CounterInterface
import com.webmobrilweatherapp.activities.mediaadapter.SliderImageAdapter
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.smarteist.autoimageslider.SliderView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

import com.webmobrilweatherapp.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity(), CounterInterface {

    lateinit var binding: ActivityCreatePostBinding
    private var imageList = ArrayList<String>()
    private var userid = 0
    var imageLists: ArrayList<MultipartBody.Part> = ArrayList()
    var accountViewModel: AccountViewModel? = null
    var VideoUrl = ""
    var keyy = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VideoUrl = getIntent().getExtras()?.getString("VideoPath").toString()
        keyy = getIntent().getExtras()?.getInt("key")!!
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post)

        if (keyy == 1) {
            binding.ImagesSlider.visibility = View.GONE
            binding.videoView.visibility = View.VISIBLE
            videoUpload();
        } else {
            binding.ImagesSlider.visibility = View.VISIBLE
            binding.videoView.visibility = View.GONE
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
                getCreatePost()
            }
        }
        imageList = intent.extras?.getSerializable("DATA") as ArrayList<String>
        // update the preview image in the layout
        getuserprofile()
        val view = binding.root
        setContentView(view)
        binding.imageSet.setImageURI(Uri.parse(imageList.get(0).toString()))

        if (imageList.size == 1) {
            binding.imageSet.visibility = View.VISIBLE
            binding.ImagesSlider.visibility = View.GONE
            binding.imageSet.setImageURI(Uri.parse(imageList.get(0).toString()))

        } else {

            binding.ImagesSlider.visibility = View.VISIBLE
            binding.imageSet.visibility = View.GONE
            setImageInSlider(imageList, binding.ImagesSlider)

        }
        // setImageInSlider(imageList, binding.ImagesSlider)
    }

    private fun videoUpload() {

        binding.post.setOnClickListener {

            if (isValidation()) {
                getCreateVideoPost()

            }

        }

        getuserprofile()

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
            //var uri=Uri.parse(image)
            // val imgPath = FileUtils.getPath(this@CreatePostActivity,uri)
            val file = File(image)
            val ImageBody = RequestBody.create(("image/*").toMediaTypeOrNull(), file)
            val part = MultipartBody.Part.createFormData("post_image[]", file.getName(), ImageBody)

            imageLists.add(part)
        }
        val decription: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            binding.editwriteheresomthing.text.toString()
        )
        accountViewModel?.getCreatePost(
            imageLists, decription, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    val i = Intent(this, HomeActivity::class.java)
                    i.putExtra("details", "2")
                    startActivity(i)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {

        val adapter = SliderImageAdapter(this, this)
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

    private fun getuserprofile() {
//ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getuserprofile(
            userid.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                //ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    binding.textdavidpost.setText(it.data?.name)
                    var nmae = it.data?.name
                    binding.davidcreatepostshaw.setText("@" + nmae)
                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL + it.data?.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.imgmaskpivcreatepost)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    if (it!!.message.toString() == "Unauthenticated.") {
                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }
                }
            }

    }

    //**************************************************************************************************************//
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
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    val i = Intent(this, HomeActivity::class.java)
                    i.putExtra("details", "2")
                    startActivity(i)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    if (it!!.message.toString() == "Unauthenticated.") {
                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }
                }
            }
    }
}