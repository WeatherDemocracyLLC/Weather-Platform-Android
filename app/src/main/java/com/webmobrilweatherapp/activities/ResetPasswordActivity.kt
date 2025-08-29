package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.function.MySingleton
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.webmobrilweatherapp.R
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.databinding.ActivityResetPasswordBinding
import com.webmobrilweatherapp.utilise.CommonUtil


class ResetPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityResetPasswordBinding
    var accountViewModel: AccountViewModel? = null
    var data: String = ""
    private var usertype = "0"
    var email: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        email = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_Email)
        Log.d("user_type", usertype)
        // usertype = SharedPreferenceManager.getInstance(this).getPreference(PrefConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)
        // MySingleton.handleTheme(this, email)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgbackimagerest!!)
        val view = binding.root
        binding.btnresetpassword.setOnClickListener {
            if (isValidation()) {
                //  getValidateInput()
                getResetData()

            }
        }
        setContentView(view)

        initialization()
    }

    private fun isValidation(): Boolean {
        if (binding.editemailforgotpwd.text.isNullOrEmpty()) {
            binding.editemailforgotpwd.requestFocus()
            showToastMessage("Enter Register Email Id")
            return false
        }
        return true
    }

    private fun initialization() {
        if (usertype == "3") {
            binding.imgframrest.setBackgroundResource(R.drawable.backimagemetro)
            binding.btnresetpassword.setBackgroundResource(R.drawable.metroshapebutton)
            binding.imgbackimagerest.setBackgroundResource(R.drawable.ic_metroblueicon)
            binding.epasswordiconreset.setBackgroundResource(R.drawable.ic_metroemail)
        }
    }

    private fun getResetData() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val email: String = binding.editemailforgotpwd.text.toString()
        usertype
        accountViewModel?.getForgotPass(email, "2")
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    binding.editemailforgotpwd.setText("")
                    // binding!!.checkBox.isChecked = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    var intent = Intent(this, ForgotOtpVerificationActivity::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra(AppConstant.USER_TYPE, usertype)
                    startActivity(intent)
                    // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun getValidateInput() {
        var isError = false
        data = binding.editemailforgotpwd.text.toString()
        if (!CommonUtil.isStringValue(data)) {
            isError = true
            binding.editemailforgotpwd.requestFocus()
            CommonUtil.showMessage(this, resources.getString(R.string.error_empty_emailornumber))
            CommonUtil.hideKeyboard(this)
        } else if (!CommonUtil.isValidEmail(data)) {
            isError = true
            binding.editemailforgotpwd.requestFocus()
            CommonUtil.showMessage(this, resources.getString(R.string.error_invalide_emailornumber))
            CommonUtil.hideKeyboard(this)
        }
        if (!isError) {
            if (CommonUtil.hasNetworkAvailable(this)) {
                CommonUtil.hideKeyboard(this)
                //getForgotPassword()
            } else CommonUtil.showMessage(this, resources.getString(R.string.error_msg_network))
        }
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

}