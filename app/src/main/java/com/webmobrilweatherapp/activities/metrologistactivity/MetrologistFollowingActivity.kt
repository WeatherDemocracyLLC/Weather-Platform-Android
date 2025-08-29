package com.webmobrilweatherapp.activities.metrologistactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.adapters.MetrologistFollowingAdapter
import com.webmobrilweatherapp.databinding.ActivityMetrologistFollowingBinding
import com.webmobrilweatherapp.model.Following.FollowingListItem

class MetrologistFollowingActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistFollowingBinding
    var accountViewModel: AccountViewModel? = null
   var metrologist=2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metrologist_following)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_following)
        getFollowings()
    }

    private fun getFollowings() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

        accountViewModel?.getFollowings("Bearer " + CommonMethod.getInstance(this).getPreference(
            AppConstant.KEY_token_Metrologist))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success== true) {
                    //Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

                    if(it.followingList!!.size>0)
                    {
                        binding.nofollowing.visibility= View.GONE

                        val layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        binding.recyleMetrofollowing.layoutManager = layoutManager
                        binding.recyleMetrofollowing.adapter = MetrologistFollowingAdapter(this,it.followingList as List<FollowingListItem>)
                    }
                    else
                    {
                        binding.nofollowing.visibility= View.VISIBLE
                    }
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }


    }
}