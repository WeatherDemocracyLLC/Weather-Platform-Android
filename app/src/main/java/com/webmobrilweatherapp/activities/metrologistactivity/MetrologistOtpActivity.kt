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
import com.bumptech.glide.Glide
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.databinding.ActivityMetrologistOtpBinding

class MetrologistOtpActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistOtpBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    var email = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_otp)
        Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgBatterfly!!)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        email = intent.getStringExtra("email").toString()
        binding.BtnVerifyOTP.setOnClickListener {
            if (isValidation()) {
                getverifyotpmetrologist()
            }
        }
        binding.textChangeEmailID.setOnClickListener {
            var intent = Intent(this, MetrologistChangeEmailActivity::class.java)
            startActivity(intent)
        }
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
            getforgotmetrologist()
        }
    }

    private fun getforgotmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getforgotmetrologist(email, "3")
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
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
        accountViewModelMetrologist?.getverifyotpmetrologist(otp, email)
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MetrologistResetPasswordActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}