package com.example.myapplication.viewmodel.webconfig.ApiConnection.network

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.myapplication.viewmodel.webconfig.ApiConnection.AccountRepositoriesMetrologist
import com.webmobrilweatherapp.beans.bottomdialog.BottomdialogResponse
import com.webmobrilweatherapp.beans.homepages.HomePageSunnyResponse
import com.webmobrilweatherapp.beans.vote.VoteResponse
import com.webmobrilweatherapp.model.WeatherAlert.WeatherAlertResponse
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
import com.webmobrilweatherapp.model.metrologistLogin.MetrologistLoginnResponse
import com.webmobrilweatherapp.model.notification.NotificationResponse
import com.webmobrilweatherapp.model.postdelete.PostDeleteResponse
import com.webmobrilweatherapp.model.sendAlert.SendAlertResponse
import com.webmobrilweatherapp.model.topfivemetrologist.TopFiveResponse
import com.webmobrilweatherapp.model.updateProfile.UpdateProfileResponse
import com.webmobrilweatherapp.model.updatepost.UpdatePostResponse
import com.webmobrilweatherapp.model.updateprofileImage.UpdateProfileImageResponse
import com.webmobrilweatherapp.model.userpost.UserPostImagesResponse
import com.webmobrilweatherapp.model.userprofile.UserProfileResponse
import com.webmobrilweatherapp.model.usersearching.UserSearchingResponse
import com.webmobrilweatherapp.model.uservotinglist.UserVotingListResponse
import com.webmobrilweatherapp.model.weathermayorlist.WeatherMayorListResponse
import com.webmobrilweatherapp.models.homepage.HomePageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody


