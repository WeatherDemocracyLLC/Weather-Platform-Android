package com.webmobrilweatherapp.activities.metrologistactivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.databinding.ActivityMertologistSignUpBinding
import com.webmobrilweatherapp.viewmodel.webconfig.Utils
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class MertologistSignUpActivity : AppCompatActivity(),PermissionCallback {
    lateinit var binding: ActivityMertologistSignUpBinding
    private val RequestPermissionCode = 1
    var profile: File? = null
    protected var locationManager: LocationManager? = null
    private val AUTOCOMPLETE_REQUEST_CODE = 3
    var checkGPS = false
    var checkNetwork = false
    var canGetLocation = false
    var loc: Location? = null
    var Checkbox = 0
    var names = ""
    var email = ""
    var password = ""
    var ltitute = 0
    var city: String = "0"
    var state: String = "0"
    var longituted = 0
    var zipCode = "0"
    var country = "0"
    var latitude = 0.0
    var longitude= 0.0
    private val geocoder: Geocoder? = null

    private var mRequestQueue: RequestQueue? = null
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var uploadedImageFile: File? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mertologist_sign_up)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgBatterfly!!)
        binding.txtLogin.setOnClickListener {
            val i = Intent(this, MetrologistLogInActivity::class.java)
            startActivity(i)
            finish()
        }
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM"]
        //  val apiKey = value.toString()
        //val apiKey = "AIzaSyBlCaysRN5XHvtLzJIyRkjaiXEKQbOl1c8"
        val apiKey = "AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM"
        Log.e("apiKey", apiKey)
        // Initializing the Places API
        // with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }
        mRequestQueue = Volley.newRequestQueue(this);
        binding.etSelectcity.setOnClickListener { v ->
            val fields =Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG)
            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
            startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE)
        }
        binding.CheckoxSignUpmetrologist.setOnClickListener {
            if (binding.CheckoxSignUpmetrologist.isChecked) {
                Checkbox = 1
            } else {
                Checkbox = 0
            }
        }
        binding.txtUploadeImg.setOnClickListener {
//            val permissionArray = ArrayList<String>()
//           if( Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q ) {
//            permissionArray.apply {
//                add(android.Manifest.permission.CAMERA)
//                add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//              //  add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            }
//        }
//            // For Android 11 and 12 (API levels 30 and 31)
//           if( Build.VERSION.SDK_INT in Build.VERSION_CODES.R..Build.VERSION_CODES.S_V2) {
//            permissionArray.apply {
//                add(android.Manifest.permission.CAMERA)
//                add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//            }
//        }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
//            ) {
//                permissionArray.apply {
//                    add(android.Manifest.permission.CAMERA)
//                    add(android.Manifest.permission.READ_MEDIA_IMAGES)
//                    add(android.Manifest.permission.READ_MEDIA_VIDEO)
//                    add(android.Manifest.permission.READ_MEDIA_AUDIO)
//                }
//            }
//          /*  permissionArray.apply {
//                add(android.Manifest.permission.CAMERA)
//                add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            }*/
//
//            PermissionManager(this, this, permissionArray)
            selectImage()

        }
        binding.txtReadMoreSignUp.setOnClickListener {
                 val i = Intent(this, WerViewActivity::class.java)
                    i.putExtra("webviewurl", "1")
                    startActivity(i)
               /* val i = Intent(this,TermsandConditionActivity::class.java)
                    startActivity(i)
                val uri = Uri.parse(ApiConstants.TermsCondition)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)*/
        }

        binding.BtnsignUpmetrologist.setOnClickListener {

           if (Utils.checkConnection(this)) {
                when {
                    isValidation() -> {
                        when {
                            uploadedImageFile != null -> {
                                updateProfile()
                            }
                            else -> {
                                Toast.makeText(
                                    this,
                                    "Please Upload a meteorologist degree,  Accreditation or\n" +
                                            "certificatio",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Oops please check your intenet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }

            //  val i = Intent(this, MetrologistButterFlySpeicesActivity::class.java)
            //  startActivity(i)


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
                startActivityForResult(intent,0)
            } else if (options[item] == "Choose From Gallery") {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 1)
            }
        }
        builder.show()
    }

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

    private fun isValidation(): Boolean {
        if (binding.etSignupName.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter Name", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.etEmailMetrologist.text.toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            return false
        } else if (!validateEmail(binding.etEmailMetrologist.text.toString())) {
            binding.etEmailMetrologist.requestFocus()
            Toast.makeText(this, "Please Enter valid Email Id", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.etPasswordSignUp.text.toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.etPasswordSignUp.text.length < 8 || binding.etPasswordSignUp.text.length > 16) {
            showToastMessage("Password Must be Between 8 to 16 Characters")
            return false
        } else if (!isValidPassword(binding.etPasswordSignUp.text.toString())) {
            showToastMessage("Please Enter One Upper case character one numeric character and one special character")
            return false
        } else if (binding.etSelectcity.text.toString().isEmpty()) {
            Toast.makeText(this, "Please Enter City Name", Toast.LENGTH_SHORT).show()
            return false
        } else if (Checkbox != 1) {
            Toast.makeText(this, " Please Use Terms & Conditions Check Box", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }

    private fun showToastMessage(message: String) {
        var toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 500)
        toast.show()
    }
    private fun updateProfile() {

        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        var image: MultipartBody.Part? = null
        profile = uploadedImageFile
        if (uploadedImageFile != null) {
            val requestFile: RequestBody = profile.getRequestBody()
            image = MultipartBody.Part.createFormData(
                "meterologist_docs",
                uploadedImageFile!!.name,
                requestFile
            )
        } else {
            image = MultipartBody.Part.createFormData(
                "",
                "meterologist_docs",
                "".getRequestBody()
            )
        }
        //  val userName:RequestBody= RequestBody.create("multipart/form-data".toMediaTypeOrNull(),uploadedImageFile!!.name)
        val name: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            binding.etSignupName.text.toString()
        )
        val email: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            binding.etEmailMetrologist.text.toString()
        )
        val password: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            binding.etPasswordSignUp.text.toString()
        )
        val selectcity: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), city)
         val state: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),state)
         val country: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),country)
         val latititudes: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),ltitute.toString())
         val longituteds: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),longituted.toString())
         val zipcode: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),zipCode)


        accountViewModelMetrologist?.getResgistrationMetrologist(
            name,
            email,
            password,
            selectcity,
            3,
            image,state,country,latititudes,longituteds,zipcode
        )
            ?.observe(this, Observer {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    Log.e("signup", it.data.toString())
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    CommonMethod.getInstance(this).savePreference(AppConstant.Key_ID_MetrologistButterfly,"0")

                    CommonMethod.getInstance(this)
                        .savePreference(AppConstant.KEY_User_idMetrologist, it.data?.id.toString())
                  //  CommonMethod.getInstance(this).savePreference(AppConstant.KEY_City_METROLOGIST,selectcity)
                    val i = Intent(this, MetrologistVerificationAccountActivity::class.java)


                    i.putExtra("email", it.data?.email)
                    startActivity(i)
                } else {
                    Toast.makeText(this, it?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {



        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)

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

    private fun setImage(bitmap: Bitmap, imageUri: Uri) {
        // profile_image.setImageBitmap(bmp)
        binding.ImgUploadImgSignup.visibility = VISIBLE

        Picasso.get()
            .load(imageUri)
            .placeholder(R.drawable.appicon)
            .into(binding.ImgUploadImgSignup)
        Log.i("TAG", "setImage123: " + getUriRealPath(this, imageUri))
        uploadedImageFile = File(getUriRealPath(this, imageUri))

        /* viewModel.queryUpdateProfile(
            PrefManager.getInstance(this).getPreference(AppConstant.KEY_AUTH_TOKEN),
            uploadedImageFile
        )*/
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

   /* fun <T> T?.getRequestBody(): RequestBody {
        val result: T? = this
        if (result is File) {
            return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), (result as File))
        } else if (result is String) {
            return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), (result as String))
        }
        throw Exception("Invalid Format !! Pass String and File Only")
    }*/

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

    fun validateEmail(email: String): Boolean {
        return Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        ).matcher(email).matches()
    }

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+*!=])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun getLocation() {
        try {
            locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
            // get GPS status
            if (locationManager != null) {
                checkGPS = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            }
            checkNetwork = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!checkGPS && !checkNetwork) {
                Toast.makeText(this, "No Service Provider is available", Toast.LENGTH_SHORT).show()
            } else {
                this.canGetLocation = true
                if (checkGPS) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    /*  locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);*/Log.e(
                        "locationManager",
                        "locationManager$locationManager"
                    )
                    if (locationManager != null) {
                        Log.e("locationManager", "locationManager")
                        loc = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        if (loc != null) {
                            val latitude: Double = loc!!.getLatitude()
                            val longitude: Double = loc!!.getLongitude()
                            getalllocation(latitude.toString() ,longitude.toString())

                            getCompleteAddress(latitude, longitude)
                            CommonMethod.getInstance(this).savePreference(AppConstant.location,latitude.toString())
                            CommonMethod.getInstance(this).savePreference(AppConstant.Longituted,longitude.toString())
                            Log.e("latitude", "==$latitude")
                            Log.e("longitude", "==$longitude")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getCompleteAddress(latitude: Double, longitude: Double): String {
        val address = ""
        var addresses: List<Address> = java.util.ArrayList()
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            )!! // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            val addressLine =addresses[0].getAddressLine(0)
            // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            zipCode = addresses[0].postalCode.toInt().toString()
            country = addresses[0].countryName
            state = addresses[0].adminArea
          //  city=addresses[0].locality
            getalllocation(latitude.toString() ,longitude.toString())
            Log.e("zip Code", zipCode.toString())
            Log.e("address", addressLine)

        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        return address
    }

    private fun getalllocation(latitude: String, longitude: String) {

        accountViewModelMetrologist?.getalllocationmetrologist(
            (latitude+","+longitude),"AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM"
        )?.observe(this) {
            //ProgressD.hideProgressDialog()
            if (it != null) {
                if (it.plusCode!!.compoundCode!=null){

                    var date = it.plusCode.compoundCode
                    val upToNCharacters: String = date!!.substring(8, Math.min(date.length,40))

                    city=upToNCharacters
                }else{
                    Toast.makeText(this,"Please Enter Valid City Name",Toast.LENGTH_LONG).show()
                }

            } else {
                //Toast.makeText(context, , Toast.LENGTH_LONG).show()
            }

        }
        // Get user sign up response
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



        when (requestCode) {
            0 -> if (resultCode == RESULT_OK) {
                assert(data != null)
                val bundle = data!!.extras!!
                val bmp = (bundle["data"] as Bitmap?)!!
                //   val photo = data.getExtras()?.get("data")
                // var uri=Uri.parse(photo as String?)
                /*

                                if (data?.data != null) {
                                    val uriPathHelper = AddGalleryFragment.URIPathHelper()
                                    val videoFullPath = uriPathHelper.getPath(this, data.data!!)

                                    if (videoFullPath != null) {
                                        val file = File(videoFullPath)


                                        // setImage(bmp,  Uri.fromFile( file))


                                    }
                                }
                */
                /*
                                Toast.makeText(this, bmp.toString(), Toast.LENGTH_SHORT).show()
                */


                getImageUri(this, bmp)?.let { setImage(bmp, it) }

                // Toast.makeText(this, getImageUri(bmp, Bitmap.CompressFormat.PNG, 80).toString(), Toast.LENGTH_SHORT).show()

            }
            1 -> if (resultCode == RESULT_OK) {
                var selectedImage: Uri? = null
                if (data != null) {
                    selectedImage = data.data
                }

                if (selectedImage != null) {
                    // Decode the image from URI
                    val inputStream = contentResolver.openInputStream(selectedImage)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()

                    // Get URI from the bitmap and set image
                    getImageUri(this, bitmap)?.let {
                        setImage(bitmap, it)
                    }
                }
            }

        }

//        if (requestCode==RequestPermissionCode) {
//            if (resultCode == RESULT_OK) {
//                assert(data != null)
//                val bundle = data!!.extras!!
//                val bmp = (bundle["data"] as Bitmap?)!!
//
//               // setImage(bmp, getImageUri(bmp, Bitmap.CompressFormat.PNG, 80)!! as Uri)
//            }
//            if (resultCode == RESULT_OK) {
//
//                var selectedImage: Uri? = null
//                if (data != null) {
//                    selectedImage = data.data
//                }
//                val filepath = arrayOf(MediaStore.Images.Media.DATA)
//                var c: Cursor? = null
//                if (selectedImage != null) {
//                    c = getContentResolver()
//                        .query(selectedImage, filepath, null, null, null)
//                }
//                c?.moveToFirst()
//                var columnIndex = 0
//                if (c != null) {
//                    columnIndex = c.getColumnIndex(filepath[0])
//                }
//                val picturePath = c!!.getString(columnIndex)
//                c.close()
//                val thumbnail = BitmapFactory.decodeFile(picturePath)
//              /*  getImageUri(thumbnail, Bitmap.CompressFormat.PNG, 80)?.let {
//                    setImage(
//                        thumbnail,
//                        it
//                    )
//                }*/
//            }
//            if (resultCode == RESULT_OK) {
//
//                if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
//                    val uri = data.data
//                    Log.i("TAG", "onActivityResult: " + getFileName(uri))
//                    val parcelFileDescriptor: ParcelFileDescriptor?
//                    try {
//                        parcelFileDescriptor = contentResolver.openFileDescriptor(uri!!, "r", null)
//                        val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
//                        val file = File(cacheDir, getFileName(uri))
//                        val outputStream = FileOutputStream(file)
//                        IoUtils.copy(inputStream, outputStream)
//                        uploadedImageFile = file
//                        Log.e("name", uploadedImageFile!!.name.toString())
//                    } catch (e: java.lang.Exception) {
//                        e.printStackTrace()
//                    }
//                }
//            }
//        }
//        else
            if  (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                var place: Place? = null
                if (data != null) {
                    place = Autocomplete.getPlaceFromIntent(data)
                    Log.e("TAG", "Place: " + place.name + ", " + place.id)
                    latitude = Objects.requireNonNull(place.latLng).latitude
                    longitude = place.latLng.longitude
                    //city=place.name
                    ltitute=place.latLng.latitude.toInt()
                    longituted=place.latLng.longitude.toInt()
                    binding.etSelectcity.setText(place.name)
                    binding.etSelectcity.setTextColor(Color.BLACK)
                    getalllocation(latitude.toString() ,longitude.toString())

                    getCompleteAddress(latitude,longitude)
                    CommonMethod.getInstance(this).savePreference(AppConstant.location,latitude.toString())
                    CommonMethod.getInstance(this).savePreference(AppConstant.Longituted,longitude.toString())
                    CommonMethod.getInstance(this).savePreference(AppConstant.cityName,place.name.toString())
                    getCountryCityCode(latitude, longitude)

                }
            }
            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data)
                assert(status.statusMessage != null)
                Log.e("TAG", status.statusMessage!!)
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }


            return
        }
    }

    private fun getCountryCityCode(latitude: Double, longitude: Double) {
        try {
            val addressList: MutableList<Address>? = geocoder!!.getFromLocation(latitude, longitude, 1)
            if (addressList != null && addressList.size > 0) {
                val locality = addressList[0].getAddressLine(0)
                val country = addressList[0].countryName
                val city = addressList[0].locality
                val state = addressList[0].adminArea
                val postalCode = addressList[0].postalCode
                val street = addressList[0].featureName + ", " + addressList[0].subLocality
                if (!locality.isEmpty() && !country.isEmpty()) {
                    runOnUiThread {
                        if (!TextUtils.isEmpty(city)) {

                            /*     PrefManager.getInstance(MainActivity.this).setLatitude(String.valueOf(latitude));
                             PrefManager.getInstance(MainActivity.this).setLongitude(String.valueOf(longitude));*/
                            /* CommonMethod.getInstance(this).savePreference("$street  $city $state $postalCode")
                             loadHome("0")*/

                        }
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    override fun allPermissionGranted(isGranted: Boolean) {
        if (isGranted) {
            val addPhotoBottomDialogFragment =
                AddPhotoBottomDialogFragment.newInstance(object : AddPhotoCallback {
                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun getImageFile(imageFile: File, imageUri: Uri) {
                        uploadedImageFile = imageFile
                        setImageInImageView(imageFile)
                    }
                })
            addPhotoBottomDialogFragment.show(
                this.supportFragmentManager,
                "add_photo_dialog_fragment"
            )

        } else {
            //Toast(this,"Please enable all the permission")
            Toast.makeText(this, "Please enable all the permission", Toast.LENGTH_SHORT).show()

        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun setImageInImageView(imageUri: File) {
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(this, R.color.toolbaar))
        circularProgressDrawable.start()
        binding.ImgUploadImgSignup.visibility = VISIBLE
        Picasso.get()
            .load(imageUri)
            .placeholder(circularProgressDrawable)
            .into(binding.ImgUploadImgSignup)

        Log.e("imageUrl", "   "+ imageUri)

/*        var  bmp:Bitmap = BitmapFactory.decodeResource(File, uploadedImageFile);
bmp.getWidth(); bmp.getHeight();*/

        var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,Uri.fromFile( File(uploadedImageFile.toString())))

        val imageWidth: Int = bitmap.getWidth()

        val imageHeight: Int = bitmap.getHeight()


        Log.e("imageResolution", "   "+ imageWidth+ imageHeight)


        /*  viewModel.queryUpdateProfile(
              PrefManager.getInstance(this).getPreference(AppConstants.KEY_AUTH_TOKEN),
              uploadedImageFile
          )*/
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
}



