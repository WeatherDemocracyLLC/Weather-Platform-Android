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
import com.webmobrilweatherapp.databinding.ActivityMetrologistContactBinding

class MetrologistContactActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistContactBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_contact)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        binding.layoutContactUs.ImgBack.setOnClickListener {
            onBackPressed()
        }
        binding.layoutContactUs.txtLocation.setText("Contact Us")
        binding.btnsubmit.setOnClickListener {
            if (isValidation()) {
                getcontactusmetrologist()

            }
        }
    }

    private fun isValidation(): Boolean {
        if (binding.name.text.isNullOrEmpty()) {
            binding.name.requestFocus()
            showToastMessage("Please enter name")
            return false
        } else if (binding.mobileNo.text.isNullOrEmpty()) {
            binding.mobileNo.requestFocus()
            showToastMessage("Please enter mobile number")
            return false
        } else if (binding.emailId.text.isNullOrEmpty()) {
            binding.emailId.requestFocus()
            showToastMessage("Please enter email Id")
            return false
        } else if (binding.messageField.text.isNullOrEmpty()) {
            binding.messageField.requestFocus()
            showToastMessage("Please enter message")
            return false
        }
        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    private fun getcontactusmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val etname: String = binding.name.text.toString()
        val etphone: String = binding.mobileNo.text.toString()
        val etemail: String = binding.emailId.text.toString()
        val etmeassage: String = binding.messageField.text.toString()
        accountViewModelMetrologist?.getcontactusmetrologist(
            etname,
            etphone,
            etemail,
            etmeassage,
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MetrilogistHomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}