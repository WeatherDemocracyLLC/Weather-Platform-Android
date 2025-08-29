package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.function.MySingleton
import android.annotation.SuppressLint
import android.os.Bundle
import com.webmobrilweatherapp.R
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.databinding.ActivityChangePasswordBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    var accountViewModel: AccountViewModel? = null
    private var usertype = "0"

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnchangepassword.setOnClickListener {
            if (isValidation()) {
                getchangepasswords()
            }
        }


        binding.toolbar.tvToolbar.setText("Change Password")
        binding.toolbar.imgarrowbackabout.setOnClickListener {
            onBackPressed()
        }



    }


    private fun isValidation(): Boolean {
        if (binding.oldpassword.text.isNullOrEmpty()) {
            binding.oldpassword.requestFocus()
            showToastMessage("Enter current password")
            return false
        } else if (binding.newpassword.text.isNullOrEmpty()) {
            binding.newpassword.requestFocus()
            showToastMessage("Enter new password")
            return false
        } else if (binding.newpassword.text.length < 8 || binding.newpassword.text.length > 16) {
            showToastMessage("Password must be between 8 to 16 characters")
            return false

        } else if (!isValidPassword(binding.newpassword.text.toString())) {
            showToastMessage("Please enter one upper case character one numeric character and one special character")
            return false
        }
        
        else if (binding.conformpassword.text.isNullOrEmpty()) {
            binding.conformpassword.requestFocus()
            showToastMessage("Enter confirm password")
            return false
        }
        else if (binding.conformpassword.text.length < 8 || binding.conformpassword.text.length > 16) {
            showToastMessage("Password must be between 8 to 16 characters")
            return false


        } else if (!isValidPassword(binding.conformpassword.text.toString())) {
            showToastMessage("Please enter one upper case character one numeric character and one special character")
            return false
        }
        else if (binding.conformpassword.equals(binding.newpassword)) {
            showToastMessage("Confirm password do not Match")
            return false

        }
        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }


    private fun getchangepasswords() {

        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val oldpassword: String = binding.oldpassword.text.toString()
        val newpassword: String = binding.newpassword.text.toString()
        val confirmpassword: String = binding.conformpassword.text.toString()
        accountViewModel?.getchangepasswords(
            oldpassword,
            newpassword,
            confirmpassword,
            "Bearer " + CommonMethod.getInstance(this).getPreference(AppConstant.KEY_token)
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    val i = Intent(this, ViewProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(i)
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