package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.webmobrilweatherapp.databinding.ActivityMetrologistForgotBinding

class MetrologistForgotActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistForgotBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_forgot)
        Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgBatterfly!!)

        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        binding.BtnResetPassword.setOnClickListener {
            if (isValidation()) {
                getLoginmetrologist()
            }
        }
    }

    private fun isValidation(): Boolean {
        if (binding.etEmailMetrologistForgot.text.isEmpty()) {
            Toast.makeText(this, "Please Enter Email ID", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun getLoginmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val etEmail: String = binding.etEmailMetrologistForgot.text.toString()
        accountViewModelMetrologist?.getforgotmetrologist(etEmail, "3")
            ?.observe(this, {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MetrologistOtpActivity::class.java)
                    intent.putExtra("email", etEmail)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            })
    }
}