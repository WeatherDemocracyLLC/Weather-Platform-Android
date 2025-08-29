package com.webmobrilweatherapp.activities

import android.Manifest
import android.annotation.SuppressLint
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
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.metrologistactivity.WerViewActivity
import com.webmobrilweatherapp.databinding.ActivityHomeBinding
import com.webmobrilweatherapp.fragment.AddGalleryFragment
import com.webmobrilweatherapp.fragment.HomeFragment
import com.webmobrilweatherapp.fragment.NewsFragment
import com.webmobrilweatherapp.fragment.ProfileFragment
import com.webmobrilweatherapp.fragment.SearchFragment
import com.webmobrilweatherapp.function.MySingleton
import com.webmobrilweatherapp.utilise.NotificationUtils
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.viewmodel.webconfig.Utils
import java.util.Arrays
import java.util.Locale
import java.util.Objects


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityHomeBinding
    private var usertype = "0"
    var detailss: String = "0"
    var accountViewModel: AccountViewModel? = null
    private var userid = 0
    var status = false
    var Address = ""
    var fragmentCurrents: Fragment? = null
    var openDrawer: String = "0"
    protected var locationManager: LocationManager? = null
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    var checkGPS = false
    var checkNetwork = false
    var canGetLocation = false
    var loc: Location? = null
    var names = ""
    var email = ""
    var password = ""
    var city: String = "0"
    var state: String = "0"
    var longituted = 0
    var zipCode = "0"
    var country = "0"
    var latitude = 0.0
    var longitude = 0.0
    var lc1="0"
    private val geocoder: Geocoder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        lc1=CommonMethod.getInstance(this).getPreference(AppConstant.lc1)

        MySingleton.handleTheme(this, usertype)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.layoutContent.navView.setOnNavigationItemSelectedListener(this)



        if(lc1!=null)
        {
            binding.layoutContent.toolbar.txtLocation.setText(lc1)

        }
        // getLastLocation()

        detailss= getIntent().getExtras()?.getString("details").toString()
        //Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

        // PushNotifications.start(getApplicationContext(), "29fb2b33-4975-4baa-986e-2b323462775f");
      //  PushNotifications.addDeviceInterest("hello");
        if (detailss.equals("2")) {

            val fragmenttent: Fragment = ProfileFragment()
            binding.layoutContent.toolbar.Toolbats.visibility = GONE
           replaceFragment(fragmenttent)
               supportFragmentManager.beginTransaction()
                  .replace(R.id.nav_host_fragment, fragmenttent).addToBackStack(null).commit()

        }
        else {

            UpdateHomeFragments(HomeFragment(), "Home")
            getuserprofile()
            getLocation()


            val ai: ApplicationInfo = applicationContext.packageManager
                .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
            val value = ai.metaData["AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM"]
            //  val apiKey = value.toString()
            //val apiKey = "AIzaSyBlCaysRN5XHvtLzJIyRkjaiXEKQbOl1c8"
            val apiKey = "AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM"

            Log.e("apiKey", apiKey)

            // Initializing the Places API
            // with the help of our API_KEY
            if (!Places.isInitialized()) {
                Places.initialize(applicationContext, apiKey)
            }

        }
        binding.layoutContent.toolbar.txtLocation.setOnClickListener { v ->
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



        binding.layoutContent.navView.setItemIconTintList(null)

        binding.layoutContent.toolbar.imgnotification.setOnClickListener {
            val i = Intent(this, NotificationActivity::class.java)
            startActivity(i)
        }
        binding.layoutContent.toolbar.imghomemenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
            openDrawer = "1"
        }
        binding.layoutDrawer.imgcrossiconprofile.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.layoutDrawer.editprofile.setOnClickListener {
            val i = Intent(this, ViewProfileActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.notification.setOnClickListener {
            val i = Intent(this, NotificationActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.farecast.setOnClickListener {
            val i = Intent(this, ForecastScreen::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.alert.setOnClickListener {
            val i = Intent(this, UserAlertActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.selectButterfly.setOnClickListener {
             var a=1
            val i = Intent(this, ButterflySpeicesActivity::class.java)
            i.putExtra("checkStatus",a.toString())
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.weathervote.setOnClickListener {
            val i = Intent(this, WeatherVoteActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.layoutDrawer.WeatherMayor.setOnClickListener {
            val i = Intent(this, WeatherMayorActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.layoutDrawer.Metrologiste.setOnClickListener {
            val i = Intent(this, TopFiveMetrologistActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.layoutDrawer.textPrivacyPolicy.setOnClickListener {
            /* val i = Intent(this,PrivacyPolicyActivity::class.java)
             startActivity(i)*/
            val i = Intent(this, WerViewActivity::class.java)
            i.putExtra("webviewurl","2")
            startActivity(i)
           /* val uri = Uri.parse(ApiConstants.privacyPolicy)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            binding.drawerLayout.closeDrawer(GravityCompat.START)*/
        }
        binding.layoutDrawer.TermsConditions.setOnClickListener {
            /*   val i = Intent(this,TermsandConditionActivity::class.java)
               startActivity(i)*/
            val i = Intent(this, WerViewActivity::class.java)
            i.putExtra("webviewurl","1")
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.layoutDrawer.AboutUs.setOnClickListener {
            /*val i = Intent(this,AboutUsActivity::class.java)
            startActivity(i)*/
          /*  val uri = Uri.parse(ApiConstants.AboutUs)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)*/
            val i = Intent(this, WerViewActivity::class.java)
            i.putExtra("webviewurl","3")
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.layoutDrawer.textContactUs.setOnClickListener {
            val i = Intent(this, ContactActivity::class.java)
            startActivity(i)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.layoutDrawer.logout.setOnClickListener {
            showDialog()
        }


        binding.layoutDrawer.layoutMyAccount.setOnClickListener {
            if (!status) {
                binding.layoutDrawer.expandIcon1.visibility = VISIBLE
                binding.layoutDrawer.LayoutItemMyAccount.visibility = VISIBLE
                binding.layoutDrawer.LayoutItemContent.visibility = GONE
                binding.layoutDrawer.LayoutItemHelp.visibility = GONE
                binding.layoutDrawer.expandIcon1.visibility = GONE
                binding.layoutDrawer.downArrow.visibility = VISIBLE
                status = true
            } else {
                binding.layoutDrawer.LayoutItemMyAccount.visibility = GONE
                binding.layoutDrawer.LayoutItemContent.visibility = GONE
                binding.layoutDrawer.LayoutItemHelp.visibility = GONE
                binding.layoutDrawer.expandIcon1.visibility = VISIBLE
                binding.layoutDrawer.downArrow.visibility = GONE
                status = false
            }
        }
        binding.layoutDrawer.layoutContent.setOnClickListener {
            if (!status) {
                binding.layoutDrawer.expandIcon2.visibility = VISIBLE
                binding.layoutDrawer.LayoutItemMyAccount.visibility = GONE
                binding.layoutDrawer.LayoutItemContent.visibility = VISIBLE
                binding.layoutDrawer.LayoutItemHelp.visibility = GONE
                binding.layoutDrawer.expandIcon2.visibility = GONE
                binding.layoutDrawer.downArrows.visibility = VISIBLE

                status = true
            } else {
                binding.layoutDrawer.LayoutItemMyAccount.visibility = GONE
                binding.layoutDrawer.LayoutItemContent.visibility = GONE
                binding.layoutDrawer.LayoutItemHelp.visibility = GONE
                binding.layoutDrawer.expandIcon2.visibility = VISIBLE
                binding.layoutDrawer.downArrows.visibility = GONE
                status = false
            }
        }
        binding.layoutDrawer.layoutHelp.setOnClickListener {
            if (!status) {
                binding.layoutDrawer.LayoutItemMyAccount.visibility = GONE
                binding.layoutDrawer.LayoutItemContent.visibility = GONE
                binding.layoutDrawer.LayoutItemHelp.visibility = VISIBLE
                binding.layoutDrawer.expandIcon3.visibility = GONE
                binding.layoutDrawer.downArrowes.visibility = VISIBLE
                status = true
            } else {
                binding.layoutDrawer.LayoutItemMyAccount.visibility = GONE
                binding.layoutDrawer.LayoutItemContent.visibility = GONE
                binding.layoutDrawer.LayoutItemHelp.visibility = GONE
                binding.layoutDrawer.expandIcon3.visibility = VISIBLE
                binding.layoutDrawer.downArrowes.visibility = GONE
                status = false
            }
        }

    }

    private fun updateNotificationCount() {
        val badgeTextView = findViewById<TextView>(R.id.tv_notification_count)
        val count = NotificationUtils.getNotificationCount(this)

        Log.e("Notifcation count", count.toString())

        if (count > 0) {
            badgeTextView.visibility = VISIBLE
            badgeTextView.text = count.toString()
        } else {
            badgeTextView.visibility = GONE
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                //ProgressD.showLoading(this,getResources().getString(R.string.logging_in))

                val fragmentt: Fragment = HomeFragment()
                binding.layoutContent.toolbar.Toolbats.visibility = VISIBLE
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragmentt)
                    .commit()
                // ProgressD.hideProgressDialog()
            }
            R.id.news -> {
                val fragmentty: Fragment = NewsFragment()
                binding.layoutContent.toolbar.Toolbats.visibility = GONE
                replaceFragment(fragmentty)
            }
            R.id.notifications -> {
                val fragmenttsh: Fragment = AddGalleryFragment()
                binding.layoutContent.toolbar.Toolbats.visibility = GONE
                replaceFragment(fragmenttsh)
            }
            R.id.search -> {
                val fragmenttshs: Fragment = SearchFragment()
                binding.layoutContent.toolbar.Toolbats.visibility = GONE
                replaceFragment(fragmenttshs)
            }
            R.id.profile -> {
                val fragmenttent: Fragment = ProfileFragment()
                binding.layoutContent.toolbar.Toolbats.visibility = GONE
                replaceFragment(fragmenttent)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragmenttent).addToBackStack(null).commit()
            }
        }
        return false
    }

    private fun UpdateHomeFragments(fragment: Fragment, title: String) {
        if (Utils.checkConnection(this)) {
        } else {
            Toast.makeText(this, "Please Check Internet", Toast.LENGTH_SHORT).show()
            ProgressD.hideProgressDialog()
        }
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (openDrawer.equals("1")) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            openDrawer = "0"
        } else {
            val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            if (fragment is HomeFragment) {
                AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Yes"
                    ) { dialog, id -> finishAffinity() }
                    .setNegativeButton("No", null)
                    .show()
            } else if (fragment is NewsFragment || fragment is AddGalleryFragment || fragment is SearchFragment || fragment is ProfileFragment) {
                val fragmentt: Fragment = HomeFragment()
                addFragment(fragmentt)
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        fragmentCurrents = fragment
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, fragment).commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentCurrents = fragment
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null).commit()
    }

    fun updateBottomBars(i: Int) {
        binding.layoutContent.navView.menu.getItem(i).isChecked = true
    }

    private fun showDialog() {
        val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure you want to Logout ? ")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton(
            "Yes"
        ) { arg0, arg1 ->
            getlogouts()
        }
        alertDialogBuilder.setNegativeButton(
            "No"
        ) { dialog, which -> dialog.dismiss() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    /* override fun onResume() {
         if (details != null) {
             val fragment: Fragment = ProfileFragment()
             val bundle = Bundle()
             bundle.putString("details", details)
             fragment.arguments = bundle
             supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, fragment).commit()
             ProgressD.hideProgressDialog()
         }
         super.onResume()
     }*/

    private fun getuserprofile() {
        //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getuserprofile(
            userid.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )

            ?.observe(this) {
                //ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {

                    binding.layoutDrawer.accountName.setText(it.data?.name)
                    binding.layoutDrawer.txtEmail.setText(it.data?.email)
                    CommonMethod.getInstance(this).savePreference(AppConstant.Key_ID_Butterfly,it.data!!.selectButterfly.toString())
                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL + it.data?.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.layoutDrawer.circleImageview)
                } else {

                }
            }
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onResume() {

        getuserprofile()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            notificationReceiver,
            IntentFilter("com.webmobrilweatherapp.NOTIFICATION_COUNT_UPDATED")
        )

        val prefs = getSharedPreferences("notification_prefs", MODE_PRIVATE)
    val count = prefs.getInt("notification_count", 0)

        Log.e("pref count", count.toString())
        val badgeTextView = findViewById<TextView>(R.id.tv_notification_count)
        badgeTextView?.let {
            if (count > 0) {
                it.visibility = View.VISIBLE
                it.text = count.toString()
            } else {
                it.visibility = View.GONE
            }
        }
        super.onResume()
    }
    private val notificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNotificationCount()
        }
    }

    private fun getlogouts() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getlogouts(
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    CommonMethod.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE,"0")

                    val intent = Intent(this, SelectOptionActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finishAffinity()
                    CommonMethod.getInstance(this).savePreference(AppConstant.Key_ApplicationId,"0")
                    CommonMethod.getInstance(this).savePreference(AppConstant.KEY_loginStatus, false)
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

                } else if (it!!.code == 401) {
                    CommonMethod.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE,"0")

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
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                        Log.e("locationManager", "locationManager")
                        loc = locationManager!!
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        if (loc != null) {
                            val latitude: String = loc!!.getLatitude().toString()
                            val longitude: String = loc!!.getLongitude().toString()
                            getalllocation(latitude,longitude)
                            //getCompleteAddress(latitude, longitude)
                            CommonMethod.getInstance(this).savePreference(AppConstant.location, latitude)
                            CommonMethod.getInstance(this).savePreference(AppConstant.Longituted, longitude)
                         }
                    }

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                var place: Place? = null
                if (data != null) {
                    place = Autocomplete.getPlaceFromIntent(data)
                    Log.e("TAG", "Place: " + place.name + ", " + place.id)
                    latitude = Objects.requireNonNull(place.latLng).latitude
                    longitude = place.latLng.longitude
                    var cityjgkjfd=place.plusCode
                    Log.d("TAG", "gfgfdgdfgdfg: "+latitude+longitude)
                   // getCompleteAddress(latitude,longitude)
                    getalllocation(latitude.toString(),longitude.toString())
                    CommonMethod.getInstance(this).savePreference(AppConstant.cityName, place.name.toString())
                    //getCountryCityCode(latitude, longitude)

                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data)
                assert(status.statusMessage != null)
                Log.e("TAG", status.statusMessage!!)
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
            Log.e("zip Code", zipCode.toString())
            Log.d("TAG", "cihefgfdlkjgkldfg: " + addresses)

            Log.e("address", addressLine)

        } catch (e: Exception) {
            //  Toast.makeText(this, "Please enter valid city", Toast.LENGTH_SHORT).show()
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

        println("Location in api")
        println(latitude)
        println(longitude)
            // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
            accountViewModel?.getalllocation(
                (latitude+","+longitude),"AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM"
            )?.observe(this) {
                //ProgressD.hideProgressDialog()
                if (it != null) {
                    if (it.plusCode?.compoundCode!=null){
                        Log.d("TAG", "ljgdfjgdfjg: "+latitude+longitude)
                        CommonMethod.getInstance(this).savePreference(AppConstant.location, latitude)
                        CommonMethod.getInstance(this).savePreference(AppConstant.Longituted, longitude)
                        var date =it.plusCode!!.compoundCode
                        val upToNCharacters: String = date!!.substring(8, Math.min(date.length,40))
                        CommonMethod.getInstance(this).savePreference(AppConstant.lc1, upToNCharacters)
                        binding.layoutContent.toolbar.txtLocation.setText(upToNCharacters)
                        UpdateHomeFragments(HomeFragment(), "Home")

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