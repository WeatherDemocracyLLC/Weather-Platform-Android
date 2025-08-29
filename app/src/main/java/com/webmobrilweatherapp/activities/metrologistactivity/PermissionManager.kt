package com.webmobrilweatherapp.activities.metrologistactivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionManager constructor(
    var mcontext: Context,
    var mPermissionCallback: PermissionCallback,
    var permissionArray: ArrayList<String>
) {

    init {
        checkPermission(permissionArray)
    }

    private fun checkPermission(arrayCollection: ArrayList<String>) {
        Log.e("check per-","==")

        Dexter.withActivity(mcontext as Activity?)
            .withPermissions(arrayCollection)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    //check if all permission granted
                    when {
                        report!!.areAllPermissionsGranted() -> {
                            mPermissionCallback.allPermissionGranted(true)
                        }
                        report.isAnyPermissionPermanentlyDenied -> {
                            // show alert dialog navigating to Settings
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", mcontext.packageName, null)
                            intent.data = uri
                            mcontext.startActivity(intent)
                        }
                        else -> {
                            mPermissionCallback.allPermissionGranted()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            }).withErrorListener {
                Log.e("Error-","=="+ it.name)
                Toast.makeText(mcontext, "permission error", Toast.LENGTH_LONG).show()
                // mcontext.showToast("Permission Error !!")
            }.check()
    }
}