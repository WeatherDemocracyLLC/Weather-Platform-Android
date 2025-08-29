package com.webmobrilweatherapp.activities.metrologistactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.webmobrilweatherapp.activities.AppConstant
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.webmobrilweatherapp.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddPhotoBottomDialogFragment : BottomSheetDialogFragment() {

    lateinit var photoCamera: TextView
    lateinit var tv_btn_add_photo_gallery: TextView

    companion object {
        private var mPhotoCallback: AddPhotoCallback? = null
        fun newInstance(mPhotoCallback: AddPhotoCallback) = AddPhotoBottomDialogFragment()
            .also { this.mPhotoCallback = mPhotoCallback }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.layout_bottom_choose_image, container, false)
        photoCamera = view.findViewById(R.id.tv_btn_add_photo_camera)
        tv_btn_add_photo_gallery = view!!.findViewById(R.id.tv_btn_add_photo_gallery)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoCamera.setOnClickListener {
            openCamera()
        }

        tv_btn_add_photo_gallery.setOnClickListener {
            openGallery()
        }
    }

    //open gallery
    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            AppConstant.CODE_PERMISSION_GALLERY
        )
    }

    //open camera
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null;
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    requireActivity(),
                    "com.webmobrilweatherapp",
                    photoFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(
                    intent,
                    AppConstant.CODE_PERMISSION_CAMERA
                )
            }

        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        GalleryUtils.mCurrentPhotoPath = image.absolutePath
        return image
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            try {
                //dismiss camera/gallery dialog
                this.dismissAllowingStateLoss()
                when (requestCode) {
                    AppConstant.CODE_PERMISSION_CAMERA -> {
                        val imageFile = File(GalleryUtils.mCurrentPhotoPath!!)
                        //    val compressedImageFile = Compressor(context).compressToFile(imageFile)
                        val imageUri = Uri.fromFile(imageFile)
                        mPhotoCallback!!.getImageFile(
                            imageFile = imageFile,
                            imageUri = imageUri
                        )

                    }
                    AppConstant.CODE_PERMISSION_GALLERY -> {
                        // Get the url from data
                        val selectedImage = data!!.data
                        try {
                            val imgPath = FilePath.getPath(requireActivity(), selectedImage!!)
                            val destination = File(imgPath!!)
                            /* val compressedImageFile =
                                 Compressor(context).compressToFile(destination)*/
                            mPhotoCallback!!.getImageFile(
                                imageFile = destination,
                                imageUri = selectedImage
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                    else -> {
                        Toast.makeText(requireActivity(), "try again", Toast.LENGTH_LONG).show()
                    }


                    //   requireActivity().showToast(requireActivity().resources.getString(R.string.try_again))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun getRealPathFromURI(contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor =
            Objects.requireNonNull(requireActivity())
                .managedQuery(contentUri, proj, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }
}