package com.webmobrilweatherapp.activities.fragment.mterologistprofile

import android.content.Intent
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.DeleteBottomInterface
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistEditpostActivity
import com.webmobrilweatherapp.activities.metrologistadapter.ProfileUserPostMetrologistAdapter
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.BottomInterfacemretologist
import com.webmobrilweatherapp.activities.metrologistadapter.mterologistinterface.LikeInterfaceMetrologist
import com.webmobrilweatherapp.adapters.UsercommentAdapter
import com.webmobrilweatherapp.model.getcomment.CommentItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.webmobrilweatherapp.model.userpost.DataItem
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.FragmentProfilePostsMetrologistBinding

class ProfilePostsFragmentMetrologist : Fragment(), BottomInterfacemretologist,
    DeleteBottomInterface,
    LikeInterfaceMetrologist {
    lateinit var binding: FragmentProfilePostsMetrologistBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"
    private var postids = ""
    private var liketypes = ""
    private var postions:Int = 0
     private var MetologistPostId = ""
     private lateinit var postIds :String
    private lateinit var description :String
    private lateinit var postIMage :String
    lateinit var txtLike: TextView
    lateinit var txtLikecomment: TextView
    lateinit var LayoutEditpost: ConstraintLayout
    lateinit var Layoutdeletepost: ConstraintLayout
    private lateinit var dialogsdeletepost: BottomSheetDialog
    lateinit var txtMessage: EditText
    lateinit var usercommentAdapter: UsercommentAdapter
    lateinit var sendButton: FloatingActionButton
    lateinit var recylerviewCommenst: RecyclerView
    private var dataItems: MutableList<DataItem> = ArrayList()
     private lateinit var profileUserPostMetrologistAdapter: ProfileUserPostMetrologistAdapter
    var arrayList = ArrayList<DataItem>()

    var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = FragmentProfilePostsMetrologistBinding.inflate(layoutInflater)
        page=1
        arrayList.clear()
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
        useridMetrologist = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getpostbyMetrologist(
            useridMetrologist,page.toString(), "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    dataItems.clear()
                    if (it.data?.post?.data as List<DataItem> != null && it.data.post.data.size > 0) {
                        binding.txtNoPhoto.visibility = GONE
                        dataItems.addAll(it.data?.post?.data)


                        for (i in it.data?.post?.data?.indices!!) {
                            it.data?.post?.data?.get(i)?.let { it1 -> arrayList.add(it1) }
                        }
                        profileUserPostMetrologistAdapter =
                            ProfileUserPostMetrologistAdapter(
                                requireActivity(),
                                arrayList, it.data?.user, this,this, this
                            )
                        val layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.recylerviewPostmetrologist.layoutManager = layoutManager
                        binding.recylerviewPostmetrologist.adapter =profileUserPostMetrologistAdapter
                    } else {
                      //  binding.txtNoPhoto.visibility = VISIBLE
                        if(page==1)

                        {
                            binding.recylerviewPostmetrologist.visibility = GONE
                            binding.txtNoPhoto.visibility = VISIBLE
                        }
                        else
                        {
                            binding.txtNoPhoto.visibility = GONE
                            Toast.makeText(context,"no more post available", Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    //  Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun selectImage(postid: String) {
        MetologistPostId=postid
        getpostMetrologistcomments(MetologistPostId)
    }

    override fun selectposition(postid: String, liketype: String,postion:Int) {
        postids = postid
        liketypes = liketype
        postions = postion
        getrlike(postion,liketypes)
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
                            profileUserPostMetrologistAdapter.plust(it.post_count.toString(), pos,"1")
                        }else{
                            profileUserPostMetrologistAdapter.nigative(it.post_count.toString(), pos,"2")
                        }
                       // Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        //Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                    }
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
                        profileUserPostMetrologistAdapter.plustcomment(it.postCount.toString(),postions)
                        txtMessage.setText("")
                       // getpostMetrologistcomments(MetologistPostId)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun selectImagedelete(position: String) {
        postIds= dataItems[position.toInt()].id.toString()
        postIMage=dataItems[position.toInt()].post.toString()
        description=dataItems[position.toInt()].description.toString()
        showBottomSheetDialogDelete()
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun showBottomSheetDialogDelete() {
        dialogsdeletepost= BottomSheetDialog(requireContext(),R.style.DialogStyle)
        dialogsdeletepost.setContentView(R.layout.deletepost)
        // dialogs.window!!.setBackgroundDrawableResource(R.drawable.dialog_curved_bg_inset)
        LayoutEditpost = dialogsdeletepost.findViewById(R.id.LayoutEditpost)!!
        Layoutdeletepost = dialogsdeletepost.findViewById(R.id.Layoutdeletepost)!!
        Layoutdeletepost.setOnClickListener {
            getpostdeleteMetrologist()
            dialogsdeletepost.hide()
        }
        LayoutEditpost.setOnClickListener {
            val intent = Intent(activity, MetrologistEditpostActivity::class.java)
            intent.putExtra("postimage", postIMage)
            intent.putExtra("postid", postIds)
            intent.putExtra("description", description)
            requireActivity().startActivity(intent)
            dialogsdeletepost.hide()
        }
        dialogsdeletepost.show()
    }
    private fun getpostdeleteMetrologist() {
        ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getpostdeleteMetrologist(postIds,
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                ProgressD.hideProgressDialog()
                if (!requireActivity().isFinishing) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        getpostbyMetrologist()
                    } else {
                        Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}