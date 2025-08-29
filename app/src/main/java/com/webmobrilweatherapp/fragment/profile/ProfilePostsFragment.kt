package com.webmobrilweatherapp.fragment.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.BottomInterface
import com.webmobrilweatherapp.DeleteBottomInterface
import com.webmobrilweatherapp.LikeInterface
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.adapters.ProfilefragmentPostsAdapter
import com.webmobrilweatherapp.adapters.UsercommentAdapter
import com.webmobrilweatherapp.databinding.FragmentProfilePostsBinding
import com.webmobrilweatherapp.model.getcomment.CommentItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.webmobrilweatherapp.model.userpost.DataItem

class ProfilePostsFragment : Fragment(), BottomInterface, LikeInterface, DeleteBottomInterface {
    lateinit var binding: FragmentProfilePostsBinding
    var accountViewModel: AccountViewModel? = null
    private var userid = 0
    private var postids = ""
    private var liketypes = ""
    private var UserPostId = ""
    private var postions:Int = 0
    private lateinit var postid :String
    private lateinit var description :String
    private lateinit var postIMage :String
    lateinit var txtLikecomment: TextView
    lateinit var txtLike: TextView
    lateinit var txtMessage: EditText
    lateinit var sendButton: FloatingActionButton
    lateinit var LayoutEditpost: ConstraintLayout
    lateinit var Layoutdeletepost: ConstraintLayout
    lateinit var recylerviewCommenst: RecyclerView
    private lateinit var dialogs: BottomSheetDialog
    lateinit var usercommentAdapter: UsercommentAdapter
    private lateinit var dialogsdeletepost: BottomSheetDialog
    private var dataItems: MutableList<DataItem> = ArrayList()
    var arrayList = ArrayList<DataItem>()
    private lateinit var profilefragmentPostsAdapter: ProfilefragmentPostsAdapter
    var page = 1


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentProfilePostsBinding.inflate(layoutInflater)
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


    private fun getpostimageuser() {
          //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))

        userid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_id, 0)

        accountViewModel?.getpostbyUser(
            userid.toString(),page.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {

              //ProgressD.hideProgressDialog()

                if (binding.RecyclerViewPosts != null) {

                    if (it != null && it.success == true) {

                        if (it.data?.post?.data?.size!! > 0) {


                            dataItems.addAll(it.data.post.data)
                            binding.txtNoPhoto.visibility = GONE

                            for (i in it.data?.post?.data?.indices!!) {
                                it.data?.post?.data?.get(i)?.let { it1 -> arrayList.add(it1) }
                            }

                            profilefragmentPostsAdapter = ProfilefragmentPostsAdapter(
                                requireActivity(),
                                arrayList,
                                it.data.user,
                                this,
                                this,
                                this)
                            val layoutManager = LinearLayoutManager(requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false)
                            binding.RecyclerViewPosts.layoutManager = layoutManager
                            //  binding.RecyclerViewPosts.setItemViewCacheSize(50);

                            binding.RecyclerViewPosts.adapter = profilefragmentPostsAdapter


                        } else {
                          //  binding.RecyclerViewPosts.visibility = GONE
                           // binding.txtNoPhoto.visibility = VISIBLE

                            if(page==1)

                            {
                                binding.RecyclerViewPosts.visibility = GONE
                                binding.txtNoPhoto.visibility = VISIBLE
                            }
                            else
                            {
                                binding.txtNoPhoto.visibility = GONE
                                Toast.makeText(context,"no more post available", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        if(it!!.message.toString()=="Unauthenticated.")
                        {
                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatus, false)
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            this?.startActivity(intent)
                        }                          }
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
                            profilefragmentPostsAdapter.plust(it.post_count.toString(), pos,"1")
                        }else{
                            profilefragmentPostsAdapter.nigative(it.post_count.toString(), pos,"2")
                        }

                        //profilefragmentPostsAdapter.plust(it.post_count!!.toInt(),pid)
                       // Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        if(it!!.message.toString()=="Unauthenticated.")
                        {

                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatus, false)
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            this?.startActivity(intent)
                        }                          }
                }

            }
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
        accountViewModel?.postcomments(
            UserPostId, txtMessage.text.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                if (!requireActivity().isFinishing) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        profilefragmentPostsAdapter.plustcomment(it.postCount.toString(),postions)

                        //  getpostUserComment(UserPostId)
                        txtMessage.setText("")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                        if(it!!.message.toString()=="Unauthenticated.")
                        {

                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatus, false)
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            this?.startActivity(intent)
                        }
                    }
                }
            }
    }




    @RequiresApi(Build.VERSION_CODES.S)
    private fun showBottomSheetDialogDelete() {
        dialogsdeletepost= BottomSheetDialog(requireContext(),R.style.DialogStyle)
        dialogsdeletepost.setContentView(R.layout.deletepost)
        // dialogs.window!!.setBackgroundDrawableResource(R.drawable.dialog_curved_bg_inset)
        LayoutEditpost = dialogsdeletepost.findViewById(R.id.LayoutEditpost)!!
        Layoutdeletepost = dialogsdeletepost.findViewById(R.id.Layoutdeletepost)!!
        Layoutdeletepost.setOnClickListener {
            getpostdelete()
            dialogsdeletepost.hide()
        }

        LayoutEditpost.setOnClickListener {
            val intent = Intent(activity, EditPostActivity::class.java)
            intent.putExtra("postimage", postIMage)
            intent.putExtra("postid", postid)
            intent.putExtra("description", description)
            requireActivity().startActivity(intent)
            dialogsdeletepost.hide()
        }


        dialogsdeletepost.show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun selectImage(postid: String) {
        UserPostId=postid
        getpostUserComment(UserPostId)
    }
    override fun selectposition(postid: String, liketype: String,position:Int) {
        UserPostId = postid
        Log.d("TAG", "selectposition: " + UserPostId)
        postids = postid
        liketypes = liketype
        postions=position
        getrlike(position,liketypes)
    }
    private fun getpostdelete() {
       ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
         accountViewModel?.getpostdelete(postid,
            "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
              ProgressD.hideProgressDialog()
                if (!requireActivity().isFinishing) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        arrayList.clear()
//                        getpostimageuser()
                        val i = Intent(requireActivity(), HomeActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(i)

                    } else {
                        Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                        if(it!!.message.toString()=="Unauthenticated.")
                        {

                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatus, false)
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            this?.startActivity(intent)
                        }                          }
                }
            }
    }


    @RequiresApi(Build.VERSION_CODES.S)
    override fun selectImagedelete(position: String) {
        postid= dataItems[position.toInt()].id.toString()
        postIMage=dataItems[position.toInt()].post.toString()
        description=dataItems[position.toInt()].description.toString()
        Log.d("TAG", "selectImagedelete: "+dataItems.toString())
        showBottomSheetDialogDelete()
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
                        it.comment as List<CommentItem>
                    )

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
                    Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                    /*if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(context)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                        (this as HomeActivity).finish()
                    }       */               }
            }
    }
}