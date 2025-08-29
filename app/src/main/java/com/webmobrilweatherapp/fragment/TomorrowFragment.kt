package com.webmobrilweatherapp.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.adapters.Bottomdialogadapter
import com.webmobrilweatherapp.beans.bottomdialog.DataItem
import com.webmobrilweatherapp.databinding.FragmentTomorrowBinding
import com.webmobrilweatherapp.utilise.CommonUtil
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TomorrowFragment : Fragment(), View.OnClickListener {

    private var participantId = 0
    private lateinit var dialogs: BottomSheetDialog
    lateinit var binding: FragmentTomorrowBinding
    var accountViewModel: AccountViewModel? = null
    lateinit var recyclerView: RecyclerView
    lateinit var tvRecycler: TextView
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    var spinDuration1 = arrayOf("Select Temperature", "High", "Low")
    var strdate = ""
    lateinit var tempId: String
    lateinit var text: String
    lateinit var city: String
    lateinit var let: String
    lateinit var longi: String
    private lateinit var bottomdialogadapter: Bottomdialogadapter


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTomorrowBinding.inflate(layoutInflater)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        listener()
        listnmer()
        getusermayorhomepages()

        binding.constrainttomorrow.setOnClickListener {
            val i = Intent(context, TopFiveMetrologistActivity::class.java)
            startActivity(i)
        }

        binding.layoutMayorodthecitry.setOnClickListener {
            val i = Intent(context, WeatherMayorActivity::class.java)
            startActivity(i)
        }

        return binding.root

        //bottomSheetDialog
       // getLastLocation()
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

        if (binding.editselecttemprature != null) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, spinDuration1)
            binding.editselecttemprature.adapter = adapter
            binding.editselecttemprature.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    text = parent?.getItemAtPosition(position).toString()
                    if (text.equals("High")) {
                        tempId = "1"
                    } else {
                        tempId = "2"
                    }
                    Log.e("erosejfgr", text)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

    }

    private fun listener() {

        binding.editSelect.setOnClickListener(this)
        binding.btnsubmittomorw.setOnClickListener(this)
        //   binding.editselecttemprature.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.editSelect -> {

                getprepration()
            }
            R.id.btnsubmittomorw -> {
                if (isValidation()) {
                    getVotes()
                }
            }
        }
    }

    private fun Dialogb() {
        dialogs = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.homescreenbottom, null)
        recyclerView = view.findViewById(R.id.recylerviewbottom)!!
        tvRecycler = view.findViewById(R.id.textselectprecipitation)!!
        dialogs.setCancelable(true)
        dialogs.setContentView(view)
        dialogs.show()
    }

    private fun isValidation(): Boolean {
        if (binding.editselecttemprature.selectedItem.equals("Select Temperature")) {
            binding.editselecttemprature.requestFocus()
            showToastMessage("Enter Temperature")
            return false
        } else if (binding.edithinttemp.text.isNullOrEmpty()) {
            binding.edithinttemp.requestFocus()
            showToastMessage("Please Enter temp. value")
            return false
        } else if (binding.editSelect.text.toString().equals("Select")) {
            binding.editSelect.requestFocus()
            showToastMessage("Please Select precipitation")
            return false
        }
        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    private fun getprepration() {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        accountViewModel?.getprepration(
            "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    Dialogb()
                    bottomdialogadapter =Bottomdialogadapter(requireActivity(), it.data as List<DataItem>)
                    val layoutManager =LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = bottomdialogadapter
                    bottomdialogadapter.setbottomInterface(object :
                        Bottomdialogadapter.BottonInterface {
                        override fun setOnItemClick(dataItem: DataItem) {
                            dialogs.dismiss()
                            binding.editSelect.text = dataItem.precipitationName
                            participantId = dataItem.id!!
                        }
                    })
                    //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun getVotes() {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        text = binding.editselecttemprature.toString()
        val tempvalue: String = binding.edithinttemp.text.toString()
        val strname = binding.editSelect.text.toString()
        //val etpassword: String = binding.editpassworde.text.toString()
        accountViewModel?.getVote(
            tempId,
            tempvalue,
            participantId.toString(),
            strdate,
            "Bearer " + CommonMethod.getInstance(context).getPreference(AppConstant.KEY_token)
        )

            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    binding.edithinttemp.text?.clear()
                    binding.editSelect.text=""
                    if (binding.editselecttemprature != null) {
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item, spinDuration1
                        )
                        binding.editselecttemprature.adapter = adapter


                        binding.editselecttemprature.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View, position: Int, id: Long
                            ) {

                                text = parent?.getItemAtPosition(position).toString()
                                if (text.equals("High")) {
                                    tempId = "1"
                                } else {
                                    tempId = "2"
                                }
                                Log.e("erosejfgr", text)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }
                        }
                    }




                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mFusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    Log.e("TAG", "location is not null")
                    Log.d("lat", location.latitude.toString())
                    Log.d("long", location.longitude.toString())
                    getLocationAddress(location.latitude, location.longitude)
                    var letlong = location.latitude + location.longitude
                     let = location.latitude.toString()
                     longi = location.longitude.toString()
                    Log.d("TAG", "getlet: "+let)
                    Log.d("TAG", "getlongi: "+longi)
                     //binding.textentertempvalue.setText(let+","+longi)
                } else {
                    Log.e("TAG", "location is null")
                }
            }
    }
    private fun getLocationAddress(latitude: Double, longitude: Double) {
        try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addressesList: MutableList<Address>? =
                geocoder.getFromLocation(latitude, longitude, 1)
            val location1 = addressesList?.get(0)?.getAddressLine(0)
              city = addressesList?.get(0)?.locality.toString()
            Log.d("TAG", "getcity: "+city)
            //   binding.layoutContent.toolbar.txtLocation.setText(countryname+","+city)
          //  binding.textentertempvalue.setText(city)
            if (location1 != null) {
                CommonUtil.showMessage(requireContext(), location1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getusermayorhomepages() {
        //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        accountViewModel?.getusermayorhomepages("Bearer " + CommonMethod.getInstance(context).getPreference(
            AppConstant.KEY_token)
        )
            ?.observe(requireActivity()) {
                //ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.mayor?.name!=null){
                        binding.txtcomingsoon.visibility=GONE
                        binding.imagehomegrupuser.setText(it.mayor!!.name+ "\nis the mayor of the\n"+it.mayor.city+" city")
                        Glide.with(this)
                            .load(ApiConstants.IMAGE_URL +it.mayor.profileImage)
                            .placeholder(R.drawable.edit_profileicon)
                            .into(binding.imagehomelogouser)
                    }else{
                        binding.txtcomingsoon.visibility= VISIBLE
                    }


                } else {
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}