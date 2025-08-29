package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.fragment.instafollowmetrologist.MetrologistAboutFollowFragment
import com.webmobrilweatherapp.activities.fragment.instafollowmetrologist.MetrologistMayorFollowFragment
import com.webmobrilweatherapp.activities.fragment.instafollowmetrologist.MetrologistPhotoFollowFragment
import com.webmobrilweatherapp.activities.fragment.instafollowmetrologist.MetrologistPostFollowFragment
import com.webmobrilweatherapp.databinding.ActivityMetrologistInstaHomeMetrologistBinding
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel

class MetrologistInstaHomeMetrologistActivity : AppCompatActivity() {
    lateinit var binding:ActivityMetrologistInstaHomeMetrologistBinding
    var clicked = false
    var UserIds = 0
    var eventid = 0
    var points:Int=0
    var followings: Boolean = false
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private lateinit var dialoges: BottomSheetDialog
    private lateinit var layoutonepoint: ConstraintLayout
    private lateinit var layoutonepointgreen: ConstraintLayout
    private lateinit var layouttwopoint: ConstraintLayout
    private lateinit var layouttwopointgreen: ConstraintLayout
    private lateinit var layoutthreepoint: ConstraintLayout
    private lateinit var layoutthreepointgreen: ConstraintLayout
    private lateinit var dotedone: ImageView
    private lateinit var dotedonegreen: ImageView
    private lateinit var imgdonedoted: ImageView
    private lateinit var dotedtwogreen: ImageView
    private lateinit var Imgdotedthree: ImageView
    private lateinit var dotedthreegreen: ImageView
    private lateinit var ImgCross: ImageView
    private lateinit var txtpoints: TextView
    private lateinit var img1subbutterFly:ImageView
    private lateinit var img2subbutterFly:ImageView
    private lateinit var img3subbutterFly:ImageView
    private lateinit var img4subbutterFly:ImageView
    private lateinit var pgBar: ProgressBar
    var requestFromm=0
    var accountViewModel: AccountViewModel? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_metrologist_insta_home_metrologist)
        setupViewPager(binding.ViewPagerFollow)
        binding.simpleTabLayoutFollow.setupWithViewPager(binding.ViewPagerFollow)
        UserIds = intent.getIntExtra("userIds", UserIds)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.toolbaar))
        getuserprofileMetrologist()
        binding.layoutChat.setOnClickListener {
            var intent = Intent(this, MetrologistChatActivity::class.java)
            intent.putExtra("userIds", UserIds)
            startActivity(intent)
        }
        binding.imgbadgeszero.setOnClickListener {
            showBottomSheetDialogbges()
        }


        binding.layoutfollowes.setOnClickListener {
            if (binding.btnfollow.text.toString().equals("Follow")) {
                binding.btnfollow.setText("Following")
                binding.btnfollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                //clicked = true
                eventid=1
                getUserflow(eventid.toString())
            } else {
                binding.btnfollow.setText("Follow")
                // clicked = false
                binding.btnfollow.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.followicon,
                    0,
                    0,
                    0
                );
                eventid=2
                getUserflow(eventid.toString())
            }
        }
        binding.conshomeinsta.setOnClickListener {
            if (binding.btnfollow.text.toString().equals("Follow")) {
                binding.btnfollow.setText("Following")
                binding.btnfollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                //clicked = true
                eventid=1
                getUserflow(eventid.toString())
            } else {
                binding.btnfollow.setText("Follow")
                // clicked = false
                binding.btnfollow.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.followicon,
                    0,
                    0,
                    0
                );
                eventid=2
                getUserflow(eventid.toString())
            }
        }

        binding.confirmBtn.setOnClickListener(View.OnClickListener {
            //  Toast.makeText(this, requestFromm.toString(), Toast.LENGTH_LONG).show()

            getAcceptReject("1",requestFromm.toString())
        })
        binding.deleteBtn.setOnClickListener(View.OnClickListener {
            //  Toast.makeText(this, requestFromm.toString(), Toast.LENGTH_LONG).show()

            getAcceptReject("2",requestFromm.toString())

        })


    }
    //******************************************************************************************//


    private fun getAcceptReject(status: String,requestFrom:String) {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getRequestAcceptReject(status,requestFrom,
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()

                if (it != null && it.success == true) {

                    // getnotification()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    getuserprofileMetrologist()
                    // onBackPressed()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }


    }
    private fun getUserflow(eventid: String) {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getUserflowMetrologist(
            UserIds.toString(), eventid, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    //Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    getuserprofileMetrologist()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun getuserprofileMetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getuserprofileMetrologist(
            UserIds.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    points= it.data!!.points!!.toInt()
                    followings=it.data!!.followbyloginuser!!
                    if (it.data.followRequestdata?.notificationData?.requestFrom != null) {
                        requestFromm= it.data.followRequestdata?.notificationData?.requestFrom!!
                    }

                    if (it.data.followRequestdata==null)
                    {
                        binding.wantToFollowingYou.visibility= GONE
                        binding.confirmDeleteConst.visibility= GONE
                    }
                    else
                    {
                        binding.wantToFollowingYou.visibility= VISIBLE
                        binding.confirmDeleteConst.visibility= VISIBLE
                    }
                    if(it.data.followbyRequest==0)
                    {

                        binding.btnfollowres.setText("Follow")
                        binding.privateCons.visibility=View.VISIBLE
                        //  binding.wantToFollowingYou.visibility= VISIBLE
                        //   binding.confirmDeleteConst.visibility= VISIBLE
                    }
                    else if (it.data.followbyRequest==1)
                    {
                        binding.privateCons.visibility= GONE
                        binding.btnfollowres.setText("Following")
                        binding.btnfollow.setText("Following")
                        binding.wantToFollowingYou.visibility= GONE
                        binding.confirmDeleteConst.visibility= GONE
                    }
                    else if (it.data.followbyRequest==2)
                    {

                        binding.btnfollowres.setText("Requested")
                        binding.privateCons.visibility=View.VISIBLE
                        //  binding.wantToFollowingYou.visibility= VISIBLE
                        //  binding.confirmDeleteConst.visibility= VISIBLE

                    }




                    if (followings==false) {
                      //  binding.btnfollow.setText("Follow")
                        binding.layoutChat.visibility= View.GONE
                        binding.layoutpostInsta.visibility= View.GONE
                       // binding.txtfolllowthem.visibility= View.VISIBLE
                        binding.conshomeinsta.visibility= View.GONE
                        binding.layoutfollowes.visibility= View.VISIBLE
                        binding.btnfollow.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.followicon,
                            0,
                            0,
                            0
                        );
                    } else {
                        if (it.data.followRequestdata==null)
                        {
                            binding.wantToFollowingYou.visibility= GONE
                            binding.confirmDeleteConst.visibility= GONE
                        }
                        else
                        {
                            binding.wantToFollowingYou.visibility= VISIBLE
                            binding.confirmDeleteConst.visibility= VISIBLE
                        }

                        //binding.btnfollow.setText("Following")
                        binding.layoutChat.visibility=VISIBLE
                        binding.layoutpostInsta.visibility=VISIBLE
                        binding.conshomeinsta.visibility=VISIBLE
                        //  binding.txtfolllowthem.visibility=GONE
                        binding.privateCons.visibility= GONE

                        binding.layoutfollowes.visibility=GONE
                        binding.btnfollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }


                    binding.textDavidShaw.setText(it.data?.name)
                    var postcount = it.data?.postCount
                    binding.wantToFollowingYou.setText(it.data?.name+" wants to follow you")

                    binding.textinsta1.setText(postcount.toString())
                    var follwoer = it.data?.followers
                    binding.textinsta2.setText(follwoer.toString())
                    // var follwoing=it.data?.following
                    binding.textinsta3.setText(it.data?.following.toString())
                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL + it.data?.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.imgProfile)

                    if(it.data.profileStatus==1)
                    {
                        //binding.privateCons.visibility=View.VISIBLE
                    }
                    else
                    {

                        binding.layoutChat.visibility=VISIBLE
                        binding.layoutpostInsta.visibility=VISIBLE
                        binding.conshomeinsta.visibility=VISIBLE
                        //binding.txtfolllowthem.visibility=GONE
                        binding.privateCons.visibility=GONE

                        binding.layoutfollowes.visibility=GONE
                        binding.btnfollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


                    }


                    if(it!!.data?.butterflies!=null)
                    {
                        if (points==0||points<=99){

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(0)!!.butterfliesImage)
                                .placeholder(R.drawable.edit_profileicon)
                                .into(binding.imgbadgeszero)


                        }else if (points==100||points<=199){

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(1)!!.butterfliesImage)
                                .placeholder(R.drawable.edit_profileicon)
                                .into(binding.imgbadgeszero)

                        } else if (points==200||points<=299){
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(2)!!.butterfliesImage)
                                .placeholder(R.drawable.edit_profileicon)
                                .into(binding.imgbadgeszero)

                        }else if (points==300||points<=100000000000){
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.butterfliesImage)
                                .placeholder(R.drawable.edit_profileicon)
                                .into(binding.imgbadgeszero)

                        }

                    }



                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MetrologistPostFollowFragment(), "Post",UserIds.toString())
        adapter.addFragment(MetrologistPhotoFollowFragment(), "      Photo",UserIds.toString())
        adapter.addFragment(MetrologistMayorFollowFragment(), "           Mayor",UserIds.toString())
        adapter.addFragment(MetrologistAboutFollowFragment(), "               About",UserIds.toString())
        viewPager.adapter = adapter
    }


    internal class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }
        override fun getCount(): Int {
            return mFragmentList.size
        }
       /* fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }*/
       fun addFragment(fragment: Fragment, title: String, id: String) {
           val bundle = Bundle()
           bundle.putString("uID", id) // Pass the ID to the fragment
           fragment.arguments = bundle // Set arguments to the fragment
           mFragmentList.add(fragment)
           mFragmentTitleList.add(title)
       }
        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun showBottomSheetDialogbges() {

        dialoges = BottomSheetDialog(this,R.style.DialogStyle)
        dialoges.setContentView(R.layout.dialogbadges)
        layoutonepoint = dialoges.findViewById(R.id.layoutonepoint)!!
        dotedone = dialoges.findViewById(R.id.dotedone)!!
        dotedtwogreen = dialoges.findViewById(R.id.dotedtwogreen)!!
        dotedonegreen = dialoges.findViewById(R.id.dotedonegreen)!!
        imgdonedoted = dialoges.findViewById(R.id.imgdonedoted)!!
        Imgdotedthree = dialoges.findViewById(R.id.Imgdotedthree)!!
        dotedthreegreen = dialoges.findViewById(R.id.dotedthreegreen)!!
        dotedonegreen = dialoges.findViewById(R.id.dotedonegreen)!!
        layoutonepointgreen = dialoges.findViewById(R.id.layoutonepointgreen)!!
        layouttwopoint = dialoges.findViewById(R.id.layouttwopoint)!!
        layouttwopointgreen = dialoges.findViewById(R.id.layouttwopointgreen)!!
        layoutthreepoint = dialoges.findViewById(R.id.layoutthreepoint)!!
        layoutthreepointgreen = dialoges.findViewById(R.id.layoutthreepointgreen)!!
        txtpoints = dialoges.findViewById(R.id.txtpoints)!!
        ImgCross = dialoges.findViewById(R.id.ImgCross)!!
        txtpoints.setText(points.toString()+" pts")
        img1subbutterFly=dialoges.findViewById(R.id.imgbedgete)!!
        img2subbutterFly=dialoges.findViewById(R.id.imgonepoint)!!
        img3subbutterFly=dialoges.findViewById(R.id.imgtwopoint)!!
        img4subbutterFly=dialoges.findViewById(R.id.imgthreepoint)!!

        pgBar=dialoges.findViewById(R.id.ivLoader)!!
        ImgCross.setOnClickListener {
            dialoges.hide()
        }
        txtpoints.setText(points.toString()+" Points")
        //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        pgBar.visibility=View.VISIBLE


        accountViewModel?.getuserprofile(
            UserIds.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                //  ProgressD.hideProgressDialog()
                pgBar.visibility=View.GONE

                if (it != null && it.success == true) {

                    if(it.data?.butterflies!=null)
                    {
                        if(it.data?.butterflies!=null) {


                            if (points == 0 || points <= 99) {


                                Glide.with(this)
                                    .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                        0)!!.butterfliesImage)
                                    .placeholder(R.drawable.edit_profileicon)
                                    .into(img1subbutterFly)


                            } else if (points == 100 || points <= 199) {

                                Glide.with(this)
                                    .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                        0)!!.butterfliesImage)
                                    .placeholder(R.drawable.edit_profileicon)
                                    .into(img1subbutterFly)

                                Glide.with(this)
                                    .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                        1)!!.butterfliesImage)
                                    .placeholder(R.drawable.edit_profileicon)
                                    .into(img2subbutterFly)


                            } else if (points == 200 || points <= 299) {
                                Glide.with(this)
                                    .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                        0)!!.butterfliesImage)
                                    .placeholder(R.drawable.edit_profileicon)
                                    .into(img1subbutterFly)

                                Glide.with(this)
                                    .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                        1)!!.butterfliesImage)
                                    .placeholder(R.drawable.edit_profileicon)
                                    .into(img2subbutterFly)
                                Glide.with(this)
                                    .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                        2)!!.butterfliesImage)
                                    .placeholder(R.drawable.edit_profileicon)
                                    .into(img3subbutterFly)


                            } else if (points == 300 || points <= 100000000000) {

                                Glide.with(this)
                                    .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                        0)!!.butterfliesImage)
                                    .placeholder(R.drawable.edit_profileicon)
                                    .into(img1subbutterFly)

                                Glide.with(this)
                                    .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                        1)!!.butterfliesImage)
                                    .placeholder(R.drawable.edit_profileicon)
                                    .into(img2subbutterFly)
                                Glide.with(this)
                                    .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                        2)!!.butterfliesImage)
                                    .placeholder(R.drawable.edit_profileicon)
                                    .into(img3subbutterFly)
                                Glide.with(this)
                                    .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.butterfliesImage)
                                    .placeholder(R.drawable.edit_profileicon)
                                    .into(img4subbutterFly)

                            }
                        }
                    }

                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
                // ProgressD.hideProgressDialog()

            }


/*
        if (points==0||points<=9){
        }else if (points==10||points<=19){
            layoutonepointgreen.visibility= VISIBLE
            dotedone.visibility= GONE
            dotedonegreen.visibility= VISIBLE
        } else if (points==20||points<=29){
            layouttwopointgreen.visibility=View.VISIBLE
            layoutonepointgreen.visibility=View.VISIBLE
            imgdonedoted.visibility=View.GONE
            dotedonegreen.visibility= VISIBLE
            dotedtwogreen.visibility= VISIBLE

        }else if (points==30||points<=100000000000){
            layouttwopointgreen.visibility=View.VISIBLE
            layoutonepointgreen.visibility=View.VISIBLE
            layoutthreepointgreen.visibility=View.VISIBLE
            Imgdotedthree.visibility=View.GONE
            dotedonegreen.visibility= VISIBLE
            dotedtwogreen.visibility= VISIBLE
            dotedthreegreen.visibility= VISIBLE
        }
*/







        // dialogs.window!!.setBackgroundDrawableResource(R.drawable.dialog_curved_bg_inset)
        //  dialogBottom.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialoges.show()

     }
}