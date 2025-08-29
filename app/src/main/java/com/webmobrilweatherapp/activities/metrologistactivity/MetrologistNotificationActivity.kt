package com.webmobrilweatherapp.activities.metrologistactivity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistNotificationAdapterr
import com.webmobrilweatherapp.databinding.ActivityMetrologistNotificationBinding
import com.webmobrilweatherapp.model.notification.NotificationsItem
import com.webmobrilweatherapp.utilise.NotificationUtils
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel

class MetrologistNotificationActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistNotificationBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    lateinit var notificationAdapter: MetrologistNotificationAdapterr
    private val instanceId = "29fb2b33-4975-4baa-986e-2b323462775f"
    var accountViewModel: AccountViewModel? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)


        accountViewModelMetrologist =ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_notification)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))

        binding.layoutmetrologistNotification.imgbackicon.setOnClickListener {
            onBackPressed()
        }

        NotificationUtils.clearNotificationCountMeterological(applicationContext)
        val intent = Intent("com.webmobrilweatherapp.NOTIFICATION_COUNT_UPDATED")
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

        binding.layoutmetrologistNotification.txtHeaderName.setText("Notification")
        //PushNotifications.start(applicationContext, instanceId)

      //  initnotification()
        getnotificationMetrologist()
    }

   /* private fun initnotification() {

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewnotification.layoutManager = layoutManager
        binding.recyclerViewnotification.adapter = MetrologistNotificationAdapter(this)

    }*/
    private fun getnotificationMetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getnotificationMetrologist(
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            ),this
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.notifications as List<NotificationsItem> != null && it.notifications.size!! > 0) {
                       // binding.txtNovotes.visibility = View.GONE
                        notificationAdapter = MetrologistNotificationAdapterr(this,it.notifications as List<NotificationsItem>)
                        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        binding.recyclerViewnotification.layoutManager = layoutManager
                        binding.recyclerViewnotification.adapter = notificationAdapter
                        // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    } else {
                        //binding.txtNovotes.visibility = View.VISIBLE
                    }
                } else
                {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    /////////////////////////////////////*************************************************/////////////////////////////////////////

    private fun getAcceptReject(status: String,requestFrom:String) {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getRequestAcceptReject(status,requestFrom,
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()

                if (it != null && it.success == true) {
                    binding.recyclerViewnotification.visibility= View.GONE

                    // getnotification()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    onBackPressed()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

}