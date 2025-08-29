package com.webmobrilweatherapp.fragment.profile


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.BottomInterface
import com.webmobrilweatherapp.LikeInterface
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.ProfilefragmenthomeAdapter
import com.webmobrilweatherapp.adapters.UsercommentAdapter
import com.webmobrilweatherapp.databinding.FragmentProfileHomefragmentBinding
import com.webmobrilweatherapp.model.getcomment.CommentItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.model.homepage.DataItem
import com.webmobrilweatherapp.model.homepage.ProfilehomePageResponse
import androidx.core.widget.NestedScrollView
import kotlin.collections.ArrayList

class ProfileHomefragment : Fragment(), BottomInterface, LikeInterface {
    lateinit var binding: FragmentProfileHomefragmentBinding
    var accountViewModel: AccountViewModel? = null
    private var userid = 0
    private var postids = ""
    private var liketypes = ""
    private var UserPostId = ""
    private var postions: Int = 0
    lateinit var txtLikecomment: TextView
    lateinit var txtLike: TextView
    lateinit var txtMessage: EditText
    lateinit var sendButton: FloatingActionButton
    lateinit var recylerviewCommenst: RecyclerView
    private lateinit var dialogs: BottomSheetDialog

    lateinit var usercommentAdapter: UsercommentAdapter
    var isScrolling: Boolean = false
    private var currentPage = PaginationListener.PAGE_START
    private var islastPage = false
    private val totalPage = 10
    private var isloading = false
    var arrayList = ArrayList<DataItem>()
    var users: List<DataItem> = java.util.ArrayList<DataItem>()

    private var userModalArrayList: ArrayList<ProfilehomePageResponse>? = null

    //var ListItem: List<PostItem>  = ArrayList()
    private lateinit var profilefragmenthomeAdapter: ProfilefragmenthomeAdapter

    // var page = 1
    var page = 1
    var limit = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentProfileHomefragmentBinding.inflate(layoutInflater)
        userModalArrayList = ArrayList()
        page = 1
        arrayList.clear()
        // getprofilehomepage()

        // adding on scroll change listener method for our nested scroll view.
        // adding on scroll change listener method for our nested scroll view.
        binding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.

                page++
                //loadingPB.setVisibility(VISIBLE)
                //getDataFromAPI(page, limit)

