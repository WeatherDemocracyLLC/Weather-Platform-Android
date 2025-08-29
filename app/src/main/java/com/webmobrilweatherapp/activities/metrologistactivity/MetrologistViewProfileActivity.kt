package com.webmobrilweatherapp.activities.metrologistactivity

import android.Manifest
import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.SelectOptionActivity
import com.webmobrilweatherapp.databinding.ActivityMetrologistViewProfileBinding
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class  MetrologistViewProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistViewProfileBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"
    private val RequestPermissionCode = 1
    var private_key=0
    lateinit var image: MultipartBody.Part
    private var uploadedImageFile: File? = null
    var accountViewModel: AccountViewModel? = null
    var profileStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist  =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_view_profile)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        getuserprofileMetrologist()
        binding.LayoutrMetrologistProfile.ImgBack.setOnClickListener {
            onBackPressed()
        }


        binding.LayoutrMetrologistProfile.txtLocation.setText("Update Profile")
        binding.texteditprofile.setOnClickListener {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            var intent = Intent(this, MeterologistEditProfileActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.txtDeleteAccount.setOnClickListener {
            showDialog()
        }
        binding.textchangepassword.setOnClickListener {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            var intent = Intent(this, MeterologistChangePasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.switch1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            Log.v("Switch State=", "" + isChecked)
            if(isChecked==true)
            {
                private_key=1
                getprivatePublic(private_key.toString())
            }
            else
            {
                private_key=2
                getprivatePublic(private_key.toString())
            }
        })

        binding.imgCamera.setOnClickListener {

            selectImage()
           /* if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU){
                selectImage()
            }else {
                if(checkAndRequestPermissions()){
                    selectImage()                }
            }*/
//            if (checkAndRequestPermissions()) {
//                selectImage()
//            }
        }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose From Gallery")
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Add Photo")
        /*   builder.setIcon(R.drawable.applogo);*/builder.setItems(
            options
        ) { dialog: DialogInterface, item: Int ->
            if (options[item] == "Take Photo") {
                dialog.dismiss()
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 0)
            } else if (options[item] == "Choose From Gallery") {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 1)
            }
        }
        builder.show()
    }

    private fun checkAndRequestPermissions(): Boolean {



        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q){
            val READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            val CAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            val listPermissionsNeeded: MutableList<String> = ArrayList()
            if (READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (CAMERA != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA)
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    listPermissionsNeeded.toTypedArray(),
                    RequestPermissionCode
                )
                return false
            }
        }

        // For Android 11 and 12 (API levels 30 and 31)
        if( Build.VERSION.SDK_INT in Build.VERSION_CODES.R..Build.VERSION_CODES.S_V2) {
            val READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            )

            val CAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            val listPermissionsNeeded: MutableList<String> = ArrayList()
            if (READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (CAMERA != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA)
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    listPermissionsNeeded.toTypedArray(),
                    RequestPermissionCode
                )
                return false
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            val READ_MEDIA_VIDEO = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_MEDIA_VIDEO
            )
            val READ_MEDIA_IMAGES = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            )
            val READ_MEDIA_AUDIO = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_AUDIO
            )
            val CAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            val listPermissionsNeeded: MutableList<String> = ArrayList()
            if (READ_MEDIA_VIDEO != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_VIDEO)
            }
            if (READ_MEDIA_IMAGES != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES)
            }
            if (READ_MEDIA_AUDIO != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_AUDIO)
            }
            if (CAMERA != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA)
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    listPermissionsNeeded.toTypedArray(),
                    RequestPermissionCode
                )
                return false
            }
        }

        return true
    }

