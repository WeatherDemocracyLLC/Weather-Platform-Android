package com.webmobrilweatherapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityEditProfileBinding
import com.webmobrilweatherapp.function.MySingleton

class EditProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditProfileBinding
    var accountViewModel: AccountViewModel? = null
    private var usertype = "0"
    private var userid = 0
    protected var locationManager: LocationManager? = null
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    var checkGPS = false
    var checkNetwork = false
    var canGetLocation = false
    var loc: Location? = null
    var names = ""
    var email = ""
    var password = ""
    var ltitute = 0
    var city: String = "0"
    var state: String = "0"
    var longituted = 0
    var zipCode = "0"
    var country = "0"
    var latitude = 0.0
    var longitude =0.0
    private val geocoder: Geocoder? = null
    private var mRequestQueue: RequestQueue? = null


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        MySingleton.handleTheme(this, usertype)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        getuserprofile()
        val view = binding.root
        binding.emailId.isEnabled = false
        setContentView(view)
        binding.imgeditback.setOnClickListener {
            onBackPressed()
        }
        binding.btnupdateprofile.setOnClickListener {
            if (isValidation()) {
                getUpdateprifile()

            }
        }
    }

    private fun isValidation(): Boolean {
        if (binding.name.text.isNullOrEmpty()) {
            binding.name.requestFocus()
            showToastMessage("Enter Name")
            return false
        }
        else if (binding.mobileNo.text.isNullOrEmpty()) {
            binding.mobileNo.requestFocus()
            showToastMessage("Enter mobile Number")
            return false
        }


        else if(binding.mobileNo.text.length < 6 || binding.mobileNo.text.length > 15)
        {   binding.mobileNo.requestFocus()
            showToastMessage("Phone number must be of minimum 6 digits and maximum 15 digits.")
            return false
        }

        return true

    }
    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }


    private fun getUpdateprifile() {


        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val name: String = binding.name.text.toString()
        val MobileNO: String = binding.mobileNo.text.toString()

        val Email: String = binding.emailId.text.toString()
        val City: String = binding.messageField.text.toString()


        accountViewModel?.getupdateprofile(
            name,
            MobileNO,
            Email,
            City,
            "Bearer " + CommonMethod.getInstance(this).getPreference(AppConstant.KEY_token)
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    //Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_LONG).show()
                    val i = Intent(this, ViewProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }


    private fun getuserprofile() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getuserprofile(
            userid.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    binding.emailId.setText(it.data?.email.toString())
                    binding.messageField.setText(it.data!!.city)
                    binding.name.setText(it.data!!.name)

                    if(it.data!!.phone==null)
                    {

                    }
                    else{
                        binding.mobileNo.setText(it.data!!.phone.toString())

                    }

                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }                }
            }
    }
}
