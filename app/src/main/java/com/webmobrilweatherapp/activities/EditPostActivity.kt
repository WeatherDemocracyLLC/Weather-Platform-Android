package com.webmobrilweatherapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.smarteist.autoimageslider.SliderView
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityEditPostBinding
import com.webmobrilweatherapp.fragment.profile.slideradapter.SliderProfileHomeAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class EditPostActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditPostBinding
    var postimage: String = ""
    var description: String = ""
    var postid: String = ""
    var postimages: String = ""
    private var profile: File? = null
    private var imageList = ArrayList<String>()
    var imageLists: ArrayList<MultipartBody.Part> = ArrayList()
    var accountViewModel: AccountViewModel? = null
    private var uploadedImageFile: File? = null
    lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_post)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        postimage = intent.getStringExtra("postimage").toString()
        postid = intent.getStringExtra("postid").toString()
        var strimg = postimage.substring(1, postimage.length - 1)
        postimages = strimg
        uploadedImageFile = File(postimages)
        Log.d("TAG", "postIMg" + postimage)
        description = intent.getStringExtra("description").toString()
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(this, R.color.toolbaar))
        circularProgressDrawable.start()

        binding.txtDescription.setText(description)
        binding.imgCrossIcon.setOnClickListener {
            onBackPressed()
        }
        //*****************************************************************************************************//
        player = ExoPlayer.Builder(this).build()


            ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

            accountViewModel?.getPostById(postid,"Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token))
                ?.observe(this) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success== true) {




//video and Images setUp

                        if (it.post!!.postVideo=="")
                        {
                            binding.ImagesSliderProfileHomeUser.visibility= View.VISIBLE
                            binding.videoPlayer.visibility= View.GONE
                            setImageInSlider(it.post.post, binding.ImagesSliderProfileHomeUser)
                        }
                        else if(it.post.postVideo==null)
                        {

                            binding.ImagesSliderProfileHomeUser.visibility= View.VISIBLE
                            binding.videoPlayer.visibility= View.GONE
                            setImageInSlider(it.post.post, binding.ImagesSliderProfileHomeUser)
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

                    } else {
                        Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    }
                }


        //****************************************************************************************************//

        binding.ImgUpdatePost.setOnClickListener {
            if (isValidation()) {
                geteditpost()

            }

        }

    }
    private fun setImageInSlider(images: List<String?>?, imageSlider: SliderView) {
        val adapter = SliderProfileHomeAdapter(this)
        adapter.renewItems(images as java.util.ArrayList<String>)
        imageSlider.setSliderAdapter(adapter)
        //  binding.txtCounter.setText("1/"+imageList.size)

        imageSlider.setCurrentPageListener {
            val position = imageSlider.currentPagePosition
            //  binding.txtCounter.setText((position+1).toString()+"/"+imageList.size)
            Log.i("TAG", "setImageInSlider: " + position)

        }
    }
    private fun isValidation(): Boolean {
        if (binding.txtDescription.text.isNullOrEmpty()) {
            binding.txtDescription.requestFocus()
            showToastMessage("Enter Description")
            return false
        }
        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    private fun geteditpost() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val txtDescription: String = binding.txtDescription.text.toString()
        accountViewModel?.geteditpost(
            postid, txtDescription, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this, Observer {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    val i = Intent(this, HomeActivity::class.java)
                    i.putExtra("details", "2")
                    startActivity(i)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
}

fun <T> T?.getRequestBody(): RequestBody {
    val result: T? = this
    if (result is File) {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), (result as File))
    } else if (result is String) {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), (result as String))
    }
    throw Exception("Invalid Format !! Pass String and File Only")


}