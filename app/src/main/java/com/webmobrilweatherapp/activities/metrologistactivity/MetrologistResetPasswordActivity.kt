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
import com.bumptech.glide.Glide
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.databinding.ActivityMetrologistResetPasswordBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class MetrologistResetPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistResetPasswordBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    var email = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_reset_password)
        Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgBatterfly!!)

        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        email = intent.getStringExtra("email").toString()
        binding.BtnResetPassword.setOnClickListener {
            if (isValidation()) {
                getresetpasswordmetrologist()
            }
        }
    }

    private fun isValidation(): Boolean {
        if (binding.etNewPassword.text.isEmpty()) {
            Toast.makeText(this, "Please Enter New Password", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.etConfirmPassword.text.isEmpty()) {
            Toast.makeText(this, "Please Enter confirm Password", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.etNewPassword.text.length < 8 || binding.etNewPassword.text.length > 16) {
            showToastMessage("Password Must be Between 8 to 16 Characters")
            return false
        } else if (!isValidPassword(binding.etNewPassword.text.toString())) {
            showToastMessage("Please Enter One Upper case character one numeric character and one special character")
            return false
        } else if (binding.etNewPassword.text.toString() != binding.etConfirmPassword.text.toString()) {
            showToastMessage("Password and confirm password do not match")
            return false

        }
        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    private fun getresetpasswordmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        email
        val etnewpassowrd: String = binding.etNewPassword.text.toString()
        val etConfirmPassword: String = binding.etConfirmPassword.text.toString()
        accountViewModelMetrologist?.getresetpasswordmetrologist(
            email,
            etnewpassowrd,
            etConfirmPassword
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MetrologistLogInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                    // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
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