package com.webmobrilweatherapp.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.webmobrilweatherapp.R
import com.bumptech.glide.Glide
import android.view.View
import androidx.fragment.app.Fragment
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.activities.*
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.adapters.Sevendays_Adapter
import com.webmobrilweatherapp.databinding.FragmentHomeBinding
import com.webmobrilweatherapp.model.TendaysWeatherApi.DailyForecastsItem
import com.webmobrilweatherapp.utilise.CommonUtil
import com.webmobrilweatherapp.utilise.LocationUtilities
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*
import android.app.ProgressDialog
import android.content.Context
import android.location.LocationManager
import android.provider.Settings
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.gms.location.Priority
import com.webmobrilweatherapp.activities.CommonMethod.Companion.getInstance

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var usertype = "0"
    private var userid = 0
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    var accountViewModel: AccountViewModel? = null
    var lat: String =" 0.0"
    var long: String ="0.0"
    var strkey: String=""
    private var mDialog: ProgressDialog? = null
    var clickBtn=0
    var b=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        (requireActivity() as HomeActivity).updateBottomBars(0)
         getuserprofile()

        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LocationUtilities.requestLocationPermission(requireActivity())
        if (isLocationEnabled1()) {
            getLastLocation()
        } else {
            Toast.makeText(requireContext(), "Please enable location services", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        usertype = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.USER_TYPE)
        //////
        openfragments()
        binding.btnViewVote.setBackgroundResource(R.drawable.homepagebtnshape)
        binding.btnViewVote.setTextColor(resources.getColor(R.color.black))
        binding.btnTomorrow.setTextColor(resources.getColor(R.color.black))
        binding.btndayAfterTomorrow.setTextColor(resources.getColor(R.color.black))
        binding.btnTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinner)
        binding.btndayAfterTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinner)
       /////
        binding.btnViewVote.setOnClickListener {

            if(clickBtn!=1)
            {
                openfragments()


                binding.btnViewVote.setBackgroundResource(R.drawable.homepagebtnshape)
                binding.btnViewVote.setTextColor(resources.getColor(R.color.black))
                binding.btnTomorrow.setTextColor(resources.getColor(R.color.black))
                binding.btndayAfterTomorrow.setTextColor(resources.getColor(R.color.black))
                binding.btnTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinner)
                binding.btndayAfterTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinner)

            }
        }


        binding.btnTomorrow.setOnClickListener {

            opentomorrowfragments()
            binding.btnViewVote.setBackgroundResource(R.drawable.homepagebtnshapeinner)
            binding.btnViewVote.setTextColor(resources.getColor(R.color.black))
            binding.btnTomorrow.setTextColor(resources.getColor(R.color.black))
            binding.btndayAfterTomorrow.setTextColor(resources.getColor(R.color.black))
            binding.btnTomorrow.setBackgroundResource(R.drawable.homepagebtnshape)
            binding.btndayAfterTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinner)

        }


        binding.btndayAfterTomorrow.setOnClickListener {
            opendayaftertommorrowfragments()
            binding.btnViewVote.setBackgroundResource(R.drawable.homepagebtnshapeinner)
            binding.btnViewVote.setTextColor(resources.getColor(R.color.black))
            binding.btnTomorrow.setTextColor(resources.getColor(R.color.black))
            binding.btndayAfterTomorrow.setTextColor(resources.getColor(R.color.black))
            binding.btnTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinner)
            binding.btndayAfterTomorrow.setBackgroundResource(R.drawable.homepagebtnshape)
        }


        binding.forecastCardview.setOnClickListener(View.OnClickListener {

            this?.startActivity(Intent(context, ForecastScreen::class.java))


        })
        var btnCounter=0
     binding.seeAll.setOnClickListener(View.OnClickListener {

        // this?.startActivity(Intent(context, SeeAllSevendays_Activity::class.java))

         btnCounter++


         if(btnCounter%2==0)
         {
             binding.seeAll.setBackgroundResource(R.drawable.homepagebtnshapeinner)

             binding.sevendayForecast.visibility= GONE
             binding.whitespace.visibility=View.GONE
             binding.whitespace2.visibility=View.GONE

         }
         else
         {

             binding.sevendayForecast.visibility= VISIBLE
             getTendaysWeather(strkey)
             binding.seeAll.setBackgroundResource(R.drawable.homepagebtnshape)
             binding.whitespace.visibility=View.VISIBLE
             binding.whitespace2.visibility=View.VISIBLE


         }

     })
    //setUpViewPager()
    }

    private fun isLocationEnabled1(): Boolean {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    private fun opendayaftertommorrowfragments() {
        clickBtn=0
        val fragment: Fragment = DayofterTomorrowFragment()
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.viewPager, fragment)

        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        //transaction.addToBackStack(fragment.getClass().getSimpleName());

        transaction.commit()

    }

    private fun opentomorrowfragments() {
        clickBtn=0
        val fragment: Fragment = TomorrowFragment()
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.viewPager, fragment)
        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit()

    }


    private fun openfragments() {
        clickBtn=1
        val fragment: Fragment = ViewVotesFragment()
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.viewPager, fragment)
        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit()


    }


    fun loadFragment(fragment: Fragment) {


        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.framlayout, fragment)
        transaction?.disallowAddToBackStack()
        transaction?.commit()


    }

    private fun getHomeapge() {
        binding.ivLoader.visibility = View.VISIBLE

        val prefs = CommonMethod.getInstance(requireContext())

        val userSelected = prefs.getPreference(AppConstant.KEY_USER_MANUALLY_SELECTED_LOCATION, false)

        val lat: String
        val lon: String

        if (userSelected) {
            // User picked a city → use saved searched location
            lat = prefs.getPreference(AppConstant.location, "0.0")
            lon = prefs.getPreference(AppConstant.Longituted, "0.0")
            Log.d("Weather", "Using USER-SELECTED location: $lat, $lon")
        } else {
            // First time or no manual selection → try current device location
            lat = prefs.getPreference(AppConstant.location, "0.0")     // fallback if never saved
            lon = prefs.getPreference(AppConstant.Longituted, "0.0")

            if (lat == "0.0" || lon == "0.0") {
                // We don't have any location yet → get current one now
                getCurrentLocationAndProceed()
                return   // ← important: don't call API yet
            }

            Log.d("Weather", "Using DEVICE location (no manual selection): $lat, $lon")
        }

        // Now call API with decided coordinates
        callHomeApiWithLocation(lat, lon)
    }

    private fun callHomeApiWithLocation(lat: String, lon: String) {
        if (!isAdded || !isVisible || view == null) {
            Log.w("HomeFragment", "View not available, skipping API call")
            return
        }
        if (lat == "0.0" || lon == "0.0") {
            binding.ivLoader.visibility = View.GONE
            Toast.makeText(requireContext(), "Location not available", Toast.LENGTH_LONG).show()
            return
        }

        accountViewModel?.getHomeapge(
            "AIzaSyBGlucHwOkpJYBAivcjZ0vKShxBUjMoVm4",
            "$lat,$lon",
            "En",
            "true"
        )?.observe(viewLifecycleOwner) { response ->
            binding.ivLoader.visibility = View.GONE

            if (response != null) {
                val key = response.key.toString()
                CommonMethod.getInstance(requireContext()).savePreference(AppConstant.KEY, key)
                strkey = key

                getHomeapgesunny(key)
                // getTendaysWeather(key)  ← call when needed (seeAll clicked)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocationAndProceed() {
        if (!isLocationEnabled1()) {
            Toast.makeText(requireContext(), "Please enable location", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            return
        }

        val fused = LocationServices.getFusedLocationProviderClient(requireActivity())

        fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val lat = location.latitude.toString()
                    val lon = location.longitude.toString()

                    // Save it — this becomes default until user searches
                    CommonMethod.getInstance(requireContext()).apply {
                        savePreference(AppConstant.location, lat)
                        savePreference(AppConstant.Longituted, lon)
                        // Do NOT set KEY_USER_MANUALLY_SELECTED_LOCATION = true here!
                    }

                    callHomeApiWithLocation(lat, lon)
                } else {
                    Toast.makeText(requireContext(), "Couldn't get location", Toast.LENGTH_LONG).show()
                    binding.ivLoader.visibility = View.GONE
                }
            }
            .addOnFailureListener {
                Log.e("Location", "getCurrentLocation failed", it)
                Toast.makeText(requireContext(), "Location error", Toast.LENGTH_SHORT).show()
                binding.ivLoader.visibility = View.GONE
            }
    }

//    @SuppressLint("SuspiciousIndentation")
//    private fun getHomeapge() {
//    binding.ivLoader.visibility=View.VISIBLE
//        if (context != null) {
//            // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
//            Log.d("TAG", "djfkdsfdsofi: "+lat+long)
////            lat=  CommonMethod.getInstance(requireContext()).getPreference(AppConstant.location)
////            long= CommonMethod.getInstance(requireActivity()).getPreference(AppConstant.Longituted)
//           //  Toast.makeText(requireContext(), lat.toString(), Toast.LENGTH_LONG).show()
//
//
//            accountViewModel?.getHomeapge(
////                "AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM", lat + "," + long,
//                "AIzaSyBGlucHwOkpJYBAivcjZ0vKShxBUjMoVm4", lat + "," + long,
//                "En",
//                "true"
//            )?.observe(requireActivity()) {
//                //ProgressD.hideProgressDialog()
//                if (it != null) {
//                    // binding!!.checkBox.isChecked = false
//                    if (it != null)
//                        getInstance(context).savePreference(AppConstant.KEY, it.key.toString())
//                     strkey = it.key.toString()
//                    getHomeapgesunny(it.key.toString())
//
//                    Log.d("TAG", "gfhjkhgjkh: "+strkey)
//                    if (strkey != null) {
//                        //getTendaysWeather(strkey.toString())
//                       // Toast.makeText(requireContext(), strkey.toString(), Toast.LENGTH_LONG).show()
//
//
//
//                    }
//
//                    /*strkey?.let { it1 -> getHomeapgesunny(it1) }*/
//                    // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
//                } else{
//
//                    /*Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()*/
//
//
//                }
//            }
//        }
//        // Get user sign up response
//    }
//***********************************************For Ten days********************************************//
private fun getTendaysWeather(strkey: String) {
    // Get user sign up response
    // key= CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY)
    if (this != null) {
        //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
//        accountViewModel?.getTendays(strkey, "wkw7ho4Gya6HakuE7dNcEVEHIVJMZAhU")
        accountViewModel?.getTendays(strkey, "9d35100f47msh8c8f1ff884860a4p153dd5jsn595cd3b79433")
            ?.observe(this) {
                // ProgressD.hideProgressDialog()

                    if (this != null) {
                        if(binding.sevendayForecast!=null) {

                            val layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            binding.sevendayForecast.layoutManager = layoutManager
                            binding.sevendayForecast.setOnFlingListener(null);
                            val snapHelper: SnapHelper = PagerSnapHelper()
                            snapHelper.attachToRecyclerView(binding.sevendayForecast)

                            binding.sevendayForecast.adapter =
                                Sevendays_Adapter(requireContext(),
                                    it?.dailyForecasts as List<DailyForecastsItem>)
                        }
                    }
            }
    }
}


    //*****************************************************************************************************//
    private fun getHomeapgesunny(strkey: String) {

        // Get user sign up response
        // key= CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY)


        if (context != null) {
             //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
//            accountViewModel?.getHomeapgesunny(strkey, "wkw7ho4Gya6HakuE7dNcEVEHIVJMZAhU", "true")
            accountViewModel?.getHomeapgesunny(strkey, "9d35100f47msh8c8f1ff884860a4p153dd5jsn595cd3b79433", "true")
                ?.observe(requireActivity()) {
                   // ProgressD.hideProgressDialog()
                    binding.ivLoader.visibility=View.GONE

                    Log.e("Get Home Sunny","yes")

                    // ProgressD.hideProgressDialog()
                    if (context != null) {
                        if (!requireActivity().isFinishing) {

                            if (context != null) {

                                if (it?.size==1){
                                    Log.d("TAG", "sdkjdslfsf: "+it?.size)
                                    if (it != null) {
                                       val Cloudy = null
                                        binding.seeAll.visibility= VISIBLE
                                        binding.poweredby.visibility= VISIBLE
                                        binding.iccuIocn.visibility= VISIBLE

                                        /*             if (it[0].weatherText == "Partly sunny") {

                                                        binding.imgrain.visibility = View.VISIBLE
                                                        Glide.with(this)
                                                            .load(R.drawable.cloudyicon)
                                                            .into(binding.imgrain)
                                                    }
                                                    if (it[0].weatherText=="Rain") {
                                                        binding.imgrain.visibility = View.VISIBLE
                                                        Glide.with(this)
                                                            .load(R.drawable.rain)
                                                            .into(binding.imgrain)
                                                    }


                                                    if (it[0].weatherText =="Sunny") {

                                                        binding.imgrain.visibility = View.VISIBLE
                                                        Glide.with(this)
                                                            .load(R.drawable.cloudyicon)
                                                            .into(binding.imgrain)

                                                    }

                                                    if(it[0].weatherText =="Hazy sunshine")
                                                    {
                                                        binding.imgrain.visibility = View.VISIBLE
                                                        Glide.with(this)
                                                            .load(R.drawable.cloudyicon)
                                                            .into(binding.imgrain)

                                                    }
                                                    if(it[0].weatherText =="Light rain")
                                                    {
                                                        binding.imgrain.visibility = View.VISIBLE
                                                        Glide.with(this)
                                                            .load(R.drawable.rain)
                                                            .into(binding.imgrain)
                                                    }
                                                    if(it[0].weatherText=="Mostly cloudy"||it[0].weatherText=="Cloudy"||it[0].weatherText=="Some clouds")
                                                    {
                                                        binding.imgrain.visibility = View.VISIBLE
                                                        Glide.with(this)
                                                            .load(R.drawable.cloudyicon)
                                                            .into(binding.imgrain)
                                                    }
                                                    else if(it[0].weatherText=="Mostly sunny"||it[0].weatherText=="Clouds and sun"||it[0].weatherText=="Partly cloudy"||it[0].weatherText=="Mostly clear")
                                                    {
                                                        binding.imgrain.visibility = View.VISIBLE
                                                        Glide.with(this)
                                                            .load(R.drawable.cloudyicon)
                                                            .into(binding.imgrain)
                                                    }


                                                    val Clear = null

                                                    if(it[0].weatherText=="Clear")
                                                    {
                                                        if(it[0].isDayTime == true)
                                                        {
                                                            binding.imghomesunriselogo.visibility = View.VISIBLE

                                                            Glide.with(this)
                                                                .load(R.drawable.sun)
                                                                .into(binding.imghomesunriselogo)
                                                        }
                                                        else{

                                                            binding.imghomesunriselogo.visibility = View.VISIBLE

                                                            Glide.with(this)
                                                                .load(R.drawable.nightmode)
                                                                .into(binding.imghomesunriselogo)
                                                        }


                                                    }

                                                    if(it[0].isDayTime == true)
                                                    {
                                                        binding.imghomesunriselogo.visibility = View.VISIBLE

                                                        Glide.with(this)
                                                            .load(R.drawable.sun)
                                                            .into(binding.imghomesunriselogo)
                                                    }
                                                    else{

                                                        binding.imghomesunriselogo.visibility = View.VISIBLE

                                                        Glide.with(this)
                                                            .load(R.drawable.nightmode)
                                                            .into(binding.imghomesunriselogo)
                                                    }*/


                                        if(it[0].isDayTime == true)
                                        {
                                            if(it[0].weatherText=="Partly sunny"||it[0].weatherText=="Sunny Day"||it[0].weatherText=="Mostly Sunny"||it[0].weatherText=="Smoke"||it[0].weatherText=="Partly cloudy")
                                            {
                                                binding.imghomesunriselogo.visibility = View.VISIBLE

                                                Glide.with(this)
                                                    .load(R.drawable.sun)
                                                    .into(binding.imghomesunriselogo)
                                                binding.imgrain.visibility = View.VISIBLE
                                                Glide.with(this)
                                                    .load(R.drawable.cloudyicon)
                                                    .into(binding.imgrain)
                                            }
                                            else if(it[0].weatherText=="Sunny"||it[0].weatherText=="Clear")
                                            {
                                                binding.imghomesunriselogo.visibility = View.VISIBLE

                                                Glide.with(this)
                                                    .load(R.drawable.sun)
                                                    .into(binding.imghomesunriselogo)
                                          /*      binding.imgrain.visibility = View.VISIBLE
                                                Glide.with(this)
                                                    .load(R.drawable.cloudyicon)
                                                    .into(binding.imgrain)*/

                                            }
                                            else if(it[0].weatherText=="Cloudy")
                                            {
                                                binding.imghomesunriselogo.visibility = View.VISIBLE

                                                Glide.with(this)
                                                    .load(R.drawable.cloudyicon)
                                                    .into(binding.imghomesunriselogo)
                                                binding.imgrain.visibility = View.VISIBLE
                                                Glide.with(this)
                                                    .load(R.drawable.cloudyicon)
                                                    .into(binding.imgrain)

                                            }
                                            else if(it[0].weatherText=="Snow showers"||it[0].weatherText=="Showers"||it[0].weatherText=="Scattered showers"||it[0].weatherText=="Scattered thunderstorms")
                                            {
                                             /*   binding.imghomesunriselogo.visibility = View.VISIBLE

                                                Glide.with(this)
                                                    .load(R.drawable.sun)
                                                    .into(binding.imghomesunriselogo)*/

                                                binding.imgrain.visibility = View.VISIBLE
                                                Glide.with(this)
                                                    .load(R.drawable.rain)
                                                    .into(binding.imgrain)

                                            }

                                            else if(it[0].weatherText=="Rain Day"||it[0].weatherText=="Rain"||it[0].weatherText=="Light rain showers")
                                            {
                                                /*  binding.imghomesunriselogo.visibility = View.VISIBLE

                                                  Glide.with(this)
                                                      .load(R.drawable.sun)
                                                      .into(binding.imghomesunriselogo)*/

                                                binding.imgrain.visibility = View.VISIBLE
                                                Glide.with(this)
                                                    .load(R.drawable.rain)
                                                    .into(binding.imgrain)

                                            }
                                            else
                                            {
                                                binding.imghomesunriselogo.visibility = View.VISIBLE

                                                Glide.with(this)
                                                    .load(R.drawable.sun)
                                                    .into(binding.imghomesunriselogo)
                                                binding.imgrain.visibility = View.VISIBLE
                                                Glide.with(this)
                                                    .load(R.drawable.cloudyicon)
                                                    .into(binding.imgrain)
                                            }

                                        }
                                        else{

                                            if(it[0].weatherText=="Partly sunny"||it[0].weatherText=="Sunny Day"||it[0].weatherText=="Mostly Sunny"||it[0].weatherText=="Clear")
                                            {
                                                binding.imghomesunriselogo.visibility = View.VISIBLE

                                                Glide.with(this)
                                                    .load(R.drawable.nightmode)
                                                    .into(binding.imghomesunriselogo)
                                            }

                                            else if(it[0].weatherText=="Rain Day"||it[0].weatherText=="Rain"||it[0].weatherText=="Light rain showers"||it[0].weatherText=="Snow showers"||it[0].weatherText=="Showers" ||it[0].weatherText=="Scattered showers" ||it[0].weatherText=="Scattered thunderstorms"     )
                                            {
                                                /*binding.imghomesunriselogo.visibility = View.VISIBLE

                                                Glide.with(this)
                                                    .load(R.drawable.nightmode)
                                                    .into(binding.imghomesunriselogo)
*/
                                                binding.imgrain.visibility = View.VISIBLE
                                                Glide.with(this)
                                                    .load(R.drawable.rain)
                                                    .into(binding.imgrain)
                                            }
                                            else if(it[0].weatherText=="Cloudy"||it[0].weatherText=="Mostly cloudy"||it[0].weatherText=="Partly cloudy")
                                            {
                                                binding.imghomesunriselogo.visibility = View.VISIBLE

                                                Glide.with(this)
                                                    .load(R.drawable.nightmode)
                                                    .into(binding.imghomesunriselogo)
                                                binding.imgrain.visibility = View.VISIBLE
                                                Glide.with(this)
                                                    .load(R.drawable.cloudyicon)
                                                    .into(binding.imgrain)
                                            }
                                            else
                                            {
                                                binding.imghomesunriselogo.visibility = View.VISIBLE

                                                Glide.with(this)
                                                    .load(R.drawable.nightmode)
                                                    .into(binding.imghomesunriselogo)
                                                binding.imgrain.visibility = View.VISIBLE
                                                Glide.with(this)
                                                    .load(R.drawable.cloudyicon)
                                                    .into(binding.imgrain)

                                            }

                                        }

                                        if (strkey != null && strkey.length != 0) {
                                            binding.txtNodata.visibility = GONE
                                            var textSunny = it[0].weatherText
                                            //var text = it[0].temperature?.metric?.value
                                            var reelliketem = it[0].realFeelTemperature?.metric?.value
                                            var km = it[0].windGust?.speed?.metric?.value
                                            var Uvindex = it[0].uVIndex

                                            var index = it[0].precip1hr?.metric?.value


                                            binding.textsunntday.setText(textSunny)
                                            var subString=
                                                it[0].temperature?.metric?.value

                                            val s = String.format("%.1f",subString)


                                            // var  tempValue=it[0].temperature?.metric?.value.toString().split(".")


                                          //  val solution: String ="%.2f".format(it[0].temperature?.metric?.value)



                                           /* var a=tempValue[1].toInt()

                                            if (5<=a) {

                                                 b=tempValue[0].toInt()+1
                                                binding.text.setText("" + b + "\u2103")


                                            }
                                            else{
                                               // Toast.makeText(context,it[0].temperature?.metric?.value.toString()+""+tempValue.toString(), Toast.LENGTH_LONG).show()
                                                binding.text.setText("" + tempValue[0] + "\u2103")

                                            }*/

                                            binding.text.setText("" + s + "\u2103")
                                            binding.textfeellike.setText("Feel Like " + reelliketem)
                                            binding.text1.setText("" + km + "km/h")
                                            binding.text2.setText("UV-" + Uvindex)
                                            binding.text3.setText("" + index + "mm")
                                            binding.text.visibility = VISIBLE
                                            binding.textsunntday.visibility = VISIBLE
                                            binding.textfeellike.visibility = VISIBLE

                                        }
                                        else {

                                            binding.txtNodata.visibility = GONE

                                        }

                                        // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                                    } else {
                                       // Toast.makeText(context,"Something went worng", Toast.LENGTH_LONG).show()
                                    }
                                }else{
                                   // Toast.makeText(context,"Something went worng", Toast.LENGTH_LONG).show()
                                }

                            }
                        }
                    }
                }
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->
            if (location != null) {
//                val lat = location.latitude.toString()
//                val long = location.longitude.toString()
//                Log.d("Location", "Lat: $lat, Long: $long")

                // Save location if needed
//                getInstance(requireContext()).savePreference(AppConstant.location, lat)
//                getInstance(requireContext()).savePreference(AppConstant.Longituted, long)

                // Continue with API call or next action
                getHomeapge()

            } else {
                Log.e("Location", "Location is null")
            }
        }.addOnFailureListener {
            Log.e("Location", "Failed to get location", it)
        }
    }


    private fun getLocationAddress(latitude: Double, longitude: Double) {
        try {
            val geocoder = Geocoder(requireActivity(), Locale.getDefault())
            val addressesList: MutableList<Address>? =
                geocoder.getFromLocation(latitude, longitude, 1)
            val location1 = addressesList?.get(0)?.getAddressLine(0)
            if (location1 != null) {
                CommonUtil.showMessage(requireActivity(), location1)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onResume() {
        if (isLocationEnabled1()) {
            getLastLocation()
        } else {
            Toast.makeText(requireContext(), "Please enable location services", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        super.onResume()
    }


    private fun getuserprofile() {
         lat = arguments?.getString("lat").toString()
         long = arguments?.getString("long").toString()

        Log.e("Lat Long after search", lat);
      //  ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(context).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getuserprofile(
            userid.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(AppConstant.KEY_token))
            ?.observe(requireActivity()) {

           //    ProgressD.hideProgressDialog()

                if(isAdded) {
                    if (it != null && it.success == true) {

                        binding.textvoteweather.setText("Vote for " + it.data!!.city + " Weather")

                    } else {
                        Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                        if(it!!.message.toString()=="Unauthenticated.")
                        {

                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatus, false)
                            CommonMethod.getInstance(requireContext()).savePreference(AppConstant.Key_ApplicationId,"0")

                            val intent = Intent(context, LoginActivity::class.java)
                 intent.flags =
                     Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                 context?.startActivity(intent)
                  (activity as HomeActivity).finish()
                     }
                 }
             }
         }
 }

}