                getprofilehomepage()

            }
        })

        return binding.root
    }

    private fun getprofilehomepage() {

        if(page==0){
            arrayList.clear();
        }

        Log.e("pageCount", page.toString())

        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))

        /*   if(page!=1)
           {
               binding.ivLoader.visibility= VISIBLE

           }*/

        userid =
            CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_id, 0)
        //  Toast.makeText(context, "page"+page.toString(), Toast.LENGTH_LONG).show()

        accountViewModel?.getprofilehomepage(
            userid.toString(),
            page.toString(),
            "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {

                if (!requireActivity().isFinishing) {

                    //  ProgressD.hideProgressDialog()
                    //  binding.ivLoader.visibility= GONE
                    // Toast.makeText(context, page.toString(), Toast.LENGTH_LONG).show()

                    if (binding.ProfileHomePage != null) {


                        if (it != null && it.success == true) {


                            if (it.post?.data?.size!! > 0) {
                                /*
                                                                Toast.makeText(context, it?.post?.data?.size!!.toString(), Toast.LENGTH_LONG).show()
                                */
//                                binding.constraintProfileHome.visibility = VISIBLE
                                binding.txtNoweather.visibility = GONE

                                // arrayList.add(it.post.data as List<DataItem>)
                                // no more post available


                                for (i in it.post.data.indices) {
                                    it.post.data.get(i)?.let { it1 -> arrayList.add(it1) }
                                }


                                val layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                binding.ProfileHomePage.layoutManager = layoutManager
                                Log.e("cheklistsize", arrayList.size.toString())
                                profilefragmenthomeAdapter = ProfilefragmenthomeAdapter(
                                    requireContext(),
                                    arrayList,
                                    this,
                                    this
                                )


                                binding.ProfileHomePage.adapter = profilefragmenthomeAdapter





//                                binding.textdavidshaww.setText(it.userDetail?.name)
//                                binding.textdavidshawemail.setText(it.userDetail?.email)
//                                binding.textdavidshawMadison.setText(it.userDetail?.city)
//                                binding.textlastactive.setText(it.userDetail?.lastActive)
                            } else {

                                //   binding.constraintProfileHome.visibility = GONE
                                // Toast.makeText(context,page.toString(), Toast.LENGTH_LONG).show()

                                if (page == 1) {
//                                    binding.constraintProfileHome.visibility = GONE
                                    binding.txtNoweather.visibility = VISIBLE
                                } else {
                                    binding.txtNoweather.visibility = GONE
                                    Toast.makeText(
                                        context,
                                        "no more post available",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            }
                        } else {
                            Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG)
                                .show()
                            if (it!!.message.toString() == "Unauthenticated.") {

                                CommonMethod.getInstance(context)
                                    .savePreference(AppConstant.KEY_loginStatus, false)
                                val intent = Intent(context, LoginActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                this?.startActivity(intent)
                                (this as HomeActivity).finish()
                            }
                        }
                    }
                }
            }

        //////////////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private fun getData() {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        Toast.makeText(context, "sscroll True", Toast.LENGTH_LONG).show()

    }

    override fun onResume() {
        getprofilehomepage()
        super.onResume()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showBottomSheetDialog() {
        dialogs = BottomSheetDialog(requireContext(), R.style.DialogStyle)
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
    override fun selectImage(postid: String) {
        UserPostId = postid
        getpostUserComment(UserPostId)
    }

    override fun selectposition(postid: String, liketype: String, positon: Int) {
        UserPostId = postid
        Log.d("TAG", "selectposition: " + UserPostId)
        postids = postid
        liketypes = liketype
        postions = positon
        getrlike(positon, liketypes)
    }

    private fun getrlike(pos: Int, liketypes: String) {
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
                        if (liketypes.equals("1")) {
                            profilefragmenthomeAdapter.plust(it.post_count.toString(), pos, "1")
                        } else {
                            profilefragmenthomeAdapter.nigative(it.post_count.toString(), pos, "2")
                        }
                        // ListItem.get(UserPostId).count
                        //  Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        // Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                        if (it!!.message.toString() == "Unauthenticated.") {

                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatus, false)
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            this?.startActivity(intent)
                            (this as HomeActivity).finish()
                        }
                    }
                }

            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun postcomments() {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        accountViewModel?.postcomments(
            UserPostId,
            txtMessage.text.toString(),
            "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                if (!requireActivity().isFinishing) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                        profilefragmenthomeAdapter.plustcomment(it.postCount.toString(), postions)
                        // getpostUserComment(UserPostId)
                        txtMessage.setText("")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                        if (it!!.message.toString() == "Unauthenticated.") {

                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatus, false)
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            this?.startActivity(intent)
                            (this as HomeActivity).finish()
                        }
                    }
                }

            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getpostUserComment(UserPostId: String) {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        accountViewModel?.getpostUserComment(
            UserPostId,
            "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    showBottomSheetDialog()
                    usercommentAdapter = UsercommentAdapter(
                        requireActivity(), it.comment as List<CommentItem>
                    )
                    val layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    recylerviewCommenst.layoutManager = layoutManager
                    recylerviewCommenst.adapter = usercommentAdapter
                    var likecount = it.likeCouunt.toString()
                    var commentcount = it.commentCount.toString()
                    txtLike.setText(likecount + " Likes |")
                    txtLikecomment.setText(commentcount + " Comments")
                    //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                    /* if(it!!.message.toString()=="Unauthenticated.")
                     {

                         CommonMethod.getInstance(context)
                             .savePreference(AppConstant.KEY_loginStatus, false)
                         val intent = Intent(context, LoginActivity::class.java)
                         intent.flags =
                             Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                         this?.startActivity(intent)
                         (this as HomeActivity).finish()
                     }  */
                }
            }
    }


}





