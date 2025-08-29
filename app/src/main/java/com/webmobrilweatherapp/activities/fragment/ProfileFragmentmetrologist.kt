package com.webmobrilweatherapp.activities.fragment

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
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.fragment.mterologistprofile.*
import com.webmobrilweatherapp.activities.metrologistactivity.MetrilogistHomeActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistFollowersActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistFollowingActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistViewProfileActivity
import com.webmobrilweatherapp.databinding.FragmentProfileFragmentmetrologistBinding
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel

class ProfileFragmentmetrologist : Fragment() {
    lateinit var binding: FragmentProfileFragmentmetrologistBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"
    var points: Int = 0
    var accountViewModel: AccountViewModel? = null

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

    //---------------------------//

    private lateinit var img1subbutterFly: ImageView
    private lateinit var img2subbutterFly: ImageView
    private lateinit var img3subbutterFly: ImageView
    private lateinit var img4subbutterFly: ImageView
    private lateinit var pgBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        binding = FragmentProfileFragmentmetrologistBinding.inflate(layoutInflater)
        (requireActivity() as MetrilogistHomeActivity).updateBottomBar(4)


        binding.constrainteditpeofileMetrologist.setOnClickListener {
            binding.constrainteditpeofileMetrologist.isEnabled = false
            val i = Intent(context, MetrologistViewProfileActivity::class.java)
            startActivity(i)
        }
        binding.textFollowing2.setOnClickListener(View.OnClickListener {
            val i = Intent(context, MetrologistFollowersActivity::class.java)
            startActivity(i)

        })

        binding.textFollowing1.setOnClickListener(View.OnClickListener {
            val i = Intent(context, MetrologistFollowingActivity::class.java)
            startActivity(i)

        })

        setupViewPager(binding.ViewPagerprofileMetrologoist)
        binding.simpleTabLayoutProfileMetrologoist.setupWithViewPager(binding.ViewPagerprofileMetrologoist)
        getuserprofileMetrologist()
        binding.imgbadgeszero.setOnClickListener {
            showBottomSheetDialogbges()
        }

        return binding.root
    }

    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ProfileHomefragmentMetrologist(), "Home")
        adapter.addFragment(ProfilePostsFragmentMetrologist(), "Posts")
        adapter.addFragment(ProfilePhotosFragmentMetrologist(), "Photos")
        adapter.addFragment(profileMayorFragmentMetrologist(), "Mayor")
        adapter.addFragment(ProfileAboutFragmentsMetrologist(), "About")
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

    private fun getuserprofileMetrologist() {
        //  ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        useridMetrologist = CommonMethod.getInstance(requireContext())
            .getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getuserprofileMetrologist(
            useridMetrologist, "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                //ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    points = it.data!!.points!!.toInt()
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

                    if (it.data?.butterflies != null) {

                        if (points == 0 || points <= 99) {

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                    0)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(binding.imgbadgeszero)


                        } else if (points == 100 || points <= 199) {

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                    1)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(binding.imgbadgeszero)

                        } else if (points == 200 || points <= 299) {
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                    2)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(binding.imgbadgeszero)

                        } else if (points == 300 || points <= 100000000000) {
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(binding.imgbadgeszero)
                        }

                    }
                } else {
                    Toast.makeText(requireContext(), it?.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
    }


    override fun onResume() {
        super.onResume()
        getuserprofileMetrologist()

        binding.constrainteditpeofileMetrologist.isEnabled = true

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showBottomSheetDialogbges() {
        dialoges = BottomSheetDialog(requireContext(),R.style.DialogStyle)
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
        txtpoints = dialoges.findViewById(R.id.txtpoints)!!

        txtpoints.setText(points.toString() + " pts")

        img1subbutterFly = dialoges.findViewById(R.id.imgbedgete)!!
        img2subbutterFly = dialoges.findViewById(R.id.imgonepoint)!!
        img3subbutterFly = dialoges.findViewById(R.id.imgtwopoint)!!
        img4subbutterFly = dialoges.findViewById(R.id.imgthreepoint)!!

        pgBar = dialoges.findViewById(R.id.ivLoader)!!

        ImgCross.setOnClickListener {
            dialoges.hide()
        }


/*
        if (points == 0 || points <= 9) {
*//*
            binding.imgbadgeszero.setImageResource(R.drawable.badgeszero)
*//*
        } else if (points == 10 || points <= 19) {
            layoutonepointgreen.visibility = View.VISIBLE
            dotedone.visibility = View.GONE
            dotedonegreen.visibility = View.VISIBLE
        } else if (points == 20 || points <= 29) {
            layouttwopointgreen.visibility = View.VISIBLE
            layoutonepointgreen.visibility = View.VISIBLE
            imgdonedoted.visibility = View.GONE
            dotedonegreen.visibility = View.VISIBLE
            dotedtwogreen.visibility = View.VISIBLE

        } else if (points == 30 || points <= 100000000000) {
            layouttwopointgreen.visibility = View.VISIBLE
            layoutonepointgreen.visibility = View.VISIBLE
            layoutthreepointgreen.visibility = View.VISIBLE
            Imgdotedthree.visibility = View.GONE
            dotedonegreen.visibility = View.VISIBLE
            dotedtwogreen.visibility = View.VISIBLE
            dotedthreegreen.visibility = View.VISIBLE
        }
        // dialogs.window!!.setBackgroundDrawableResource(R.drawable.dialog_curved_bg_inset)
        //  dialogBottom.behavior.state = BottomSheetBehavior.STATE_EXPANDED*/

        pgBar.visibility = View.VISIBLE

       useridMetrologist = CommonMethod.getInstance(context).getPreference(AppConstant.KEY_User_idMetrologist)

        //  Toast.makeText(context, userid.toString(), Toast.LENGTH_LONG).show()

        accountViewModel?.getuserprofile(
            useridMetrologist.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                //  ProgressD.hideProgressDialog()
                pgBar.visibility = View.GONE
                val circularProgressDrawable = CircularProgressDrawable(requireContext())
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.toolbaar))
                circularProgressDrawable.start()
                if (it != null && it.success == true) {

                    if (it.data?.butterflies != null) {


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
                                .placeholder(circularProgressDrawable)
                                .into(img1subbutterFly)

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                    1)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img2subbutterFly)


                        } else if (points == 200 || points <= 299) {
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                    0)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img1subbutterFly)

                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                    1)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
                                .into(img2subbutterFly)
                            Glide.with(this)
                                .load(ApiConstants.im_url + it.data?.butterflies!!.get(0)!!.subbutterflies!!.get(
                                    2)!!.butterfliesImage)
                                .placeholder(circularProgressDrawable)
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

                } else {
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
                // ProgressD.hideProgressDialog()


                dialoges.show()
            }
    }
}