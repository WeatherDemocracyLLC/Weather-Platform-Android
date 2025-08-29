package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.UsertopfivemetrologistAdapter
import com.webmobrilweatherapp.databinding.ActivityTopFiveMetrologistBinding
import com.webmobrilweatherapp.model.topfivemetrologist.DataItem

class TopFiveMetrologistActivity : AppCompatActivity() {
    lateinit var binding: ActivityTopFiveMetrologistBinding

    var accountViewModel: AccountViewModel? = null
    private lateinit var usertopfivemetrologistAdapter: UsertopfivemetrologistAdapter

    var userType = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_five_metrologist)

        getsearchprofilesmetrologist()
        binding.imgarrowback.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getsearchprofilesmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.gettopfiveUser(
            userType.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    usertopfivemetrologistAdapter =
                        UsertopfivemetrologistAdapter(this, it.data as List<DataItem>)
                    val layoutManager = LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL, false
                    )
                    binding.recylweviewtopfive.layoutManager = layoutManager
                    binding.recylweviewtopfive.adapter = usertopfivemetrologistAdapter
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}