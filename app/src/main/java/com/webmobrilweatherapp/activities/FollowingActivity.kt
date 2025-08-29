package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.FollowingsAdapter
import com.webmobrilweatherapp.databinding.ActivityFollowingBinding
import com.webmobrilweatherapp.model.Following.FollowingListItem

class FollowingActivity : AppCompatActivity() {

    lateinit var binding: ActivityFollowingBinding
    var accountViewModel: AccountViewModel? = null
    var user =1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_following)


        binding.imgbackicon.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        getFollowings()
    }

    private fun getFollowings() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

        accountViewModel?.getFollowings("Bearer " + CommonMethod.getInstance(this).getPreference(
            AppConstant.KEY_token))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success== true) {
                    //Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

                    if(it.followingList!!.size>0)
                    {
                        binding.nofollowing.visibility=View.GONE

                        val layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        binding.recylefollowing.layoutManager = layoutManager
                        binding.recylefollowing.adapter = FollowingsAdapter(this,user,it.followingList as List<FollowingListItem>)
                    }
                    else
                    {
                        binding.nofollowing.visibility=View.VISIBLE
                    }
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

}