class AccountViewModelMetrologist(application: Application) : AndroidViewModel(application) {
    private var accountRepositoriesMetrologist: AccountRepositoriesMetrologist
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
        return accountRepositoriesMetrologist.getResgistrationMetrologist(
            name,
            email,
            password,
            city,
            user_type,
            meterologist_docs,state,country,city_lat,city_long,zipcode
        )
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getLoginmetrologist(
        email: String, password: String, user_type: String,device_type:String,device_token:String
    ): LiveData<MetrologistLoginnResponse?> {
        return accountRepositoriesMetrologist.getLoginmetrologist(email, password, user_type,device_type,device_token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getforgotmetrologist(
        email: String, user_type: String
    ): LiveData<MetrologistForgotResponse?> {
        return accountRepositoriesMetrologist.getforgotmetrologist(email, user_type)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getverifyotpmetrologist(
        otp: String, email: String
    ): LiveData<MetrologistOtpResponse?> {
        return accountRepositoriesMetrologist.getverifyotpmetrologist(otp, email)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getresetpasswordmetrologist(
        email: String, new_password: String, confirm_password: String
    ): LiveData<MetrologistResetPasswordResponse?> {
        return accountRepositoriesMetrologist.getresetpasswordmetrologist(
            email,
            new_password,
            confirm_password
        )
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getemailverificationmetrologist(
        email: String, user_type: String
    ): LiveData<MetrologistEmailverificationResponse?> {
        return accountRepositoriesMetrologist.getemailverificationmetrologist(email, user_type)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getsignupotpverificationmetrologist(
        otp: String, email: String
    ): LiveData<MetrologistOtpVerificationResponse?> {
        return accountRepositoriesMetrologist.getsignupotpverificationmetrologist(otp, email)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getViewVote(
        token: String
    ): LiveData<ViewUserVoteResponse?> {
        return accountRepositoriesMetrologist.getViewVote(token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getHomeapgemetrolgist(
        apikey:String?,
        longitude:String?,
        language:String?,
        details:String?
    ): LiveData<HomePageResponse?> {
        return accountRepositoriesMetrologist.getHomeapgemetrolgist("apikey",longitude,language,details)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getHomeapgesunnymetrlogist(
        key:String?,
        apikey:String?,
        details:String?,
    ): LiveData<HomePageSunnyResponse?> {
        return accountRepositoriesMetrologist.getHomeapgesunnymetrlogist(key.toString(),apikey,details)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getchangepasswordsMetrologist(
        current_password: String, new_password: String, confirm_password: String, token: String
    ): LiveData<ChangePasswordResponse?> {
        return accountRepositoriesMetrologist.getchangepasswordsMetrologist(
            current_password,
            new_password,
            confirm_password,
            token
        )
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getupdateprofileMetrologist(
        name: String, phone: String,
        email: String, city: String, token: String
    ): LiveData<UpdateProfileResponse?> {
        return accountRepositoriesMetrologist.getupdateprofileMetrologist(
            name,
            phone,
            email,
            city,
            token
        )
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getuserprofileMetrologist(
        user_id: String, token: String
    ): LiveData<UserProfileResponse?> {
        return accountRepositoriesMetrologist.getuserprofileMetrologist(user_id, token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getupdateprofileImagesMetrologist(
        profile_image: MultipartBody.Part, token: String
    ): LiveData<UpdateProfileImageResponse?> {
        return accountRepositoriesMetrologist.getupdateprofileImagesMetrologist(
            profile_image,
            token
        )
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getCreatePostMetrologist(
        profile_image: List<MultipartBody.Part>, description: RequestBody, token: String
    ): LiveData<CreatePostResponse?> {
        return accountRepositoriesMetrologist.getCreatePostMetrologist(
            profile_image,
            description,
            token
        )
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getprofilehomepageMetrologist(
        user_id: String, page:String,token: String
    ): LiveData<ProfilehomePageResponse?> {
        return accountRepositoriesMetrologist.getprofilehomepageMetrologist(user_id, page,token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getpostimageMetrologist(
        user_id: String, token: String
    ): LiveData<GetImagePostuserResponse?> {
        return accountRepositoriesMetrologist.getpostimageMetrologist(user_id, token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getsearchprofilesmetrologist(
        user_type: String, token: String
    ): LiveData<UserSearchingResponse?> {
        return accountRepositoriesMetrologist.getsearchprofilesmetrologist(user_type, token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getpostbyMetrologist(
        user_id: String,page: String, token: String
    ): LiveData<UserPostImagesResponse?> {
        return accountRepositoriesMetrologist.getpostbyMetrologist(user_id,page, token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getcontactusmetrologist(
        name: String, phone_number: String, email: String, message: String, token: String
    ): LiveData<ContactUsResponse?> {
        return accountRepositoriesMetrologist.getcontactusmetrologist(
            name,
            phone_number,
            email,
            message,
            token
        )
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun gettopfivemetrologist(
        user_type: String, token: String
    ): LiveData<TopFiveResponse?> {
        return accountRepositoriesMetrologist.gettopfivemetrologist(user_type, token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getUserflowMetrologist(
        followed_id: String, event: String, token: String
    ): LiveData<FollowResponse?> {
        return accountRepositoriesMetrologist.getUserflowMetrologist(followed_id, event, token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }

    fun getrlikeMterologist(
        post_id: String, like: String, token: String
    ): LiveData<LikeResponse?> {
        return accountRepositoriesMetrologist.getrlikeMterologist(post_id, like, token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun postMetrologistcomments(
        post_id: String, comment: String, token: String
    ): LiveData<PostCommentResponse?> {
        return accountRepositoriesMetrologist.postMetrologistcomments(post_id, comment, token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getpostMetrologistcomments(
        post_id: String, token: String
    ): LiveData<GetCommentResponse?> {
        return accountRepositoriesMetrologist.getpostMetrologistcomments(post_id, token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getmetrologistchat(
        reciver_id: String, token: String
    ): LiveData<GetChatsResponse?> {
        return accountRepositoriesMetrologist.getmetrologistchat(reciver_id, token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }



    fun getnotificationMetrologist(
        token: String,context: Context
    ): LiveData<NotificationResponse?> {
        return accountRepositoriesMetrologist.getnotificationMetrologist(token,context)
    }


    fun getWeatherAlert(
    ): LiveData<WeatherAlertResponse?> {
        return accountRepositoriesMetrologist.getWeatherAlert()
    }



    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getpostdeleteMetrologist(
        id: String,token:String
    ): LiveData<PostDeleteResponse?> {
        return accountRepositoriesMetrologist.getpostdeleteMetrologist(id,token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun geteditpostMetrologist(
        id: String,description:String,token:String
    ): LiveData<UpdatePostResponse?> {
        return accountRepositoriesMetrologist.geteditpostMetrologist(id,description,token)
    }
    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getlogoutsmetrologist(token:String
    ): LiveData<LogoutResponse?> {
        return accountRepositoriesMetrologist.getlogoutsmetrologist(token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getUservotinglistmetrologist(user_id: String,token:String
    ): LiveData<UserVotingListResponse?> {
        return accountRepositoriesMetrologist.getUservotinglistmetrologist(user_id,token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getpreprationmetrologist(token:String
    ): LiveData<BottomdialogResponse?> {
        return accountRepositoriesMetrologist.getpreprationmetrologist(token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getVotemetrologist(is_temp: String, temp_value: String,precipitation_id: String,weatherdate: String,token:String
    ): LiveData<VoteResponse?> {
        return accountRepositoriesMetrologist.getVotemetrologist(is_temp,temp_value,precipitation_id,weatherdate,token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getUservotinglistMetrologist(user_id:String,token:String
    ): LiveData<UserVotingListResponse?> {
        return accountRepositoriesMetrologist.getUservotinglistMetrologist(user_id,token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getweathermayorlistsmetrologist(token:String
    ): LiveData<WeatherMayorListResponse?> {
        return accountRepositoriesMetrologist.getweathermayorlistsmetrologist(token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getmakingweathervotemetrologist(user_id: String,token:String
    ): LiveData<MakingWeatherResponse?> {
        return accountRepositoriesMetrologist.getmakingweathervotemetrologist(user_id,token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getdeleteaccountmetrologist(user_id: String,token:String
    ): LiveData<DeleteAccountResponse?> {
        return accountRepositoriesMetrologist.getdeleteaccountmetrologist(user_id,token)
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getsendalerts(message: String,title:String,userId:String,token:String
    ): LiveData<SendAlertResponse?> {
        return accountRepositoriesMetrologist.getsendalerts(message,title,userId,token )
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
    fun getalllocationmetrologist(latlng:String,key:String
    ): LiveData<GetallLocationResponse?> {
        return accountRepositoriesMetrologist.getalllocationmetrologist(latlng,key )
    }

    init {
        accountRepositoriesMetrologist = AccountRepositoriesMetrologist()
    }
}