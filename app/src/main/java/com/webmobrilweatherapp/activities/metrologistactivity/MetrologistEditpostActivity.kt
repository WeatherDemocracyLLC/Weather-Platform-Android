package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.smarteist.autoimageslider.SliderView
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.databinding.ActivityMetrologistEditpostBinding
import com.webmobrilweatherapp.fragment.profile.slideradapter.SliderProfileHomeAdapter
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel

class MetrologistEditpostActivity : AppCompatActivity() {
    lateinit var binding:ActivityMetrologistEditpostBinding
    var postimage: String = ""
    var description: String = ""
    var postimages:String=""
    var PostIds:String=""
    lateinit var player: ExoPlayer
    var accountViewModel: AccountViewModel? = null

    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        binding=DataBindingUtil.setContentView(this,R.layout.activity_metrologist_editpost)
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        postimage = intent.getStringExtra("postimage").toString()
        PostIds = intent.getStringExtra("postid").toString()
        description = intent.getStringExtra("description").toString()
        //*****************************************************************************************************//
        player = ExoPlayer.Builder(this).build()


        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

        accountViewModel?.getPostById(PostIds,"Bearer " + CommonMethod.getInstance(this).getPreference(
            AppConstant.KEY_token_Metrologist))
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


        binding.txtDescription.setText(description)
        binding.imgCrossIcon.setOnClickListener {
            onBackPressed()
        }
        binding.ImgUpdatepost.setOnClickListener {
            if (isValidation()) {
                geteditpostMetrologist()

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
    private fun geteditpostMetrologist() {
        ProgressD.showLoading(this,getResources().getString(R.string.logging_in))
        val txtDescription: String = binding.txtDescription.text.toString()
        accountViewModelMetrologist?.geteditpostMetrologist(PostIds,txtDescription,
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this, {
                ProgressD.hideProgressDialog()
                if (!this.isFinishing) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        val i = Intent(this, MetrilogistHomeActivity::class.java)
                        i.putExtra("details", "1")
                        startActivity(i)

                    } else {
                        Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}