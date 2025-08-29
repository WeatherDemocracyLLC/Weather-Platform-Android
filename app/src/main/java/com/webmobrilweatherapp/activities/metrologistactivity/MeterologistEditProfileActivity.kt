package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
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
import com.webmobrilweatherapp.databinding.ActivityMeterologistEditProfileBinding

class MeterologistEditProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityMeterologistEditProfileBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"
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
    var longitude= 0.0
    private val geocoder: Geocoder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meterologist_edit_profile)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        getuserprofileMetrologist()
        binding.etEmailMetologist.isEnabled = false
        binding.metrologistEditProfile.ImgBack.setOnClickListener {
            onBackPressed()
        }
        binding.metrologistEditProfile.txtLocation.setText("Edit Profile")
        binding.BtnupdateMetrologist.setOnClickListener {

            if (isValidation()) {
                getupdateprofileMetrologist()

            }
        }
    }

    private fun isValidation(): Boolean {
        if (binding.etEditmetero.text.isNullOrEmpty()) {
            binding.etEditmetero.requestFocus()
            showToastMessage("Enter Name")
            return false
        } else if (binding.etPhoneNOmetero.text.isNullOrEmpty()) {
            binding.etPhoneNOmetero.requestFocus()
            showToastMessage("Enter Phone NO")
            return false
        }else if (binding.etPhoneNOmetero.text.length<6) {
            binding.etPhoneNOmetero.requestFocus()
            showToastMessage("Phone number should be minimum 6 and maximum 15 digit.")
            return false
        }else if (binding.etPhoneNOmetero.text.length>15) {
            binding.etPhoneNOmetero.requestFocus()
            showToastMessage("Phone number should be minimum 6 and maximum 15 digit.")
            return false
        }
        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    private fun getupdateprofileMetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        val name: String = binding.etEditmetero.text.toString()
        val phone: String = binding.etPhoneNOmetero.text.toString()
        val email: String = binding.etEmailMetologist.text.toString()
        val city: String = binding.etSelectcityEditmetero.text.toString()
        accountViewModelMetrologist?.getupdateprofileMetrologist(
            name, phone, email, city, "Bearer " + CommonMethod.getInstance(this).getPreference(
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
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun getuserprofileMetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        useridMetrologist =
            CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getuserprofileMetrologist(
            useridMetrologist, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    binding.etEmailMetologist.setText(it.data?.email)
                    binding.etSelectcityEditmetero.setText(it.data!!.city)
                    binding.etEditmetero.setText(it.data!!.name)
                    if(it.data!!.phone==null)
                    {

                    }
                    else{
                        binding.etPhoneNOmetero.setText(it.data!!.phone.toString())
                    }
                    // Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                }

            }
    }

}