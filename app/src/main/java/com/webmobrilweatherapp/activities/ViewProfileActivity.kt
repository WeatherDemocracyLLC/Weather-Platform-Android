package com.webmobrilweatherapp.activities

import android.Manifest
import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.metrologistactivity.IoUtils
import com.webmobrilweatherapp.databinding.ActivityViewProfileBinding
import com.webmobrilweatherapp.function.MySingleton
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import android.widget.CompoundButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import okhttp3.RequestBody.Companion.toRequestBody


class ViewProfileActivity : AppCompatActivity() {


    lateinit var binding: ActivityViewProfileBinding
    private val RequestPermissionCode = 1
    var accountViewModel: AccountViewModel? = null
    private var usertype = "0"
    private var userid = 0
    var stremail = ""
    lateinit var image: MultipartBody.Part
    var private_key = 0
    var profileStatus = 0
    private var uploadedImageFile: File? = null
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = ActivityViewProfileBinding.inflate(layoutInflater)
        getuserprofile()
        val view = binding.root
        setContentView(view)
        binding.imgpenviewprofile.setOnClickListener {
            val i = Intent(this, ChangePasswordActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(i)
            finish()
        }
//
//        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
//            if (success && imageUri != null) {
//                val bitmap = getBitmapFromUri(imageUri!!)
//                bitmap?.let { bmp ->
//                    getImageUri(this, bmp)?.let { uri ->
//                        setImage(bmp, uri)
//                    }
//                }
//            }
//        }


//        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            uri?.let {
//                val bitmap = getBitmapFromUri(it)
//                bitmap?.let {
//                    getImageUri(this, it)?.let { uri -> setImage(it, uri) }
//                }
//            }
//        }
        binding.switch1.setOnClickListener(View.OnClickListener {

        })


        binding.txtDeleteAccount.setOnClickListener {
            showDialog()
        }
        binding.imgarrowback.setOnClickListener {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            onBackPressed()
        }


        binding.switch1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            Log.v("Switch State=",
                "" + isChecked)
            if (isChecked == true) {
                private_key = 1


                getprivatePublic(private_key.toString())


            } else {


                private_key = 2
                getprivatePublic(private_key.toString())

            }

        })

        binding.imgCamera.setOnClickListener {

            selectImage()
          /*  if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU){
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

    /*  public static Uri getImageUri(Context inContext, Bitmap inImage) {
          ByteArrayOutputStream bytes = new ByteArrayOutputStream();
          inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
          String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
          return Uri.parse(path);
      }*/

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


    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
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
        whereClause: String? = null,
    ): String? {
        var ret: String? = null

        val projection = arrayOf(MediaStore.Images.Media.DATA)  // request this column explicitly

        val cursor = contentResolver.query(uri, projection, whereClause, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
                if (columnIndex != -1) {
                    ret = it.getString(columnIndex)
                }
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


    // Helper function to extract file name from Uri if possible
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


    private fun getupdateprofileImages()
    {

        print("Api is cslling")
        //ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getupdateprofileImages(
            image,
            "Bearer " + CommonMethod.getInstance(this).getPreference(AppConstant.KEY_token)
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    getuserprofile()
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }
                }
            }
    }


    private fun getuserprofile() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getuserprofile(
            userid.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    profileStatus = it.data?.profileStatus!!
                    if (it.data?.profileStatus == 1) {
                        binding.switch1.isChecked = true
                    }
                    else{
                        binding.switch1.isChecked = false
                    }

                    binding.textDavidview.setText(it.data?.name)
                    stremail = it.data?.email.toString()
                    binding.textemailview.setText(it.data?.email.toString())
                    if (it.data?.phone != null) {
                        if (it.data!!.phone!!.equals("null")) {
                            binding.textphonenumbers.visibility = GONE
                        } else {
                            binding.textphonenumbers.visibility = VISIBLE
                            var number = it.data!!.phone.toString()
                            binding.textphonenumbers.setText(number)
                        }
                    }
                    binding.textaddress.setText(it.data?.city.toString())
                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL + it.data?.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.imgProfile)
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                        (this as HomeActivity).finish()
                    }                }
            }
        binding.imgpenmetro.setOnClickListener {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            val i = Intent(this, EditProfileActivity::class.java)
            intent.putExtra("email", stremail)
            startActivity(i)
            finish()
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
            getdeleteaccounts()
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun getdeleteaccounts() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getdeleteaccounts(
            userid.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {

                    val intent = Intent(this, SelectOptionActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finishAffinity()
                    CommonMethod.getInstance(this)
                        .savePreference(AppConstant.KEY_loginStatus, false)
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }                }
            }
    }

    //*******************************************Private Profile***************************************//


    private fun getprivatePublic(toggle: String) {


        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getPublicPrivate(
            toggle.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token))
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {

                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_LONG).show()
                    if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(this)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                    }                }
            }
        
    }
    }
