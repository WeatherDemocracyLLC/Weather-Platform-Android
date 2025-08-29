package com.webmobrilweatherapp.activities.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistChallengebyfriend_Adapter
import com.webmobrilweatherapp.adapters.Bottomdialogadapter
import com.webmobrilweatherapp.databinding.FragmentMetrologistChallengeByFriendsBinding
import com.webmobrilweatherapp.model.challengebyfriends.DataItem
import com.google.android.material.bottomsheet.BottomSheetDialog

class MetrologistChallengeByFriendsFragment : Fragment(),MetrologistChallengebyfriend_Adapter.SelectItem,MetrologistChallengebyfriend_Adapter.ChallengeItem {

    lateinit var binding: FragmentMetrologistChallengeByFriendsBinding
    var accountViewModel: AccountViewModel? = null
    var spinDuration1 = arrayOf("Select Temperature", "High", "Low")
    private lateinit var dialogs: BottomSheetDialog
    lateinit var tempId: String
    lateinit var text: String
    lateinit var  editSelect: TextView
    lateinit var  selectCity: TextView
    lateinit var tvRecycler: TextView
    lateinit var tempValue: EditText
    lateinit var selecttemp: Spinner
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


    private lateinit var bottomdialogadapter: Bottomdialogadapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentMetrologistChallengeByFriendsBinding.inflate(layoutInflater)

