package com.webmobrilweatherapp.activities.fragment.instafollowmetrologist

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
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
import com.webmobrilweatherapp.activities.metrologistadapter.MetrolgistPostFollowAdapter
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.BottomInterfacemretologist
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.LikeInterfaceMetrologist
import com.webmobrilweatherapp.adapters.UsercommentAdapter
import com.webmobrilweatherapp.model.getcomment.CommentItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.webmobrilweatherapp.model.userpost.DataItem
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.FragmentMetrologistPostFollowBinding

class MetrologistPostFollowFragment : Fragment(), BottomInterfacemretologist,
    LikeInterfaceMetrologist {
    lateinit var binding: FragmentMetrologistPostFollowBinding
    private var userInstaid = ""
    private var postids = ""
    private var liketypes = ""
    private var MetologistPostId = ""
    private var postions:Int = 0
    lateinit var txtLikecomment: TextView
    lateinit var txtLike: TextView
    lateinit var txtMessage: EditText
    lateinit var sendButton: FloatingActionButton
    lateinit var usercommentAdapter: UsercommentAdapter
    lateinit var recylerviewCommenst: RecyclerView
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private lateinit var dialogsdeletepost: BottomSheetDialog
    private lateinit var metrologistPostFollowAdapter: MetrolgistPostFollowAdapter
    var arrayList = ArrayList<DataItem>()
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userInstaid = it.getString("uID").toString() // Retrieve the passed ID
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding=FragmentMetrologistPostFollowBinding.inflate(layoutInflater)
        page=1
        arrayList.clear()

      //  userInstaid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_IntsUserIdMetrologist,"2")
        //Log.e("userId", userInstaid)

        getpostbyMetrologist()
        binding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.

                page++
                //loadingPB.setVisibility(VISIBLE)
                //getDataFromAPI(page, limit)

                getpostbyMetrologist()
            }
        })
          return binding.root
    }


    private fun getpostbyMetrologist() {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
       // userInstaid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_IntsUserIdMetrologist)
        var tokenn:String =CommonMethod.getInstance(requireContext()).getPreference(
            AppConstant.KEY_token_Metrologist
        )
        accountViewModelMetrologist?.getpostbyMetrologist(
            userInstaid, page.toString(),"Bearer " +tokenn
        )
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
              //  Toast.makeText(context, userInstaid.toString(), Toast.LENGTH_LONG).show()

                if (it != null && it.success == true) {
                    if (it.data?.post?.data as List<DataItem> != null && it.data.post.data.size > 0) {
                        binding.txtNoPhoto.visibility = View.GONE


                        for (i in it.data?.post?.data?.indices!!) {
                            it.data?.post?.data?.get(i)?.let { it1 -> arrayList.add(it1) }
                        }


                        metrologistPostFollowAdapter =MetrolgistPostFollowAdapter(requireActivity(),
                                arrayList, it.data?.user,this,this)
                        val layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.RecyclerFollowMetrologist.layoutManager = layoutManager
                        binding.RecyclerFollowMetrologist.adapter =metrologistPostFollowAdapter
                    } else {
                       // binding.txtNoPhoto.visibility = View.VISIBLE
                        if(page==1)

                        {
                            binding.RecyclerFollowMetrologist.visibility = View.GONE
                            binding.txtNoPhoto.visibility = View.VISIBLE
                        }
                        else
                        {
                            binding.txtNoPhoto.visibility = View.GONE
                            Toast.makeText(context,"no more post available", Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    //  Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun selectImage(postId: String) {
        MetologistPostId=postId
        getpostMetrologistcomments(MetologistPostId)
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun getpostMetrologistcomments(MetologistPostId:String) {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getpostMetrologistcomments(MetologistPostId,
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            ))
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
                    txtLikecomment.setText(commentcount  +" Comments")

                    //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun showBottomSheetDialog() {
        var bottomSheet= layoutInflater.inflate(R.layout.commentdialogmetrologist,null)
        // dialogs.window!!.setBackgroundDrawableResource(R.drawable.dialog_curved_bg_inset)
        txtLikecomment = bottomSheet.findViewById(R.id.txtLikecomment)!!
        txtLike = bottomSheet.findViewById(R.id.txtLike)!!
        recylerviewCommenst = bottomSheet.findViewById(R.id.recylerviewCommenst)!!
        txtMessage = bottomSheet.findViewById(R.id.txtMessage)!!
        sendButton = bottomSheet.findViewById(R.id.sendButton)!!
        val dialogBottom= BottomSheetDialog(this.requireContext(),R.style.DialogStyle)
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
    private fun postMetrologistcomments() {
        ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.postMetrologistcomments(
            MetologistPostId, txtMessage.text.toString(), "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                if (!requireActivity().isFinishing) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        metrologistPostFollowAdapter.plustcomment(it.postCount.toString(),postions)
                        txtMessage.setText("")
                       // getpostMetrologistcomments(MetologistPostId)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
    override fun selectposition(postid: String, liketype: String,position:Int) {
        postids = postid
        liketypes = liketype
        postions = position
        getrlike(position,liketypes)
    }
    private fun getrlike(pos: Int,liketypes: String) {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getrlikeMterologist(
            postids, liketypes, "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                if (!requireActivity().isFinishing) {
                    //  ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        if (liketypes.equals("1")){
                            metrologistPostFollowAdapter.plust(it.post_count.toString(), pos,"1")
                        }else{
                            metrologistPostFollowAdapter.nigative(it.post_count.toString(), pos,"2")
                        }
                        // Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        //Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
    }
}