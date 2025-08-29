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
import com.webmobrilweatherapp.databinding.ActivityMeterologistChangePasswordBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class MeterologistChangePasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityMeterologistChangePasswordBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_meterologist_change_password)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        binding.layoutchangepassword.txtLocation.setText("Change Password")
        binding.layoutchangepassword.ImgBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnchangepasswordmetrologist.setOnClickListener {

            if (isValidation()) {
                getchangepasswordsMetrologist()

            }
        }


    }

    private fun isValidation(): Boolean {
        if (binding.oldpassword.text.isNullOrEmpty()) {
            binding.oldpassword.requestFocus()
            showToastMessage("Enter Current Password")
            return false
        } else if (binding.newpassword.text.isNullOrEmpty()) {
            binding.newpassword.requestFocus()
            showToastMessage("Enter new Password")
            return false
        } else if (binding.newpassword.text.length < 8 || binding.newpassword.text.length > 16) {
            showToastMessage("Password Must be Between 8 to 16 Characters")
            return false
        } else if (!isValidPassword(binding.newpassword.text.toString())) {
            showToastMessage("Please Enter One Upper case character one numeric character and one special character")
            return false
        } else if (binding.conformpassword.text.isNullOrEmpty()) {
            binding.conformpassword.requestFocus()
            showToastMessage("Enter Confirm Password")
            return false
        } else if (binding.conformpassword.text.toString()!=(binding.newpassword.text.toString())) {
            showToastMessage("New Password and Confirm Password doesn't match.")
            return false
        }

        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    private fun getchangepasswordsMetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val oldpassword: String = binding.oldpassword.text.toString()
        val newpassword: String = binding.newpassword.text.toString()
        val confirmpassword: String = binding.conformpassword.text.toString()
        accountViewModelMetrologist?.getchangepasswordsMetrologist(
            oldpassword,
            newpassword,
            confirmpassword,
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MetrologistViewProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Your current password does not match", Toast.LENGTH_LONG)
                        .show()
                }
            }


    }

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+*!=])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

}