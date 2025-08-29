package com.webmobrilweatherapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityForgotOtpVerificationBinding
import com.webmobrilweatherapp.function.MySingleton
import com.webmobrilweatherapp.network.ApiStatusConstant
import com.webmobrilweatherapp.utilise.CommonUtil
import android.os.CountDownTimer
import android.view.View.GONE
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.util.Log


class ForgotOtpVerificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotOtpVerificationBinding
    var accountViewModel: AccountViewModel? = null
    private var totalTimeCountInMilliseconds: Long = 0
    private var timeBlinkInMilliseconds: Long = 0
    private var usertype = "0"
    var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        // email=intent.getStringExtra("email").toString()
        usertype = CommonMethod.getInstance(this).getPreference(AppConstant.USER_TYPE)
        MySingleton.handleTheme(this, email)
//        MySingleton.handleTheme(this, usertype)

        binding = ActivityForgotOtpVerificationBinding.inflate(layoutInflater)
        Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgspleshotps!!)
        val view = binding.root
        binding.btnVerifyotp.setOnClickListener {
            if (isValidation()) {
                getVerifyOtp()
            }
        }
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.layoutOtpForgot.visibility = VISIBLE
                binding.txtresenotp.visibility = GONE
               // binding.otpview.visibility= VISIBLE

                binding.textcodeexpiresnumberforgot.setText("" + millisUntilFinished / 1000)
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                binding.textcodeexpiresnumberforgot.setText("")
                binding.layoutOtpForgot.visibility = GONE
                binding.txtresenotp.visibility = VISIBLE
             //   binding.otpview.visibility= GONE
            }
        }
        timer.start()
        binding.textChangeEmailID.setOnClickListener {
            val intent = Intent(this, ChangeEmailActivity::class.java)
            startActivity(intent)
        }
        setContentView(view)
        initialization()
    }

    private fun isValidation(): Boolean {
        if (binding.otpview.text.isNullOrEmpty()) {
            binding.otpview.requestFocus()
            showToastMessage("Enter Otp")
            return false
        }
        return true
    }

    private fun initialization() {
        email = intent.getStringExtra("email").toString()
        binding.textotpsentemailid.setText("" + email)
        if (usertype == "3") {
            binding.imgotpbackscreen.setBackgroundResource(R.drawable.backimagemetro)
            binding.btnVerifyotp.setBackgroundResource(R.drawable.metroshapebutton)
            binding.imgspleshotp.setBackgroundResource(R.drawable.ic_metroblueicon)
        }
        binding.txtresenotp.setOnClickListener {
            getResetData()
        }
    }

    private fun getVerifyOtp() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val etotp: String = binding.otpview.text.toString()
        accountViewModel?.getVerifyOtp(email, etotp)
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null) {
                    Log.e("comehere",it.message);
                    cleartext()
                    val respo = it
                    val statusCode = respo.code
                    val message = respo.message
                    if (statusCode == ApiStatusConstant.API_200) {
                        Log.e("comehere21",it.message);
                        val intent = Intent(this, CreateNewPasswordActivity::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra(AppConstant.USER_TYPE, usertype)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        CommonUtil.showMessage(this, message)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("comehere24",it.message);
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                    }
                }else{
                    it?.message?.let { it1 -> Log.e("comehere25", it1) };
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun cleartext() {
        binding.otpview.setText("")
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    private fun getResetData() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        usertype
        accountViewModel?.getForgotPass(email, "2")
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}