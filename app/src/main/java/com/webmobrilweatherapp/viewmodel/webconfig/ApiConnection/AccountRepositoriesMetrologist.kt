package com.myapplication.viewmodel.webconfig.ApiConnection

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.beans.bottomdialog.BottomdialogResponse
import com.webmobrilweatherapp.beans.homepages.HomePageSunnyResponse
import com.webmobrilweatherapp.beans.vote.VoteResponse
import com.webmobrilweatherapp.model.accountdelete.DeleteAccountResponse
import com.webmobrilweatherapp.model.changepassword.ChangePasswordResponse
import com.webmobrilweatherapp.model.comment.PostCommentResponse
import com.webmobrilweatherapp.model.contactus.ContactUsResponse
import com.webmobrilweatherapp.model.followUnfollow.FollowResponse
 import com.webmobrilweatherapp.model.getchat.GetChatsResponse
import com.webmobrilweatherapp.model.getcomment.GetCommentResponse
import com.webmobrilweatherapp.model.getcurrentlocarion.GetallLocationResponse
import com.webmobrilweatherapp.model.getimagepostuser.GetImagePostuserResponse
import com.webmobrilweatherapp.model.homepage.ProfilehomePageResponse
import com.webmobrilweatherapp.model.like.LikeResponse
import com.webmobrilweatherapp.model.logout.LogoutResponse
import com.webmobrilweatherapp.model.mayorwether.MakingWeatherResponse
import com.webmobrilweatherapp.model.metrologist.createpost.CreatePostResponse
import com.webmobrilweatherapp.model.metrologist.emailverification.MetrologistEmailverificationResponse
import com.webmobrilweatherapp.model.metrologist.forgot.MetrologistForgotResponse
import com.webmobrilweatherapp.model.metrologist.otp.MetrologistOtpResponse
import com.webmobrilweatherapp.model.metrologist.resetpassword.MetrologistResetPasswordResponse
import com.webmobrilweatherapp.model.metrologist.signup.MetrologistSignUpResponse
import com.webmobrilweatherapp.model.metrologist.signupotpverification.MetrologistOtpVerificationResponse
import com.webmobrilweatherapp.model.metrologist.viewuservote.ViewUserVoteResponse
import com.webmobrilweatherapp.model.notification.NotificationResponse
import com.webmobrilweatherapp.model.postdelete.PostDeleteResponse
import com.webmobrilweatherapp.model.sendAlert.SendAlertResponse
import com.webmobrilweatherapp.model.topfivemetrologist.TopFiveResponse
import com.webmobrilweatherapp.model.updateProfile.UpdateProfileResponse
import com.webmobrilweatherapp.model.updatepost.UpdatePostResponse
import com.webmobrilweatherapp.model.updateprofileImage.UpdateProfileImageResponse
import com.webmobrilweatherapp.model.userpost.UserPostImagesResponse
import com.webmobrilweatherapp.model.userprofile.UserProfileResponse
import com.webmobrilweatherapp.model.uservotinglist.UserVotingListResponse
import com.webmobrilweatherapp.model.weathermayorlist.WeatherMayorListResponse
import com.webmobrilweatherapp.models.homepage.HomePageResponse
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.RetrofitConnections
import com.google.gson.Gson
import com.webmobrilweatherapp.model.WeatherAlert.WeatherAlertResponse
import com.webmobrilweatherapp.model.metrologistLogin.MetrologistLoginnResponse
import com.webmobrilweatherapp.model.usersearching.UserSearchingResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AccountRepositoriesMetrologist {
    private val TAG = AccountRepositoriesMetrologist::class.java.simpleName
    fun getResgistrationMetrologist(
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        city: RequestBody,
        user_type: Int,
        meterologist_docs: MultipartBody.Part,
        state:RequestBody,
        country:RequestBody,
        city_lat:RequestBody,
        city_long:RequestBody,
        zipcode:RequestBody,
    ): LiveData<MetrologistSignUpResponse?> {
        val mutableLiveData = MutableLiveData<MetrologistSignUpResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getResgistrationMetrologist(
            name,
            email,
            password,
            city,
            user_type,
            meterologist_docs,
            state,country,city_lat,city_long,zipcode,2
        )
        call!!.enqueue(object : Callback<MetrologistSignUpResponse?> {
            override fun onResponse(
                call: Call<MetrologistSignUpResponse?>,
                response: Response<MetrologistSignUpResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<MetrologistSignUpResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getLoginmetrologist(
        email: String, password: String, user_type: String,device_type:String,device_token:String
    ): LiveData<MetrologistLoginnResponse?> {
        val mutableLiveData = MutableLiveData<MetrologistLoginnResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getLoginmetrologist(email, password, user_type,device_type,device_token)
        call!!.enqueue(object : Callback<MetrologistLoginnResponse?> {
            override fun onResponse(
                call: Call<MetrologistLoginnResponse?>,
                response: Response<MetrologistLoginnResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<MetrologistLoginnResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getforgotmetrologist(
        email: String, user_type: String
    ): LiveData<MetrologistForgotResponse?> {
        val mutableLiveData = MutableLiveData<MetrologistForgotResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getforgotmetrologist(email, user_type)
        call!!.enqueue(object : Callback<MetrologistForgotResponse?> {
            override fun onResponse(
                call: Call<MetrologistForgotResponse?>,
                response: Response<MetrologistForgotResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<MetrologistForgotResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getverifyotpmetrologist(
        otp: String, email: String
    ): LiveData<MetrologistOtpResponse?> {
        val mutableLiveData = MutableLiveData<MetrologistOtpResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getverifyotpmetrologist(otp, email)
        call!!.enqueue(object : Callback<MetrologistOtpResponse?> {
            override fun onResponse(
                call: Call<MetrologistOtpResponse?>,
                response: Response<MetrologistOtpResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<MetrologistOtpResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getresetpasswordmetrologist(
        email: String, new_password: String, confirm_password: String
    ): LiveData<MetrologistResetPasswordResponse?> {
        val mutableLiveData = MutableLiveData<MetrologistResetPasswordResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getresetpasswordmetrologist(email, new_password, confirm_password)
        call!!.enqueue(object : Callback<MetrologistResetPasswordResponse?> {
            override fun onResponse(
                call: Call<MetrologistResetPasswordResponse?>,
                response: Response<MetrologistResetPasswordResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<MetrologistResetPasswordResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getemailverificationmetrologist(
        email: String, user_type: String
    ): LiveData<MetrologistEmailverificationResponse?> {
        val mutableLiveData = MutableLiveData<MetrologistEmailverificationResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getemailverificationmetrologist(email, user_type)
        call!!.enqueue(object : Callback<MetrologistEmailverificationResponse?> {
            override fun onResponse(
                call: Call<MetrologistEmailverificationResponse?>,
                response: Response<MetrologistEmailverificationResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(
                call: Call<MetrologistEmailverificationResponse?>,
                t: Throwable
            ) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getsignupotpverificationmetrologist(
        otp: String, email: String
    ): LiveData<MetrologistOtpVerificationResponse?> {
        val mutableLiveData = MutableLiveData<MetrologistOtpVerificationResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getsignupotpverificationmetrologist(otp, email)
        call!!.enqueue(object : Callback<MetrologistOtpVerificationResponse?> {
            override fun onResponse(
                call: Call<MetrologistOtpVerificationResponse?>,
                response: Response<MetrologistOtpVerificationResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<MetrologistOtpVerificationResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getViewVote(
        token: String
    ): LiveData<ViewUserVoteResponse?> {
        val mutableLiveData = MutableLiveData<ViewUserVoteResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getViewVote(token)
        call!!.enqueue(object : Callback<ViewUserVoteResponse?> {
            override fun onResponse(
                call: Call<ViewUserVoteResponse?>,
                response: Response<ViewUserVoteResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<ViewUserVoteResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getHomeapgemetrolgist(
        apikey: String?, longitude: String?, language: String?, details: String?
    ): LiveData<HomePageResponse?> {
        val mutableLiveData = MutableLiveData<HomePageResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getHomeapgemetrolgist("AIzaSyCnRAGJaYpc4edJi8DcHaimmJ9mW4k4EVM",longitude,language,details)
        call!!.enqueue(object : Callback<HomePageResponse?> {
            override fun onResponse(
                call: Call<HomePageResponse?>,
                response: Response<HomePageResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))

                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                }
                else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<HomePageResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getHomeapgesunnymetrlogist(
        key: String,apikey: String?, details: String?
    ): LiveData<HomePageSunnyResponse?> {
        val mutableLiveData = MutableLiveData<HomePageSunnyResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getHomeapgesunnymetrlogist(key,apikey,details)
        call!!.enqueue(object : Callback<HomePageSunnyResponse?> {
            override fun onResponse(
                call: Call<HomePageSunnyResponse?>,
                response: Response<HomePageSunnyResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<HomePageSunnyResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getchangepasswordsMetrologist(
        current_password: String,
        new_password: String,
        confirm_password: String,
        token: String
    ): LiveData<ChangePasswordResponse?> {
        val mutableLiveData = MutableLiveData<ChangePasswordResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getchangepasswordsMetrologist(
            current_password,
            new_password,
            confirm_password,
            token
        )
        call!!.enqueue(object : Callback<ChangePasswordResponse?> {
            override fun onResponse(
                call: Call<ChangePasswordResponse?>,
                response: Response<ChangePasswordResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getupdateprofileMetrologist(
        name: String, phone: String,
        email: String, city: String, token: String
    ): LiveData<UpdateProfileResponse?> {
        val mutableLiveData = MutableLiveData<UpdateProfileResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getupdateprofileMetrologist(name, phone, email, city, token)
        call!!.enqueue(object : Callback<UpdateProfileResponse?> {
            override fun onResponse(
                call: Call<UpdateProfileResponse?>,
                response: Response<UpdateProfileResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getuserprofileMetrologist(user_id: String, token: String): LiveData<UserProfileResponse?> {
        val mutableLiveData = MutableLiveData<UserProfileResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getuserprofileMetrologist(user_id, token)
        call!!.enqueue(object : Callback<UserProfileResponse?> {
            override fun onResponse(
                call: Call<UserProfileResponse?>,
                response: Response<UserProfileResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserProfileResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getupdateprofileImagesMetrologist(
        profile_image: MultipartBody.Part,
        token: String
    ): LiveData<UpdateProfileImageResponse?> {
        val mutableLiveData = MutableLiveData<UpdateProfileImageResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getupdateprofileImagesMetrologist(profile_image, token)
        call!!.enqueue(object : Callback<UpdateProfileImageResponse?> {
            override fun onResponse(
                call: Call<UpdateProfileImageResponse?>,
                response: Response<UpdateProfileImageResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<UpdateProfileImageResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getCreatePostMetrologist(
        profile_image: List<MultipartBody.Part>,
        description: RequestBody,
        token: String
    ): LiveData<CreatePostResponse?> {
        val mutableLiveData = MutableLiveData<CreatePostResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getCreatePostMetrologist(profile_image, description, token)
        call!!.enqueue(object : Callback<CreatePostResponse?> {
            override fun onResponse(
                call: Call<CreatePostResponse?>,
                response: Response<CreatePostResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<CreatePostResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getprofilehomepageMetrologist(
        user_id: String,
        page:String,
        token: String
    ): LiveData<ProfilehomePageResponse?> {
        val mutableLiveData = MutableLiveData<ProfilehomePageResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getprofilehomepageMetrologist(user_id,page, token)
        call!!.enqueue(object : Callback<ProfilehomePageResponse?> {
            override fun onResponse(
                call: Call<ProfilehomePageResponse?>,
                response: Response<ProfilehomePageResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<ProfilehomePageResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getpostimageMetrologist(
        user_id: String,
        token: String
    ): LiveData<GetImagePostuserResponse?> {
        val mutableLiveData = MutableLiveData<GetImagePostuserResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getpostimageMetrologist(user_id, token)
        call!!.enqueue(object : Callback<GetImagePostuserResponse?> {
            override fun onResponse(
                call: Call<GetImagePostuserResponse?>,
                response: Response<GetImagePostuserResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<GetImagePostuserResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getsearchprofilesmetrologist(
        user_type: String,
        token: String
    ): LiveData<UserSearchingResponse?> {
        val mutableLiveData = MutableLiveData<UserSearchingResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getsearchprofilesmetrologist(user_type, token)
        call!!.enqueue(object : Callback<UserSearchingResponse?> {
            override fun onResponse(
                call: Call<UserSearchingResponse?>,
                response: Response<UserSearchingResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserSearchingResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getpostbyMetrologist(user_id: String,page: String, token: String): LiveData<UserPostImagesResponse?> {
        val mutableLiveData = MutableLiveData<UserPostImagesResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getpostbyMetrologist(user_id,page, token)
        Log.e("useridid", user_id);
        call!!.enqueue(object : Callback<UserPostImagesResponse?> {

            override fun onResponse(
                call: Call<UserPostImagesResponse?>,
                response: Response<UserPostImagesResponse?>
            ) {
                Log.e("TAG", Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                    // Toast.makeText(this@AccountRepositoriesMetrologist,response., Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<UserPostImagesResponse?>, t: Throwable) {



                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getcontactusmetrologist(
        name: String,
        phone_number: String,
        email: String,
        message: String,
        token: String
    ): LiveData<ContactUsResponse?> {
        val mutableLiveData = MutableLiveData<ContactUsResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getcontactusmetrologist(name, phone_number, email, message, token)
        call!!.enqueue(object : Callback<ContactUsResponse?> {
            override fun onResponse(
                call: Call<ContactUsResponse?>,
                response: Response<ContactUsResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<ContactUsResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun gettopfivemetrologist(user_type: String, token: String): LiveData<TopFiveResponse?> {
        val mutableLiveData = MutableLiveData<TopFiveResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.gettopfivemetrologist(user_type, token)
        call!!.enqueue(object : Callback<TopFiveResponse?> {
            override fun onResponse(
                call: Call<TopFiveResponse?>,
                response: Response<TopFiveResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<TopFiveResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getUserflowMetrologist(
        followed_id: String,
        event: String,
        token: String
    ): LiveData<FollowResponse?> {
        val mutableLiveData = MutableLiveData<FollowResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getUserflowMetrologist(followed_id, event, token)
        call!!.enqueue(object : Callback<FollowResponse?> {
            override fun onResponse(
                call: Call<FollowResponse?>,
                response: Response<FollowResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<FollowResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    fun getrlikeMterologist(post_id: String, like: String, token: String): LiveData<LikeResponse?> {
        val mutableLiveData = MutableLiveData<LikeResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getrlikeMterologist(post_id, like, token)
        call!!.enqueue(object : Callback<LikeResponse?> {
            override fun onResponse(
                call: Call<LikeResponse?>,
                response: Response<LikeResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<LikeResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun postMetrologistcomments(post_id: String, comment: String, token: String): LiveData<PostCommentResponse?> {
        val mutableLiveData = MutableLiveData<PostCommentResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.postMetrologistcomments(post_id, comment, token)
        call!!.enqueue(object : Callback<PostCommentResponse?> {
            override fun onResponse(
                call: Call<PostCommentResponse?>,
                response: Response<PostCommentResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<PostCommentResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getpostMetrologistcomments(post_id: String,  token: String): LiveData<GetCommentResponse?> {
        val mutableLiveData = MutableLiveData<GetCommentResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getpostMetrologistcomments(post_id, token)
        call!!.enqueue(object : Callback<GetCommentResponse?> {
            override fun onResponse(
                call: Call<GetCommentResponse?>,
                response: Response<GetCommentResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<GetCommentResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getmetrologistchat(reciver_id: String,  token: String): LiveData<GetChatsResponse?> {
        val mutableLiveData = MutableLiveData<GetChatsResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getmetrologistchat(reciver_id, token)
        call!!.enqueue(object : Callback<GetChatsResponse?> {
            override fun onResponse(
                call: Call<GetChatsResponse?>,
                response: Response<GetChatsResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<GetChatsResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getnotificationMetrologist(token: String,context:Context): LiveData<NotificationResponse?> {
        val mutableLiveData = MutableLiveData<NotificationResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getnotificationMetrologist(token)
        call!!.enqueue(object : Callback<NotificationResponse?> {
            override fun onResponse(
                call: Call<NotificationResponse?>,
                response: Response<NotificationResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                   // mutableLiveData.setValue(response.body())
                    ProgressD.hideProgressDialog()

                    try {

                        if(response.message().equals("Not Found")){
                            Toast.makeText(context, "Data not found!", Toast.LENGTH_SHORT).show()
                        }
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val errorMsg = jObjError.getInt("Not Found")
                        Log.e("response errorMsg", "=" + errorMsg)
                        //Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                        Log.e("no data fond",errorMsg.toString()+"    "+Gson().toJson(response))
                        Toast.makeText(context, "data not found", Toast.LENGTH_SHORT).show()

                        if(errorMsg.equals("Not Found")){
                            Toast.makeText(context, "data not found", Toast.LENGTH_SHORT).show()
                            Log.e("no data fond",errorMsg.toString()+"    "+Gson().toJson(response))
                        }
                    } catch (e: Exception) {
                    }
                }
            }

            override fun onFailure(call: Call<NotificationResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getpostdeleteMetrologist(id: String,token:String): LiveData<PostDeleteResponse?> {
        val mutableLiveData = MutableLiveData<PostDeleteResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getpostdeleteMetrologist(id,token)
        call!!.enqueue(object : Callback<PostDeleteResponse?> {
            override fun onResponse(
                call: Call<PostDeleteResponse?>,
                response: Response<PostDeleteResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<PostDeleteResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun geteditpostMetrologist(id: String,description:String,token:String): LiveData<UpdatePostResponse?> {
        val mutableLiveData = MutableLiveData<UpdatePostResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.geteditpostMetrologist(id,description,token)
        call!!.enqueue(object : Callback<UpdatePostResponse?> {
            override fun onResponse(
                call: Call<UpdatePostResponse?>,
                response: Response<UpdatePostResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<UpdatePostResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getlogoutsmetrologist(token:String): LiveData<LogoutResponse?> {
        val mutableLiveData = MutableLiveData<LogoutResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getlogoutsmetrologist(token)
        call!!.enqueue(object : Callback<LogoutResponse?> {
            override fun onResponse(
                call: Call<LogoutResponse?>,
                response: Response<LogoutResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<LogoutResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getUservotinglistmetrologist(user_id: String,token:String): LiveData<UserVotingListResponse?> {
        val mutableLiveData = MutableLiveData<UserVotingListResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getUservotinglistmetrologist(user_id,token)
        call!!.enqueue(object : Callback<UserVotingListResponse?> {
            override fun onResponse(
                call: Call<UserVotingListResponse?>,
                response: Response<UserVotingListResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserVotingListResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getpreprationmetrologist(token:String): LiveData<BottomdialogResponse?> {
        val mutableLiveData = MutableLiveData<BottomdialogResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getpreprationmetrologist(token)
        call!!.enqueue(object : Callback<BottomdialogResponse?> {
            override fun onResponse(
                call: Call<BottomdialogResponse?>,
                response: Response<BottomdialogResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<BottomdialogResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getVotemetrologist(is_temp: String, temp_value: String,precipitation_id: String,weatherdate: String,token:String): LiveData<VoteResponse?> {
        val mutableLiveData = MutableLiveData<VoteResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getVotemetrologist(is_temp,temp_value,precipitation_id,weatherdate,token)
        call!!.enqueue(object : Callback<VoteResponse?> {
            override fun onResponse(
                call: Call<VoteResponse?>,
                response: Response<VoteResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<VoteResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getUservotinglistMetrologist(user_id:String,token:String): LiveData<UserVotingListResponse?> {
        val mutableLiveData = MutableLiveData<UserVotingListResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getUservotinglistMetrologist(user_id,token)
        call!!.enqueue(object : Callback<UserVotingListResponse?> {
            override fun onResponse(
                call: Call<UserVotingListResponse?>,
                response: Response<UserVotingListResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserVotingListResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getweathermayorlistsmetrologist(token:String): LiveData<WeatherMayorListResponse?> {
        val mutableLiveData = MutableLiveData<WeatherMayorListResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getweathermayorlistsmetrologist(token)
        call!!.enqueue(object : Callback<WeatherMayorListResponse?> {
            override fun onResponse(
                call: Call<WeatherMayorListResponse?>,
                response: Response<WeatherMayorListResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<WeatherMayorListResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getmakingweathervotemetrologist(user_id: String,token:String): LiveData<MakingWeatherResponse?> {
        val mutableLiveData = MutableLiveData<MakingWeatherResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getmakingweathervotemetrologist(user_id,token)
        call!!.enqueue(object : Callback<MakingWeatherResponse?> {
            override fun onResponse(
                call: Call<MakingWeatherResponse?>,
                response: Response<MakingWeatherResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<MakingWeatherResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getdeleteaccountmetrologist(user_id: String,token:String): LiveData<DeleteAccountResponse?> {
        val mutableLiveData = MutableLiveData<DeleteAccountResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getdeleteaccountmetrologist(user_id,token)
        call!!.enqueue(object : Callback<DeleteAccountResponse?> {
            override fun onResponse(
                call: Call<DeleteAccountResponse?>,
                response: Response<DeleteAccountResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<DeleteAccountResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getsendalerts(message: String,title:String,userId:String,token:String): LiveData<SendAlertResponse?> {
        val mutableLiveData = MutableLiveData<SendAlertResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getsendalerts(message,title,userId,token)
        call!!.enqueue(object : Callback<SendAlertResponse?> {
            override fun onResponse(
                call: Call<SendAlertResponse?>,
                response: Response<SendAlertResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<SendAlertResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    fun getWeatherAlert(): LiveData<WeatherAlertResponse?> {
        val mutableLiveData = MutableLiveData<WeatherAlertResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getWeatherAlert()
        call!!.enqueue(object : Callback<WeatherAlertResponse?> {
            override fun onResponse(
                call: Call<WeatherAlertResponse?>,
                response: Response<WeatherAlertResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<WeatherAlertResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    fun getalllocationmetrologist(latlng:String,key:String): LiveData<GetallLocationResponse?> {
        val mutableLiveData = MutableLiveData<GetallLocationResponse?>()
        val apiService = RetrofitConnections.instance?.createService()
        val call = apiService!!.getalllocationmetrologist(latlng,key)
        call!!.enqueue(object : Callback<GetallLocationResponse?> {
            override fun onResponse(
                call: Call<GetallLocationResponse?>,
                response: Response<GetallLocationResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<GetallLocationResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
}