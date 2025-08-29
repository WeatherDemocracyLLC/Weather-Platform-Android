package com.webmobrilweatherapp.activities.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistForecastFolowers
import com.webmobrilweatherapp.adapters.challenegbyme_Adpater
import com.webmobrilweatherapp.databinding.FragmentMetrologistChallengeByMeBinding
import com.webmobrilweatherapp.model.challengebyme.DataItem

class MetrologistChallengeByMeFragment : Fragment(),challenegbyme_Adpater.SelectItem {
    lateinit var binding:  FragmentMetrologistChallengeByMeBinding
    var accountViewModel: AccountViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentMetrologistChallengeByMeBinding.inflate(layoutInflater)
        binding.btnchallengeUser.setOnClickListener(View.OnClickListener {


            this?.startActivity(Intent(context, MetrologistForecastFolowers::class.java))
        })


        getChallenegByMe()
        return binding.root


    }
    private fun getChallenegByMe() {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))

        accountViewModel?.getChallengeByMe(requireContext(),"Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
            AppConstant.KEY_token_Metrologist))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it!!.data!!.size > 0) {

                    val layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.recylerviewchallengebyMe.layoutManager = layoutManager
                    binding.recylerviewchallengebyMe.adapter =
                        challenegbyme_Adpater(requireContext(),this,it.data as List<DataItem>)


                } else {
                    Toast.makeText(context,"Data not found!", Toast.LENGTH_LONG).show()
                }
            }

    }

    override fun selectItem(
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
            layoutInflater.inflate(R.layout.metrologistchallenge_vote_dialog, null)
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



        competetorName.setText("Challenge by "+CompetetorName)
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
