package com.webmobrilweatherapp.activities.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.fragment.votemetrologist.DayofterTomorrowFragmentMetrologist
import com.webmobrilweatherapp.activities.fragment.votemetrologist.TomorrowVoteFragmentMetrologist
import com.webmobrilweatherapp.activities.fragment.votemetrologist.ViewVotesFragmentMetrologist
import com.webmobrilweatherapp.activities.metrologistactivity.MetrilogistHomeActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistForecastChallengeActivity
import com.webmobrilweatherapp.activities.metrologistadapter.Matro_SevenDaysAdapter
import com.webmobrilweatherapp.model.TendaysWeatherApi.DailyForecastsItem
import com.webmobrilweatherapp.utilise.CommonUtil
import com.webmobrilweatherapp.utilise.LocationUtilities
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistLogInActivity
import com.webmobrilweatherapp.databinding.FragmentHomeFragmentsmetrologistBinding
import java.util.*


class HomeFragmentsmetrologist : Fragment() {

    lateinit var binding: FragmentHomeFragmentsmetrologistBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var usertype = "0"
    var accountViewModel: AccountViewModel? = null
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"

    var lat: String = "0.0"
    var long: String = "0.0"
    var strkey: String=""
    var clickBtn=0
    var b=0


    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModelMetrologist =ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentHomeFragmentsmetrologistBinding.inflate(layoutInflater)
        (requireActivity() as MetrilogistHomeActivity).updateBottomBar(0)


        // Inflate the layout for this fragment
        /* binding.imgnotificationmetrologist.setOnClickListener {
             val i = Intent(activity, MetrologistNotificationActivity::class.java)
             startActivity(i)
         }*/

        binding.metrologistforecastCardview.setOnClickListener(View.OnClickListener {
            val i = Intent(activity, MetrologistForecastChallengeActivity::class.java)
            startActivity(i)
        })




        openfragmentsMetrologist()

