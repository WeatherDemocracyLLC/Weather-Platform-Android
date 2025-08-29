package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.databinding.ActivitySendAlertBinding

class SendAlertActivity : AppCompatActivity() {
    lateinit var binding: ActivitySendAlertBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_alert)
        useridMetrologist = CommonMethod.getInstance(this)
            .getPreference(AppConstant.KEY_User_idMetrologist)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        binding.layoutSendAlart.ImgBack.setOnClickListener {
            onBackPressed()
        }
        binding.layoutSendAlart.txtLocation.setText("Send Alert")
        binding.btnSendAlart.setOnClickListener {
            if (isValidation()) {
                getsendalerts()

            }
        }
    }
    private fun isValidation(): Boolean {
        if (binding.edittitle.text.isNullOrEmpty()) {
            binding.edittitle.requestFocus()
            showToastMessage("Enter Title")
            return false
        } else if (binding.editmessage.text.isNullOrEmpty()) {
            binding.editmessage.requestFocus()
            showToastMessage("Enter Message")
            return false
        }
        return true
    }
    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    private fun getsendalerts() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val title: String = binding.edittitle.text.toString()
        val message: String = binding.editmessage.text.toString()

        accountViewModelMetrologist?.getsendalerts(title,message,useridMetrologist, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MetrilogistHomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }


}