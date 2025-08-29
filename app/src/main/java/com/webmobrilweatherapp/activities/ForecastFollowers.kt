package com.webmobrilweatherapp.activities
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.Bottomdialogadapter
import com.webmobrilweatherapp.adapters.ForecastFollowerAdapter
import com.webmobrilweatherapp.beans.bottomdialog.DataItem
import com.webmobrilweatherapp.databinding.ActivityForecastFollowersBinding
import com.webmobrilweatherapp.model.Followers.FollowerListItem
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class ForecastFollowers : AppCompatActivity(),ForecastFollowerAdapter.SelectItem {
    lateinit var binding: ActivityForecastFollowersBinding
    var accountViewModel: AccountViewModel? = null
    var spinDuration1 = arrayOf("Select Temperature", "High", "Low")
    private lateinit var dialogs: BottomSheetDialog
    lateinit var tempId: String
    lateinit var text: String
    var strkey: String=""

    lateinit var  editSelect:TextView
    lateinit var  selectCity:TextView
    lateinit var tvRecycler: TextView
    lateinit var tempValue:EditText
    lateinit var selecttemp:Spinner
    lateinit var dialog:BottomSheetDialog
    lateinit var recyclerView: RecyclerView
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    var city: String = "0"
    var cityy:String="0"
    var state: String = "0"
    var longituted = 0
    var zipCode = "0"
    var country = "0"
    var latitude = 0.0
    var longitude = 0.0
    var strdate = ""
    var participantId=0
    
     lateinit var forecastadapter:ForecastFollowerAdapter
    var courseModelArrayList: MutableList<FollowerListItem> = ArrayList<FollowerListItem>()
    private lateinit var bottomdialogadapter: Bottomdialogadapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_followers)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_forecast_followers)
        binding.imgbackicon.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })


        getFollowers()

        binding.searchview.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(arg0: Editable) {
                val text: String = binding.searchview.text.toString()
                filterResult(text,courseModelArrayList)

            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int,
                arg2: Int, arg3: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun onTextChanged(
                arg0: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                // TODO Auto-generated method stub
            }
        })
    }

    private fun filterResult(text: String, productFilterList: MutableList<FollowerListItem>) {
        val temp: MutableList<FollowerListItem> = ArrayList()
        for (d in productFilterList) {

            if (d.name != null) {
                if (d.name!!.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                    temp.add(d)
                }
            }

        }


        //update recyclerview
        //update recyclerview
       forecastadapter .updateList(temp)

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    override fun selectItem(id: String) {
        listnmer()
        val btnsheet =
            layoutInflater.inflate(R.layout.voteforday2_item, null)

         dialog = BottomSheetDialog(this,R.style.DialogStyle)
         val submitButton: Button = btnsheet.findViewById<Button>(R.id.btnsubmittomorw)
         tempValue=btnsheet.findViewById(R.id.edithintemp)


         selecttemp=btnsheet.findViewById(R.id.edithinttemppp)
         editSelect=btnsheet.findViewById(R.id.editSelet)

         selectCity=btnsheet.findViewById(R.id.editSelect)
         selectCity.setOnClickListener(View.OnClickListener {

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
        })


        editSelect.setOnClickListener(View.OnClickListener {

            getprepration()

        })





        submitButton.setOnClickListener(View.OnClickListener {

            if (isValidation()) {

                getChallengeVote(id,tempId,participantId.toString(),tempValue.text.toString(),strdate,city,strkey)
                 //Toast.makeText(this, strkey.toString(), Toast.LENGTH_LONG).show()

                /*      Toast.makeText(this, "id"+id.toString(), Toast.LENGTH_LONG).show()
                      Toast.makeText(this, "tempId"+tempId.toString(), Toast.LENGTH_LONG).show()
                      Toast.makeText(this, "parti"+participantId.toString(), Toast.LENGTH_LONG).show()
                      Toast.makeText(this, "tempValue"+tempValue.text.toString(), Toast.LENGTH_LONG).show()
                      Toast.makeText(this, "strdate"+strdate.toString(), Toast.LENGTH_LONG).show()
                      Toast.makeText(this, "city"+city.toString(), Toast.LENGTH_LONG).show()
                      Toast.makeText(this, "zipcode"+zipCode.toString(), Toast.LENGTH_LONG).show()
      */
            }
        })


        if (selecttemp != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, spinDuration1
            )
            selecttemp.adapter = adapter


            selecttemp.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {

                    text = parent?.getItemAtPosition(position).toString()
                    if (text.equals("High")) {
                        tempId = "1"
                    } else {
                        tempId = "0"
                    }

                    Log.e("erosejfgr", text)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }

       /* fun getprepration() {
            ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
            accountViewModel?.getprepration(
                "Bearer " + CommonMethod.getInstance(this).getPreference(
                    AppConstant.KEY_token
                )
            )
                ?.observe(this) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.code == 200) {

                        Dialogb()
                        bottomdialogadapter =
                            Bottomdialogadapter(this, it.data as List<DataItem>)
                        val layoutManager =LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = bottomdialogadapter
                        bottomdialogadapter.setbottomInterface(object :
                            Bottomdialogadapter.BottonInterface {
                            override fun setOnItemClick(dataItem: DataItem) {
                                dialogs.dismiss()
                                editSelect.text = dataItem.precipitationName
                                //participantId = dataItem.id!!

                            }
                        })
                        //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }*/

        dialog.setContentView(btnsheet)
        dialog.show()
        dialog.setCancelable(true)

    }

    fun getprepration() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getprepration(
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {


                    Dialogb()
                    bottomdialogadapter =
                        Bottomdialogadapter(this, it.data as List<DataItem>)
                    val layoutManager =LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = bottomdialogadapter
                    bottomdialogadapter.setbottomInterface(object :
                        Bottomdialogadapter.BottonInterface {
                        override fun setOnItemClick(dataItem: DataItem) {

                            editSelect.text = dataItem.precipitationName
                            participantId = dataItem.id!!

                            dialogs.cancel()
                        }


                    })
                    //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun Dialogb() {
        dialogs = BottomSheetDialog(this,R.style.DialogStyle)
        val view = layoutInflater.inflate(R.layout.homescreenbottom, null)
        recyclerView = view.findViewById(R.id.recylerviewbottom)!!
        tvRecycler = view.findViewById(R.id.textselectprecipitation)!!
        dialogs.setCancelable(true)
        dialogs.setContentView(view)
        dialogs.show()
    }

    private fun getFollowers() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

        accountViewModel?.getFollowers("Bearer " + CommonMethod.getInstance(this).getPreference(
            AppConstant.KEY_token))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it!!.followerList!!.size>0) {
                    binding.searchview.visibility=View.VISIBLE

                    binding.totalfollowers.text=it.followerList?.size.toString()+" Followers"

                    courseModelArrayList?.clear()
                    if (it.followerList!!.size>0){
                        courseModelArrayList?.addAll(it.followerList as List<FollowerListItem>)
                    }


                  forecastadapter= ForecastFollowerAdapter(this,this,it.followerList as List<FollowerListItem>)
                    val layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.forecastfollowerRecy.layoutManager = layoutManager
                    binding.forecastfollowerRecy.adapter=forecastadapter
                } else {
                    binding.searchview.visibility=View.GONE
                    Toast.makeText(this,"Data not found!", Toast.LENGTH_LONG).show()
                }


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
                    getDefaultAddress(latitude,longitude)
                    var cityjgkjfd=place.plusCode
                    Log.d("TAG", "gfgfdgdfgdfg: "+latitude+longitude)
                    // getCompleteAddress(latitude,longitude)
                    getalllocation(latitude.toString(),longitude.toString())
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
        
    }
    private fun getalllocation(latitude: String, longitude: String) {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        accountViewModel?.getalllocation(
            (latitude+","+longitude),"AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM"
        )?.observe(this) {
            //ProgressD.hideProgressDialog()
            if (it != null) {


                if (it.plusCode!!.compoundCode!=null){
                    Log.d("TAG", "ljgdfjgdfjg: "+latitude+longitude)
                    CommonMethod.getInstance(this).savePreference(AppConstant.location, latitude)
                    CommonMethod.getInstance(this).savePreference(AppConstant.Longituted, longitude)
                    var date =it.plusCode!!.compoundCode
                    val upToNCharacters: String = date!!.substring(8, Math.min(date.length,40))
                      cityy=date!!.substring(8, Math.min(date.length,40))
                        selectCity.setText(city)

                    ///////////////////////////////////////////////////////////////////

                    accountViewModel?.getHomeapge(
                        "AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM", latitude + "," + longitude,
                        "En",
                        "true"
                    )?.observe(this) {
                        //ProgressD.hideProgressDialog()
                        if (it != null) {
                            // binding!!.checkBox.isChecked = false
                            if (it != null)

                                strkey = it.key.toString()
                            Log.d("TAG", "gfhjkhgjkh: "+strkey)
                            if (strkey != null) {
                                //getTendaysWeather(strkey.toString())
                                // Toast.makeText(requireContext(), strkey.toString(), Toast.LENGTH_LONG).show()


                            }

                            /*strkey?.let { it1 -> getHomeapgesunny(it1) }*/
                            // intent.putExtra("email", it.result!!.email,Toast.LENGTH_LONG)
                        } else{


                            /*Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()*/


                        }
                    }


/////////////////////////////////////////////////////////////////////////
                }else{
                    Toast.makeText(this,"Please Enter Valid City Name",Toast.LENGTH_LONG).show()
                }

            } else {
                //Toast.makeText(context, , Toast.LENGTH_LONG).show()
            }

        }
        // Get user sign up response

    }
    private fun getDefaultAddress(la: Double, lang: Double): String {
        val address = ""
        var addresses: List<Address> = java.util.ArrayList()
        val geocoder = Geocoder(this, Locale.getDefault())
        try {

            addresses = geocoder.getFromLocation(
                la,
                lang,
                1
            )!! // Here 1 represent max location result to returned, by documents it recommended 1 to 5
           // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()




            city = addresses[0].locality.toUpperCase()
            state = addresses[0].adminArea
            val country = addresses[0].countryName
            zipCode = addresses[0].postalCode
            city=addresses[0].locality
            // mLocationMarkerText!!.setText(addressLine)
        } catch (e: Exception) {
            // Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        return address

    }
    private fun listnmer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val tomorrow = current.plusDays(1)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            var answers: String = tomorrow.format(formatter)
            strdate = answers
            Log.d("answers", tomorrow.toString())

        }
    }

    //*****************************************************************************************************//

    private fun getChallengeVote(competitor_id:String,is_temp:String,precipitation_id:String,vote_temp_value:String,vote_date:String,city:String,city_code:String) {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

        accountViewModel?.getChallengevote("Bearer " + CommonMethod.getInstance(this).getPreference(
            AppConstant.KEY_token),competitor_id,is_temp,precipitation_id,vote_temp_value,vote_date,city,city_code)
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                this?.startActivity(Intent(this, ForecastScreen::class.java))

                if (it != null && it.error== true) {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                  //  this?.startActivity(Intent(this, ForecastFollowers::class.java))
                    dialog.cancel()
                 //   getFollowers()
                    this?.startActivity(Intent(this, ForecastScreen::class.java))
                    /*val layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.forecastfollowerRecy.layoutManager = layoutManager
                    binding.forecastfollowerRecy.adapter =
                      ForecastFollowerAdapter(this,this,it.followingList as List<FollowingListItem>)*/

                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun isValidation(): Boolean {


        if (selecttemp.selectedItem.equals("Select Temperature")) {


            selecttemp.requestFocus()
            Toast.makeText(this, "Enter Temperature", Toast.LENGTH_LONG).show()
            return false

        }


        else if(selectCity.text.equals("Select"))
        {
            selectCity.requestFocus()
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_LONG).show()
            return false

        }
        else if (tempValue.text.isNullOrEmpty())
        {

            tempValue.requestFocus()
            Toast.makeText(this, "Please Enter temp. value", Toast.LENGTH_LONG).show()
            return false
        }
        else if (editSelect.text.toString().equals("Select")) {
            editSelect.requestFocus()

            Toast.makeText(this, "Please Select precipitation", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }


}

