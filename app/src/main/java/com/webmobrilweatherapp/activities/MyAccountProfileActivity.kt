package com.webmobrilweatherapp.activities

import com.webmobrilweatherapp.R
import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.function.MySingleton
import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.webmobrilweatherapp.databinding.ActivityMyAccountProfileBinding

class MyAccountProfileActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMyAccountProfileBinding
    private var usertype = "0"
    var clicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)

        binding = ActivityMyAccountProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialization()
        listener()
        /*  binding.cardView.setOnClickListener {
              if (!clicked){
                  binding.cardView2.visibility= View.VISIBLE
                  clicked=true
              }else{
                  binding.cardView.isClickable=false
                  binding.cardView2.visibility=View.GONE
                  clicked=false

              }
          }*/
    }
    
    private fun listener() {
        binding.logout.setOnClickListener(this)
        binding.Metrologiste.setOnClickListener(this)
        binding.AboutUs.setOnClickListener(this)
        binding.editprofile.setOnClickListener(this)
        binding.WeatherMayor.setOnClickListener(this)
        binding.weathervote.setOnClickListener(this)
        binding.notification.setOnClickListener(this)
        binding.textPrivacyPolicy.setOnClickListener(this)
        binding.TermsConditions.setOnClickListener(this)
        binding.imgcrossiconprofile.setOnClickListener(this)
        binding.textContactUs.setOnClickListener(this)
        binding.cardView.setOnClickListener(this)
        binding.cardView3.setOnClickListener(this)
        binding.cardView5.setOnClickListener(this)
        binding.circleImageview.setOnClickListener(this)

    }

    private fun initialization() {

        if (usertype == "3") {
            binding.toolbar.setBackgroundResource(R.color.metroappback)
            binding.weathervote.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.logout -> {
                showDialog()
            }

            R.id.Metrologiste -> {
                val i = Intent(this, WeatherMayorActivity::class.java)
                startActivity(i)
            }
            R.id.weathervote -> {

                val i = Intent(this, WeatherVoteActivity::class.java)
                startActivity(i)
            }
            R.id.AboutUs -> {
                val i = Intent(this, AboutUsActivity::class.java)
                startActivity(i)

            }
            R.id.editprofile -> {
                val i = Intent(this, ViewProfileActivity::class.java)
                startActivity(i)

            }

            R.id.notification -> {

                val i = Intent(this, NotificationActivity::class.java)
                startActivity(i)
            }

            R.id.textPrivacyPolicy -> {
                val i = Intent(this, PrivacyPolicyActivity::class.java)
                startActivity(i)
            }

            R.id.Terms_Conditions -> {
                val i = Intent(this, TermsandConditionActivity::class.java)
                startActivity(i)
            }
            R.id.imgcrossiconprofile -> {
                onBackPressed()
            }
            R.id.textContactUs -> {
                val i = Intent(this, ContactActivity::class.java)
                startActivity(i)
            }
            R.id.WeatherMayor -> {
                val i = Intent(this, VotingListActivity::class.java)
                startActivity(i)

            }
            R.id.circle_imageview -> {
                val i = Intent(this, ViewProfileActivity::class.java)
                startActivity(i)
            }

        }


    }

    /*private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure you want to Logout ?")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton("Yes") { arg0, arg1 ->
            startActivity(Intent(this, SelectOptionActivity::class.java))
            finishAffinity()
            CommonMethod.getInstance(this).savePreference(AppConstant.KEY_loginStatus,true)
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, which -> dialog.dismiss() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }*/
    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure you want to Logout ? ")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton(
            "Yes"
        ) { arg0, arg1 ->
            val intent = Intent(this, SelectOptionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finishAffinity()
            CommonMethod.getInstance(this).savePreference(AppConstant.KEY_loginStatus, false)
        }
        alertDialogBuilder.setNegativeButton(
            "No"
        ) { dialog, which -> dialog.dismiss() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}