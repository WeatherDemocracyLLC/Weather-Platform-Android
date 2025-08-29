package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.SelectOptionActivity
import com.webmobrilweatherapp.databinding.ActivityMyAccountProfileMetrologist2Binding

class MyAccountProfileMetrologistActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyAccountProfileMetrologist2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_my_account_profile_metrologist2)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        binding.logoutmetrologist.setOnClickListener {
            showDialog()
        }
    }

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
            CommonMethod.getInstance(this).savePreference(AppConstant.KEY_loginStatues, false)
        }
        alertDialogBuilder.setNegativeButton(
            "No"
        ) { dialog, which -> dialog.dismiss() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}