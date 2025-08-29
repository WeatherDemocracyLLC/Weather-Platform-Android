package com.webmobrilweatherapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityInstaHomeBinding
import com.webmobrilweatherapp.fragment.instafollow.UserAboutFollowFragment
import com.webmobrilweatherapp.fragment.instafollow.UserMayorFollowFragment
import com.webmobrilweatherapp.fragment.instafollow.UserPhotoFollowFragment
import com.webmobrilweatherapp.fragment.instafollow.UserPostFollowFragment
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.google.android.material.bottomsheet.BottomSheetDialog


class InstaHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityInstaHomeBinding
    var accountViewModel: AccountViewModel? = null
    var UserIds = 0
    var type :Int= 0
     var clicked = false
    var eventid = 0
    var points:Int=0
    var followings: Boolean = false
    private lateinit var dialoges: BottomSheetDialog
    private lateinit var layoutzeropoint: ConstraintLayout

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
    private lateinit var imgonepointgreen: ImageView
    private lateinit var imgonepoint: ImageView
    //---------------------------//
    private lateinit var img1subbutterFly:ImageView
    private lateinit var img2subbutterFly:ImageView
    private lateinit var img3subbutterFly:ImageView
    private lateinit var img4subbutterFly:ImageView
    private lateinit var pgBar: ProgressBar
    var requestFromm=0
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserIds = intent.getIntExtra("userIds", UserIds)
        Log.d("TAG", "23456789765432567 "+UserIds)

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = ActivityInstaHomeBinding.inflate(layoutInflater)
        val intent = intent

        if (intent != null) {

             type = intent.getIntExtra("type", 1)
             Log.d("TAG", "typessdfdsf "+type)

        }
        setupViewPager(binding.viewPager)
        binding.tabProfile.setupWithViewPager(binding.viewPager)
        val view = binding.root
        setContentView(view)
        getuserprofile()

        binding.layoutChat.setOnClickListener {
            var intent = Intent(this, UserChatActivity::class.java)
            intent.putExtra("userIds", UserIds)
            intent.putExtra("UserType",2)
            startActivity(intent)
        }


        binding.imgbadgeszero.setOnClickListener {
            showBottomSheetDialogbges()
        }
        binding.layoutfollowes.setOnClickListener {
            if (binding.btnfollow.text.toString().equals("Follow")) {
                binding.btnfollow.setText("Following")
                binding.btnfollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                //  clicked = true
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
              //  clicked = true
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

    private fun getUserflow(eventid: String) {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getUserflow(
            UserIds.toString(),eventid,
            "Bearer " + CommonMethod.getInstance(this).getPreference(AppConstant.KEY_token))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                if (it != null && it.success == true) {
                   //Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    getuserprofile()
                } else {
                   // Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }                      }
            }

    }


    //******************************************************************************************//



    private fun getAcceptReject(status: String,requestFrom:String) {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getRequestAcceptReject(status,requestFrom,
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()

                if (it != null && it.success == true) {

                    // getnotification()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    getuserprofile()
                   // onBackPressed()
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }                      }
            }
    }




    //********************************************************************************************//
    private fun getuserprofile() {


        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getuserprofile(
            UserIds.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )

            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                 //   Toast.makeText(this, it.data?.followByRequest.toString(), Toast.LENGTH_LONG).show()
                    points= it.data!!.points!!.toInt()
                    followings = it.data?.followbyloginuser!!

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

                     if (followings == false) {

                        // binding.btnfollow.setText("Follow")
                         binding.layoutChat.visibility=GONE
                         binding.layoutuserposts.visibility=GONE
                         binding.conshomeinsta.visibility=GONE
                        // binding.txtfolllowthem.visibility= VISIBLE
                         binding.layoutfollowes.visibility= VISIBLE
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
                         binding.layoutuserposts.visibility=VISIBLE
                         binding.conshomeinsta.visibility=VISIBLE
                       //  binding.txtfolllowthem.visibility=GONE
                         binding.privateCons.visibility= GONE

                         binding.layoutfollowes.visibility=GONE
                         binding.btnfollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


                    }
                   // Toast.makeText(this, it.data?.id.toString(), Toast.LENGTH_LONG).show()
                    binding.textDavidShaw.setText(it.data?.name)
                    binding.wantToFollowingYou.setText(it.data?.name+" wants to follow you")
                    binding.textinsta1.setText(it.data?.postCount.toString())
                    binding.textinsta2.setText(it.data?.followers.toString())
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
                        binding.layoutuserposts.visibility=VISIBLE
                        binding.conshomeinsta.visibility=VISIBLE
                        //binding.txtfolllowthem.visibility=GONE
                        binding.privateCons.visibility=GONE

                        binding.layoutfollowes.visibility=GONE
                        binding.btnfollow.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


                    }
                } else {
                    if(it?.message.toString()=="Unauthenticated.") {
                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }
                }


           /*     if(it!!.data?.butterflies!=null)
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

                }*/
            }
    }


    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(UserPostFollowFragment(), "Post",UserIds.toString())
        adapter.addFragment(UserPhotoFollowFragment(), "      Photo",UserIds.toString())
        adapter.addFragment(UserMayorFollowFragment(), "          Mayor",UserIds.toString())
        adapter.addFragment(UserAboutFollowFragment(), "            About",UserIds.toString())
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


        /*fun addFragment(fragment: Fragment, title: String) {
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
        ImgCross = dialoges.findViewById(R.id.ImgCross)!!
        imgonepointgreen = dialoges.findViewById(R.id.imgonepointgreen)!!
        imgonepoint = dialoges.findViewById(R.id.imgonepoint)!!
        txtpoints = dialoges.findViewById(R.id.txtpoints)!!
        layoutzeropoint = dialoges.findViewById(R.id.layoutzeropoint)!!

        img1subbutterFly=dialoges.findViewById(R.id.imgbedgete)!!
        img2subbutterFly=dialoges.findViewById(R.id.imgonepoint)!!
        img3subbutterFly=dialoges.findViewById(R.id.imgtwopoint)!!
        img4subbutterFly=dialoges.findViewById(R.id.imgthreepoint)!!

        pgBar=dialoges.findViewById(R.id.ivLoader)!!
        txtpoints.setText(points.toString()+" Points")
        //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        pgBar.visibility=View.VISIBLE


        accountViewModel?.getuserprofile(
            UserIds.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
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
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }                      }
                // ProgressD.hideProgressDialog()

            }




        ImgCross.setOnClickListener {
            dialoges.hide()
        }
        dialoges.show()

    }
}
