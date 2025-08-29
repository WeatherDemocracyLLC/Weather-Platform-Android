package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.WeatherAlertAdapter
import com.webmobrilweatherapp.model.WeatherAlert.DataItem

class UserAlertActivity : AppCompatActivity() {

    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    lateinit var weatherAlertAdapter: WeatherAlertAdapter
    lateinit var alertViewRecyclerView: RecyclerView
    lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_alert)
        alertViewRecyclerView=findViewById(R.id.recycler_viewAlert)
        backButton=findViewById(R.id.imgbackicon)
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.toolbaar))
        backButton.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        getWeatherAlert()


    }
///////////////////*****************************************Weather Alert Api******************************////////////////

    private fun getWeatherAlert() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getWeatherAlert()
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {

                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                    weatherAlertAdapter =
                        WeatherAlertAdapter(this, it.data as List<DataItem>)
                   // val layoutManager =
                      //  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true,)
                    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false).apply {
                        stackFromEnd = true // Keeps the scroller at the bottom initially
                        reverseLayout = true // Reverses the list order
                    }
                    alertViewRecyclerView.layoutManager = layoutManager
                    alertViewRecyclerView.adapter = weatherAlertAdapter

                } else
                {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    /////////////////////////////////////*************************************************/////////////////////////////////////////

}