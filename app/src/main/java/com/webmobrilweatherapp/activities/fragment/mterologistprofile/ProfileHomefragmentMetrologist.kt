package com.webmobrilweatherapp.activities.fragment.mterologistprofile

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistadapter.ProfilefragmentMetrologisthomeAdapter
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.BottomInterfacemretologist
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.LikeInterfaceMetrologist
import com.webmobrilweatherapp.adapters.UsercommentAdapter
import com.webmobrilweatherapp.model.getcomment.CommentItem
import com.webmobrilweatherapp.model.getcomment.User
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.webmobrilweatherapp.model.homepage.DataItem
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.FragmentProfileHomemetrologistBinding


class ProfileHomefragmentMetrologist : Fragment(), BottomInterfacemretologist,
    LikeInterfaceMetrologist {
    lateinit var binding: FragmentProfileHomemetrologistBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"
    private var postids = ""
    private var liketypes = ""
    private var postions:Int = 0
    private var MetologistPostId = ""
    lateinit var txtLike: TextView
    lateinit var txtLikecomment: TextView
    lateinit var txtMessage: EditText
    lateinit var sendButton: FloatingActionButton
    lateinit var recylerviewCommenst: RecyclerView
    private lateinit var dialogs: BottomSheetDialog
    lateinit var usercommentAdapter: UsercommentAdapter
    lateinit var user: User
    lateinit var likebyme: String
    var checked=false
    var page = 1
    var arrayList = ArrayList<DataItem>()

    private lateinit var profilefragmentMetrologisthomeAdapter: ProfilefragmentMetrologisthomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = FragmentProfileHomemetrologistBinding.inflate(layoutInflater)
        page=1
        arrayList.clear()
      //  getuserprofileMetrologist()
        binding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.

                page++
                //loadingPB.setVisibility(VISIBLE)
                //getDataFromAPI(page, limit)

                getuserprofileMetrologist()

            }
        })
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getuserprofileMetrologist() {
        //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        useridMetrologist = CommonMethod.getInstance(requireContext())
            .getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getprofilehomepageMetrologist(
            useridMetrologist, page.toString(),"Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                //   ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.post?.data?.size!! > 0) {
                        binding.constraintProfileHome.visibility = VISIBLE
                        binding.txtnodata.visibility = GONE
                        for (i in it.post.data.indices) {
                            it.post.data.get(i)?.let { it1 -> arrayList.add(it1) }
                        }
                        profilefragmentMetrologisthomeAdapter =
                            ProfilefragmentMetrologisthomeAdapter(
                                requireActivity(),
                                arrayList,
                                this,
                                this
                            )

                        val layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                       // likebyme=it.post.get(0).likedbyme.toString()
                        binding.ProfileHomePageMetrolgist.layoutManager = layoutManager
                        binding.ProfileHomePageMetrolgist.adapter =
                            profilefragmentMetrologisthomeAdapter
                        binding.textdavidshaww.setText(it.userDetail?.name)
                        binding.textdavidshawemail.setText(it.userDetail?.email)
                        // binding.textphonenumber.setText(it.data?.phone.toString())
                        binding.textdavidshawMadison.setText(it.userDetail?.city)
                        binding.textlastactive.setText(it.userDetail?.lastActive)
                    } else {
                        binding.constraintProfileHome.visibility = GONE
                        if(page==1)

                        {
                            binding.txtnodata.visibility = VISIBLE
                        }
                        else
                        {
                            binding.txtnodata.visibility = GONE
                            Toast.makeText(context,"no more post available", Toast.LENGTH_LONG).show()
                        }
                    }
                    //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    //Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun selectImage(postid: String) {
        MetologistPostId = postid
        getpostMetrologistcomments(MetologistPostId)

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun selectposition(postid: String, liketype: String, postion: Int) {
        postids = postid
        liketypes = liketype
        postions = postion
        getrlikeMterologist(postion,liketypes)

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getrlikeMterologist(pos: Int,liketypes:String) {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getrlikeMterologist(
            postids,
            liketypes,
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )

            ?.observe(requireActivity()) {
                if (!requireActivity().isFinishing) {
                    //  ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        if (liketypes.equals("1")){
                            profilefragmentMetrologisthomeAdapter.plust(it.post_count.toString(), pos,"1")
                        }else{
                            profilefragmentMetrologisthomeAdapter.nigative(it.post_count.toString(), pos,"2")
                        }
                        //CommonMethod.getInstance(requireContext()).savePreference(AppConstant.KEY_POST_COUNT, it.post_count!!.toInt())


                      /* if (!checked){
                           likebyme.equals(1)
                           profilefragmentMetrologisthomeAdapter.plust(it.post_count.toString(), pos)
                       }else{
                           likebyme.equals(0)
                           profilefragmentMetrologisthomeAdapter.plust(it.post_count.toString(), pos)
                       }*/
                        // Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        // Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showBottomSheetDialog() {
        var bottomSheet = layoutInflater.inflate(R.layout.commentdialogmetrologist, null)
        // dialogs.window!!.setBackgroundDrawableResource(R.drawable.dialog_curved_bg_inset)
        txtLike = bottomSheet.findViewById(R.id.txtLike)!!
        txtLikecomment = bottomSheet.findViewById(R.id.txtLikecomment)!!
        recylerviewCommenst = bottomSheet.findViewById(R.id.recylerviewCommenst)!!
        txtMessage = bottomSheet.findViewById(R.id.txtMessage)!!
        sendButton = bottomSheet.findViewById(R.id.sendButton)!!
        val dialogBottom = BottomSheetDialog(this.requireContext(),R.style.DialogStyle)
        dialogBottom.setContentView(bottomSheet)
        txtMessage.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }
        //  dialogBottom.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        sendButton.setOnClickListener {
            if (isValidation()) {
                postMetrologistcomments()
                dialogBottom.hide()
            }
        }


        dialogBottom.show()
    }

    private fun isValidation(): Boolean {
        if (txtMessage.text.isNullOrEmpty()) {
            txtMessage.requestFocus()
            showToastMessage("Please Enter Comment")
            return false
        }
        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getpostMetrologistcomments(MetologistPostId: String) {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getpostMetrologistcomments(
            MetologistPostId,
            "Bearer " + CommonMethod.getInstance(requireContext())
                .getPreference(AppConstant.KEY_token_Metrologist)
        )
            ?.observe(requireActivity()) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    showBottomSheetDialog()
                    usercommentAdapter =
                        UsercommentAdapter(requireActivity(), it.comment as List<CommentItem>)
                    val layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    recylerviewCommenst.layoutManager = layoutManager
                    recylerviewCommenst.adapter = usercommentAdapter
                    var likecount=it.likeCouunt.toString()
                    var commentcount=it.commentCount.toString()
                    txtLike.setText(likecount  +" Likes |")
                    txtLikecomment.setText(commentcount  + " Comments")
                    //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun postMetrologistcomments() {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.postMetrologistcomments(
            MetologistPostId, txtMessage.text.toString(),
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist))
            ?.observe(requireActivity()) {
                if (!requireActivity().isFinishing) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        profilefragmentMetrologisthomeAdapter.plustcomment(it.postCount.toString(),postions)
                        txtMessage.setText("")
                        getpostcommentsMetrologistcomments(MetologistPostId)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun getpostcommentsMetrologistcomments(MetologistPostId: String) {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getpostMetrologistcomments(
            MetologistPostId,
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    // showBottomSheetDialog()
                    usercommentAdapter =
                        UsercommentAdapter(requireActivity(), it.comment as List<CommentItem>)
                    val layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    recylerviewCommenst.layoutManager = layoutManager
                    recylerviewCommenst.adapter = usercommentAdapter
                    //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onResume() {
        super.onResume()
        getuserprofileMetrologist()
    }
}