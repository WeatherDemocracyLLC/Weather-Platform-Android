package com.webmobrilweatherapp.activities.metrologistactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistUsertopfivemetrologistAdapter
import com.webmobrilweatherapp.databinding.ActivityMeterologistBinding
import com.webmobrilweatherapp.model.topfivemetrologist.DataItem

class MeterologistActivity : AppCompatActivity() {
    lateinit var binding:ActivityMeterologistBinding
    private lateinit var metrologistUsertopfivemetrologistAdapter: MetrologistUsertopfivemetrologistAdapter
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    var userType = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_meterologist)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        getsearchprofilesmetrologist()
        binding.Metrologist.ImgBack.setOnClickListener {
            onBackPressed()
        }
        binding.Metrologist.txtLocation.setText("Meteorologist")


     }
    private fun getsearchprofilesmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.gettopfivemetrologist(
            userType.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    metrologistUsertopfivemetrologistAdapter =
                        MetrologistUsertopfivemetrologistAdapter(this, it.data as List<DataItem>)
                    val layoutManager = LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL, false
                    )
                    binding.metrologist.layoutManager = layoutManager
                    binding.metrologist.adapter = metrologistUsertopfivemetrologistAdapter
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}