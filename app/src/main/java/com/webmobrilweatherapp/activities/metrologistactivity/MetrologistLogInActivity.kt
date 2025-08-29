package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityMetrologistLogInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.webmobrilweatherapp.activities.*

class MetrologistLogInActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistLogInBinding
    var device_token = ""
    lateinit var text:TextView
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_log_in)
        Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgBatterfly!!)
        val window: Window = this.getWindow()

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        firebaseetoken()
        binding.txtSignup.setOnClickListener {
            val i = Intent(this, MertologistSignUpActivity::class.java)
            startActivity(i)
        }
        binding.txtForGotPassword.setOnClickListener {
            val i = Intent(this, MetrologistForgotActivity::class.java)
            startActivity(i)
        }
        binding.BtnLiginmetrologist.setOnClickListener {
            if (isValidation()) {
                getLoginmetrologist()
            }
        }
    }

    private fun isValidation(): Boolean {
        if (binding.etEmailMetrologist.text.isEmpty()) {
            Toast.makeText(this, "Please Enter Email ID", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.etPasswordSignIn.text.isEmpty()) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
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

    private fun getLoginmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val etEmail: String = binding.etEmailMetrologist.text.toString()
        val etpassword: String = binding.etPasswordSignIn.text.toString()
        accountViewModelMetrologist?.getLoginmetrologist(etEmail, etpassword, "3","1",device_token)
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    // binding!!.checkBox.isChecked = false

                    if(it.status.toString().equals("2"))
                    {
                        var intent = Intent(this, MetrologistVerificationAccountActivity::class.java)
                        intent.putExtra("email", etEmail)
                        startActivity(intent)
                    }
                    else {
                        if (it.data!!.selectButterfly.toString().equals("null")) {

                            var a=0
                            CommonMethod.getInstance(this).savePreference(AppConstant.Key_ID_MetrologistButterfly,"0")

                            var intent = Intent(this, MetrologistButterFlySpeicesActivity::class.java)
                            intent.putExtra("checkStatus",a.toString())
                            startActivity(intent)
                        } else {
                            val inflater: LayoutInflater = layoutInflater
                            val layout: View = inflater.inflate(R.layout.custom_toast, null)
                            text= layout.findViewById(R.id.toast_text)
                            text.text = it?.message
                            with (Toast(applicationContext)) {
                                duration = Toast.LENGTH_LONG
                                view = layout
                                show()
                            }
                            //Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                            CommonMethod.getInstance(this)
                                .savePreference(AppConstant.Key_ApplicationId,"2")
                            CommonMethod.getInstance(this)
                                .savePreference(AppConstant.KEY_loginStatues, true)
                            CommonMethod.getInstance(this)
                                .savePreference(AppConstant.KEY_User_idMetrologist, it.data?.id.toString())
                            CommonMethod.getInstance(this)
                                .savePreference(AppConstant.KEY_token_Metrologist, it.token!!)
                            var intent = Intent(this, MetrilogistHomeActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                    }

                } else {
                    val inflater: LayoutInflater = layoutInflater
                    val layout: View = inflater.inflate(R.layout.custom_toast, null)
                    text= layout.findViewById(R.id.toast_text)
                    text.text = it?.message
                    with (Toast(applicationContext)) {
                        duration = Toast.LENGTH_LONG
                        view = layout
                        show()
                    }
                    //Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }


    }
}