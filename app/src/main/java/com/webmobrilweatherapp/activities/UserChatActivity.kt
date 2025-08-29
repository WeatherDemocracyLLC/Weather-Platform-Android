package com.webmobrilweatherapp.activities

import android.Manifest
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.*
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.metrologistactivity.IoUtils
import com.webmobrilweatherapp.adapters.userchat.RetrofitInstanceUser
import com.webmobrilweatherapp.databinding.ActivityUserChatBinding
import com.webmobrilweatherapp.model.getchat.DataItem
import com.webmobrilweatherapp.viewmodel.ApiConstants
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.pusherchat.MessageModels
import com.pusher.pusherchat.MessageUserAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList

class UserChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserChatBinding
    var accountViewModel: AccountViewModel? = null
    private lateinit var adapters: MessageUserAdapter
    var currentUser = 0
    private var userid = 0

    var type = 0
    var profile: File? = null
    private var uploadedImageFile: File? = null
    private val RequestPermissionCode = 1
    lateinit var handler: Handler
    lateinit var runnableCode: Runnable
    lateinit var image: MultipartBody.Part
    private var list: ArrayList<MessageModels>? = ArrayList()
    private val pusherAppKey = "6fa600d6bc6f679d4eec"
    private val pusherAppCluster = "ap2"
    private var formattedDate:String = ""
    private var userType=0


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_chat)
        currentUser = intent.getIntExtra("userIds", currentUser)
        userType= intent.getIntExtra("UserType",userType)

        userid = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_id, 0)
        if(userType==3)
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
            binding.layoutCahts.setBackgroundColor(resources.getColor(R.color.red))
        }
        else
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.toolbaar))
            binding.layoutCahts.setBackgroundColor(resources.getColor(R.color.toolbaar))

        }
        Log.d("TAG", "usdfdsf"+currentUser)
        Log.d("TAG", "setUpPusher: setting up the cluster")
        val c = Calendar.getInstance()
        System.out.println("Current time => " + c.time)
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formattedDate = df.format(c.time)

        // currentUser = intent.getStringExtra("userIds", currentUser)
        adapters = MessageUserAdapter(this, list as ArrayList<DataItem>,userType)
        handler= Handler()
        getuserprofile()
        setupPusher()
        handler=Handler(Looper.getMainLooper())
        val linearLayoutManager: LinearLayoutManager = object : LinearLayoutManager(this) {
            override fun smoothScrollToPosition(
                recyclerView: RecyclerView,
                state: RecyclerView.State,
                position: Int,
            ) {
                val smoothScroller: LinearSmoothScroller =
                    object : LinearSmoothScroller(this@UserChatActivity) {
                        private val SPEED = 10f //Change this value (default=25f)
                        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                            return SPEED / displayMetrics.densityDpi
                        }
                    }

                smoothScroller.targetPosition = position+1
                startSmoothScroll(smoothScroller)
            }
        }
        binding.messageListUser.apply {
            this.layoutManager = linearLayoutManager
            this.adapter = adapters
        }
        binding.imgbackarrowChat.setOnClickListener {
            onBackPressed()
        }
        binding.ImgCameras.setOnClickListener {
          /*  if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU){
                selectImage()
            }else {
                if(checkAndRequestPermissions()){
                    selectImage()                }
            }*/
//            if (checkAndRequestPermissions()) {
//                selectImage()
//            }

            selectImage()
        }
        binding.sendButton.setOnClickListener {
/*
            val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now()
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formatted = current.format(formatter)*/


            var c = Calendar.getInstance()
            var df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var formattedDatee = df.format(c.time)


           // Toast.makeText(this, formattedDatee.toString(), Toast.LENGTH_LONG).show()

            if (binding.txtMessage.text.isNotEmpty()) {
                val jsonObject = JSONObject()
                jsonObject.put("message", binding.txtMessage.text.toString())
                //   jsonObject.put("my-channel",nameOfChannel)
                jsonObject.put("receiver_id", currentUser)
                val jsonBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(),
                    jsonObject.toString())
                RetrofitInstanceUser.retrofit.sendMessageUser(currentUser.toString(),
                    binding.txtMessage.text.toString(),"1",formattedDatee,
                    "Bearer " + CommonMethod.getInstance(this).getPreference(AppConstant.KEY_token)
                ).enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        //resetInput()
                        // Log.e("MetrologistchatActivity",t!!.localizedMessage)
                    }
                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        getchat()
                        // Log.e("MetrologistchatActivity", response!!.body()!!)
                    }
                })
                binding.txtMessage.text.clear()
                hideKeyBoard()
            } else {
                Toast.makeText(this, "Please Enter Message", Toast.LENGTH_LONG).show()
            }
        }

      //  setupPusher()
    }

    private fun resetInput() {
        // Clean text box
        //  binding.txtMessage.text.clear()
        // Hide keyboard
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun setupPusher() {
        val options = PusherOptions()
        options.setCluster(pusherAppCluster)
        Log.d("TAG", "setUpPusher: setting up the cluster")
        val pusher = Pusher(pusherAppKey, options)
        val channel = pusher.subscribe("my-channel")
        channel.bind("my-event") { channelName, eventName, data ->
            val jsonObject = JSONObject(data)
            Log.i("inside channel", "onBindViewHolder " + jsonObject.toString())
            val dataItem = DataItem(


                jsonObject["is_read"].toString().toInt(),
                jsonObject["message_time"].toString(),
                null,null,
                jsonObject["from"].toString().toInt(),
                jsonObject["message_type"].toString().toInt(),
                null,
                jsonObject["to"].toString().toInt(),
                jsonObject["message"].toString(),
            )
            runOnUiThread {
                if ((jsonObject["from"].toString().toInt() ==currentUser) && (jsonObject["to"].toString().toInt() == userid.toInt()))
                {
                    adapters.add(dataItem)
                    binding.messageListUser.scrollToPosition(adapters.itemCount - 1);
                }
                else  if ((jsonObject["to"].toString().toInt() ==currentUser) && (jsonObject["from"].toString().toInt() == userid.toInt())){
                    adapters.add(dataItem)
                    binding.messageListUser.scrollToPosition(adapters.itemCount - 1);
                }

            }
        }
        pusher.connect()
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getuserprofile() {
        //   ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getuserprofile(
            currentUser.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                //  ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    binding.Namess.setText(it.data?.name)
                    binding.Namess.visibility = VISIBLE
                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL + it.data?.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.imgProfile)
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

  private fun getchat() {
        //  ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getchat(
            currentUser.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(this) {
                //  ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    list?.clear()
                    adapters = MessageUserAdapter(this, it.data as ArrayList<DataItem>,userType)
                    val layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.messageListUser.layoutManager = layoutManager
                    binding.messageListUser.adapter = adapters
                    binding.messageListUser.scrollToPosition(adapters.getItemCount() - 1)

                    //getTimer()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }

 }
   /* private fun getTimer() {
        handler!!.postDelayed(Runnable {
            getchat()
        }, 2000)
    }*/
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
                val intentGalley = Intent(Intent.ACTION_PICK)
                intentGalley.type = "image/*"
                startActivityForResult(intentGalley, 1)
              /*  val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 1)*/
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
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU){
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
    */

    @Override
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
                val filepath = arrayOf(MediaStore.Images.Media.DATA)
                var c: Cursor? = null
                if (selectedImage != null) {
                    c = getContentResolver()
                        .query(selectedImage, filepath, null, null, null)
                }
                c?.moveToFirst()
                var columnIndex = 0
                if (c != null) {
                    columnIndex = c.getColumnIndex(filepath[0])
                }
                val picturePath = c!!.getString(columnIndex)
                c.close()
                val thumbnail = BitmapFactory.decodeFile(picturePath)
                getImageUri(this, thumbnail)?.let {
                    setImage(thumbnail, it)
                }

                Log.e("gallery",thumbnail.toString())
             //  Toast.makeText(this, thumbnail.toString(), Toast.LENGTH_SHORT).show()

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
                        Log.e("name", uploadedImageFile!!.name.toString())
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

 /*   fun getImageUri(src: Bitmap, format: Bitmap.CompressFormat?, quality: Int): Uri? {
        val os = ByteArrayOutputStream()
        src.compress(format, quality, os)
        val path = MediaStore.Images.Media.insertImage(getContentResolver(), src, "Calendar.getInstance().getTime()", null)

        return Uri.parse(path)
    }*/

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
      /*  val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)*/
        val tempFile = File.createTempFile("temprentpk", ".png")
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val bitmapData = bytes.toByteArray()

        val fileOutPut = FileOutputStream(tempFile)
        fileOutPut.write(bitmapData)
        fileOutPut.flush()
        fileOutPut.close()
        return Uri.fromFile(tempFile)
    }




    private fun getUriRealPath(ctx: Context, uri: Uri): String? {
 /*       var ret: String? = ""
        if (isAboveKitKat()) {
            // Android OS above sdk version 19.
            ret = getUriRealPathAboveKitkat(ctx, uri)
        } else {
            // Android OS below sdk version 19
            ret = getImageRealPath(getContentResolver(), uri, null)
        }
        return ret*/



        var ret: String? = ""
        if (isAboveKitKat()) {
            // Android OS above sdk version 19.
            ret = getUriRealPathAboveKitkat(ctx, uri)
        } else {
            // Android OS below sdk version 19
            ret = getImageRealPath(this.getContentResolver(), uri, null)
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
        whereClause: String?,
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
        // binding.ImgUploadImgSignup.visibility=VISIBLE

        /* Picasso.get()
             .load(imageUri)
             .error(R.drawable.appicon)
             .placeholder(R.drawable.appicon)
             .into(binding.imgProfile)*/
        Log.i("TAG", "setImage123: " + getUriRealPath(this, imageUri))
        uploadedImageFile = File(getUriRealPath(this, imageUri))
        val requestFile = uploadedImageFile.getRequestBody()
        image = MultipartBody.Part.createFormData("message", uploadedImageFile!!.name, requestFile)
        sendMessageImage()


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
    private fun isValidation(): Boolean {
        if (binding.txtMessage.text.isEmpty()) {
            Toast.makeText(this, "Please enter message or image", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun sendMessageImage() {
        var c = Calendar.getInstance()
        var df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var formattedDateee = df.format(c.time)
        //  ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        var image: MultipartBody.Part? = null
        profile = uploadedImageFile
        if (uploadedImageFile != null) {
            val requestFile: RequestBody = profile.getRequestBody()
            image = MultipartBody.Part.createFormData(
                "message",
                uploadedImageFile!!.name,
                requestFile
            )
        } else {
            image = MultipartBody.Part.createFormData(
                "",
                "message",
                "".getRequestBody()
            )
        }
        val currentUserType: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "2")
        val currentUserRequest: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), currentUser.toString())
        val currentUserRequesttime: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), formattedDateee.toString())
        RetrofitInstanceUser.retrofit.sendMessageImageuser(
            currentUserRequest,image, currentUserType,
            formattedDateee, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token)
        ).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                //resetInput()
                // Log.e("MetrologistchatActivity",t!!.localizedMessage)
            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                getchat()
                // Log.e("MetrologistchatActivity", response!!.body()!!)
            }
            
        })
        binding.txtMessage.text.clear()
        hideKeyBoard()
    }

    override fun onResume() {
        super.onResume()
        //Toast.makeText(requireContext(), "on resume", Toast.LENGTH_SHORT).show()
        //getMessage()
        handler.post(getChatTask)
    }


    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(getChatTask)
    }
    private val getChatTask= object :Runnable{
        override fun run() {
            //call get api here
            getchat()
        }
    }
}