        getChallenegByFriends()
        return binding.root

    }


    private fun getChallenegByFriends() {


        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))


        accountViewModel?.getChallengeByFriends(requireContext(),"Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
            AppConstant.KEY_token_Metrologist))


            ?.observe(this) {
                ProgressD.hideProgressDialog()

                if (it != null && it.success== true) {
                    Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()


                    val layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        binding.recylerviewchallengebyfriends.layoutManager = layoutManager
                        binding.recylerviewchallengebyfriends.adapter =
                            MetrologistChallengebyfriend_Adapter(requireContext(),this,this,it.data as List<DataItem>)



                } else {

                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()

                }
            }

    }






    private fun isValidation(): Boolean {


        if (tempValue.text.isNullOrEmpty()) {
            tempValue.requestFocus()
            Toast.makeText(requireContext(), "Please Enter temp. value", Toast.LENGTH_LONG).show()
            return false

        }


        return true
    }


    private fun getAcceptChallenge(competitor_id:String,vote_temp_value_by_competitor:String,tempId:String,precipitation_id:String) {

        ProgressD.showLoading(requireContext(), getResources().getString(R.string.logging_in))
        accountViewModel?.getAcceptChallenge(competitor_id,vote_temp_value_by_competitor,tempId,precipitation_id,"Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
            AppConstant.KEY_token_Metrologist))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null)
                {

                    Toast.makeText(requireContext(), it?.message, Toast.LENGTH_LONG).show()
                    dialog.cancel()
                    //    getChallenegByFriends()
                } else {
                    Toast.makeText(requireContext(), it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun selectItem(id: String, temp: String, city: String, precption: String) {
        val btnsheet =
            layoutInflater.inflate(R.layout.voteforday_item, null)

        dialog = BottomSheetDialog(requireContext())
        val submitButton: Button = btnsheet.findViewById<Button>(R.id.btnsubmittomorw)
        tempValue=btnsheet.findViewById(R.id.edithintemp)


        selecttemp=btnsheet.findViewById(R.id.edithinttemppp)
        editSelect=btnsheet.findViewById(R.id.editSelet)

        selectCity=btnsheet.findViewById(R.id.editSelect)
       // selecttemp.setText(temp + "\u2103")
        selectCity.setText(city)
      //  editSelect.setText(precption)

        editSelect.setOnClickListener(View.OnClickListener {

            getprepration()

        })

        submitButton.setOnClickListener(View.OnClickListener {

            if (isValidation()) {

                getAcceptChallenge(id,tempValue.text.toString(),tempId,participantId.toString());

            }
        })
        if (selecttemp != null) {
            val adapter = ArrayAdapter(
                requireContext(),
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

        dialog.setContentView(btnsheet)
        dialog.show()
        dialog.setCancelable(true)

    }
    fun getprepration() {
        ProgressD.showLoading(requireContext(), getResources().getString(R.string.logging_in))
        accountViewModel?.getprepration(
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {


                    Dialogb()
                    bottomdialogadapter =
                        Bottomdialogadapter(requireContext(), it.data as List<com.webmobrilweatherapp.beans.bottomdialog.DataItem>)
                    val layoutManager =LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = bottomdialogadapter
                    bottomdialogadapter.setbottomInterface(object :
                        Bottomdialogadapter.BottonInterface {
                        override fun setOnItemClick(dataItem: com.webmobrilweatherapp.beans.bottomdialog.DataItem) {

                            editSelect.text = dataItem.precipitationName
                            participantId = dataItem.id!!

                            dialogs.cancel()
                        }

                    })
                    //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), it?.message, Toast.LENGTH_LONG).show()
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

    override fun challengeItem(
        challengeByMeName: String,
        meTemp: String,
        meTemptypr: String,
        location: String,
        date: String,
        mePreception: String,
        CompetetorName: String,
        comptTemp: String,
        comptTempType: String,
        comptPrecption: String,
    ) {
        val btnsheet =
            layoutInflater.inflate(R.layout.metrologistchallengefriend_vote_dialog, null)
        val dialog = Dialog(requireContext())

        var tempratureByme=btnsheet.findViewById<TextView>(R.id.temperatureindegree)
        var locationn=btnsheet.findViewById<TextView>(R.id.locationname)

        var TempratureTypeMe=btnsheet.findViewById<TextView>(R.id.tvspeed)
        var voteDate=btnsheet.findViewById<TextView>(R.id.date)
        var competetorName=btnsheet.findViewById<TextView>(R.id.challengeByComptname)
        var competetortemp=btnsheet.findViewById<TextView>(R.id.temperatureindegre)
        var competetorLocation=btnsheet.findViewById<TextView>(R.id.locationnamee)
        var competetorTempType=btnsheet.findViewById<TextView>(R.id.tvspeedd)
        var competetorvotedate=btnsheet.findViewById<TextView>(R.id.dateee)
        var mePrecption=btnsheet.findViewById<TextView>(R.id.precptionMe)
        var comptPrection=btnsheet.findViewById<TextView>(R.id.comptPrecption)
        var cross=btnsheet.findViewById<ImageView>(R.id.cross)
        var texttemptomorrow=btnsheet.findViewById<TextView>(R.id.texttemptomorrow)


        tempratureByme.setText(meTemp + "\u2103")
        locationn.setText(location)
        if (meTemptypr!="null") {

            if (meTemptypr == "1") {
                TempratureTypeMe.setText("High,")
            } else {
                TempratureTypeMe.setText("Low,")

            }
        }
        if(mePreception!=null)
        {
            if (mePreception=="1")
            {
                mePrecption.setText("No precipitation")
            }
            else if (mePreception=="2")
            {
                mePrecption.setText("Rain")
            }
            else if (mePreception=="3")
            {
                mePrecption.setText("Snow")
            }
            else if (mePreception=="4")
            {
                mePrecption.setText("Ice")
            }
            else if (mePreception=="5")
            {
                mePrecption.setText("Mixed")
            }
        }



        if(comptPrecption!=null)
        {
            if (comptPrecption=="1")
            {
                comptPrection.setText("No precipitation")
            }
            else if (comptPrecption=="2")
            {
                comptPrection.setText("Rain")
            }
            else if (comptPrecption=="3")
            {
                comptPrection.setText("Snow")
            }
            else if (comptPrecption=="4")
            {
                comptPrection.setText("Ice")
            }
            else if (comptPrecption=="5")
            {

                comptPrection.setText("Mixed")

            }
        }
        voteDate.setText(date)



        texttemptomorrow.setText("Challenge by "+CompetetorName)
        competetortemp.setText(comptTemp + "\u2103")
        competetorLocation.setText(location)

        if (comptTempType!="null")
        {
            if(comptTempType=="1")
            {
                competetorTempType.setText("High,")
            }
            else
            {
                competetorTempType.setText("Low,")

            }
        }

        competetorvotedate.setText(date)

        cross.setOnClickListener(View.OnClickListener {
            dialog.dismiss()

        })
        dialog.setContentView(btnsheet)
        dialog.show()
        dialog.setCancelable(true)

    }
}
