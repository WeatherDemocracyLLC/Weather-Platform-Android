package com.webmobrilweatherapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityCreateNewPasswordBinding
import com.webmobrilweatherapp.function.MySingleton
import java.util.regex.Matcher
import java.util.regex.Pattern

class CreateNewPasswordActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityCreateNewPasswordBinding
    var accountViewModel: AccountViewModel? = null


    var email = ""
    private var usertype = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()

        MySingleton.handleTheme(this, usertype)
        binding = ActivityCreateNewPasswordBinding.inflate(layoutInflater)
        Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgspleshscreennewpwd!!)
        val view = binding.root
        setContentView(view)
        /* binding.btnresetnewpwd.setOnClickListener {
             if (isValidation()){

             }
         }*/
        initialization()
        listener()
        binding.btnresetnewpwd.setOnClickListener {
            if (isValidation()) {
                getResetViewModel()
            }
        }
        //getResetViewModel()

    }

    private fun isValidation(): Boolean {
        if (binding.editnewpwd.text.isNullOrEmpty()) {
            binding.editnewpwd.requestFocus()
            showToastMessage("Enter New Password")
            return false
        } else if (binding.editconfirmpwd.text.isNullOrEmpty()) {
            binding.editconfirmpwd.requestFocus()
            showToastMessage("Enter Confirm Password")
            return false
        } else if (binding.editnewpwd.text.length < 8 || binding.editnewpwd.text.length > 16) {
            showToastMessage("Password Must be Between 8 to 16 Characters")
            return false
        } else if (!isValidPassword(binding.editnewpwd.text.toString())) {
            showToastMessage("Please Enter One Upper case character one numeric character and one special character")
            return false
        } else if (binding.editnewpwd.text.toString() != binding.editconfirmpwd.text.toString()) {
            showToastMessage("Password and confirm password do not match")
            return false

        }
        return true
    }

    private fun getResetViewModel() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        email
        val etnewpassowrd: String = binding.editnewpwd.text.toString()
        val etConfirmPassword: String = binding.editconfirmpwd.text.toString()
        accountViewModel?.getCreateNewPassword(email, etnewpassowrd, etConfirmPassword)
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    clearText()
                    // binding!!.checkBox.isChecked = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    var intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra(AppConstant.USER_TYPE, usertype)
                    startActivity(intent)
                    finish()
                    // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun clearText() {
        binding.editnewpwd.setText("")
        binding.editconfirmpwd.setText("")

    }

    private fun initialization() {
        email = intent.getStringExtra("email").toString()
        if (usertype == "3") {
            binding.imgbacksreennewpwd.setBackgroundResource(R.drawable.backimagemetro)
            binding.btnresetnewpwd.setBackgroundResource(R.drawable.metroshapebutton)
            binding.imgspleshscreennewpwd.setBackgroundResource(R.drawable.ic_metroblueicon)
            binding.imgnewpwdcreate.setBackgroundResource(R.drawable.ic_metropassworicon)
            binding.imgnewpwd.setBackgroundResource(R.drawable.ic_metropassworicon)
        }

    }

    private fun listener() {
        binding.btnresetnewpwd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            /* R.id.btnresetnewpwd -> {
                 getValidateInput()
                 getResetViewModel()

             }*/
        }
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
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