package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.function.MySingleton
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import com.webmobrilweatherapp.R
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.databinding.ActivityContactBinding
import java.util.regex.Pattern

class ContactActivity : AppCompatActivity() {

    lateinit var binding: ActivityContactBinding
    private var usertype = "0"
    var accountViewModel: AccountViewModel? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)

        binding = ActivityContactBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.toolbar.tvToolbar.setText("Contact Us")
        binding.toolbar.imgarrowbackabout.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })


        binding.btnsubmit.setOnClickListener {
            if (isValidation()) {
                getcontactus()
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
        }
        else if(binding.mobileNo.text.length < 6 || binding.mobileNo.text.length > 15)
        {
            binding.mobileNo.requestFocus()
            showToastMessage("Phone number must be of minimum 6 digits and maximum 15 digits.")
            return false
        }


            else if (binding.emailId.text.isNullOrEmpty()) {
            binding.emailId.requestFocus()
            showToastMessage("Please enter email Id")
            return false
        }
        else if (!validateEmail(binding.emailId.text.toString())) {
            binding.emailId.requestFocus()
            Toast.makeText(this, "Please enter valid Email Id", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (binding.messageField.text.isNullOrEmpty()) {
            binding.messageField.requestFocus()
            showToastMessage("Please enter message")
            return false
        }
        return true
    }
    fun validateEmail(email: String): Boolean {
        return Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        ).matcher(email).matches()
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    private fun getcontactus() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val etname: String = binding.name.text.toString()
        val etmobile: String = binding.mobileNo.text.toString()
        val etEmail: String = binding.emailId.text.toString()
        val etmeassage: String = binding.messageField.text.toString()
        accountViewModel?.getcontactus(
            etname,
            etmobile,
            etEmail,
            etmeassage,
            "Bearer " + CommonMethod.getInstance(this).getPreference(AppConstant.KEY_token)
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    val i = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

                    startActivity(i)
                    finish()
                } else {

                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

                }
            }
    }
}