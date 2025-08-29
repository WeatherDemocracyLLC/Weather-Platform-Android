package com.webmobrilweatherapp.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.ButterFlyAdapter
import com.webmobrilweatherapp.databinding.ActivityButterflySpeicesBinding
import com.webmobrilweatherapp.model.butterflySpecies.DataItem

class ButterflySpeicesActivity : AppCompatActivity(), ButterFlyAdapter.SelectItem {

    lateinit var binding: ActivityButterflySpeicesBinding
    var accountViewModel: AccountViewModel? = null
    var idd="0"
    var checkStatus="0"
    var ButterflyIdactivitiy="0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_butterfly_speices)


        checkStatus= getIntent().getExtras()?.getString("checkStatus").toString()

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_butterfly_speices)
        ButterflyIdactivitiy= CommonMethod.getInstance(this).getPreference(AppConstant.Key_ID_Butterfly,"0")

        binding.btnproceed.setOnClickListener(View.OnClickListener {


            if(idd.equals("0"))
            {
                Toast.makeText(this, "Please select", Toast.LENGTH_LONG).show()
            }

            else{
                getSelectButterFly(idd,CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_id,0).toString())
            }


        })


        getButterFly()

        //**************************************Api Calling****************************************

    }

    private fun getButterFly() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))

        accountViewModel?.getButterFly()
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                   // Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    it.data?.forEach { item ->
                        if (item != null) {
                            if(item.id.toString()==ButterflyIdactivitiy.toString()) {
                            idd=item.id.toString();
                            }
                        }   // Logic for each item

                    }
                       val layoutManager =
                        GridLayoutManager(this, 3)
                    binding.recylerviewbutterfly.layoutManager = layoutManager
                    binding.recylerviewbutterfly.adapter = ButterFlyAdapter(this, it.data as List<DataItem>,this)

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
                        this?.startActivity(Intent(this, HomeActivity::class.java))

                    }

                    else{
                        this?.startActivity(Intent(this, LoginActivity::class.java))
                        Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

                    }

                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }

    }

    override fun selectItem(id: String)
    {
        idd=id
    }
}