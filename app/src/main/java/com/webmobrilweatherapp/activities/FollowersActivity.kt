package com.webmobrilweatherapp.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.FollowersAdapter
import com.webmobrilweatherapp.databinding.ActivityFollowersBinding
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.model.Followers.FollowerListItem


class FollowersActivity : AppCompatActivity() {

    lateinit var binding: ActivityFollowersBinding
    var accountViewModel: AccountViewModel? = null
    var user=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_followers)

        binding.imgbackicon.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })


        getFollowers()
    }

    private fun getFollowers() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

        accountViewModel?.getFollowers("Bearer " + CommonMethod.getInstance(this).getPreference(
            AppConstant.KEY_token))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success== true) {
                    //Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()


                    if(it.followerList!!.size>0)
                    {
                        binding.nofollowers.visibility=View.GONE
                        val layoutManager =LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        binding.recylefollowers.layoutManager = layoutManager
                        binding.recylefollowers.adapter =FollowersAdapter(this,user,it.followerList as List<FollowerListItem>)
                    }
                else
                    {
                        binding.nofollowers.visibility=View.VISIBLE
                    }
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

}
