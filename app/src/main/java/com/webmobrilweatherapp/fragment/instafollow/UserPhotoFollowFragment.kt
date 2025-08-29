package com.webmobrilweatherapp.fragment.instafollow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.adapters.ProfilefragmentphotosAdapter
import com.webmobrilweatherapp.databinding.FragmentUserPhotoFollowBinding

class UserPhotoFollowFragment : Fragment(),ProfilefragmentphotosAdapter.SelectItem {
    lateinit var binding: FragmentUserPhotoFollowBinding
    private var userInstaid = ""
    var accountViewModel: AccountViewModel? = null
    private lateinit var profilefragmentphotosAdapter: ProfilefragmentphotosAdapter

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
        binding= FragmentUserPhotoFollowBinding.inflate(layoutInflater)
        getpostimageuser()
        return binding.root
    }
    private fun getpostimageuser() {
        //  ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
     //   userInstaid =CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_IntsUserId)
     //   Toast.makeText(requireActivity(),userInstaid.toString(),Toast.LENGTH_SHORT).show();

        accountViewModel?.getpostimageuser(
            userInstaid.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                //  ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.post as List<String> != null && it.post.size > 0) {
                        binding.Txtnovoting.visibility = View.GONE
                        profilefragmentphotosAdapter =
                            ProfilefragmentphotosAdapter(requireActivity(), it.post as List<String>,this)
                        val layoutManager = GridLayoutManager(requireContext(), 3)
                        binding.RecylerViewPhotos.layoutManager = layoutManager
                        binding.RecylerViewPhotos.adapter = profilefragmentphotosAdapter


                    } else {
                        binding.Txtnovoting.visibility = View.VISIBLE
                    }
                } else {
                    //  Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun selectItem(imgLink: String) {

        var name=CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_Fullname,"0")
        val btnsheet =
            layoutInflater.inflate(R.layout.imgview_item, null)

        var dialog: BottomSheetDialog = BottomSheetDialog(requireContext())
        var ImageView=btnsheet.findViewById<ImageView>(R.id.view_image)
        var nameview=btnsheet.findViewById<TextView>(R.id.nameView)
        var cross=btnsheet.findViewById<ImageView>(R.id.cross)

/*
        nameview.setText(name)
*/
        Glide.with(requireContext())
            .load(imgLink)
            .placeholder(R.drawable.galleryimg)
            .into(ImageView)
        cross.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.setContentView(btnsheet)
        dialog.show()
        dialog.setCancelable(true)

    }
}