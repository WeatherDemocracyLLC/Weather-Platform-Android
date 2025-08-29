package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistButterflyAdapter
import com.webmobrilweatherapp.databinding.ActivityMetrologistButterFlySpeicesBinding
import com.webmobrilweatherapp.model.butterflySpecies.DataItem

class MetrologistButterFlySpeicesActivity : AppCompatActivity(),MetrologistButterflyAdapter.SelectItem {

    lateinit var binding: ActivityMetrologistButterFlySpeicesBinding

    var accountViewModel: AccountViewModel? = null
    var idd="0"
    private var useridMetrologist = "0"
    var checkStatus="0"
    var ButterflyIdactivitiy="0"



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metrologist_butter_fly_speices)
        checkStatus= getIntent().getExtras()?.getString("checkStatus").toString()
        useridMetrologist = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_idMetrologist)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_metrologist_butter_fly_speices)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))


        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        ButterflyIdactivitiy= CommonMethod.getInstance(this).getPreference(AppConstant.Key_ID_MetrologistButterfly,"0")

        binding.btnproceed.setOnClickListener(View.OnClickListener {
            //   Toast.makeText(this, CommonMethod.getInstance(this).getPreference(AppConstant.USER_TYPE), Toast.LENGTH_LONG).show()


            if(idd.equals("0"))
            {
                Toast.makeText(this, "Please select", Toast.LENGTH_LONG).show()
            }

            else{
                getSelectButterFly(idd,useridMetrologist.toString())
            }


        })

        getButterFly()

    }


    private fun getButterFly() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))


        accountViewModel?.getButterFly()
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {

                    it.data?.forEach { item ->
                        if (item != null) {
                            if(item.id.toString()==ButterflyIdactivitiy.toString()) {
                                idd=item.id.toString();
                            }
                        }   // Logic for each item

                    }
                    // Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()


                    val layoutManager =
                        GridLayoutManager(this, 3)
                    binding.metrologistrecylerviewbutterfly.layoutManager = layoutManager
                    binding.metrologistrecylerviewbutterfly.adapter = MetrologistButterflyAdapter(this, it.data as List<DataItem>,this)


                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }

    }
    private fun getSelectButterFly(select_butterfly:String,user_id:String) {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

        accountViewModel?.getSelectButterFly(select_butterfly, user_id)
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null &&  it.success== true) {


                    if (checkStatus.equals("1"))
                    {
                        this?.startActivity(Intent(this, MetrilogistHomeActivity::class.java))

                    }
                else
                    {

                        Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                        this?.startActivity(Intent(this, MetrologistLogInActivity::class.java))

                    }



                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }

    }

    override fun selectItem(id: String) {
        idd=id
    }


}