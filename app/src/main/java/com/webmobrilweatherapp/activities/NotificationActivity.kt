package com.webmobrilweatherapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.NotificationAdapter
import com.webmobrilweatherapp.databinding.ActivityNotificationBinding
import com.webmobrilweatherapp.function.MySingleton
import com.webmobrilweatherapp.model.notification.NotificationsItem
import com.webmobrilweatherapp.model.uservotinglist.DataItem
import com.webmobrilweatherapp.utilise.NotificationUtils
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel


class NotificationActivity : AppCompatActivity(), NotificationAdapter.SelectItem, View.OnClickListener {

    lateinit var binding: ActivityNotificationBinding
    lateinit var notificationAdapter: NotificationAdapter
    var accountViewModel: AccountViewModel? = null
    private val instanceId = "29fb2b33-4975-4baa-986e-2b323462775f"
    private var usertype = "0"


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        //usertype = SharedPreferenceManager.getInstance(this).getUserType(PrefConstant.USER_TYPE)
        MySingleton.handleTheme(this, usertype)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        NotificationUtils.clearNotificationCount(applicationContext)
        val intent = Intent("com.webmobrilweatherapp.NOTIFICATION_COUNT_UPDATED")
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
       /* PushNotifications.start(applicationContext, instanceId)
        PushNotifications.setOnDeviceInterestsChangedListener(object: SubscriptionsChangedListener {
            override fun onSubscriptionsChanged(interests: Set<String>) {
              //  Log.i("MainActivity", "Interests: ${interests.toString()}")
            }
        })
        PushNotifications.getDeviceInterests().forEach { interest ->
            //Log.i("MainActivity", "\t$interest")
        }*/
        listener()
        getnotification()

    }
    override fun onResume() {
        super.onResume()

    /*    PushNotifications.setOnMessageReceivedListenerForVisibleActivity(this, object:
            PushNotificationReceivedListener {
            override fun onMessageReceived(remoteMessage: RemoteMessage) {
                //Log.i("MainActivity", "Remote message received while this activity is visible!")
            }
        })*/

    }
    private fun listener() {
        binding.notifyToolbar.imgbackicon.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgbackicon -> {
                onBackPressed()
            }
        }
    }

    private fun getnotification() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getnotification(this,
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                if (it != null && it.success == true) {
                    binding.recyclerView.visibility= View.VISIBLE
                    if (it.notifications as List<DataItem> != null && it.notifications.size > 0) {
                        binding.noNotifications.visibility= View.GONE
                        //  binding.Txtnovoting.visibility = View.GONE
                        notificationAdapter =
                            NotificationAdapter(this, it.notifications as List<NotificationsItem>,this)
                        val layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        binding.recyclerView.layoutManager = layoutManager
                        binding.recyclerView.adapter = notificationAdapter
                    } else {
                        binding.recyclerView.visibility= View.GONE

                        //  binding.Txtnovoting.visibility = View.VISIBLE
                    }

                    // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    binding.recyclerView.visibility= View.GONE
                    binding.noNotifications.visibility= View.VISIBLE
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }                }
            }
    }

    /////////////////////////////////////*************************************************/////////////////////////////////////////

    private fun getAcceptReject(status: String,requestFrom:String) {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getRequestAcceptReject(status,requestFrom,
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()

                if (it != null && it.success == true) {
                    binding.recyclerView.visibility= View.GONE

                   // getnotification()
                     Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
                    onBackPressed()
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }


    override fun selectItem(status: String, requestFrom: String) {
        getAcceptReject(status,requestFrom)
    }

}