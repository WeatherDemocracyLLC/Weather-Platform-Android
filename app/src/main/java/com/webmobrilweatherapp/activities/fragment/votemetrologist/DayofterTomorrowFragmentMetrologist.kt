package com.webmobrilweatherapp.activities.fragment.votemetrologist

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.activities.metrologistactivity.SendAlertActivity
import com.webmobrilweatherapp.activities.metrologistactivity.ViewVotingListActivity
import com.webmobrilweatherapp.adapters.Bottomdialogadapter
import com.webmobrilweatherapp.beans.bottomdialog.DataItem
import com.webmobrilweatherapp.utilise.CommonUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.FragmentDayofterTomorrowMetrologistBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class DayofterTomorrowFragmentMetrologist : Fragment() {
    lateinit var binding: FragmentDayofterTomorrowMetrologistBinding
    private var participantId = 0
    private lateinit var dialogs: BottomSheetDialog
    lateinit var recyclerView: RecyclerView
    var tempSpinner: HashMap<String, String> = HashMap()
    lateinit var tvRecycler: TextView
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    var spinDuration2 = arrayOf("Select Temperature", "High", "Low")
    var strdayoferdate = ""
    lateinit var tempId: String
    lateinit var text: String
    lateinit var city: String
    lateinit var let: String
    lateinit var longi: String
    private lateinit var bottomdialogadapter: Bottomdialogadapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding=FragmentDayofterTomorrowMetrologistBinding.inflate(layoutInflater)
        listnmer()
        binding.ImgViewUser.setOnClickListener {
            val i = Intent(activity, ViewVotingListActivity::class.java)
            startActivity(i)
        }
        binding.layoutSendWeathrAlart.setOnClickListener {
            var intent = Intent(context, SendAlertActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun listnmer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val dayoftertomorrow = current.plusDays(2)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            var dayofreanswers: String = dayoftertomorrow.format(formatter)
            strdayoferdate = dayofreanswers
            Log.d("answers", dayoftertomorrow.toString())

        }
        if (binding.editselecttemprature != null) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, spinDuration2
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

        binding.editSelect.setOnClickListener {
            getprepration()
        }
        binding.btnsubmittomorw.setOnClickListener {
            if (isValidation()) {
                getVotes()
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
        if (binding.editselecttemprature.selectedItem.equals(("Select Temperature"))) {
            binding.editselecttemprature.requestFocus()
            showToastMessage("Enter temperature")
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
        accountViewModelMetrologist?.getpreprationmetrologist(
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            ))
            ?.observe(requireActivity(), {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    Dialogb()
                    bottomdialogadapter =
                        Bottomdialogadapter(requireActivity(), it.data as List<DataItem>)
                    val layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
            })
    }
    private fun getVotes() {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        val ettemp: String = binding.editselecttemprature.toString()
        val tempvalue: String = binding.edithinttemp.text.toString()
        val strname = binding.editSelect.text.toString()
        accountViewModelMetrologist?.getVotemetrologist(
            tempId,
            tempvalue,
            participantId.toString(),
            strdayoferdate,
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity(), {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    binding.edithinttemp.text.clear()
                    binding.editSelect.text=""
                    if (binding.editselecttemprature != null) {
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item, spinDuration2
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
                    Toast.makeText(
                        context,
                        "You Already Voted For Day After Tommorow",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
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
}