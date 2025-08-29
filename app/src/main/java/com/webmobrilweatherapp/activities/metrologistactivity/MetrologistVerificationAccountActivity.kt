package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.databinding.ActivityMetrologistVerificationAccountBinding

class MetrologistVerificationAccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistVerificationAccountBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null

    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_metrologist_verification_account)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        email = intent.getStringExtra("email").toString()
        getemailverificationmetrologist()
        binding.txtSetEmail.setText(email)
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.layoutOtpForgotMerologist.visibility = View.VISIBLE
                binding.txtresenotpMetrologist.visibility = View.GONE
                binding.textcodeexpiresnumberforgot.setText("" + millisUntilFinished / 1000)
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                binding.textcodeexpiresnumberforgot.setText("")
                binding.layoutOtpForgotMerologist.visibility = View.GONE
                binding.txtresenotpMetrologist.visibility = View.VISIBLE
            }
        }
        timer.start()
        binding.txtresenotpMetrologist.setOnClickListener {
           /// getforgotmetrologist()
            getemailverificationmetrologist()
        }
        binding.BtnVerifyAccountOTP.setOnClickListener {
            if (isValidation()) {
               getverifyotpmetrologist()
              //  getemailverificationmetrologist()
            }
        }

    }

    private fun isValidation(): Boolean {
        if (binding.otpviewMetrologist.otp.isEmpty()) {
            Toast.makeText(this, "Please Enter Otp", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun getverifyotpmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val otp: String = binding.otpviewMetrologist.otp.toString()
        accountViewModelMetrologist?.getsignupotpverificationmetrologist(otp, email)
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false
                  //  Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                    var a=0

                    var intent = Intent(this, MetrologistButterFlySpeicesActivity::class.java)
                    intent.putExtra("checkStatus",a.toString())
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                } else {
                    //Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun getemailverificationmetrologist() {
          ProgressD.showLoading(this,getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getemailverificationmetrologist(email, "3")
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false
                    // Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun getforgotmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getforgotmetrologist(email, "3")
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }
}