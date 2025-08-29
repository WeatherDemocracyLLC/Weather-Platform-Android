package com.webmobrilweatherapp.fragment.instafollow

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.BottomInterface
import com.webmobrilweatherapp.LikeInterface
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
 import com.webmobrilweatherapp.adapters.UserProfilefragmentFollowAdapter
import com.webmobrilweatherapp.adapters.UsercommentAdapter
import com.webmobrilweatherapp.databinding.FragmentUserPostFollowBinding
import com.webmobrilweatherapp.model.getcomment.CommentItem
import com.webmobrilweatherapp.model.userpost.DataItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton


class UserPostFollowFragment : Fragment() , BottomInterface, LikeInterface {
    lateinit var binding: FragmentUserPostFollowBinding
    private var userInstaid = ""
    private var UserPostId = ""
    private var postids = ""
    private var liketypes = ""
    private var postions:Int = 0
    lateinit var recylerviewCommenst: RecyclerView
    var accountViewModel: AccountViewModel? = null
    private lateinit var dialogs: BottomSheetDialog
    lateinit var txtLikecomment: TextView
    lateinit var txtLike: TextView
    lateinit var txtMessage: EditText
    lateinit var sendButton: FloatingActionButton
    lateinit var usercommentAdapter: UsercommentAdapter
    private lateinit var userProfilefragmentFollowAdapter: UserProfilefragmentFollowAdapter
    var page = 1
    var arrayList = ArrayList<DataItem>()

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


        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding=FragmentUserPostFollowBinding.inflate(layoutInflater)
        page=1
        arrayList.clear()
        getpostimageuser()
        binding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.

                page++
                //loadingPB.setVisibility(VISIBLE)
                //getDataFromAPI(page, limit)

                getpostimageuser()
            }
        })
        return binding.root

    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun showBottomSheetDialog() {
        dialogs = BottomSheetDialog(requireContext(),R.style.DialogStyle)
        dialogs.setContentView(R.layout.commentdialog)
        // dialogs.window!!.setBackgroundDrawableResource(R.drawable.dialog_curved_bg_inset)
        txtLikecomment = dialogs.findViewById(R.id.txtLikecomment)!!
        txtLike = dialogs.findViewById(R.id.txtLike)!!
        recylerviewCommenst = dialogs.findViewById(R.id.recylerviewCommenst)!!
        txtMessage = dialogs.findViewById(R.id.txtMessage)!!
        sendButton = dialogs.findViewById(R.id.sendButton)!!
        txtMessage.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }
        /*val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recylerviewCommenst.layoutManager = layoutManager
        recylerviewCommenst.adapter = UsercommentAdapter(requireContext())*/
        sendButton.setOnClickListener {
            if (isValidation()) {
                postcomments()
                dialogs.hide()
            }
        }
        // initRecyclerview()
        dialogs.show()
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
    private fun postcomments() {
        ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        accountViewModel?.postcomments(UserPostId, txtMessage.text.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token))
            ?.observe(requireActivity()) {
                if (!requireActivity().isFinishing) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        userProfilefragmentFollowAdapter.plustcomment(it.postCount.toString(),postions)
                        //getpostUserComment(UserPostId)
                        txtMessage.setText("")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
    }
    private fun getpostimageuser() {

        //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
       // userInstaid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_IntsUserId)
    //    Toast.makeText(requireContext(), userInstaid.toString(), Toast.LENGTH_LONG).show()

        accountViewModel?.getpostbyUser(
            userInstaid.toString(), page.toString(),"Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token)
        )
            ?.observe(requireActivity()) {

                // ProgressD.hideProgressDialog()
               // Toast.makeText(context, "it?.message", Toast.LENGTH_LONG).show()


                if (it != null && it.success == true) {

                    if (it.data?.post?.data as List<DataItem> != null && it.data?.post?.data.size > 0) {
                        binding.txtNoPhoto.visibility = View.GONE

                        for (i in it.data?.post?.data?.indices!!) {
                            it.data?.post?.data?.get(i)?.let { it1 -> arrayList.add(it1) }
                        }

                        userProfilefragmentFollowAdapter =UserProfilefragmentFollowAdapter(requireActivity(), arrayList, it.data.user,this,this)
                        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                        binding.RecyclerViewPostFollow.layoutManager = layoutManager
                        binding.RecyclerViewPostFollow.adapter = userProfilefragmentFollowAdapter
                    } else {
                       // binding.RecyclerViewPostFollow.visibility= View.GONE
                        //binding.txtNoPhoto.visibility = View.VISIBLE

                        if(page==1)
                        {
                            binding.RecyclerViewPostFollow.visibility = View.GONE
                            binding.txtNoPhoto.visibility = View.VISIBLE
                        }
                        else
                        {
                            binding.txtNoPhoto.visibility = View.GONE
                            Toast.makeText(context,"no more post available", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    // Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    @RequiresApi(Build.VERSION_CODES.S)
    override fun selectImage(postId: String) {
        UserPostId=postId
        getpostUserComment(UserPostId)
    }

    override fun selectposition(postid: String, liketype: String,position:Int) {

        UserPostId = postid
        Log.d("TAG", "selectposition: " + UserPostId)
        postids = postid
        liketypes = liketype
        postions = position
        getrlike(position,liketypes)

    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun getpostUserComment(UserPostId:String) {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        accountViewModel?.getpostUserComment(UserPostId,
            "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            ))
            ?.observe(requireActivity()) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    showBottomSheetDialog()
                    usercommentAdapter = UsercommentAdapter(
                        requireActivity(),
                        it.comment as List<CommentItem>)
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


    private fun getrlike(pos:Int,liketypes: String) {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        accountViewModel?.getrlike(
            postids, liketypes, "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                if (!requireActivity().isFinishing) {
                    //  ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        if (liketypes.equals("1")){
                            userProfilefragmentFollowAdapter.plust(it.post_count.toString(), pos,"1")
                        }else{
                            userProfilefragmentFollowAdapter.nigative(it.post_count.toString(), pos,"2")
                        }

                        //profilefragmentPostsAdapter.plust(it.post_count!!.toInt(),pid)
                        // Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        // Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
    }
}