package com.webmobrilweatherapp.activities


import android.Manifest
 import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.webmobrilweatherapp.R
import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.function.MySingleton
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.webmobrilweatherapp.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityLocationBinding
    private val LOCATION_PERMISSION_REQUEST_CODE = 102
    private val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124

    private val CAMERA_PERMISSION_REQUEST_CODE = 104
    private val GALLERY_PERMISSION_REQUEST_CODE = 105
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 106
    private val requirePermissions: ArrayList<String> = ArrayList()
    private val permissionsToRequest: ArrayList<String> = ArrayList()
    private val permissionsRejected: ArrayList<String> = ArrayList()
    private val requirePermissions13: ArrayList<String> = ArrayList()
    private val permissionsToRequest13: ArrayList<String> = ArrayList()
    private val permissionsRejected13: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MySingleton.handleTheme(this, "2")
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialization()
        listener()
    }

    private fun initialization() {
        Glide.with(this)
            .load(R.drawable.locationgift)
            .into(binding.imglocation)
    }

    private fun listener() {
        binding.btnallowlocation.setOnClickListener(this)
        binding.textskip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnallowlocation -> {
               // checkAndRequestPermissions()
                askPermissions()
            }
            R.id.textskip -> {
                proceedToNextActivity()
            }
        }
    }

    private fun askPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 and above
            requirePermissions13.clear()
            permissionsToRequest13.clear()

            requirePermissions13.add(Manifest.permission.CAMERA)
            requirePermissions13.add(Manifest.permission.READ_MEDIA_IMAGES)
            requirePermissions13.add(Manifest.permission.ACCESS_FINE_LOCATION)
            requirePermissions13.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            requirePermissions13.add(Manifest.permission.POST_NOTIFICATIONS)

            for (permission in requirePermissions13) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest13.add(permission)
                }
            }

            if (permissionsToRequest13.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest13.toTypedArray(),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
                )
            } else {
                proceedToNextActivity()
            }

        } else {
            // Below Android 13
            requirePermissions.clear()
            permissionsToRequest.clear()

            requirePermissions.add(Manifest.permission.CAMERA)
            requirePermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            requirePermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            requirePermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)

            for (permission in requirePermissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission)
                }
            }

            if (permissionsToRequest.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toTypedArray(),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
                )
            } else {
                proceedToNextActivity()
            }
        }
    }



    /*
        private fun checkAndRequestPermissions() {
            val permissionsToRequest = mutableListOf<String>()

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(Manifest.permission.CAMERA)
                }
                if ( ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED

                ) {
                permissionsToRequest.add(Manifest.permission.READ_MEDIA_IMAGES)
                permissionsToRequest.add(Manifest.permission.READ_MEDIA_AUDIO)
                permissionsToRequest.add(Manifest.permission.READ_MEDIA_VIDEO)
                permissionsToRequest.add(Manifest.permission.CAMERA)
            }

            if (permissionsToRequest.isNotEmpty()) {
                ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), LOCATION_PERMISSION_REQUEST_CODE)
            } else {
                // All permissions are already granted
                proceedToNextActivity()
            }
        }
    */

    private fun proceedToNextActivity() {
        SessionManager.setPreference(this, SessionManager.KEY_IS_VERIFIED, "1")
        val intent = Intent(this, SelectOptionActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            val deniedPermissions = mutableListOf<String>()
            val criticalPermissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            permissions.forEachIndexed { index, permission ->
                if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permission)
                }
            }

            if (deniedPermissions.isEmpty()) {
                // All permissions granted
                proceedToNextActivity()
            } else {
                // Notification if POST_NOTIFICATIONS is denied (optional)
                if (deniedPermissions.contains(Manifest.permission.POST_NOTIFICATIONS)) {
                    Toast.makeText(
                        this,
                        "Notification permission denied. You won't receive alerts.",
                        Toast.LENGTH_SHORT
                    ).show()
                    deniedPermissions.remove(Manifest.permission.POST_NOTIFICATIONS)
                }

                // If no critical permissions denied, still proceed
                if (deniedPermissions.none { it in criticalPermissions }) {
                    proceedToNextActivity()
                } else {
                    Toast.makeText(
                        this,
                        "Permissions denied: ${deniedPermissions.joinToString(", ")}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}




