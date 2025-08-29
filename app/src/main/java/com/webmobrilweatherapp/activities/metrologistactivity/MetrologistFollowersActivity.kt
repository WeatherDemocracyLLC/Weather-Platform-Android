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
import com.webmobrilweatherapp.adapters.MetrologistFollowersAdapter
import com.webmobrilweatherapp.databinding.ActivityMetrologistFollowersBinding
import com.webmobrilweatherapp.model.Followers.FollowerListItem


class MetrologistFollowersActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistFollowersBinding
    var accountViewModel: AccountViewModel? = null
    var metrologist=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metrologist_followers)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_followers)
        getFollowers()
    }

    private fun getFollowers() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

        accountViewModel?.getFollowers("Bearer " + CommonMethod.getInstance(this).getPreference(
            AppConstant.KEY_token_Metrologist))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success== true) {
                    //Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

                    if(it.followerList!!.size>0)
                    {
                        binding.nofollowers.visibility= View.GONE
                        val layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        binding.recylemetrofollowers.layoutManager = layoutManager
                        binding.recylemetrofollowers.adapter =MetrologistFollowersAdapter(this,it.followerList as List<FollowerListItem>)

                    }
                    else
                    {
                        binding.nofollowers.visibility= View.VISIBLE
                    }
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}