        binding.btnViewVote.setBackgroundResource(R.drawable.homepagebtnshape)
        binding.btnViewVote.setTextColor(resources.getColor(R.color.black))
        binding.btnTomorrow.setTextColor(resources.getColor(R.color.black))
        binding.btndayAfterTomorrow.setTextColor(resources.getColor(R.color.black))
        binding.btnTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinnermetrologist)
        binding.btndayAfterTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinnermetrologist)


        binding.btnViewVote.setOnClickListener {

            if(clickBtn!=1) {

                openfragmentsMetrologist()
                binding.btnViewVote.setBackgroundResource(R.drawable.homepagebtnshape)
                binding.btnViewVote.setTextColor(resources.getColor(R.color.black))
                binding.btnTomorrow.setTextColor(resources.getColor(R.color.black))
                binding.btndayAfterTomorrow.setTextColor(resources.getColor(R.color.black))
                binding.btnTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinnermetrologist)
                binding.btndayAfterTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinnermetrologist)
            }
        }


        binding.btnTomorrow.setOnClickListener {
            opentomorrowfragmentsMetrologist()
            binding.btnViewVote.setBackgroundResource(R.drawable.homepagebtnshapeinnermetrologist)
            binding.btnViewVote.setTextColor(resources.getColor(R.color.black))
            binding.btnTomorrow.setTextColor(resources.getColor(R.color.black))
            binding.btndayAfterTomorrow.setTextColor(resources.getColor(R.color.black))
            binding.btnTomorrow.setBackgroundResource(R.drawable.homepagebtnshape)
            binding.btndayAfterTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinnermetrologist)
        }

        binding.btndayAfterTomorrow.setOnClickListener {
            opendayaftertommorrowfragmentsMetrologist()
            binding.btnViewVote.setBackgroundResource(R.drawable.homepagebtnshapeinnermetrologist)
            binding.btnViewVote.setTextColor(resources.getColor(R.color.black))
            binding.btnTomorrow.setTextColor(resources.getColor(R.color.black))
            binding.btndayAfterTomorrow.setTextColor(resources.getColor(R.color.black))
            binding.btnTomorrow.setBackgroundResource(R.drawable.homepagebtnshapeinnermetrologist)
            binding.btndayAfterTomorrow.setBackgroundResource(R.drawable.homepagebtnshape)
        }

        var btnCounter=0
        binding.seeAll.setOnClickListener(View.OnClickListener {

            // this?.startActivity(Intent(context, SeeAllSevendays_Activity::class.java))

            btnCounter++


            if(btnCounter%2==0)
            {
                binding.seeAll.setBackgroundResource(R.drawable.homepagebtnshapeinnermetrologist)

                binding.sevendayForecast.visibility= View.GONE
                binding.seeAll.setTextColor(resources.getColor(R.color.white))
                binding.whitespace.visibility=View.GONE
                binding.whitespace2.visibility=View.GONE
            }
            else
            {

                binding.sevendayForecast.visibility= VISIBLE
                getTendaysWeather(strkey)
                binding.seeAll.setTextColor(resources.getColor(R.color.black))
                binding.seeAll.setBackgroundResource(R.drawable.homepagebtnshape)
                binding.whitespace.visibility=View.VISIBLE
                binding.whitespace2.visibility=View.VISIBLE

            }

        })

        return binding.root

        // return inflater.inflate(R.layout.fragment_home_fragmentsmetrologist, container, false)


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
        getuserprofileMetrologist()
        usertype = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.USER_TYPE)

    }

    private fun isLocationEnabled1(): Boolean {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getHomeapge() {
        binding.ivLoader.visibility=View.VISIBLE

        if (context != null) {
            // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
            lat=  CommonMethod.getInstance(requireContext()).getPreference(AppConstant.location)
            long= CommonMethod.getInstance(requireActivity()).getPreference(AppConstant.Longituted)
            accountViewModel?.getHomeapge(
                "wkw7ho4Gya6HakuE7dNcEVEHIVJMZAhU", lat + "," + long,
                "En",
                "true"
            )?.observe(requireActivity()) {
                //ProgressD.hideProgressDialog()
                if (it != null) {
                    // binding!!.checkBox.isChecked = false
                    if (it != null)
                        CommonMethod.getInstance(context).savePreference(AppConstant.KEY, it.key.toString())
                      strkey = it.key.toString()
                    var strkey = it.key
                    if (strkey != null) {
                        getHomeapgesunny(strkey.toString())
                    }
                    /*strkey?.let { it1 -> getHomeapgesunny(it1) }*/
                    // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                } else {
                    /*Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()*/
                }
            }
        }

        // Get user sign up response

    }
    //***********************************************For Ten days********************************************//
    private fun getTendaysWeather(strkey: String) {
        // Get user sign up response
        // key= CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY)
        if (this != null) {
            //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
            accountViewModel?.getTendays(strkey, "wkw7ho4Gya6HakuE7dNcEVEHIVJMZAhU")
                ?.observe(this) {
                    // ProgressD.hideProgressDialog()
                    if (this != null) {

                        val layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        binding.sevendayForecast.layoutManager = layoutManager
                        binding.sevendayForecast.setOnFlingListener(null);
                        val snapHelper: SnapHelper = PagerSnapHelper()
                        snapHelper.attachToRecyclerView(binding.sevendayForecast)
                        binding.sevendayForecast.adapter =
                            Matro_SevenDaysAdapter(requireContext(),
                                it?.dailyForecasts as List<DailyForecastsItem>)

                    }
                }
        }
    }

    private fun getHomeapgesunny(strkey: String) {
        // Get user sign up response
        // key= CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY)
        if (context != null) {
            //   ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
            accountViewModel?.getHomeapgesunny(strkey, "wkw7ho4Gya6HakuE7dNcEVEHIVJMZAhU", "true")
                ?.observe(requireActivity()) {
                    // ProgressD.hideProgressDialog()
                    binding.ivLoader.visibility=View.GONE

                    if (context != null) {
                        if (!requireActivity().isFinishing) {
                            if (context != null) {
                                if (it != null) {
                                    val Cloudy = null
                                    binding.seeAll.visibility= VISIBLE
                                    binding.poweredby.visibility= VISIBLE
                                   binding.iccuIocn.visibility= VISIBLE

                                   /* if (it[0].weatherText == "Partly sunny") {

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
                                         /*   binding.imghomesunriselogo.visibility = View.VISIBLE

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
                                          /*  binding.imghomesunriselogo.visibility = View.VISIBLE

                                            Glide.with(this)
                                                .load(R.drawable.nightmode)
                                                .into(binding.imghomesunriselogo)*/

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
                                        binding.txtNodata.visibility = View.GONE
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

                                        //  val solution: String ="%.2f".format(it[0].temperature?.metric?.value)


                                    /*    var a=tempValue[1].toInt()

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


                                    } else {
                                        binding.txtNodata.visibility = View.GONE
                                    }

                                    //   binding.mainLayout.textfeellike.setText(it.homePageSunnyResponse.get(0).f)
                                    // binding!!.checkBox.isChecked = false
                                    // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                                } else {
                                    /*Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()*/
                                }
                            }


                        }
                    }


                }
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mFusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    Log.e("TAG", "location is not null")
                    Log.d("lat", location.latitude.toString())
                    Log.d("long", location.longitude.toString())
                    getLocationAddress(location.latitude, location.longitude)
                    var letlong = location.latitude + location.longitude
                    lat = location.latitude.toString()
                    long = location.longitude.toString()
                    CommonMethod.getInstance(context).savePreference(AppConstant.location,lat.toString())
                    CommonMethod.getInstance(context).savePreference(AppConstant.Longituted,long.toString())
                     getHomeapge()
                 } else {

                    Log.e("TAG", "location is null")

                }
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

    private fun opendayaftertommorrowfragmentsMetrologist() {
        clickBtn=0

        val fragment: Fragment = DayofterTomorrowFragmentMetrologist()
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.viewPager, fragment)
        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit()
    }

    private fun opentomorrowfragmentsMetrologist() {
        clickBtn=0

        val fragment: Fragment = TomorrowVoteFragmentMetrologist()
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.viewPager, fragment)
        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit()
    }

    private fun openfragmentsMetrologist() {
        clickBtn=1
        val fragment: Fragment = ViewVotesFragmentMetrologist()
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.viewPager, fragment)
        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        //transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit()
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

    private fun getuserprofileMetrologist() {
       // ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        useridMetrologist = CommonMethod.getInstance(context).getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getuserprofileMetrologist(
            useridMetrologist, "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (isAdded) {
                    if (it != null && it.success == true) {
                        binding.textvoteweather.setText("Vote for " + it.data!!.city + " Weather")

                        // Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                        if(it!!.message.toString()=="Unauthenticated.")
                        {

                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatues, false)
                            CommonMethod.getInstance(requireContext()).savePreference(AppConstant.Key_ApplicationId,"0")

                            val intent = Intent(context, MetrologistLogInActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            context?.startActivity(intent)
                        }
                    }
                }
            }
    }
}