/*
    private fun checkAndRequestPermissions(): Boolean {
        val READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val CAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (CAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                RequestPermissionCode
            )
            return false
        }
        return true
    }
*/

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        print("request code");
        when (requestCode) {


            0 -> if (resultCode == RESULT_OK && data != null) {
                val bmp = data.extras?.get("data") as? Bitmap
                if (bmp != null) {
                    getImageUri(this, bmp)?.let { uri ->
                        setImage(uri)
                    } ?: run {
                        Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
                }
            }

            1 -> if (resultCode == RESULT_OK && data != null) {
                val selectedImage = data.data
                if (selectedImage != null) {
                    setImage(selectedImage)
                } else {
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                }
            }


            2 -> if (resultCode == RESULT_OK) {
                if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
                    val uri = data.data
                    Log.i("TAG", "onActivityResult: " + getFileName(uri))
                    val parcelFileDescriptor: ParcelFileDescriptor?
                    try {
                        parcelFileDescriptor = contentResolver.openFileDescriptor(uri!!, "r", null)
                        val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
                        val file = File(cacheDir, getFileName(uri))
                        val outputStream = FileOutputStream(file)
                        IoUtils.copy(inputStream, outputStream)
                        uploadedImageFile = file
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }


    fun getImageUri(context: Context, image: Bitmap): Uri? {
        // Create temp file
        val tempFile = File.createTempFile("temprentpk", ".png", context.cacheDir)
        val bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val bitmapData = bytes.toByteArray()

        tempFile.outputStream().use {
            it.write(bitmapData)
            it.flush()
        }

        // Use FileProvider to get content Uri
        return FileProvider.getUriForFile(
            context,
            context.getString(R.string.file_provider_name),
            tempFile
        )
    }

    private fun getUriRealPath(ctx: Context, uri: Uri): String? {
        var ret: String? = ""
        if (isAboveKitKat()) {
            // Android OS above sdk version 19.
            ret = getUriRealPathAboveKitkat(ctx, uri)
        } else {
            // Android OS below sdk version 19
            ret = getImageRealPath(getContentResolver(), uri, null)
        }
        return ret
    }

    private fun getUriRealPathAboveKitkat(ctx: Context?, uri: Uri?): String? {
        var ret: String? = ""
        if (ctx != null && uri != null) {
            if (isContentUri(uri)) {
                if (isGooglePhotoDoc(uri.authority.toString())) {
                    ret = uri.lastPathSegment
                } else {
                    ret = getImageRealPath(getContentResolver(), uri, null)
                }
            } else if (isFileUri(uri)) {
                ret = uri.path
            } else if (isDocumentUri(ctx, uri)) {

                // Get uri related document id.
                var documentId: String? = null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    documentId = DocumentsContract.getDocumentId(uri)
                }

                // Get uri authority.
                val uriAuthority = uri.authority
                if (isMediaDoc(uriAuthority!!)) {
                    val idArr = documentId!!.split(":").toTypedArray()
                    if (idArr.size == 2) {
                        // First item is document type.
                        val docType = idArr[0]

                        // Second item is document real id.
                        val realDocId = idArr[1]

                        // Get content uri by document type.
                        var mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        if ("image" == docType) {
                            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        } else if ("video" == docType) {
                            mediaContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        } else if ("audio" == docType) {
                            mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }
                        // Get where clause with real document id.
                        val whereClause = MediaStore.Images.Media._ID + " = " + realDocId
                        ret = getImageRealPath(
                            getContentResolver(),
                            mediaContentUri,
                            whereClause
                        )
                    }
                } else if (uriAuthority?.let { isDownloadDoc(it) } == true) {
                    // Build download uri.
                    val downloadUri = Uri.parse("content://downloads/public_downloads")

                    // Append download document id at uri end.
                    var downloadUriAppendId: Uri? = null
                    if (documentId != null) {
                        downloadUriAppendId = ContentUris.withAppendedId(
                            downloadUri,
                            java.lang.Long.valueOf(documentId)
                        )
                    }
                    ret = downloadUriAppendId?.let {
                        getImageRealPath(
                            getContentResolver(),
                            it,
                            null
                        )
                    }
                } else if (isExternalStoreDoc(uriAuthority.toString())) {
                    var idArr = arrayOfNulls<String>(0)
                    if (documentId != null) {
                        idArr = documentId.split(":").toTypedArray()
                    }
                    if (idArr.size == 2) {
                        val type = idArr[0]
                        val realDocId = idArr[1]
                        if ("primary".equals(type, ignoreCase = true)) {
                            ret = Environment.getExternalStorageDirectory()
                                .toString() + "/" + realDocId
                        }
                    }
                }
            }
        }
        return ret
    }

    private fun isExternalStoreDoc(uriAuthority: String): Boolean {
        var ret = false
        if ("com.android.externalstorage.documents" == uriAuthority) {
            ret = true
        }
        return ret
    }

    private fun getImageRealPath(
        contentResolver: ContentResolver,
        uri: Uri,
        whereClause: String?
    ): String {
        var ret = ""

        // Query the uri with condition.
        val cursor = contentResolver.query(uri, null, whereClause, null, null)
        if (cursor != null) {
            val moveToFirst = cursor.moveToFirst()
            if (moveToFirst) {

                // Get columns name by uri type.
                var columnName = MediaStore.Images.Media.DATA
                if (uri === MediaStore.Images.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Images.Media.DATA
                } else if (uri === MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Audio.Media.DATA
                } else if (uri === MediaStore.Video.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Video.Media.DATA
                }
                // Get column index.
                val imageColumnIndex = cursor.getColumnIndex(columnName)

                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex)
            }
        }
        return ret
    }

    private fun setImage(imageUri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(imageUri)
            if (inputStream != null) {
                val bytes = inputStream.readBytes()
                val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())
                val fileName = getFileNameFromUri(imageUri) ?: "upload_image.jpg"
                image = MultipartBody.Part.createFormData(
                    "profile_image",
                    fileName,
                    requestBody
                )
                getupdateprofileImages()
            } else {
                Log.e("TAG", "Failed to open InputStream for URI")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFileNameFromUri(uri: Uri): String? {
        var name: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    name = it.getString(index)
                }
            }
        }
        return name
    }

    private fun isDownloadDoc(uriAuthority: String): Boolean {
        var ret = false
        if ("com.android.providers.downloads.documents" == uriAuthority) {
            ret = true
        }
        return ret
    }

    private fun isDocumentUri(ctx: Context?, uri: Uri?): Boolean {
        var ret = false
        if (ctx != null && uri != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ret = DocumentsContract.isDocumentUri(ctx, uri)
            }
        }
        return ret
    }

    private fun isMediaDoc(uriAuthority: String): Boolean {
        var ret = false
        if ("com.android.providers.media.documents" == uriAuthority) {
            ret = true
        }
        return ret
    }

    private fun isGooglePhotoDoc(uriAuthority: String): Boolean {
        var ret = false
        if ("com.google.android.apps.photos.content" == uriAuthority) {
            ret = true
        }
        return ret
    }

    private fun isAboveKitKat(): Boolean {
        var ret = false
        ret = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        return ret
    }

    private fun isContentUri(uri: Uri?): Boolean {
        var ret = false
        if (uri != null) {
            val uriSchema = uri.scheme
            if ("content".equals(uriSchema, ignoreCase = true)) {
                ret = true
            }
        }
        return ret
    }

    private fun isFileUri(uri: Uri?): Boolean {
        var ret = false
        if (uri != null) {
            val uriSchema = uri.scheme
            if ("file".equals(uriSchema, ignoreCase = true)) {
                ret = true
            }
        }
        return ret
    }

    fun <T> T?.getRequestBody(): RequestBody {
        val result: T? = this
        if (result is File) {
            return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), (result as File))
        } else if (result is String) {
            return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), (result as String))
        }
        throw Exception("Invalid Format !! Pass String and File Only")
    }

    fun getFileName(fileUri: Uri?): String? {
        var name = ""
        val returnCursor = contentResolver.query(fileUri!!, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    private fun getupdateprofileImages() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getupdateprofileImagesMetrologist(image, "Bearer " + CommonMethod.getInstance(this).getPreference(AppConstant.KEY_token_Metrologist))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    getuserprofileMetrologist()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun getuserprofileMetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        useridMetrologist =
            CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getuserprofileMetrologist(
            useridMetrologist, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    profileStatus = it.data?.profileStatus!!
                    if (it.data?.profileStatus == 1) {
                        binding.switch1.isChecked = true
                    } else {
                        binding.switch1.isChecked = false
                    }


                    binding.textDavidview.setText(it.data?.name)
                    binding.textemailview.setText(it.data?.email)
                    var phoneno = it.data?.phone.toString()
                    if (phoneno.equals("null")) {
                        binding.textphonenumber.visibility = View.GONE
                    } else {
                        binding.textphonenumber.visibility = View.VISIBLE
                        binding.textphonenumber.setText(it.data?.phone.toString())
                    }
                    binding.textaddress.setText(it.data?.city)
                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL + it.data?.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.imgProfile)
                    // Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)
        val yesBtn = dialog.findViewById(R.id.btnYes) as Button
        val noBtn = dialog.findViewById(R.id.btnNo) as Button
        yesBtn.setOnClickListener {
            getlogoutsmetrologist()
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun getlogoutsmetrologist() {
        ProgressD.showLoading(this,getResources().getString(R.string.logging_in))
        useridMetrologist =
            CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getdeleteaccountmetrologist(useridMetrologist,"Bearer " + CommonMethod.getInstance(this).getPreference(
            AppConstant.KEY_token_Metrologist
        )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    val intent = Intent(this, SelectOptionActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finishAffinity()
                     Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }


    //*******************************************Private Profile***************************************//


    private fun getprivatePublic(toggle:String)
    {

       // ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getPublicPrivate(
            toggle.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist))
            ?.observe(this) {
               // ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {

                  //  Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}