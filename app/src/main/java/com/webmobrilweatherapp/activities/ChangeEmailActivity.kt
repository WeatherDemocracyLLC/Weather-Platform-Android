package com.webmobrilweatherapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityChangeEmailBinding

class ChangeEmailActivity : AppCompatActivity() {
    lateinit var binding: ActivityChangeEmailBinding
    var accountViewModel: AccountViewModel? = null
    private var usertype = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_email)
        Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgBatterfly!!)

        binding.BtnchangeEmail.setOnClickListener {
            if (isValidation()) {
                getResetData()

            }
        }
    }

    private fun isValidation(): Boolean {
        if (binding.etEmailChange.text.isNullOrEmpty()) {
            binding.etEmailChange.requestFocus()
            showToastMessage("Enter Register Email Id")
            return false
        }
        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    private fun getResetData() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val email: String = binding.etEmailChange.text.toString()
        usertype
        accountViewModel?.getForgotPass(email, "2")
            ?.observe(this, {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false
                    binding.etEmailChange.setText("")
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    var intent = Intent(this, ForgotOtpVerificationActivity::class.java)
                    intent.putExtra("email", email)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.putExtra(AppConstant.USER_TYPE, usertype)
                    startActivity(intent)
                    finish()
                    // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            })

    }
}