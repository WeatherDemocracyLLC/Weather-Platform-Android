package com.webmobrilweatherapp.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.databinding.FragmentProfileBinding
import com.webmobrilweatherapp.fragment.profile.*
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    var accountViewModel: AccountViewModel? = null
    private var userid = 0
    var points:Int=0
    private lateinit var dialoges: BottomSheetDialog
    private lateinit var layoutonepoint: ConstraintLayout

    private lateinit var layoutzeropoint: ConstraintLayout

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
    private lateinit var imgonepointgreen: ImageView
    private lateinit var imgonepoint: ImageView
    private lateinit var txtpoints: TextView


    //---------------------------//
    private lateinit var img1subbutterFly:ImageView
    private lateinit var img2subbutterFly:ImageView
    private lateinit var img3subbutterFly:ImageView
    private lateinit var img4subbutterFly:ImageView
    private lateinit var pgBar:ProgressBar



    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        (requireActivity() as HomeActivity).updateBottomBars(4)
        getuserprofile()
        binding.constrainteditpeofile.setOnClickListener {
            binding.constrainteditpeofile.isEnabled = false
            val i = Intent(requireContext(), ViewProfileActivity::class.java)
            startActivity(i)
        }
        binding.imgbadgeszero.setOnClickListener {
            showBottomSheetDialogbges()
        }

        binding.textFollowing2.setOnClickListener(View.OnClickListener {

            val i = Intent(requireContext(), FollowersActivity::class.java)
            startActivity(i)
        })
        binding.textFollowing1.setOnClickListener(View.OnClickListener {

            val i = Intent(requireContext(), FollowingActivity::class.java)
            startActivity(i)
        })

        binding.ViewPagerprofile.offscreenPageLimit=0
        setupViewPager(binding.ViewPagerprofile)
        binding.simpleTabLayoutProfile.setupWithViewPager(binding.ViewPagerprofile)
        return binding.root


    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ProfileHomefragment(), "Home")
        adapter.addFragment(ProfilePostsFragment(), "Posts")
        adapter.addFragment(ProfilePhotosFragment(), "Photos")
        adapter.addFragment(profileMayorFragment(), "Mayor")
        adapter.addFragment(ProfileAboutFragments(), "About")
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

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

    }

    private fun getuserprofile() {
        //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_id, 0)
        //Toast.makeText(context, userid.toString(), Toast.LENGTH_LONG).show()


        accountViewModel?.getuserprofile(
            userid.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                //  ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    points= it.data!!.points!!.toInt()
                    binding.textdavidshaw.setText(it.data?.name)
                    var postcount = it.data?.postCount
                    binding.text21.setText(postcount.toString())
                    var follwoer = it.data?.followers
                    binding.text63.setText(follwoer.toString())
                    // var follwoing=it.data?.following
                    binding.text64444.setText(it.data?.following.toString())
                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL + it.data?.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.imgProfile)

                    val circularProgressDrawable = CircularProgressDrawable(requireContext())
                    circularProgressDrawable.strokeWidth = 5f
                    circularProgressDrawable.centerRadius = 30f
                    circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.toolbaar))
                    circularProgressDrawable.start()
                    //Toast.makeText(context, it.data?.butterflies!!.get(0)!!.butterfliesImage.toString(), Toast.LENGTH_LONG).show()

                    if(it.data?.butterflies!=null)
                    {

                        if (points==0||points<=99){

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(0)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(binding.imgbadgeszero)


                        }else if (points==100||points<=199){

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(1)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(binding.imgbadgeszero)

                        } else if (points==200||points<=299){
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(2)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(binding.imgbadgeszero)

                        }else if (points==300||points<=100000000000){
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(binding.imgbadgeszero)

                        }

                    }

                } else {
                    Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(context)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        context?.startActivity(intent)
                        (activity as HomeActivity).finish()
                    }
                }
                // ProgressD.hideProgressDialog()

            }
    }

    override fun onResume() {
        super.onResume()
        getuserprofile()
        binding.constrainteditpeofile.isEnabled = true

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showBottomSheetDialogbges() {

        dialoges = BottomSheetDialog(requireContext(),R.style.DialogStyle)
        dialoges.setContentView(R.layout.dialogbadges)

        layoutzeropoint = dialoges.findViewById(R.id.layoutzeropoint)!!
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

        img1subbutterFly=dialoges.findViewById(R.id.imgbedgete)!!
        img2subbutterFly=dialoges.findViewById(R.id.imgonepoint)!!
        img3subbutterFly=dialoges.findViewById(R.id.imgtwopoint)!!
        img4subbutterFly=dialoges.findViewById(R.id.imgthreepoint)!!

        pgBar=dialoges.findViewById(R.id.ivLoader)!!


        txtpoints.setText(points.toString()+" Points")
        //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        pgBar.visibility=View.VISIBLE

        userid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_id, 0)
        //  Toast.makeText(context, userid.toString(), Toast.LENGTH_LONG).show()

        accountViewModel?.getuserprofile(
            userid.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                //  ProgressD.hideProgressDialog()
                pgBar.visibility=View.GONE
                val circularProgressDrawable = CircularProgressDrawable(requireContext())
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.toolbaar))
                circularProgressDrawable.start()
                if (it != null && it.success == true) {

                    if(it.data?.butterflies!=null)
                    {


                        if (points==0||points<=99){


                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(0)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img1subbutterFly)

                        }else if (points==100||points<=199){

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(0)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img1subbutterFly)


                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(1)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img2subbutterFly)


                        } else if (points==200||points<=299){
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(0)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img1subbutterFly)

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(1)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img2subbutterFly)
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(2)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img3subbutterFly)



                        }else if (points==300||points<=100000000000){

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(0)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img1subbutterFly)

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(1)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img2subbutterFly)
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(2)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img3subbutterFly)
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img4subbutterFly)

                        }


                    }

                } else {
                    Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(context)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        CommonMethod.getInstance(requireContext()).savePreference(AppConstant.Key_ApplicationId,"0")

                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        context?.startActivity(intent)
                        (activity as HomeActivity).finish()
                    }
                }
                // ProgressD.hideProgressDialog()

            }
        ImgCross.setOnClickListener {
            dialoges.hide()
        }


        dialoges.show()

    }
}

