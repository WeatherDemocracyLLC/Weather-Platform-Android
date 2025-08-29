package com.webmobrilweatherapp.activities.metrologistactivity

import android.Manifest
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.activities.fragment.*
import com.webmobrilweatherapp.databinding.ActivityMetrilogistHomeBinding
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.webmobrilweatherapp.viewmodel.webconfig.Utils
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.webmobrilweatherapp.utilise.NotificationUtils
import java.util.*


class MetrilogistHomeActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMetrilogistHomeBinding
    var fragmentCurrent: Fragment? = null
    var openDrawer: String = "0"
    var status = false
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
    var longitude = 0.0
    private val geocoder: Geocoder? = null
    var detailss ="0"
    var lc="0"
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrilogist_home)
        binding.layoutContent.navView.setOnNavigationItemSelectedListener(this)
        binding.layoutContent.navView.setItemIconTintList(null)
        val window: Window = this.getWindow()
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        lc=CommonMethod.getInstance(this).getPreference(AppConstant.lc)

        if(lc!=null)
        {
            binding.layoutContent.Homemetrologist.txtLocation.setText(lc)

        }
        detailss= getIntent().getExtras()?.getString("details").toString()

        //getLastLocation()
        if (detailss.equals("1")) {


            val fragmenttent: Fragment = ProfileFragmentmetrologist()
            binding.layoutContent.Homemetrologist.Toolbarmetrologist.visibility = GONE
            replaceFragment(fragmenttent)
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragments, fragmenttent).addToBackStack(null).commit()

        }
        else if(detailss.equals("2"))
        {
            val fragmenttent: Fragment = ProfileFragmentmetrologist()
            binding.layoutContent.Homemetrologist.Toolbarmetrologist.visibility = GONE
            replaceFragment(fragmenttent)
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragments, fragmenttent).addToBackStack(null).commit()
        }
        else {
            getLocation()
            if(lc!=null)
            {
                binding.layoutContent.Homemetrologist.txtLocation.setText(lc)

            }
            UpdateHomeFragment(HomeFragmentsmetrologist(), "Home")


            /*  PushNotifications.start(getApplicationContext(), "29fb2b33-4975-4baa-986e-2b323462775f");
        PushNotifications.addDeviceInterest("hello");*/
            getuserprofileMetrologist()

            val ai: ApplicationInfo = applicationContext.packageManager
                .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
            val value = ai.metaData["AIzaSyCFQTLSqMaiV4k2KTNcilh6V3tjUwqFrSs"]
            //  val apiKey = value.toString()
            //val apiKey = "AIzaSyBlCaysRN5XHvtLzJIyRkjaiXEKQbOl1c8"
            val apiKey = "AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM"

          //  Log.e("apiKey", apiKey)

            // Initializing the Places API
            // with the help of our API_KEY
            if (!Places.isInitialized()) {
                Places.initialize(applicationContext, apiKey)

            }

        }
        binding.layoutContent.Homemetrologist.txtLocation.setOnClickListener { v ->
            val fields =
                Arrays.asList(
                    Place.Field.ID,
                    Place.Field.ADDRESS,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG
                )

            // Start the autocomplete intent.
            val intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
        /* binding.layoutContent.Homemetrologist.txtLocation.setOnClickListener {
             val i = Intent(this, WeatherLocationMetrologistActivity::class.java)
             startActivity(i)
         }*/


        binding.layoutContent.Homemetrologist.imgnotification.setOnClickListener {
            val i = Intent(this, MetrologistNotificationActivity::class.java)
            startActivity(i)
        }
        binding.layoutContent.Homemetrologist.imghomemenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
            openDrawer = "1"
        }
        binding.layoutDrawer.imgcrossiconprofile.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.layoutDrawer.editprofile.setOnClickListener {
            val i = Intent(this, MetrologistViewProfileActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.notification.setOnClickListener {
            val i = Intent(this, MetrologistNotificationActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.alert.setOnClickListener {
            val i = Intent(this, MetrologistAlertActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.Metrologiste.setOnClickListener {
            val i = Intent(this, MeterologistActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.ForecastChallenge.setOnClickListener {
            val i = Intent(this, MetrologistForecastChallengeActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.WeatherMayor.setOnClickListener {
            val i = Intent(this, MetrologistWeatherMayorActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.layoutDrawer.textPrivacyPolicy.setOnClickListener {
            /* val i = Intent(this, PrivacyPolicyActivity::class.java)
             startActivity(i)*/
           /* val uri = Uri.parse(ApiConstants.privacyPolicy)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)*/
            val i = Intent(this, WerViewActivity::class.java)
            i.putExtra("webviewurl","2")
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.TermsConditions.setOnClickListener {
            /* val i = Intent(this, TermsandConditionActivity::class.java)
             startActivity(i)*/
           /* val uri = Uri.parse(ApiConstants.TermsCondition)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)*/
            val i = Intent(this, WerViewActivity::class.java)
            i.putExtra("webviewurl","1")
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.AboutUs.setOnClickListener {
            /*  val i = Intent(this, MetrologistAboutUsActivity::class.java)
              startActivity(i)*/
           /* val uri = Uri.parse(ApiConstants.AboutUs)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)*/
            val i = Intent(this, WerViewActivity::class.java)
            i.putExtra("webviewurl","3")
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.textContactUs.setOnClickListener {
            val i = Intent(this, MetrologistContactActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.logoutmetrologist.setOnClickListener {
            showDialog()
        }

        binding.layoutDrawer.selectButterfly.setOnClickListener {
            var a=1
            val i = Intent(this, MetrologistButterFlySpeicesActivity::class.java)
            i.putExtra("checkStatus",a.toString())
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.layoutMyAccountMetrologist.setOnClickListener {
            if (!status) {
                binding.layoutDrawer.LayoutItemMyAccountMterologist.visibility = VISIBLE
                binding.layoutDrawer.expandIcon1.visibility = GONE
                binding.layoutDrawer.LayoutItemContentMterologist.visibility = GONE
                binding.layoutDrawer.LayoutItemHelpMterologist.visibility = GONE
                binding.layoutDrawer.downarrows.visibility = VISIBLE
                status = true
            } else {
                binding.layoutDrawer.LayoutItemMyAccountMterologist.visibility = GONE
                binding.layoutDrawer.LayoutItemContentMterologist.visibility = GONE
                binding.layoutDrawer.LayoutItemHelpMterologist.visibility = GONE
                binding.layoutDrawer.expandIcon1.visibility = VISIBLE
                binding.layoutDrawer.downarrows.visibility = GONE
                status = false
            }
        }
        binding.layoutDrawer.layoutContentMterologist.setOnClickListener {
            if (!status) {
                binding.layoutDrawer.LayoutItemMyAccountMterologist.visibility = GONE
                binding.layoutDrawer.LayoutItemContentMterologist.visibility = VISIBLE
                binding.layoutDrawer.LayoutItemHelpMterologist.visibility = GONE
                binding.layoutDrawer.expandIcon2.visibility = GONE
                binding.layoutDrawer.downarrowes.visibility = VISIBLE
                status = true
            } else {
                binding.layoutDrawer.LayoutItemMyAccountMterologist.visibility = GONE
                binding.layoutDrawer.LayoutItemContentMterologist.visibility = GONE
                binding.layoutDrawer.LayoutItemHelpMterologist.visibility = GONE
                binding.layoutDrawer.expandIcon2.visibility = VISIBLE
                binding.layoutDrawer.downarrowes.visibility = GONE
                status = false
            }
        }
        binding.layoutDrawer.layoutHelpMterologist.setOnClickListener {
            if (!status) {
                binding.layoutDrawer.LayoutItemMyAccountMterologist.visibility = GONE
                binding.layoutDrawer.LayoutItemContentMterologist.visibility = GONE
                binding.layoutDrawer.LayoutItemHelpMterologist.visibility = VISIBLE
                binding.layoutDrawer.expandIcon3.visibility = GONE
                binding.layoutDrawer.downarrowess.visibility = VISIBLE
                status = true


            } else {
                binding.layoutDrawer.LayoutItemMyAccountMterologist.visibility = GONE
                binding.layoutDrawer.LayoutItemContentMterologist.visibility = GONE
                binding.layoutDrawer.LayoutItemHelpMterologist.visibility = GONE
                binding.layoutDrawer.expandIcon3.visibility = VISIBLE
                binding.layoutDrawer.downarrowess.visibility = GONE
                status = false

            }
        }



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homemetrologist -> {
                val fragmentt: Fragment = HomeFragmentsmetrologist()
                binding.layoutContent.Homemetrologist.Toolbarmetrologist.visibility = VISIBLE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragments, fragmentt)
                    .commit()
            }
            R.id.newmetrologist -> {
                val fragmentty: Fragment = NewFragmentmetrologist()
                binding.layoutContent.Homemetrologist.Toolbarmetrologist.visibility = GONE
                replaceFragment(fragmentty)
            }
            R.id.notificationsselectedmetrologist -> {
                val fragmenttsh: Fragment = AddgaleryFragmentmetrologist()
                binding.layoutContent.Homemetrologist.Toolbarmetrologist.visibility = GONE
                replaceFragment(fragmenttsh)
            }
            R.id.searchmetrologist -> {
                val fragmenttshs: Fragment = SearchFragmentmetrologist()
                binding.layoutContent.Homemetrologist.Toolbarmetrologist.visibility = GONE
                replaceFragment(fragmenttshs)
            }
            R.id.profilemetrologist -> {
                val fragmenttent: Fragment = ProfileFragmentmetrologist()
                binding.layoutContent.Homemetrologist.Toolbarmetrologist.visibility = GONE
                replaceFragment(fragmenttent)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragments, fragmenttent).addToBackStack(null).commit()
            }

        }
        return false
    }

    private fun UpdateHomeFragment(fragment: Fragment, title: String) {
        if (Utils.checkConnection(this)) {
        } else {
            Toast.makeText(this, "Please Check Internet", Toast.LENGTH_SHORT).show()
            ProgressD.hideProgressDialog()
        }
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.nav_host_fragments, fragment)
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (openDrawer.equals("1")) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            openDrawer = "0"
        } else {
            val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragments)
            if (fragment is HomeFragmentsmetrologist) {
                AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Yes"
                    ) { dialog, id -> finishAffinity() }
                    .setNegativeButton("No", null)
                    .show()
            } else if (fragment is NewFragmentmetrologist || fragment is AddgaleryFragmentmetrologist || fragment is SearchFragmentmetrologist || fragment is ProfileFragmentmetrologist) {
                val fragmentt: Fragment = HomeFragmentsmetrologist()
                binding.layoutContent.Homemetrologist.Toolbarmetrologist.visibility = VISIBLE
                addFragment(fragmentt)
            } else {
                super.onBackPressed()

            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        fragmentCurrent = fragment
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragments, fragment).commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentCurrent = fragment
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragments, fragment)
            .addToBackStack(null).commit()
    }

    fun updateBottomBar(i: Int) {
        binding.layoutContent.navView.menu.getItem(i).isChecked = true
    }


    private fun showDialog() {
        val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure you want to Logout ? ")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton(
            "Yes"
        ) { arg0, arg1 ->
            getlogoutsmetrologist()
        }
        alertDialogBuilder.setNegativeButton(
            "No"
        ) { dialog, which -> dialog.dismiss() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private fun getuserprofileMetrologist() {
        //  ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        useridMetrologist =
            CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getuserprofileMetrologist(
            useridMetrologist, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                //ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    binding.layoutDrawer.accountName.setText(it.data?.name)
                    binding.layoutDrawer.textEmialId.setText(it.data?.email)
                    CommonMethod.getInstance(this).savePreference(AppConstant.Key_ID_MetrologistButterfly,it.data!!.selectButterfly.toString())

                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL + it.data?.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.layoutDrawer.circleImageview)
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }


    /* @SuppressLint("MissingPermission")
     private fun getLastLocation() {
         mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
         mFusedLocationClient.lastLocation
             .addOnSuccessListener { location: Location? ->
                 if (location != null) {
                     Log.e("TAG", "location is not null")
                     Log.d("lat", location.latitude.toString())
                     Log.d("long", location.longitude.toString())
                     getLocationAddress(location.latitude, location.longitude)
                     var letlong = location.latitude + location.longitude
                     var let = location.latitude
                     var longi = location.longitude
                     // binding.mainLayout.txtLocation.setText(""+let)
                 } else {
                     Log.e("TAG", "location is null")
                 }
             }
     }

     private fun getLocationAddress(latitude: Double, longitude: Double) {
         try {
             val geocoder = Geocoder(this, Locale.getDefault())
             val addressesList: List<Address> =
                 geocoder.getFromLocation(latitude, longitude, 1)
             val location1 = addressesList[0].getAddressLine(0)
             var countryname = addressesList[0].countryName
             var city = addressesList[0].locality
             //m binding.layoutContent.Homemetrologist.txtLocation.setText(countryname+","+city)
             binding.layoutContent.Homemetrologist.txtLocation.setText(city + "," + countryname)
             CommonUtil.showMessage(this, location1)
         } catch (e: Exception) {
             e.printStackTrace()
         }
     }*/
    override fun onResume() {
        getuserprofileMetrologist()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            notificationReceiver,
            IntentFilter("com.webmobrilweatherapp.NOTIFICATION_COUNT_UPDATED")
        )

        val prefs = getSharedPreferences("notification_prefs", MODE_PRIVATE)
        var count = prefs.getInt("notification_count", 0)

        Log.e("pref count", count.toString())
        val badgeTextView = findViewById<TextView>(R.id.tv_notification_count_metero)

        if (count > 0) {
            badgeTextView.visibility = VISIBLE
            badgeTextView.text = count.toString()
        } else {
            badgeTextView.visibility = GONE
        }
        super.onResume()
    }

    private val notificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNotificationCount()
        }
    }
    private fun updateNotificationCount() {
        val badgeTextView = findViewById<TextView>(R.id.tv_notification_count_metero)
        val count = NotificationUtils.getNotificationCountMeterological(this)

        Log.e("Notifcation count", count.toString())

        if (count > 0) {
            badgeTextView.visibility = VISIBLE
            badgeTextView.text = count.toString()
        } else {
            badgeTextView.visibility = GONE
        }
    }


    private fun getlogoutsmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        useridMetrologist =
            CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getlogoutsmetrologist(
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    CommonMethod.getInstance(this).savePreference(AppConstant.Key_ApplicationId,"0")

                    CommonMethod.getInstance(this)
                        .getPreference(AppConstant.KEY_loginStatues, false)
                    val intent = Intent(this, SelectOptionActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finishAffinity()

                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()


                } else if (it!!.code == 401) {
                    val intent = Intent(this, SelectOptionActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finishAffinity()
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun getLocation() {
        try {
            locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
            // get GPS status
            if (locationManager != null) {
                checkGPS = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            }
            checkNetwork = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!checkGPS && !checkNetwork) {
                Toast.makeText(this, "No Service Provider is available", Toast.LENGTH_SHORT).show()
            } else {
                this.canGetLocation = true
                if (checkGPS) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    /*  locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);*/Log.e(
                        "locationManager",
                        "locationManager$locationManager"
                    )
                    if (locationManager != null) {
                       // Log.e("locationManager", "locationManager")
                        loc = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        if (loc != null) {
                            val latitude: String = loc!!.getLatitude().toString()
                            val longitude: String = loc!!.getLongitude().toString()
                            getalllocation(latitude,longitude)
                                //getCompleteAddress(latitude, longitude)
                            //CommonMethod.getInstance(this).savePreference(AppConstant.locationMetrologist, latitude.toString())
                            //CommonMethod.getInstance(this).savePreference(AppConstant.LongitutedMetrologist, longitude.toString())
                          //  Log.e("latitude", "==$latitude")
                           // Log.e("longitude", "==$longitude")
                        } else {
                            Toast.makeText(this, "Please enter valid city", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                /*if (checkNetwork) {


                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }

                    if (loc != null) {
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();
                    }
                }*/
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
    public static class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            //call ended by either party
            //  Toast.makeText(MainActivity.this, "Call Ended", Toast.LENGTH_SHORT).show();
            Log.e("call-", "ended");
        }

        @Override
        public void onCallEstablished(Call establishedCall) {
            //  Toast.makeText(MainActivity.this, "Call Pick up", Toast.LENGTH_SHORT).show();
            Log.e("call-", "start");
        }

        @Override
        public void onCallProgressing(Call progressingCall) {
            //call is ringing
            //  Toast.makeText(MainActivity.this, "Call Ringing", Toast.LENGTH_SHORT).show();
            Log.e("call- ", "in process");
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            Log.e("call- ", "send push noti");
        }
    }
*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                var place: Place? = null
                if (data != null) {
                    place = Autocomplete.getPlaceFromIntent(data)
                  //  Log.e("TAG", "Place: " + place.name + ", " + place.id)
                    latitude = Objects.requireNonNull(place.latLng).latitude
                    longitude = place.latLng.longitude

                    getalllocation(latitude.toString(),longitude.toString())
                    //getCompleteAddress(latitude, longitude)
                    //binding.layoutContent.Homemetrologist.txtLocation.setText(place.name)
                   // CommonMethod.getInstance(this).savePreference(AppConstant.cityName, place.name.toString())

                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data)
                assert(status.statusMessage != null)
               // Log.e("TAG", status.statusMessage!!)
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun getCompleteAddress(latitude: Double, longitude: Double): String {
        val address = ""
        var addresses: List<Address> = ArrayList()
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            )!! // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            val addressLine =
                addresses[0].getAddressLine(0)
            // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            zipCode = addresses[0].postalCode.toInt().toString()
            country = addresses[0].countryName
            state = addresses[0].adminArea
            city = addresses[0].locality
          //  Log.e("zip Code", zipCode.toString())
            //m binding.layoutContent.Homemetrologist.txtLocation.setText(countryname+","+city)
           // binding.layoutContent.Homemetrologist.txtLocation.setText(city + "," + country)
           // Log.e("address", addressLine)

        } catch (e: Exception) {
            //Toast.makeText(this, "Please enter valid city", Toast.LENGTH_SHORT).show()
        }
        return address
    }

    private fun getCountryCityCode(latitude: Double, longitude: Double) {
        try {
            val addressList: MutableList<Address>? = geocoder!!.getFromLocation(latitude, longitude, 1)
            if (addressList != null && addressList.size > 0) {
                val locality = addressList[0].getAddressLine(0)
                val country = addressList[0].countryName
                val city = addressList[0].locality
                val state = addressList[0].adminArea
                val postalCode = addressList[0].postalCode
                val street = addressList[0].featureName + ", " + addressList[0].subLocality
                if (!locality.isEmpty() && !country.isEmpty()) {
                    runOnUiThread {
                        if (!TextUtils.isEmpty(city)) {
                            /*     PrefManager.getInstance(MainActivity.this).setLatitude(String.valueOf(latitude));
                             PrefManager.getInstance(MainActivity.this).setLongitude(String.valueOf(longitude));*/
                            /* PrefManager.getInstance(this@MainActivity).setLocation("$street  $city $state $postalCode")
                             loadHome("0")*/
                        }
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    private fun getalllocation(latitude: String, longitude: String) {
        accountViewModelMetrologist?.getalllocationmetrologist(
            (latitude+","+longitude),"AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM"
        )?.observe(this) {
            //ProgressD.hideProgressDialog()
            if (it != null) {
                if (it.plusCode!!.compoundCode!=null){
                    Log.d("TAG", "ljgdfjgdfjg: "+latitude+longitude)
                    CommonMethod.getInstance(this).savePreference(AppConstant.location, latitude)
                    CommonMethod.getInstance(this).savePreference(AppConstant.Longituted, longitude)
                    var date = it.plusCode.compoundCode
                    val upToNCharacters: String = date!!.substring(8, Math.min(date.length,40))
                    CommonMethod.getInstance(this).savePreference(AppConstant.lc, upToNCharacters)

                    binding.layoutContent.Homemetrologist.txtLocation.setText(upToNCharacters)
                    UpdateHomeFragment(HomeFragmentsmetrologist(), "Home")
                }else{
                    Toast.makeText(this,"Please Enter Valid City Name",Toast.LENGTH_LONG).show()
                }

            } else {
                //Toast.makeText(context, , Toast.LENGTH_LONG).show()
            }

        }
        // Get user sign up response
    }
}
