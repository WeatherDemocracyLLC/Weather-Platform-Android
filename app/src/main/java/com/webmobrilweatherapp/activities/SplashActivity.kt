package com.webmobrilweatherapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.webmobrilweatherapp.activities.metrologistactivity.MetrilogistHomeActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistLogInActivity
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistPostActivity
import com.webmobrilweatherapp.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {


    lateinit var binding: ActivitySplashBinding
    var handler: Handler? = null
    var idShare="0"
    var productId="0"
    var applicationId="0"
    private var sessionManager: SessionManager? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        sessionManager = SessionManager(this)
        setContentView(view)

        applicationId = CommonMethod.getInstance(this).getPreference(AppConstant.Key_ApplicationId,"0")

        idShare=CommonMethod.getInstance(this).getPreference(AppConstant.KEY_ID_SHARE,"0")
        productId=CommonMethod.getInstance(this).getPreference(AppConstant.KEY_PRODUCT_ID,"0")
        //////////////////////////////


            FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this,
                    OnSuccessListener<PendingDynamicLinkData?> { pendingDynamicLinkData ->
                        // Get deep link from result (may be null if no link is found)

                        var deepLink: Uri? = null
                        Log.e("idshare", idShare)
                        if (pendingDynamicLinkData != null) {

                            deepLink = pendingDynamicLinkData.link
                            Log.e("deepLinkk", "" + deepLink.toString())
                            //https://weatherdemocracy.com/post?id=377&userType=2
                            //https://weatherdemocracy.com/post?id=370&userType=2



                            var postid=deepLink.toString().split("id=")
                            var po=postid[1].toString().split("&userType")
                            var userType=deepLink.toString().split("&userType=")

                          //  Toast.makeText(this, applicationId.toString(), Toast.LENGTH_LONG).show()

                            if (userType[1].equals("3"))
                            {
                                if (applicationId.equals("2"))
                                {
                                    var a=1
                                    var intent= Intent(this, MetrologistPostActivity::class.java)
                                    intent.putExtra("statusBarcolor",a.toString())
                                    intent.putExtra("PostId", po[0].toString())
                                    intent.putExtra("backPressed", "1")
                                    // PrefManager.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE, "0")
                                    startActivity(intent)
                                }
                                else if (applicationId.equals("1"))
                                {
                                    CommonMethod.getInstance(this)
                                        .savePreference(AppConstant.KEY_loginStatus, false)
                                    var intent= Intent(this, MetrologistLogInActivity::class.java)
                                    // PrefManager.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE, "0")
                                    startActivity(intent)
                                }

                            }
                            else if (userType[1].equals("2"))
                            {
                                if (applicationId.equals("1"))
                                {
                                    var intent= Intent(this, PostActivity::class.java)
                                    intent.putExtra("PostId", po[0].toString())
                                    intent.putExtra("backPressed", "1")
                                    // PrefManager.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE, "0")
                                    startActivity(intent)
                                }
                                else if(applicationId.equals("2"))
                                {
                                    CommonMethod.getInstance(this)
                                        .savePreference(AppConstant.KEY_loginStatues, false)
                                    var intent= Intent(this, LoginActivity::class.java)

                                    // PrefManager.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE, "0")
                                    startActivity(intent)
                                }


                            }
                            /*if(applicationId.equals("0")) {
                                var intent= Intent(this,LocationActivity ::class.java)
                                startActivity(intent)
                            }*/
                           /* else{

                                var intent= Intent(this,SelectOptionActivity ::class.java)
                                // PrefManager.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE, "0")
                                startActivity(intent)

                            }*/
                           /* Log.e("idshare", postid[1].toString())
                            var intent= Intent(this, PostActivity::class.java)
                            intent.putExtra("PostId", postid[1].toString())
                            intent.putExtra("backPressed", "1")
                            // PrefManager.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE, "0")
                            startActivity(intent)*/

                            /* String paramValue = deepLink.getQueryParameters("id").get(0);
                                  String strImage=deepLink.getQueryParameter("si");
                                 Log.e("deep_link",paramValue);*/
                            // Uri uri = Uri.parse("http://base_path/some_segment/id");
                            // String lastPathSegment = deepLink.toString().substring(deepLink.toString().lastIndexOf("?")+1);
                            val lastPathSegment = deepLink!!.lastPathSegment
                            if (idShare.equals("0")){
                                CommonMethod.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE,"0")

                                Log.e("success", "Fail")
                                handler = Handler()

                                try {

                                } catch (ex: Exception) {
                                    Log.e("mg==",ex.toString())
                                    ex.printStackTrace()

                                }

                            }
                            else {
                                Log.e("idshare", idShare)
                                if (productId.equals(lastPathSegment)) {
                                    Log.e("success", "success")
                                    Log.e("failon", "success")
                                    var intent= Intent(this, PostActivity::class.java)
                                    intent.putExtra("PostId", productId.toString())
                                    intent.putExtra("backPressed", "1")
                                    // PrefManager.getInstance(this).savePreference(AppConstant.KEY_ID_SHARE, "0")
                                    startActivity(intent)
                                }

                                else {
                                    Log.e("success", "Fail")
                                    handler = Handler()

                                    try {
//
                                        // jump()

                                    } catch (ex: Exception) {
                                        Log.e("mg==",ex.toString())
                                        ex.printStackTrace()
                                        //jump()
                                    }
                                }



                            }
                            //  Log.e("deepLink",lastPathSegment+" "+deepLink);
                        }
                        else
                        {
                           jump()
                        }


                    })
                .addOnFailureListener(this,
                    OnFailureListener { e ->


                        handler = Handler()

                        try {
                            jump()


                        } catch (ex: Exception) {
                            Log.e("mg==",ex.toString())
                            ex.printStackTrace()
                            jump()
                        }
                    })
    }

    private fun jump() {
        val isVerified: String = sessionManager!!.isVerified()
        Log.d("TAG", "dsafgh: ${isVerified}")
        Handler().postDelayed({
            if (CommonMethod.getInstance(this).getPreference(AppConstant.KEY_loginStatus, false)) {
                if (CommonMethod.getInstance(this).getPreference(AppConstant.KEY_loginStatus, false)
                ) {
                    val i = Intent(applicationContext, HomeActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(i)
                    finish()
                } else {
                    val i = Intent(this, LoginActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(i)
                    finish()
                }
            } else if (CommonMethod.getInstance(this)
                    .getPreference(AppConstant.KEY_loginStatues, false)
            ) {
                if (CommonMethod.getInstance(this)
                        .getPreference(AppConstant.KEY_loginStatues, true)
                ) {
                    val i = Intent(this, MetrilogistHomeActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(i)
                    finish()
                } else {

                    val i = Intent(applicationContext, MetrologistLogInActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(i)
                    finish()
                }
            } else{
                if (isVerified=="1"){
                    val i = Intent(this, SelectOptionActivity::class.java)
                    startActivity(i)
                    finish()
                }else{
                    val i = Intent(this, LocationActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(i)
                    finish()
                }


            }
        }, 5000)
    }
}

