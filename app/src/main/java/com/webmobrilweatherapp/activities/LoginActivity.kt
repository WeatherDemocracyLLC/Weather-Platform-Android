package com.webmobrilweatherapp.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityLoginBinding
import com.webmobrilweatherapp.function.MySingleton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    var accountViewModel: AccountViewModel? = null
    private var usertype = "0"
    var device_token = ""
    var sendusertype: String = "0"
    private var pressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        // usertype = intent.getStringExtra(PrefConstant.USER_TYPE).toString()
        Log.d("user_type", usertype)
        firebaseetoken()
        //usertype = SharedPreferenceManager.getInstance(this).getUserType(PrefConstant.USER_TYPE)
        MySingleton.handleTheme(this, usertype)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
       // Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgbackloginimages!!)
        binding.textsignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.putExtra(AppConstant.USER_TYPE, usertype)
            startActivity(intent)
            binding.editemailid.setText("")
            binding.editpassworde.setText("")
        }
        binding.textforgotpassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            intent.putExtra(AppConstant.USER_TYPE, usertype)
            startActivity(intent)
            binding.editemailid.setText("")
            binding.editpassworde.setText("")
        }
        binding.btnLogin.setOnClickListener {
            if (isValidation()) {
                getlogin()
            }
        }
    }




    private fun firebaseetoken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            device_token = task.result
            // Log and toast


            Log.e("device Token", device_token)
            //Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        })
    }


    private fun isValidation(): Boolean {

        if (binding.editemailid.text.isNullOrEmpty()) {
            binding.editemailid.requestFocus()
            showToastMessage("Enter Register Email Id")
            return false
        } else if (binding.editpassworde.text.isNullOrEmpty()) {
            binding.editpassworde.requestFocus()
            showToastMessage("Enter register Password")
            return false
        }


        return true
    }


    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)

        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }
    private fun getlogin() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val etEmail: String = binding.editemailid.text.toString()
        val etpassword: String = binding.editpassworde.text.toString()
       // Toast.makeText(this, device_token.toString(), Toast.LENGTH_SHORT).show()
        Log.e("device Token", device_token)

        accountViewModel?.getlogin(etEmail, etpassword, "2", "1", device_token)
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    getLoginClear()
                    // binding!!.checkBox.isChecked = false

                    if(it.status.toString().equals("2"))
                    {
                        var intent = Intent(this, OtpScreenActivity::class.java)
                        intent.putExtra("email", etEmail)
                        startActivity(intent)
                    }
                    else {
                        if(it.data!!.selectButterfly.toString().equals("null"))
                        {
                            var a=0
                            CommonMethod.getInstance(this).savePreference(AppConstant.Key_ID_Butterfly,"0")

                            var intent = Intent(this, ButterflySpeicesActivity::class.java)
                            intent.putExtra("checkStatus",a.toString())
                            startActivity(intent)
                        }
                        else
                        {
                        //Toast.makeText(this, it.st, Toast.LENGTH_LONG).show()

                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                            CommonMethod.getInstance(this)
                                .savePreference(AppConstant.Key_ApplicationId,"1")

                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_Fullname, it.data?.name!!)
                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_Email, it.data.email!!)
                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.USER_TYPE, it.data.userType!!)
                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_User_id, it.data.id!!)
                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_token, it.token!!)
                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, true)
                        var intent = Intent(this, HomeActivity::class.java)
                        intent.putExtra(AppConstant.USER_TYPE, usertype)
                        startActivity(intent)
                        finish()
                        }

                    }

                // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

    }
    private fun getLoginClear() {

        binding.editemailid.setText("")
        binding.editpassworde.setText("")

    }

}
