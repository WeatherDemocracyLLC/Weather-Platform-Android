package com.webmobrilweatherapp.activities.fragment


import android.Manifest
import android.app.Activity
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
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.anilokcun.uwmediapicker.model.UwMediaPickerMediaModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.CreatePostActivity
import com.webmobrilweatherapp.activities.metrologistactivity.CreatepostMetrologistActivity
import com.webmobrilweatherapp.activities.metrologistactivity.IoUtils
import com.webmobrilweatherapp.activities.metrologistactivity.MetrilogistHomeActivity
import com.webmobrilweatherapp.databinding.FragmentAddgaleryFragmentmetrologistBinding
import com.webmobrilweatherapp.fragment.AddGalleryFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class AddgaleryFragmentmetrologist : Fragment() {
    lateinit var binding: FragmentAddgaleryFragmentmetrologistBinding
    private val selectedMediaGridColumnCount = 1
    private val RequestPermissionCode = 1
    private var uploadedImageFile: File? = null
    var profile: File? = null
    private lateinit var cameraImg: ArrayList<File>
    private val allSelectedMediaPaths by lazy { arrayListOf<String>() }
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private var imageUri: Uri? = null
    private var currentPhotoFile: File? = null
    private var imageList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddgaleryFragmentmetrologistBinding.inflate(layoutInflater)
        (requireActivity() as MetrilogistHomeActivity).updateBottomBar(2)
        val displayMetrics = DisplayMetrics()
        /* val deviceWidth = displayMetrics.widthPixels
         val gridSize = deviceWidth / selectedMediaGridColumnCount
         binding.rvSelectedMediaMetrologist.layoutManager = GridLayoutManager(requireContext(), selectedMediaGridColumnCount)
         binding.rvSelectedMediaMetrologist.itemAnimator = DefaultItemAnimator()
         binding.rvSelectedMediaMetrologist.addItemDecoration(GalleryItemDecoration(resources.getDimensionPixelSize(R.dimen.uwmediapicker_gallery_spacing),selectedMediaGridColumnCount))
         binding.rvSelectedMediaMetrologist.adapter = SelectedMediaRvAdapter(allSelectedMediaPaths, gridSize)*/

        cameraImg = ArrayList()

        // Register for camera result - captures photo into Uri
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && currentPhotoFile != null) {
                // Add photo file absolute path as String to imageList
                imageList.clear()
                imageList.add(currentPhotoFile!!.absolutePath)

                // Show preview (optional)
//                imageView.setImageURI(Uri.fromFile(currentPhotoFile))

                // Send imageList to CreatePostActivity
                goToNextScreen()
            }
        }

        // Register for gallery result - picks one image Uri
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val realPath = getRealPathFromUri(requireContext(),it)
                if (realPath != null) {
                    imageList.clear()
                    imageList.add(realPath)
//                    imageView.setImageURI(uri) // optional preview
                    goToNextScreen()
                } else {
                    // Handle error getting path
                }
            }
        }

        binding.LayoutGallery.setOnClickListener {



            val options = arrayOf<CharSequence>("Images", "Videos", "Cancel")
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(requireContext())
            builder.setTitle("Select From...")
            builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
                if (options[item] == "Images") {

                    pickImageLauncher.launch("image/*")

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
//                        intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, 1)
//                        startActivityForResult(intent, 1)
//                    } else {
//                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                        startActivityForResult(intent, 1)
//                    }

//                    if(checkAndRequestPermissions()){
//                        openUwMediaPicker( UwMediaPicker.GalleryMode.ImageGallery)
//                    }

                } else if (options[item] == "Videos") {


                    /*  val intent =
                          Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                      intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                      startActivityForResult(intent, 1)*/
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val intent = Intent(MediaStore.ACTION_PICK_IMAGES) // For now, system picker only works for images
                        intent.type = "video/*" // Optional, if you want to restrict type
                        startActivityForResult(intent, 100)
                    } else {
                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(intent, 100)
                    }

                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
                dialog.dismiss()
            })

            builder.show()

        }


        binding.LayoutCamera.setOnClickListener {
            currentPhotoFile = createImageFile()
            currentPhotoFile?.let {
                val photoUri = FileProvider.getUriForFile(
                    requireContext(),
                    getString(R.string.file_provider_name),
                    it
                )
                takePictureLauncher.launch(photoUri)
            }
        }

        return binding.root
    }

    private fun createImageFile(): File {
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, "${System.currentTimeMillis()}.jpg")
    }

    private fun goToNextScreen() {
//        uploadedImageFile = File(getUriRealPath(requireContext(), imageUri))
//        cameraImg.add(uploadedImageFile.toString())
        val intent = Intent(requireContext(), CreatepostMetrologistActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("DATA", imageList)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun getRealPathFromUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(android.provider.MediaStore.Images.Media.DATA)
        requireContext().contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA)
                return cursor.getString(columnIndex)
            }
        }
        // fallback
        return null

    }


    private fun openUwMediaPicker(galleryMode: UwMediaPicker.GalleryMode) {
        UwMediaPicker.with(this)
            .setGalleryMode(galleryMode)
            .setLightStatusBar(true)
            .setCompressFormat(Bitmap.CompressFormat.JPEG)
            .setCompressedFileDestinationPath("${context?.getExternalFilesDir(null)!!.path}/Pictures")
            .setCancelCallback(::onMediaSelectionCancelled)
            .launch(::onMediaSelected)
    }

    private fun onMediaSelected(selectedMediaList: List<UwMediaPickerMediaModel>?) {
        if (selectedMediaList != null) {
            Log.i("TAG", "onMediaSelected: " + "Hello")
            allSelectedMediaPaths.clear()
            val selectedMediaPathList = selectedMediaList.map { it.mediaPath }
            allSelectedMediaPaths.addAll(selectedMediaPathList)
//            val intent = Intent(requireContext(), CreatepostMetrologistActivity::class.java)
//            val bundle = Bundle()
//            bundle.putSerializable("DATA", allSelectedMediaPaths)
//            intent.putExtras(bundle)
//            startActivity(intent)
            // (binding.rvSelectedMedia.adapter as SelectedMediaRvAdapter).update(allSelectedMediaPaths)
        } else {
            Toast.makeText(context, "Unexpected Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onMediaSelectionCancelled() {
       // Toast.makeText(context, "Media Selection Cancelled ", Toast.LENGTH_SHORT).show()
    }

    private fun requestToOpenImagePicker(
        requestCode: Int,
        galleryMode: UwMediaPicker.GalleryMode,
        openMediaPickerFunc: (UwMediaPicker.GalleryMode) -> Unit
    ) {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(
                    this.requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    // First time
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode
                    )
                } else {
                    // Not first time
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode
                    )
                }
            }
            else {
                // Permission has already been granted
                openMediaPickerFunc.invoke(galleryMode)
            }
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if ( ContextCompat.checkSelfPermission(
                    this.requireActivity(),
                    Manifest.permission.READ_MEDIA_IMAGES,
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted
                if ( ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                ) {
                    // First time
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES), requestCode
                    )
                } else {
                    // Not first time
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES), requestCode
                    )
                }
            }else {
                // Permission has already been granted
                openMediaPickerFunc.invoke(galleryMode)
            }
        }
       else {
            // Permission has already been granted
            openMediaPickerFunc.invoke(galleryMode)
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE_PICK_IMAGE = 9
        const val PERMISSION_REQUEST_CODE_PICK_VIDEO = 10
        const val PERMISSION_REQUEST_CODE_PICK_IMAGE_VIDEO = 11

        init {
            // For using vector drawables on lower APIs
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo")
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Capture Image from Camera")
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
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            val CAMERA = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
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
                    requireActivity(),
                    listPermissionsNeeded.toTypedArray(),
                    RequestPermissionCode
                )
                return false
            }
        }

        // For Android 11 and 12 (API levels 30 and 31)
        if( Build.VERSION.SDK_INT in Build.VERSION_CODES.R..Build.VERSION_CODES.S_V2) {
            val READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            )

            val CAMERA = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            val listPermissionsNeeded: MutableList<String> = ArrayList()
            if (READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (CAMERA != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA)
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    listPermissionsNeeded.toTypedArray(),
                    RequestPermissionCode
                )
                return false
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            val READ_MEDIA_VIDEO = ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_MEDIA_VIDEO
            )
            val READ_MEDIA_IMAGES = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            )
            val READ_MEDIA_AUDIO = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_AUDIO
            )
            val CAMERA = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
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
                    requireActivity(),
                    listPermissionsNeeded.toTypedArray(),
                    RequestPermissionCode
                )
                return false
            }
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {

            print("request code");
            print(resultCode)
            when (requestCode) {

                // ✅ Video Picker
                100 -> {
                    data.data?.let { uri ->
                        val file = uriToTempFile(uri, "video.mp4")
                        if (file != null) {
                            val fileSizeInMB = file.length() / (1024 * 1024)
                            if (fileSizeInMB <= 50) {
                                val intent = Intent(requireContext(), CreatePostActivity::class.java)
                                intent.putExtra("VideoPath", file.absolutePath)
                                intent.putExtra("key", 1)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Please select a video under 50 MB",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Unable to read selected video", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                // ✅ Camera Capture (bitmap)
//                0 -> {
//                    val bitmap = data.extras?.get("data") as? Bitmap
//                    bitmap?.let {
//                        getImageUri(requireContext(), it)?.let { uri ->
//                            setImage(it, uri)
//                        }
//                    }
//                }

                // ✅ Image Picker
//                1 -> {
//                    data.data?.let { uri ->
//                        val bitmap = getBitmapFromUri(uri)
//                        bitmap?.let {
//                            getImageUri(requireContext(), it)?.let { imgUri ->
//                                setImage(it, imgUri)
//                            }
//                        }
//                    }
//                }

                // ✅ File Picker / Upload Image
//                2 -> {
//                    data.data?.let { uri ->
//                        try {
//                            val file = getFileName(uri)?.let { uriToTempFile(uri, it) }
//                            uploadedImageFile = file
//                            Log.e("UploadedFile", uploadedImageFile?.name ?: "")
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                        }
//                    }
//                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun uriToTempFile(uri: Uri, fileName: String): File? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val tempFile = File(requireContext().cacheDir, fileName)
            val outputStream = FileOutputStream(tempFile)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {

/*
        val bytes = ByteArrayOutputStream()
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
        var ret: String? = ""
        if (isAboveKitKat()) {
            // Android OS above sdk version 19.
            ret = getUriRealPathAboveKitkat(ctx, uri)
        } else {
            // Android OS below sdk version 19
            ret = getImageRealPath(requireContext().getContentResolver(), uri, null)
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
                    ret = getImageRealPath(requireContext().getContentResolver(), uri, null)
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
                            requireContext().getContentResolver(),
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
                            requireContext().getContentResolver(),
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

        /*Picasso.get()
            .load(imageUri)
            .error(com.webmobrilweatherapp.R.drawable.appicon)
            .placeholder(com.webmobrilweatherapp.R.drawable.appicon)
            .into(binding.ImgUploadImgSignup)*/
        Log.i("TAG", "setImage123: " + getUriRealPath(requireContext(), imageUri))
        uploadedImageFile = File(getUriRealPath(requireContext(), imageUri))
//        cameraImg.add(uploadedImageFile.toString())
//        val intent = Intent(requireContext(), CreatepostMetrologistActivity::class.java)
//        val bundle = Bundle()
//        bundle.putSerializable("DATA", cameraImg)
//        intent.putExtras(bundle)
//        startActivity(intent)

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
        val returnCursor = context?.contentResolver?.query(fileUri!!, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }
}