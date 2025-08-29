package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.R

class PhotosViewActivity : AppCompatActivity() {
     var photoURL=""
     lateinit var ViewPhoto: ImageView
     lateinit var backImage:ImageView
     lateinit var namee:TextView
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos_view)

        photoURL= getIntent().getExtras()?.getString("Photourl").toString()
         var name=CommonMethod.getInstance(this).getPreference(AppConstant.KEY_Fullname,"0")
          namee=findViewById(R.id.nameView)
         ViewPhoto=findViewById(R.id.view_image)
        backImage=findViewById(R.id.cross)

        backImage.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

         val circularProgressDrawable = CircularProgressDrawable(this)
         circularProgressDrawable.strokeWidth = 5f
         circularProgressDrawable.centerRadius = 30f
         circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(this, R.color.toolbaar))
         circularProgressDrawable.start()


        //namee.setText(name)
        Glide.with(this)
            .load(photoURL)
            .placeholder(circularProgressDrawable)
            .into(ViewPhoto)


    }
}