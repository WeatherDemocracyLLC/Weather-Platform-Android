package com.webmobrilweatherapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityOtpScreenBinding
import com.webmobrilweatherapp.function.MySingleton

class OtpScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityOtpScreenBinding
    var usertype = "0"
    var email = ""
    var accountViewModel: AccountViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)
        email = intent.getStringExtra("email").toString()
        binding = ActivityOtpScreenBinding.inflate(layoutInflater)

        getemailverification()

        val view = binding.root
        setContentView(view)
        binding.textotpsentemailid.setText("" + email)
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.layoutOtp.visibility = View.VISIBLE
                binding.txtresenotpsignup.visibility = View.GONE
               // binding.otpview.visibility=View.VISIBLE
                binding.textcodeexpiresnumber.setText("" + millisUntilFinished / 1000)
                //here you can have your logic to set text to edittext
            }


            override fun onFinish() {

              //  binding.otpview.visibility=View.GONE

                binding.textcodeexpiresnumber.setText("")
                binding.layoutOtp.visibility = View.GONE
                binding.txtresenotpsignup.visibility = View.VISIBLE

            }

        }
        timer.start()
        binding.txtresenotpsignup.setOnClickListener {

            getemailverification()

        }


        binding.btnVerifyOTP.setOnClickListener {
            if (isValidation()) {
                getSignupVerifyOtp()
            }

            /*var intent = Intent(this, ButterflySpeicesActivity::class.java)
            startActivity(intent)*/
        }
        initialization()

    }

    private fun isValidation(): Boolean {
        if (binding.otpview.text.isNullOrEmpty()) {
            binding.otpview.requestFocus()
            showToastMessage(" Please Enter OTP Send Your Register Email ID")
            return false
        }
        return true
    }

    private fun initialization() {

        binding.textotpsentemailid.setText("" + email)

        if (usertype == "3") {
            binding.imgotpbackscreen.setBackgroundResource(R.drawable.backimagemetro)
            binding.btnVerifyOTP.setBackgroundResource(R.drawable.metroshapebutton)
            binding.imgspleshotp.setBackgroundResource(R.drawable.ic_metroblueicon)

        }

    }

    private fun showToastMessage(message: String) {

        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()

    }

    private fun getemailverification() {
         ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        // Get user sign up response
        accountViewModel?.getemailverification(email, "2")
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false
                    //Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }

            }
    }


    private fun getSignupVerifyOtp() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val etotp: String = binding.otpview.text.toString()
        accountViewModel?.getSignupVerifyOtp(email, etotp)
            ?.observe(this) {

                //  ProgressBarUtils.hideProgressDialog()
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {

                    // binding!!.checkBox.isChecked = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    /*var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()*/


                    var a=0
                    CommonMethod.getInstance(this).savePreference(AppConstant.Key_ID_Butterfly,"0")

                    var intent = Intent(this, ButterflySpeicesActivity::class.java)
                    intent.putExtra("checkStatus",a.toString())
                    startActivity(intent)

                    // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)

                } else if (it != null && it.code == 400) {

                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()


            }
                else {

                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()

                }
            }

    }

    private fun getResetData() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        usertype
        accountViewModel?.getForgotPass(email, "2")
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false

                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                    // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

}