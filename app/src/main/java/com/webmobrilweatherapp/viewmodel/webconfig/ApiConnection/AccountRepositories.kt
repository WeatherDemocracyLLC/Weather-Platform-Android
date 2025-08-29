package com.myapplication.viewmodel.webconfig.ApiConnection

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.beans.bottomdialog.BottomdialogResponse
import com.webmobrilweatherapp.beans.createnewpassword.CreateNewPassResponse
import com.webmobrilweatherapp.beans.emailverification.EmailVerificationResponse
import com.webmobrilweatherapp.beans.forgotpassword.ForgotPassResponse
import com.webmobrilweatherapp.beans.homepages.HomePageSunnyResponse
import com.webmobrilweatherapp.beans.loginrespons.SignInResponse
import com.webmobrilweatherapp.beans.registerresponse.SignUpResponse
import com.webmobrilweatherapp.beans.singupverifyotp.SignUpverifyotpResponse
import com.webmobrilweatherapp.beans.termsprivacyresponse.TermPrivacyResponse
import com.webmobrilweatherapp.beans.verifyotp.VerifyOtpResponse
import com.webmobrilweatherapp.beans.vote.VoteResponse
import com.webmobrilweatherapp.model.Followers.FollowersResponse
import com.webmobrilweatherapp.model.Following.FollowingResponse
import com.webmobrilweatherapp.model.TendaysWeatherApi.TendaysWeatherApiResponse
import com.webmobrilweatherapp.model.accountdelete.DeleteAccountResponse
import com.webmobrilweatherapp.model.butterflySpecies.ButterflySpeciesResponse
import com.webmobrilweatherapp.model.challengeAccept.ChallengeAcceptResponse
import com.webmobrilweatherapp.model.challenge_vote.ChallengeVoteResponse
import com.webmobrilweatherapp.model.challengebyfriends.ChallengeByFriendsResponse
import com.webmobrilweatherapp.model.challengebyme.ChallengeByMeResponse
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
import com.webmobrilweatherapp.model.locationcity.LocationgetCityResponse
import com.webmobrilweatherapp.model.logout.LogoutResponse
import com.webmobrilweatherapp.model.mayorhomepage.MayorHomepageResponse
import com.webmobrilweatherapp.model.mayorwether.MakingWeatherResponse
import com.webmobrilweatherapp.model.metrologist.createpost.CreatePostResponse
import com.webmobrilweatherapp.model.metrologist.viewuservote.ViewUserVoteResponse
import com.webmobrilweatherapp.model.myweathervotepersentage.MyweathervotepersentageResponse
import com.webmobrilweatherapp.model.notification.NotificationResponse
import com.webmobrilweatherapp.model.postdelete.PostDeleteResponse
import com.webmobrilweatherapp.model.publicPrivate.PublicPrivateResponse
import com.webmobrilweatherapp.model.requestAcceptReject.RequestAcceptRejectResponse
import com.webmobrilweatherapp.model.selectButterfly.SelectButterFlyResponse
import com.webmobrilweatherapp.model.topfivemetrologist.TopFiveResponse
import com.webmobrilweatherapp.model.updateProfile.UpdateProfileResponse
import com.webmobrilweatherapp.model.updatepost.UpdatePostResponse
import com.webmobrilweatherapp.model.updateprofileImage.UpdateProfileImageResponse
import com.webmobrilweatherapp.model.userpost.UserPostImagesResponse
import com.webmobrilweatherapp.model.userprofile.UserProfileResponse
import com.webmobrilweatherapp.model.uservotinglist.UserVotingListResponse
import com.webmobrilweatherapp.model.weathermayorlist.WeatherMayorListResponse
import com.webmobrilweatherapp.models.homepage.HomePageResponse
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.RetrofitConnection
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.RetrofitConnectionLocation
import com.google.gson.Gson
import com.webmobrilweatherapp.model.postById.PostByIdResponse
import com.webmobrilweatherapp.model.usersearching.UserSearchingResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AccountRepositories {
    private val TAG = AccountRepositories::class.java.simpleName
    fun getregistration(name: String, email: String, password: String, city: String,state: String,country: String,
                        user_type: Int,city_lat:Double,city_long:Double,zipcode:String
    ): LiveData<SignUpResponse?> {
        val mutableLiveData = MutableLiveData<SignUpResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getRegistration(name, email, password, city,state,country,user_type,city_lat,city_long,zipcode,2)
        call!!.enqueue(object : Callback<SignUpResponse?> {
            override fun onResponse(
                call: Call<SignUpResponse?>,
                response: Response<SignUpResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                    Log.e("signupee",response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<SignUpResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e("signupee", Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getlogin( email: String, password: String,user_type: String,device_type: String,device_token: String
    ): LiveData<SignInResponse?> {
        val mutableLiveData = MutableLiveData<SignInResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getLogin(email, password,user_type,device_type,device_token)
        call!!.enqueue(object : Callback<SignInResponse?> {
            override fun onResponse(
                call: Call<SignInResponse?>,
                response: Response<SignInResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<SignInResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getforgot( email: String,user_type: String
    ): LiveData<ForgotPassResponse?> {
        val mutableLiveData = MutableLiveData<ForgotPassResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getForgotPass(email,user_type)
        call!!.enqueue(object : Callback<ForgotPassResponse?> {
            override fun onResponse(
                call: Call<ForgotPassResponse?>,
                response: Response<ForgotPassResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<ForgotPassResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getotp( email: String,otp:String
    ): LiveData<VerifyOtpResponse?> {
        val mutableLiveData = MutableLiveData<VerifyOtpResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getVerifyOtp(email,otp)
        call!!.enqueue(object : Callback<VerifyOtpResponse?> {
            override fun onResponse(
                call: Call<VerifyOtpResponse?>,
                response: Response<VerifyOtpResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<VerifyOtpResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getSignupVerifyOtp( email: String,otp:String
    ): LiveData<SignUpverifyotpResponse?> {
        val mutableLiveData = MutableLiveData<SignUpverifyotpResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getSignupVerifyOtp(email,otp)
        call!!.enqueue(object : Callback<SignUpverifyotpResponse?> {
            override fun onResponse(
                call: Call<SignUpverifyotpResponse?>,
                response: Response<SignUpverifyotpResponse?>
            ) {
                Log.e("thisone", Gson().toJson(response.message()))
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    Log.e("thisone12", Gson().toJson(response.message()))
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<SignUpverifyotpResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
                Log.e("thisone12", Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getCreateNewPassword( email: String,new_password:String,confirm_password: String,
    ): LiveData<CreateNewPassResponse?> {
        val mutableLiveData = MutableLiveData<CreateNewPassResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getCreateNewPassword(email,new_password,confirm_password)
        call!!.enqueue(object : Callback<CreateNewPassResponse?> {
            override fun onResponse(
                call: Call<CreateNewPassResponse?>,
                response: Response<CreateNewPassResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<CreateNewPassResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getTermPrivacy( user_type: String,
    ): LiveData<TermPrivacyResponse?> {
        val mutableLiveData = MutableLiveData<TermPrivacyResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getTermPrivacy(user_type)
        call!!.enqueue(object : Callback<TermPrivacyResponse?> {
            override fun onResponse(
                call: Call<TermPrivacyResponse?>,
                response: Response<TermPrivacyResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<TermPrivacyResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getHomeapge(apikey: String?, longitude: String?, language: String?, details: String?
    ): LiveData<HomePageResponse?> {
        val mutableLiveData = MutableLiveData<HomePageResponse?>()
        val apiService = RetrofitConnectionLocation.instance?.createServiceeee()
        val call=apiService!!.getHomeapge("wkw7ho4Gya6HakuE7dNcEVEHIVJMZAhU",longitude,language,details)
        call!!.enqueue(object : Callback<HomePageResponse?> {
            override fun onResponse(
                call: Call<HomePageResponse?>,
                response: Response<HomePageResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    val statusMessageModel = HomePageResponse()
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(statusMessageModel)
                }
            }
            override fun onFailure(call: Call<HomePageResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
                val statusMessageModel = HomePageResponse()
                //statusMessageModel.setError(false);
                mutableLiveData.value = statusMessageModel
            }
        })
        return mutableLiveData
    }
    fun getalllocation(latlng:String,key:String
    ): LiveData<GetallLocationResponse?> {
        val mutableLiveData = MutableLiveData<GetallLocationResponse?>()
        val apiService = RetrofitConnectionLocation.instance?.createServiceeee()
        val call=apiService!!.getalllocation(latlng,key)
        call!!.enqueue(object : Callback<GetallLocationResponse?> {
            override fun onResponse(
                call: Call<GetallLocationResponse?>,
                response: Response<GetallLocationResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    val statusMessageModel = GetallLocationResponse()
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(statusMessageModel)
                }
            }
            override fun onFailure(call: Call<GetallLocationResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
                val statusMessageModel = GetallLocationResponse()
                //statusMessageModel.setError(false);
                mutableLiveData.value = statusMessageModel
            }
        })
        return mutableLiveData
    }
    fun getLocationcity(apikey: String?, longitude: String?, language: String?, details: String?
    ): LiveData<LocationgetCityResponse?> {
        val mutableLiveData = MutableLiveData<LocationgetCityResponse?>()
        val apiService = RetrofitConnectionLocation.instance?.createServiceeee()
        val call=apiService!!.getLocationcity("wkw7ho4Gya6HakuE7dNcEVEHIVJMZAhU",longitude,language,details)
        call!!.enqueue(object : Callback<LocationgetCityResponse?> {
            override fun onResponse(
                call: Call<LocationgetCityResponse?>,
                response: Response<LocationgetCityResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    val statusMessageModel = LocationgetCityResponse()
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(statusMessageModel)
                }
            }
            override fun onFailure(call: Call<LocationgetCityResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
                val statusMessageModel = LocationgetCityResponse()
                //statusMessageModel.setError(false);
                mutableLiveData.value = statusMessageModel
            }
        })
        return mutableLiveData
    }
    fun getHomeapgesunny(key: String,apikey: String?, details: String?
    ): LiveData<HomePageSunnyResponse?> {
        println("Keys")
        println(key)
        val mutableLiveData = MutableLiveData<HomePageSunnyResponse?>()
        val apiService = RetrofitConnectionLocation.instance?.createServiceeee()
        val call=apiService!!.getHomeapgesunny(key,apikey,details)
        call!!.enqueue(object : Callback<HomePageSunnyResponse?> {
            override fun onResponse(
                call: Call<HomePageSunnyResponse?>,
                response: Response<HomePageSunnyResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    val statusMessageModel = HomePageSunnyResponse()
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(statusMessageModel)
                }
            }

            override fun onFailure(call: Call<HomePageSunnyResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
                val statusMessageModel = HomePageSunnyResponse()
                //statusMessageModel.setError(false);
                mutableLiveData.value = statusMessageModel
            }
        })
        return mutableLiveData
    }
    fun getVote( is_temp: String, temp_value: String,precipitation_id: String,weatherdate: String,token:String
    ): LiveData<VoteResponse?> {
        val mutableLiveData = MutableLiveData<VoteResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getVote(is_temp,temp_value,precipitation_id,weatherdate,token)
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
    fun getCreatePost(post_image: List<MultipartBody.Part>, description: RequestBody, token:String
    ): LiveData<CreatePostResponse?> {
        val mutableLiveData = MutableLiveData<CreatePostResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getCreatePost(post_image,description,token)
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
    fun getprepration(token:String
    ): LiveData<BottomdialogResponse?> {
        val mutableLiveData = MutableLiveData<BottomdialogResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getprepration(token)
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
    fun getpostimageuser(user_id:String,token:String
    ): LiveData<GetImagePostuserResponse?> {
        val mutableLiveData = MutableLiveData<GetImagePostuserResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getpostimageuser(user_id,token)
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
    fun getchangepasswords(current_password:String,new_password:String,confirm_password:String,token:String
    ): LiveData<ChangePasswordResponse?> {
        val mutableLiveData = MutableLiveData<ChangePasswordResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getchangepasswords(current_password,new_password,confirm_password,token)
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
    fun getupdateprofile(name:String,phone:String,email:String,city:String,token:String
    ): LiveData<UpdateProfileResponse?> {
        val mutableLiveData = MutableLiveData<UpdateProfileResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getupdateprofile(name,phone,email,city,token)
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
    fun getprofilehomepage(user_id:String,page:String,token:String
    ): LiveData<ProfilehomePageResponse?> {
        val mutableLiveData = MutableLiveData<ProfilehomePageResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getprofilehomepage(user_id,page,token,)
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
    fun getuserprofile(user_id:String,token:String
    ): LiveData<UserProfileResponse?> {
        val mutableLiveData = MutableLiveData<UserProfileResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getuserprofile(user_id,token)
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
    fun getpostbyUser(user_id:String,page: String,token:String
    ): LiveData<UserPostImagesResponse?> {
        val mutableLiveData = MutableLiveData<UserPostImagesResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getpostbyUser(user_id,page,token)
        call!!.enqueue(object : Callback<UserPostImagesResponse?> {
            override fun onResponse(
                call: Call<UserPostImagesResponse?>,
                response: Response<UserPostImagesResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<UserPostImagesResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getupdateprofileImages(
        profile_image: MultipartBody.Part, token:String
    ): LiveData<UpdateProfileImageResponse?> {
        val mutableLiveData = MutableLiveData<UpdateProfileImageResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getupdateprofileImages(profile_image,token)
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
    fun getsearchprofiles(
        user_type:String, token:String
    ): LiveData<UserSearchingResponse?> {
        val mutableLiveData = MutableLiveData<UserSearchingResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getsearchprofiles(user_type,token)
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
    fun getemailverification(email: String, user_type: String
    ): LiveData<EmailVerificationResponse?> {
        val mutableLiveData = MutableLiveData<EmailVerificationResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getemailverification(email,user_type)
        call!!.enqueue(object : Callback<EmailVerificationResponse?> {
            override fun onResponse(
                call: Call<EmailVerificationResponse?>,
                response: Response<EmailVerificationResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<EmailVerificationResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getcontactus(name:String,phone_number:String,email: String,message:String, token:String
    ): LiveData<ContactUsResponse?> {
        val mutableLiveData = MutableLiveData<ContactUsResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getcontactus(name,phone_number,email,message,token)
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
    fun gettopfiveUser(user_type:String, token:String
    ): LiveData<TopFiveResponse?> {
        val mutableLiveData = MutableLiveData<TopFiveResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.gettopfiveUser(user_type,token)
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
    fun getUservotinglist(user_id:String, token:String
    ): LiveData<UserVotingListResponse?> {
        val mutableLiveData = MutableLiveData<UserVotingListResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getUservotinglist(user_id,token)
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
    fun getUserfilter(user_id:String,filter: String, token:String
    ): LiveData<UserVotingListResponse?> {
        val mutableLiveData = MutableLiveData<UserVotingListResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getUserfilter(user_id,filter,token)
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
    fun getUserflow(followed_id:String,event:String, token:String
    ): LiveData<FollowResponse?> {
        val mutableLiveData = MutableLiveData<FollowResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getUserflow(followed_id,event,token)
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
    fun getrlike(post_id:String,like:String, token:String
    ): LiveData<LikeResponse?> {
        val mutableLiveData = MutableLiveData<LikeResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getrlike(post_id,like,token)
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
    fun postcomments(post_id:String,comment:String, token:String
    ): LiveData<PostCommentResponse?> {
        val mutableLiveData = MutableLiveData<PostCommentResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.postcomments(post_id,comment,token)
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
    fun getpostUserComment(post_id:String, token:String
    ): LiveData<GetCommentResponse?> {
        val mutableLiveData = MutableLiveData<GetCommentResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getpostUserComment(post_id,token)
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
    fun getchat(reciver_id:String, token:String
    ): LiveData<GetChatsResponse?> {
        val mutableLiveData = MutableLiveData<GetChatsResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getchat(reciver_id,token)
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
    fun getnotification( context:Context,token:String
    ): LiveData<NotificationResponse?> {
        val mutableLiveData = MutableLiveData<NotificationResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getnotification(token)
        call!!.enqueue(object : Callback<NotificationResponse?> {
            override fun onResponse(
                call: Call<NotificationResponse?>,
                response: Response<NotificationResponse?>
            ) {
                Log.e("==", Gson().toJson(response.message()))

                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())

                } else {
                    //statusMessageModel.setError(false);
                  //  mutableLiveData.setValue(response.body())
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
                Log.e("TAG==", Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getpostdelete(id: String,token:String
    ): LiveData<PostDeleteResponse?> {
        val mutableLiveData = MutableLiveData<PostDeleteResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getpostdelete(id,token)
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
    fun geteditpost(id: String,description:String,token:String
    ): LiveData<UpdatePostResponse?> {
        val mutableLiveData = MutableLiveData<UpdatePostResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.geteditpost(id,description,token)
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
    fun getlogouts(token:String
    ): LiveData<LogoutResponse?> {
        val mutableLiveData = MutableLiveData<LogoutResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getlogouts(token)
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
    fun getmyweathervotelist(filter: String,user_id:String,token:String
    ): LiveData<MyweathervotepersentageResponse?> {
        val mutableLiveData = MutableLiveData<MyweathervotepersentageResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getmyweathervotelist(filter,user_id,token)
        call!!.enqueue(object : Callback<MyweathervotepersentageResponse?> {
            override fun onResponse(
                call: Call<MyweathervotepersentageResponse?>,
                response: Response<MyweathervotepersentageResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<MyweathervotepersentageResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getweathermayorlists(token:String
    ): LiveData<WeatherMayorListResponse?> {
        val mutableLiveData = MutableLiveData<WeatherMayorListResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getweathermayorlists(token)
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
    fun getmakingweathervote(t:TextView,context: Context,user_id: String,token:String
    ): LiveData<MakingWeatherResponse?> {
        val mutableLiveData = MutableLiveData<MakingWeatherResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getmakingweathervote(user_id,token)
        call!!.enqueue(object : Callback<MakingWeatherResponse?> {
            override fun onResponse(
                call: Call<MakingWeatherResponse?>,
                response: Response<MakingWeatherResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    t.visibility= View.GONE

                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                   // mutableLiveData.setValue(response.body())
                    try {

                        if(response.message().equals("Not Found")){
                            t.visibility= View.VISIBLE

                            // Toast.makeText(context, "Data not found!", Toast.LENGTH_SHORT).show()
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
                    } catch (e: Exception)
                    {

                    }
                }
            }
            override fun onFailure(call: Call<MakingWeatherResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }
    fun getdeleteaccounts(user_id: String,token:String
    ): LiveData<DeleteAccountResponse?> {
        val mutableLiveData = MutableLiveData<DeleteAccountResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getdeleteaccounts(user_id,token)
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
    fun getViewVoteUser(token:String
    ): LiveData<ViewUserVoteResponse?> {
        val mutableLiveData = MutableLiveData<ViewUserVoteResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getViewVoteUser(token)
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
    fun getusermayorhomepages(token:String
    ): LiveData<MayorHomepageResponse?> {
        val mutableLiveData = MutableLiveData<MayorHomepageResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getusermayorhomepages(token)
        call!!.enqueue(object : Callback<MayorHomepageResponse?> {
            override fun onResponse(
                call: Call<MayorHomepageResponse?>,
                response: Response<MayorHomepageResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<MayorHomepageResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    fun getButterFly(
    ): LiveData<ButterflySpeciesResponse?> {
        val mutableLiveData = MutableLiveData<ButterflySpeciesResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getButterFlySpeices()
        call!!.enqueue(object : Callback<ButterflySpeciesResponse?> {
            override fun onResponse(
                call: Call<ButterflySpeciesResponse?>,
                response: Response<ButterflySpeciesResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<ButterflySpeciesResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }

//////////////////////////////////////////////////////////////////////////////////////////////

    fun getFollowers(token:String
    ): LiveData<FollowersResponse?> {
        val mutableLiveData = MutableLiveData<FollowersResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getFollowers(token)
        call!!.enqueue(object : Callback<FollowersResponse?> {
            override fun onResponse(
                call: Call<FollowersResponse?>,
                response: Response<FollowersResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<FollowersResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }


    fun getFollowings(token:String
    ): LiveData<FollowingResponse?> {
        val mutableLiveData = MutableLiveData<FollowingResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getFollowings(token)
        call!!.enqueue(object : Callback<FollowingResponse?> {
            override fun onResponse(
                call: Call<FollowingResponse?>,
                response: Response<FollowingResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<FollowingResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }



    fun getSelectButterFly(select_butterfly:String,user_id:String
    ): LiveData<SelectButterFlyResponse?> {
        val mutableLiveData = MutableLiveData<SelectButterFlyResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getSelectButterefly(select_butterfly,user_id)
        call!!.enqueue(object : Callback<SelectButterFlyResponse?> {
            override fun onResponse(
                call: Call<SelectButterFlyResponse?>,
                response: Response<SelectButterFlyResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<SelectButterFlyResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }


    fun getChallenegeByMe(context: Context,token:String
    ): LiveData<ChallengeByMeResponse?> {
        val mutableLiveData = MutableLiveData<ChallengeByMeResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getChallenegeByMe(token)
        call!!.enqueue(object : Callback<ChallengeByMeResponse?> {
            override fun onResponse(
                call: Call<ChallengeByMeResponse?>,
                response: Response<ChallengeByMeResponse?>
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
                    }
                    catch (e: Exception)
                    {


                    }
                }
            }
            override fun onFailure(call: Call<ChallengeByMeResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }

        })
        return mutableLiveData
    }





    fun getChallengeVote(token:String,competitor_id:String,is_temp:String,precipitation_id:String,vote_temp_value:String,vote_date:String,city:String,city_code:String
    ): LiveData<ChallengeVoteResponse?> {
        val mutableLiveData = MutableLiveData<ChallengeVoteResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getChallengeVote(token,competitor_id,is_temp,precipitation_id,vote_temp_value,vote_date,city,city_code)
        call!!.enqueue(object : Callback<ChallengeVoteResponse?> {
            override fun onResponse(
                call: Call<ChallengeVoteResponse?>,
                response: Response<ChallengeVoteResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<ChallengeVoteResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData
    }




    fun getChallenegeByFriends(context: Context,token:String
    ): LiveData<ChallengeByFriendsResponse?> {
        val mutableLiveData = MutableLiveData<ChallengeByFriendsResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getChallenegeByFriends(token)
        call!!.enqueue(object : Callback<ChallengeByFriendsResponse?> {
            override fun onResponse(
                call: Call<ChallengeByFriendsResponse?>,
                response: Response<ChallengeByFriendsResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {
                    //statusMessageModel.setError(false);
               //     mutableLiveData.setValue(response.body())
                    ProgressD.hideProgressDialog()
                    try {

                        if(response.message().equals("Not Found")){
                            Toast.makeText(context, "Data not found!", Toast.LENGTH_SHORT).show()
                        }
                }
                    catch (e: Exception)
                    {


                    }                    }
            }
            override fun onFailure(call: Call<ChallengeByFriendsResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }

        })
        return mutableLiveData
    }


    fun getPrivatePublic(toggle: String,token:String
    ): LiveData<PublicPrivateResponse?> {
        val mutableLiveData = MutableLiveData<PublicPrivateResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getPrivatePublic(toggle,token)
        call!!.enqueue(object : Callback<PublicPrivateResponse?> {
            override fun onResponse(
                call: Call<PublicPrivateResponse?>,
                response: Response<PublicPrivateResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {

                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())

                }
            }
            override fun onFailure(call: Call<PublicPrivateResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData

    }


    fun getChallengeAccept(challenge_id: String,vote_temp_value_by_competitor:String,is_temp_by_competitor:String,precipitation_id_by_competitor:String,token:String
    ): LiveData<ChallengeAcceptResponse?> {
        val mutableLiveData = MutableLiveData<ChallengeAcceptResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getChallengeAccept(challenge_id,vote_temp_value_by_competitor,is_temp_by_competitor,precipitation_id_by_competitor,token)
        call!!.enqueue(object : Callback<ChallengeAcceptResponse?> {
            override fun onResponse(
                call: Call<ChallengeAcceptResponse?>,
                response: Response<ChallengeAcceptResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {

                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())

                }
            }
            override fun onFailure(call: Call<ChallengeAcceptResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData

    }




    fun getTendaysWeather(key: String,apiKey:String
    ): LiveData<TendaysWeatherApiResponse?> {
        val mutableLiveData = MutableLiveData<TendaysWeatherApiResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getTendaysWeather(key,apiKey)
        call!!.enqueue(object : Callback<TendaysWeatherApiResponse?> {
            override fun onResponse(
                call: Call<TendaysWeatherApiResponse?>,
                response: Response<TendaysWeatherApiResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {

                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())

                }
            }
            override fun onFailure(call: Call<TendaysWeatherApiResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData

    }




    fun getRequestAcceptReject(status: String,requestFrom:String,token:String
    ): LiveData<RequestAcceptRejectResponse?> {
        val mutableLiveData = MutableLiveData<RequestAcceptRejectResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getRequestAcceptReject(status,requestFrom,token)
        call!!.enqueue(object : Callback<RequestAcceptRejectResponse?> {
            override fun onResponse(
                call: Call<RequestAcceptRejectResponse?>,
                response: Response<RequestAcceptRejectResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {

                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())

                }
            }
            override fun onFailure(call: Call<RequestAcceptRejectResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData

    }


    fun getCreateVideoPost(post_video: MultipartBody.Part, description: RequestBody, token:String
    ): LiveData<CreatePostResponse?> {
        val mutableLiveData = MutableLiveData<CreatePostResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getCreateVideoPost(post_video,description,token)
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

    //PostById



    fun getPostById(post_id:String,token:String
    ): LiveData<PostByIdResponse?> {
        val mutableLiveData = MutableLiveData<PostByIdResponse?>()
        val apiService = RetrofitConnection.instance?.createService()
        val call = apiService!!.getPostById(post_id,token)
        call!!.enqueue(object : Callback<PostByIdResponse?> {
            override fun onResponse(
                call: Call<PostByIdResponse?>,
                response: Response<PostByIdResponse?>
            ) {
                Log.e(TAG, Gson().toJson(response.message()))
                if (response.isSuccessful) {
                    mutableLiveData.setValue(response.body())
                } else {

                    //statusMessageModel.setError(false);
                    mutableLiveData.setValue(response.body())

                }
            }
            override fun onFailure(call: Call<PostByIdResponse?>, t: Throwable) {
                Objects.requireNonNull(t.message)?.let { Log.e("error", it) }
                Log.e(TAG, Gson().toJson(t.cause))
            }
        })
        return mutableLiveData

    }


}