package com.webmobrilweatherapp.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.function.MySingleton
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.Gson
import com.webmobrilweatherapp.activities.metrologistactivity.WerViewActivity
import com.webmobrilweatherapp.databinding.ActivitySignUpBinding
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivitySignUpBinding
    private var usertype = "0"
    var Checkbox = 0
    protected var locationManager: LocationManager? = null
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    var checkGPS = false
    var checkNetwork = false
    var canGetLocation = false
    var loc: Location? = null
    var names = ""
    var email = ""
    var password = ""
    var ltitute = 0.0
    var city: String = "0"
    var citycustom: String = "0"
    var state: String = "0"
    var longituted = 0.0
    var zipCode = "0"
    var country = "0"
    var latitude = 0.0
    var longitude= 0.0
    private val geocoder: Geocoder? = null
    private var mRequestQueue: RequestQueue? = null
    var accountViewModel: AccountViewModel? = null

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        Log.d("user_type", usertype)
        //usertype = SharedPreferenceManager.getInstance(this).getPreference(PrefConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
         Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgBatterfly!!)
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
        // Display the fetched information after clicking on one of the options


        mRequestQueue = Volley.newRequestQueue(this);
        //binding.editSelectCitiy.isEnabled = false
        val view = binding.root
        binding.checkboxsignup.setOnClickListener {
            if (binding.checkboxsignup.isChecked) {
                Checkbox = 1
            } else {
                Checkbox = 0
            }
        }

        /*binding.editSelectCitiy.setOnClickListener {
            var name = binding.editnamesign.text.toString()
            var email = binding.editemailidsign.text.toString()
            var password = binding.editPasswordsign.text.toString()
            var intent = Intent(this, CityLocationSignupActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }*/
        binding.btnsignup.setOnClickListener {
          //  this?.startActivity(Intent(this, ButterflySpeicesActivity::class.java))
           if (isValidation()) {

                getRegistration()
                //getemailverification()
            }

        }
        binding.txtcity.setOnClickListener { v ->
            val fields =
                Arrays.asList(
                    Place.Field.ID,
                    Place.Field.ADDRESS,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG
                )

            // Start the autocomplete intent.
            val intent =Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
            startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE)
        }
        setContentView(view)
        listener()
    }


    private fun isValidation(): Boolean {
        if (binding.editnamesign.text.isNullOrEmpty()) {
            binding.editnamesign.requestFocus()
            showToastMessage("Please Enter Name")
            return false


        } else if (binding.editemailidsign.text.isNullOrEmpty()) {
            binding.editemailidsign.requestFocus()
            showToastMessage("Please Enter Email")
            return false


        } else if (!validateEmail(binding.editemailidsign.text.toString())) {
            binding.editemailidsign.requestFocus()
            Toast.makeText(this, "Please Enter valid Email Id", Toast.LENGTH_SHORT).show()
            return false


        }else if (binding.editPasswordsign.text.isNullOrEmpty()) {
            binding.editPasswordsign.requestFocus()
            showToastMessage("Please Enter Password")
            return false


        } else if (binding.editPasswordsign.text.length < 8 || binding.editPasswordsign.text.length > 16) {
            showToastMessage("Password must be of minimum 8 charcaters and maximum 16 characters.")
            return false


        } else if (!isValidPassword(binding.editPasswordsign.text.toString())) {
            showToastMessage("Password must contain - 1 uppercase character, 1 lowercase character, 1 digit and 1 special character.")
            return false


        }else if(binding.txtcity.text.toString().isEmpty()){
            binding.txtcity.requestFocus()
            showToastMessage("Please select city")
            return false
        } else if (Checkbox != 1) {
            Toast.makeText(this, " Please Use Terms & Conditions Check Box", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        return true

    }
    fun validateEmail(email: String): Boolean {
        return Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        ).matcher(email).matches()
    }


    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+*!=])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }


    private fun getRegistration() {

        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        // Get user sign up response
        val etFullname: String = binding.editnamesign.text.toString()
        val etEmail: String = binding.editemailidsign.text.toString()
        val etpassword: String = binding.editPasswordsign.text.toString()

        /*Toast.makeText(getApplicationContext(),etFullname.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),etEmail.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),etpassword.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),city.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),state.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),country.toString(),Toast.LENGTH_SHORT).show();

        Toast.makeText(getApplicationContext(),ltitute.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),longituted.toString(),Toast.LENGTH_SHORT).show();*/
      //  Toast.makeText(getApplicationContext(),citycustom.toString(),Toast.LENGTH_SHORT).show();
        accountViewModel?.getRegistration(
            etFullname,
            etEmail,
            etpassword,
            citycustom,
            state,
            country,
            2,
           ltitute,
            longituted,
            zipCode
        )?.observe(this) {
                ProgressD.hideProgressDialog()

                    if (it != null && it.code == 200) {
                        getSignUpClear()
                        // binding!!.checkBox.isChecked = false
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                        CommonMethod.getInstance(this).savePreference(AppConstant.KEY_Fullname, it.data?.name!!)
                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_Email, it.data.email!!)

                        CommonMethod.getInstance(this).savePreference(AppConstant.KEY_City, it.data.city!!)
                        CommonMethod.getInstance(this).savePreference(AppConstant.USER_TYPE, it.data.userType!!)
                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_User_id, it.data.id!!)


                        var intent = Intent(this, OtpScreenActivity::class.java)
                        intent.putExtra("email", etEmail)
                        startActivity(intent)

                        // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)

                    } else {

                        Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

                    }

            }
    }

    private fun listener() {
        // binding.btnsignup.setOnClickListener(this)
        binding.textlogin.setOnClickListener(this)
        binding.textreadmore.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textlogin -> {
                val i = Intent(this, LoginActivity::class.java)
                i.putExtra(AppConstant.USER_TYPE, usertype)
                startActivity(i)
                finish()
            }

            R.id.textreadmore -> {
                binding.textreadmore.setOnClickListener {
                    val i = Intent(this, WerViewActivity::class.java)
                    i.putExtra("webviewurl","1")
                       startActivity(i)
                   /* val uri = Uri.parse(ApiConstants.TermsCondition)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)*/

                }
            }
        }
    }
    private fun getSignUpClear() {
        binding.editnamesign.setText("")
        binding.editemailidsign.setText("")
        binding.editPasswordsign.setText("")
    }
    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
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
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
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
                        loc = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        if (loc != null) {
                            val latitude: Double = loc!!.getLatitude()
                            val longitude: Double = loc!!.getLongitude()
                            getalllocation(latitude.toString(), longitude.toString())
                            getCompleteAddress(latitude, longitude)
                            CommonMethod.getInstance(this).savePreference(AppConstant.location,latitude.toString())
                            CommonMethod.getInstance(this).savePreference(AppConstant.Longituted,longitude.toString())
                            Log.e("latitude", "==$latitude")
                            Log.e("longitude", "==$longitude")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            //citycustom=addresses[0].locality
            Log.e("zip Code", zipCode.toString())
            Log.e("address", Gson().toJson(addresses))
            Log.e("citycustom", citycustom)
            Log.e("check1",  addresses[0].locale.toString())


        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }

        return address

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
                    city=place.name
                    ltitute= place.latLng.latitude.toDouble()
                    longituted=place.latLng.longitude.toDouble()
                    binding.txtcity.setText(place.name)
                    binding.txtcity.setTextColor(Color.BLACK)
                    getalllocation(latitude.toString(), longitude.toString())

                    getCompleteAddress(latitude,longitude)
                    CommonMethod.getInstance(this).savePreference(AppConstant.location,latitude.toString())
                    CommonMethod.getInstance(this).savePreference(AppConstant.Longituted,longitude.toString())
                    CommonMethod.getInstance(this).savePreference(AppConstant.cityName,place.name.toString())
                    getCountryCityCode(latitude, longitude)
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
    private fun getalllocation(latitude: String, longitude: String) {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        accountViewModel?.getalllocation(
            (latitude+","+longitude),"AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM"
        )?.observe(this) {
            //ProgressD.hideProgressDialog()
            if (it != null) {
                if (it.plusCode!!.compoundCode!=null){

                    var date =it.plusCode!!.compoundCode
                    val upToNCharacters: String = date!!.substring(8, Math.min(date.length,40))
                    citycustom=upToNCharacters

                }else{
                    Toast.makeText(this,"Please Enter Valid City Name",Toast.LENGTH_LONG).show()
                }
            } else {
                //Toast.makeText(context, , Toast.LENGTH_LONG).show()
            }

        }
        // Get user sign up response

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
                           /* CommonMethod.getInstance(this).savePreference("$street  $city $state $postalCode")
                            loadHome("0")*/
                        }
                    }
                }
            }
        }catch (e: java.lang.Exception)
        {

            e.printStackTrace()

        }
    }
}

