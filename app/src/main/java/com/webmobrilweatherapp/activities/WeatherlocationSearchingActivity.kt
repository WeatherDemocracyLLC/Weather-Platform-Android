package com.webmobrilweatherapp.activities

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityWeatherlocationSearchingBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.*
import kotlin.collections.ArrayList

class WeatherlocationSearchingActivity : AppCompatActivity() {
    lateinit var binding:ActivityWeatherlocationSearchingBinding
    var  latituted:Int=0
    var  longituted:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_weatherlocation_searching)
        binding.tvCurrentLocation.setOnClickListener {
            var intent = Intent(applicationContext,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.imgcross.setOnClickListener {
            onBackPressed()
        }
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["AIzaSyCFQTLSqMaiV4k2KTNcilh6V3tjUwqFrSs"]
        //  val apiKey = value.toString()
        //val apiKey = "AIzaSyBlCaysRN5XHvtLzJIyRkjaiXEKQbOl1c8"
        val apiKey = "AIzaSyCFQTLSqMaiV4k2KTNcilh6V3tjUwqFrSs"

        Log.e("apiKey", apiKey)

        // Initializing the Places API
        // with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }




        // Initialize Autocomplete Fragments
        // from the main activity layout file
        val autocompleteSupportFragment1 = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment1) as AutocompleteSupportFragment?

        // Information that we wish to fetch after typing
        // the location and clicking on one of the options
        autocompleteSupportFragment1!!.setPlaceFields(
            listOf(

                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.PHONE_NUMBER,
                Place.Field.LAT_LNG,
                Place.Field.OPENING_HOURS,
                Place.Field.RATING,
                Place.Field.USER_RATINGS_TOTAL
            )
        )
        // Display the fetched information after clicking on one of the options
        autocompleteSupportFragment1.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onPlaceSelected(place: Place) {

                // Text view where we will
                // append the information that we fetch
                val textView = findViewById<TextView>(R.id.tv1)

                // Information about the place
                val name = place.name
                val address = place.address
                val plusecode = place.plusCode
                // val phone = place.phoneNumber.toString()
                val latlng = place.latLng
                val latitudes = latlng?.latitude
                val longitudes = latlng?.longitude
                if (latitudes != null) {
                    if (longitudes != null) {
                        getCompleteAddress(latitudes, longitudes)
                    }
                }
                var intent = Intent(applicationContext,HomeActivity::class.java)
                CommonMethod.getInstance(this@WeatherlocationSearchingActivity).savePreference(AppConstant.KEY_LATITITED_CHANAGE_CITY_USER,
                    latitudes!!)
                CommonMethod.getInstance(this@WeatherlocationSearchingActivity).savePreference(AppConstant.KEY_LONGITUTED_CHANAGE_CITY_USER,
                    longitudes!!)
                startActivity(intent)
                finish()

                onBackPressed()




                //  onBackPressed()

                Log.e("name",name)

                val isOpenStatus : String = if(place.isOpen == true){
                    "Open"
                } else {
                    "Closed"
                }

                val rating = place.rating
                val userRatings = place.userRatingsTotal

                /* textView.text = "Name: $name \nAddress: $address \nPhone Number: $phone \n" +
                         "Latitude, Longitude: $latitude , $longitude \nIs open: $isOpenStatus \n" +
                         "Rating: $rating \nUser ratings: $userRatings"
                 */


                /* textView.text = "Name: $name \nAddress: $address" +
                         "Latitude, Longitude: $latitude , $longitude \nIs open: $isOpenStatus \n"*/
            }



            override fun onError(status: Status) {
                Toast.makeText(applicationContext,"Some error occurred", Toast.LENGTH_SHORT).show()
            }
        })



    }

    /*  private fun requestLocationPermission() {
          Dexter.withContext(this)
              .withPermissions(
                  Manifest.permission.ACCESS_COARSE_LOCATION,
                  Manifest.permission.ACCESS_FINE_LOCATION
              ).withListener(object : MultiplePermissionsListener {
                  override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                      if (report.areAllPermissionsGranted()) {
                          fetchLocation()
                          permissionflag = true
                      } else if (!permissionflag) {
                          *//* Toast.makeText(this@LoginActivity, "Permission Denied", Toast.LENGTH_LONG)
                             .show()

                         finishAffinity()*//*

                        *//*    PrefManager.getInstance(this@LoginActivity)
                                .savePreference(AppConstant.KEY_LATITUDE, 0.0)
                            PrefManager.getInstance(this@LoginActivity)
                                .savePreference(AppConstant.KEY_LONGITUDE, 0.0)*//*
                    }
                    if (report.isAnyPermissionPermanentlyDenied) {
//                        // show alert dialog navigating to Settings
                        showPermissionDeniedDialog()

                    }

                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {

                    token?.continuePermissionRequest()

                }


            })
            .onSameThread().check()
    }

    private fun showPermissionDeniedDialog() {

        val alert11 = android.app.AlertDialog.Builder(this)
            .setTitle(R.string.locationPermissionTitle)
            .setMessage(R.string.locationPermissionNeedded)
            .setPositiveButton("ok") { dialogInterface, i ->
                // send to app settings if permission is denied permanently
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", "com.healthservice", null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->


                *//*PrefManager.getInstance(this@LoginActivity)
                    .savePreference(AppConstant.KEY_LATITUDE, 0.0)
                PrefManager.getInstance(this@LoginActivity)
                    .savePreference(AppConstant.KEY_LONGITUDE, 0.0)

*//*

                //finishAffinity()
            })
            .show()

        val mbutton: Button = alert11.getButton(DialogInterface.BUTTON_NEGATIVE)
        mbutton.setTextColor(Color.BLACK)

        val mbutton1: Button = alert11.getButton(DialogInterface.BUTTON_POSITIVE)
        mbutton1.setTextColor(Color.BLACK)

    }

    private fun fetchLocation() {
        @SuppressLint("MissingPermission") val task: Task<Location> =
            fusedLocationProviderClient!!.getLastLocation()
        task.addOnSuccessListener(object : OnSuccessListener<Location?> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onSuccess(location: Location?) {
                if (location != null) {
                    currentLocation = location
                    Log.i("TAG", "onSuccess: "+currentLocation?.latitude.toString())
                    Log.i("TAG", "onSuccess: "+currentLocation?.longitude.toString())
                    getCompleteAddress(currentLocation!!.latitude, currentLocation!!.longitude)
                    var intent = Intent(applicationContext, SignUpActivity::class.java)
                    startActivity(intent)
                    *//*getDashboardOpportunities()
                     getCurrentData()//health card
                     getDashboardBanners()*//*

                    // getDashboardOpportunities()

                }
            }
        })
    }
*/
    private fun getCompleteAddress(la: Double, lang: Double): String {
        val address = ""
        var addresses: List<Address> = ArrayList()
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            addresses = geocoder.getFromLocation(
                la,
                lang,
                1
            )!! // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            val addressLine =
                addresses[0].getAddressLine(0)
            // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            latituted= addresses[0].latitude.toInt()
            longituted= addresses[0].longitude.toInt()



            Log.e("address", addressLine)

        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        return address
    }
}