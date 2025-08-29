package com.webmobrilweatherapp.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
import com.webmobrilweatherapp.databinding.FragmentProfilePhotosBinding

class ProfilePhotosFragment : Fragment(),ProfilefragmentphotosAdapter.SelectItem {
    lateinit var binding: FragmentProfilePhotosBinding
    var accountViewModel: AccountViewModel? = null
    private var userid = 0
    private lateinit var profilefragmentphotosAdapter: ProfilefragmentphotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePhotosBinding.inflate(layoutInflater)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        getpostimageuser()
        return binding.root
    }
    /*  private fun initPhotosItem() {
          val layoutManager = GridLayoutManager(requireContext(),3)
          binding.RecylerViewPhotos.layoutManager = layoutManager
          binding.RecylerViewPhotos.adapter = ProfilefragmentphotosAdapter(requireContext())

      }*/


    private fun getpostimageuser() {
        //  ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        userid =
            CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getpostimageuser(
            userid.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                //  ProgressD.hideProgressDialog()
                if (binding.RecylerViewPhotos != null) {

                    if (it != null && it.success == true) {
                        if (it.post as List<String> != null && it.post.size > 0) {
                            binding.Txtnovoting.visibility = GONE
                            profilefragmentphotosAdapter = ProfilefragmentphotosAdapter(
                                requireActivity(),
                                it.post as List<String>,this)
                            val layoutManager = GridLayoutManager(requireContext(), 3)
                            binding.RecylerViewPhotos.layoutManager = layoutManager
                            binding.RecylerViewPhotos.adapter = profilefragmentphotosAdapter
                        } else {
                            binding.Txtnovoting.visibility = VISIBLE
                        }
                    } else {

                            binding.Txtnovoting.visibility = VISIBLE

                        /*if(it!!.message.toString()=="Unauthenticated.")
                        {

                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatus, false)
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            requireContext()?.startActivity(intent)
                            (context as HomeActivity).finish()
                        }      */                    }
                }
            }
    }

    override fun selectItem(imgLink: String) {
        var name=CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_Fullname,"0")
        val btnsheet =
            layoutInflater.inflate(R.layout.imgview_item, null)

         var dialog: BottomSheetDialog=BottomSheetDialog(requireContext())
        var ImageView=btnsheet.findViewById<ImageView>(R.id.view_image)
        var nameview=btnsheet.findViewById<TextView>(R.id.nameView)
        var cross=btnsheet.findViewById<ImageView>(R.id.cross)

     //   nameview.setText(name)
        Glide.with(requireContext())
            .load(imgLink)
            .placeholder(R.drawable.galleryimg)
            .into(ImageView)
    cross.setOnClickListener(View.OnClickListener {
          dialog.dismiss()
   })
      //  dialog.setContentView(btnsheet)
       // dialog.show()
      //  dialog.setCancelable(true)


    }
}