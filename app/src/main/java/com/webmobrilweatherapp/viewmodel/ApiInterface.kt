package com.webmobrilweatherapp.viewmodel


import com.webmobrilweatherapp.beans.bottomdialog.BottomdialogResponse
import com.webmobrilweatherapp.beans.termsprivacyresponse.TermPrivacyResponse
import com.webmobrilweatherapp.beans.verifyotp.VerifyOtpResponse
import com.webmobrilweatherapp.beans.createnewpassword.CreateNewPassResponse
import com.webmobrilweatherapp.beans.emailverification.EmailVerificationResponse
import com.webmobrilweatherapp.beans.forgotpassword.ForgotPassResponse
import com.webmobrilweatherapp.beans.homepages.HomePageSunnyResponse
import com.webmobrilweatherapp.beans.loginrespons.SignInResponse
import com.webmobrilweatherapp.beans.registerresponse.SignUpResponse
import com.webmobrilweatherapp.beans.singupverifyotp.SignUpverifyotpResponse
import com.webmobrilweatherapp.beans.vote.VoteResponse
import com.webmobrilweatherapp.model.Followers.FollowersResponse
import com.webmobrilweatherapp.model.Following.FollowingResponse
import com.webmobrilweatherapp.model.TendaysWeatherApi.TendaysWeatherApiResponse
import com.webmobrilweatherapp.model.WeatherAlert.WeatherAlertResponse
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
import com.webmobrilweatherapp.model.metrologist.emailverification.MetrologistEmailverificationResponse
import com.webmobrilweatherapp.model.metrologist.forgot.MetrologistForgotResponse
import com.webmobrilweatherapp.model.metrologist.otp.MetrologistOtpResponse
import com.webmobrilweatherapp.model.metrologist.resetpassword.MetrologistResetPasswordResponse
import com.webmobrilweatherapp.model.metrologist.signup.MetrologistSignUpResponse
import com.webmobrilweatherapp.model.metrologist.signupotpverification.MetrologistOtpVerificationResponse
import com.webmobrilweatherapp.model.metrologist.viewuservote.ViewUserVoteResponse
import com.webmobrilweatherapp.model.metrologistLogin.MetrologistLoginnResponse
import com.webmobrilweatherapp.model.myweathervotepersentage.MyweathervotepersentageResponse
import com.webmobrilweatherapp.model.notification.NotificationResponse
import com.webmobrilweatherapp.model.postById.PostByIdResponse
import com.webmobrilweatherapp.model.postdelete.PostDeleteResponse
import com.webmobrilweatherapp.model.publicPrivate.PublicPrivateResponse
import com.webmobrilweatherapp.model.requestAcceptReject.RequestAcceptRejectResponse
import com.webmobrilweatherapp.model.selectButterfly.SelectButterFlyResponse
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
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.API_NEW_USER
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.API_SIGN_UP_METROLOGIST
 import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.Homepages
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getButterFlySpeices
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getChallengeAccept
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getChallengeByFriends
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getChallengeByMe
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getChallengeVote
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getFollowers
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getFollowings
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getPostById
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getPostcomment
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getPrivatePublic
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getRequestAcceptReject
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getSelectButterfly
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getTendaysApi
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getTopfive
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getView_Vote
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getWeatherAlert
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getchangepassword
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getchat
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getcomment
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getcontactUs
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getdeleteaccount
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getfilter
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getlike
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getlogout
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getmakingweather
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getmyweathervote
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getnotification
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getpostbyuser
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getpostdeletes
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getpostimageUser
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getprecipitations
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getprofilehomepage
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getsearchprofile
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getsendalert
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getupdatepost
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getupdateprofile
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getupdateprofileImage
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getuserflow
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getusermayorhomepage
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getuserprofile
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getuservotingList
import com.webmobrilweatherapp.viewmodel.ApiConstants.Companion.getweathermayorlist
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
interface ApiInterface {


    @FormUrlEncoded
    @POST(ApiConstants.API_SIGN_UP)
    fun getRegistration(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("country") country: String,
        @Field("user_type") user_type: Int,
        @Field("city_lat") city_lat: Double,
        @Field("city_long") city_long: Double,
        @Field("zipcode") zipcode: String,
        @Field("device_type")device_type:Int
    ): Call<SignUpResponse>

    @FormUrlEncoded
    @POST(ApiConstants.API_LOGIN)
    fun getLogin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("user_type") user_type: String,
        @Field("device_type") device_type: String,
        @Field("device_token") device_token: String
    ): Call<SignInResponse>


    @FormUrlEncoded
    @POST(ApiConstants.API_FORGOT_PASSWORD)
    fun getForgotPass(
        @Field("email") email: String,
        @Field("user_type") user_type: String
    ): Call<ForgotPassResponse>

    @FormUrlEncoded
    @POST(ApiConstants.getemailverification)
    fun getemailverification(
        @Field("email") email: String,
        @Field("user_type") user_type: String
    ): Call<EmailVerificationResponse>


    @FormUrlEncoded
    @POST(ApiConstants.API_VERIFY_OTP)
    fun getVerifyOtp(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): Call<VerifyOtpResponse>


    @FormUrlEncoded
    @POST(ApiConstants.VerfiyOtp)
    fun getSignupVerifyOtp(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): Call<SignUpverifyotpResponse>


    @FormUrlEncoded
    @POST(ApiConstants.API_RESET_PASSWORD)
    fun getCreateNewPassword(
        @Field("email") email: String,
        @Field("new_password") new_password: String,
        @Field("confirm_password") confirm_password: String
    ): Call<CreateNewPassResponse>


    @FormUrlEncoded
    @POST(ApiConstants.API_GET_STATIC_CONTENT)
    fun getTermPrivacy(@Field("user_type") user_type: String): Call<TermPrivacyResponse>

    @GET(ApiConstants.Homepage)
    fun getHomeapge(
        @Query("apikey") apikey: String,
        @Query("q") longitude: String?,
        @Query("language") language: String?,
        @Query("details") details: String?
    ): Call<HomePageResponse>


    @GET(ApiConstants.LocationCity)
    fun getLocationcity(
        @Query("apikey") apikey: String,
        @Query("q") longitude: String?,
        @Query("language") language: String?,
        @Query("details") details: String?
    ): Call<LocationgetCityResponse>

    @GET(Homepages)
    fun getHomeapgesunny(
        @Path("key") key: String?,
        @Query("apikey") apikey: String?,
        @Query("details") details: String?,
    ): Call<HomePageSunnyResponse>


    @GET(getTendaysApi)
    fun getTendaysWeather(
        @Path("key") key: String?,
        @Query("apikey") apikey: String?,
    ): Call<TendaysWeatherApiResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(ApiConstants.getvote)
    fun getVote(
        @Field("is_temp") is_temp: String,
        @Field("temp_value") temp_value: String,
        @Field("precipitation_id") precipitation_id: String,
        @Field("weatherdate") weatherdate: String,
        @Header("authorization") token: String
    ): Call<VoteResponse>

    @Headers("Accept: application/json")
    @GET(getprecipitations)
    fun getprepration(
        @Header("authorization") token: String?,
    ): Call<BottomdialogResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getpostimageUser)
    fun getpostimageuser(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<GetImagePostuserResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getchangepassword)
    fun getchangepasswords(
        @Field("current_password") current_password: String,
        @Field("new_password") new_password: String,
        @Field("confirm_password") confirm_password: String,
        @Header("authorization") token: String?
    ): Call<ChangePasswordResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getupdateprofile)
    fun getupdateprofile(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("city") city: String,
        @Header("authorization") token: String?
    ): Call<UpdateProfileResponse>


    @Headers("Accept: application/json")
    @GET(getprofilehomepage)
    fun getprofilehomepage(
        @Query("user_id") user_id: String,
        @Query("page") page:String,
        @Header("authorization") token: String?
    ): Call<ProfilehomePageResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getuserprofile)
    fun getuserprofile(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<UserProfileResponse>


    @Headers("Accept: application/json")
    @Multipart
    @POST(getupdateprofileImage)
    fun getupdateprofileImages(
        @Part profile_image: MultipartBody.Part?,
        @Header("authorization") token: String?
    ): Call<UpdateProfileImageResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getsearchprofile)
    fun getsearchprofiles(
        @Field("user_type") user_type: String,
        @Header("authorization") token: String?
    ): Call<UserSearchingResponse>

    @Headers("Accept: application/json")
    @Multipart
    @POST(ApiConstants.getCreatepost)
    fun getCreatePost(
        @Part post_image: List<MultipartBody.Part>,
        @Part("description") description: RequestBody,
        @Header("authorization") token: String
    ): Call<CreatePostResponse>


    @Headers("Accept: application/json")
    @Multipart
    @POST(ApiConstants.getCreatepost)
    fun getCreateVideoPost(
        @Part post_video: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("authorization") token: String
    ): Call<CreatePostResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getpostbyuser)
    fun getpostbyUser(
        @Field("user_id") user_id: String,
        @Query("page") page:String,
        @Header("authorization") token: String?
    ): Call<UserPostImagesResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getcontactUs)
    fun getcontactus(
        @Field("name") name: String,
        @Field("phone_number") phone_number: String,
        @Field("email") email: String,
        @Field("message") message: String,
        @Header("authorization") token: String?
    ): Call<ContactUsResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getTopfive)
    fun gettopfiveUser(
        @Field("user_type") user_type: String,
        @Header("authorization") token: String?
    ): Call<TopFiveResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getuservotingList)
    fun getUservotinglist(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<UserVotingListResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getfilter)
    fun getUserfilter(
        @Field("user_id") user_id: String,
        @Field("filter") filter: String,
        @Header("authorization") token: String?
    ): Call<UserVotingListResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getuserflow)
    fun getUserflow(
        @Field("followed_id") followed_id: String,
        @Field("event") event: String,
        @Header("authorization") token: String?
    ): Call<FollowResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getlike)
    fun getrlike(
        @Field("post_id") post_id: String,
        @Field("like") like: String,
        @Header("authorization") token: String?
    ): Call<LikeResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getcomment)
    fun postcomments(
        @Field("post_id") post_id: String,
        @Field("comment") comment: String,
        @Header("authorization") token: String?
    ): Call<PostCommentResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getPostcomment)
    fun getpostUserComment(
        @Field("post_id") post_id: String,
        @Header("authorization") token: String?
    ): Call<GetCommentResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getchat)
    fun getchat(
        @Field("reciver_id") reciver_id: String,
        @Header("authorization") token: String?
    ): Call<GetChatsResponse>

    @Headers("Accept: application/json")
    @GET(getnotification)
    fun getnotification(
        @Header("authorization") token: String?
    ): Call<NotificationResponse>

    @Headers("Accept: application/json")
     @DELETE(getpostdeletes)
    fun getpostdelete(
        @Path("version") id: String,
        @Header("authorization") token: String?
    ): Call<PostDeleteResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getupdatepost)
    fun geteditpost(
        @Path("version") id: String,
        @Field("description")description:String,
        @Header("authorization") token: String?
    ): Call<UpdatePostResponse>

    @Headers("Accept: application/json")
    @GET(getlogout)
    fun getlogouts(
        @Header("authorization") token: String?
    ): Call<LogoutResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getmyweathervote)
    fun getmyweathervotelist(
        @Field("filter") filter: String,
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<MyweathervotepersentageResponse>

    @Headers("Accept: application/json")
    @GET(getweathermayorlist)
    fun getweathermayorlists(
        @Header("authorization") token: String?
    ): Call<WeatherMayorListResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getmakingweather)
    fun getmakingweathervote(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<MakingWeatherResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getdeleteaccount)
    fun getdeleteaccounts(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<DeleteAccountResponse>

    @GET(getView_Vote)
    fun getViewVoteUser(
        @Header("authorization") token: String?,
    ): Call<ViewUserVoteResponse>


    @GET(getusermayorhomepage)
    fun getusermayorhomepages(
        @Header("authorization") token: String?,
    ): Call<MayorHomepageResponse>

    @GET(API_NEW_USER)
     fun getNewUser(
        @Header("X-BingApis-SDK")sdk:String,
        @Header("X-RapidAPI-Key")rapidkey:String,
        @Header("RapidAPI-Host")newHost:String,
    ): Call<String>


     @GET("https://maps.googleapis.com/maps/api/geocode/json?")
     fun getalllocation(
         @Query("latlng")latlng:String,
         @Query("key")key:String,
     ):Call<GetallLocationResponse>


    //metrologistApi's

    @Multipart
    @POST(API_SIGN_UP_METROLOGIST)
    fun getResgistrationMetrologist(
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("city") city: RequestBody?,
        @Part("user_type") user_type: Int?,
        @Part meterologist_docs: MultipartBody.Part,
        @Part("state") state: RequestBody,
        @Part("country") country: RequestBody,
        @Part("city_lat") city_lat: RequestBody,
        @Part("city_long") city_long: RequestBody,
        @Part("zipcode") zipcode: RequestBody,
        @Part("device_type")device_type:Int?
        ): Call<MetrologistSignUpResponse?>?


    @FormUrlEncoded
    @POST(ApiConstants.API_LOGIN_METROLOGIST)
    fun getLoginmetrologist(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("user_type") user_type: String,
        @Field("device_type") device_type: String,
        @Field("device_token") device_token: String
    ): Call<MetrologistLoginnResponse>


    @FormUrlEncoded
    @POST(ApiConstants.API_FORGOT_METROLOGIST)
    fun getforgotmetrologist(
        @Field("email") email: String,
        @Field("user_type") user_type: String
    ): Call<MetrologistForgotResponse>

    @FormUrlEncoded
    @POST(ApiConstants.API_VERIFY_OTP_METROLOGIST)
    fun getverifyotpmetrologist(
        @Field("otp") otp: String,
        @Field("email") email: String,
    ): Call<MetrologistOtpResponse>


    @FormUrlEncoded
    @POST(ApiConstants.API_RESET_PASSWORD_METROLOGIST)
    fun getresetpasswordmetrologist(
        @Field("email") email: String,
        @Field("new_password") new_password: String,
        @Field("confirm_password") confirm_password: String,
    ): Call<MetrologistResetPasswordResponse>


    @FormUrlEncoded
    @POST(ApiConstants.API_EMAIL_VERIFICATION_METROLOGIST)
    fun getemailverificationmetrologist(
        @Field("email") email: String,
        @Field("user_type") user_type: String,
    ): Call<MetrologistEmailverificationResponse>


    @FormUrlEncoded
    @POST(ApiConstants.API_SIGN_VERIFY_OTP_METROLOGIST)
    fun getsignupotpverificationmetrologist(
        @Field("otp") otp: String,
        @Field("email") email: String,
    ): Call<MetrologistOtpVerificationResponse>

    @GET(ApiConstants.Homepage)
    fun getHomeapgemetrolgist(
        @Query("apikey") apikey: String,
        @Query("q") longitude: String?,
        @Query("language") language: String?,
        @Query("details") details: String?
    ): Call<HomePageResponse>
    @GET(Homepages)
    fun getHomeapgesunnymetrlogist(
        @Path("key") key: String?,
        @Query("apikey") apikey: String?,
        @Query("details") details: String?,
    ): Call<HomePageSunnyResponse>


    @GET(getView_Vote)
    fun getViewVote(
        @Header("authorization") token: String?,
    ): Call<ViewUserVoteResponse>


    ///16-3-22
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getpostimageUser)
    fun getpostimageMetrologist(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<GetImagePostuserResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getchangepassword)
    fun getchangepasswordsMetrologist(
        @Field("current_password") current_password: String,
        @Field("new_password") new_password: String,
        @Field("confirm_password") confirm_password: String,
        @Header("authorization") token: String?
    ): Call<ChangePasswordResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getupdateprofile)
    fun getupdateprofileMetrologist(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("city") city: String,
        @Header("authorization") token: String?
    ): Call<UpdateProfileResponse>



    @GET(getprofilehomepage)
    fun getprofilehomepageMetrologist(
        @Query("user_id") user_id: String,
        @Query("page") page:String,
        @Header("authorization") token: String?
    ): Call<ProfilehomePageResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getuserprofile)
    fun getuserprofileMetrologist(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<UserProfileResponse>


    @Headers("Accept: application/json")
    @Multipart
    @POST(getupdateprofileImage)
    fun getupdateprofileImagesMetrologist(
        @Part profile_image: MultipartBody.Part?,
        @Header("authorization") token: String?
    ): Call<UpdateProfileImageResponse>

    @Headers("Accept: application/json")
    @Multipart
    @POST(ApiConstants.getCreatepost)
    fun getCreatePostMetrologist(
        @Part post_image: List<MultipartBody.Part>,
        @Part("description") description: RequestBody,
        @Header("authorization") token: String
    ): Call<CreatePostResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getsearchprofile)
    fun getsearchprofilesmetrologist(
        @Field("user_type") user_type: String,
        @Header("authorization") token: String?
    ): Call<UserSearchingResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getpostbyuser)
    fun getpostbyMetrologist(
        @Field("user_id") user_id: String,
        @Query("page") page:String,
        @Header("authorization") token: String?
    ): Call<UserPostImagesResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getcontactUs)
    fun getcontactusmetrologist(
        @Field("name") name: String,
        @Field("phone_number") phone_number: String,
        @Field("email") email: String,
        @Field("message") message: String,
        @Header("authorization") token: String?
    ): Call<ContactUsResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getTopfive)
    fun gettopfivemetrologist(
        @Field("user_type") user_type: String,
        @Header("authorization") token: String?
    ): Call<TopFiveResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getuserflow)
    fun getUserflowMetrologist(
        @Field("followed_id") followed_id: String,
        @Field("event") event: String,
        @Header("authorization") token: String?
    ): Call<FollowResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getlike)
    fun getrlikeMterologist(
        @Field("post_id") post_id: String,
        @Field("like") like: String,
        @Header("authorization") token: String?
    ): Call<LikeResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getcomment)
    fun postMetrologistcomments(
        @Field("post_id") post_id: String,
        @Field("comment") comment: String,
        @Header("authorization") token: String?
    ): Call<PostCommentResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getPostcomment)
    fun getpostMetrologistcomments(
        @Field("post_id") post_id: String,
        @Header("authorization") token: String?
    ): Call<GetCommentResponse>

    @FormUrlEncoded
    @POST(getchat)
    fun getmetrologistchat(
        @Field("reciver_id") reciver_id: String,
        @Header("authorization") token: String?
    ): Call<GetChatsResponse>

    @Headers("Accept: application/json")
    @GET(getnotification)
    fun getnotificationMetrologist(
        @Header("authorization") token: String?
    ): Call<NotificationResponse>

    //******weather alrt*****//

    @Headers("Accept: application/json")
    @GET(getWeatherAlert)
    fun getWeatherAlert(): Call<WeatherAlertResponse>
    //**********************//

    @Headers("Accept: application/json")
    @DELETE(getpostdeletes)
    fun getpostdeleteMetrologist(
        @Path("version") id: String,
        @Header("authorization") token: String?
    ): Call<PostDeleteResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getupdatepost)
    fun geteditpostMetrologist(
        @Path("version") id: String,
        @Field("description")description:String,
        @Header("authorization") token: String?
    ): Call<UpdatePostResponse>

    @Headers("Accept: application/json")
    @GET(getlogout)
    fun getlogoutsmetrologist(
        @Header("authorization") token: String?
    ): Call<LogoutResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getuservotingList)
    fun getUservotinglistmetrologist(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<UserVotingListResponse>

    @Headers("Accept: application/json")
    @GET(getprecipitations)
    fun getpreprationmetrologist(
        @Header("authorization") token: String?,
    ): Call<BottomdialogResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(ApiConstants.getvote)
    fun getVotemetrologist(
        @Field("is_temp") is_temp: String,
        @Field("temp_value") temp_value: String,
        @Field("precipitation_id") precipitation_id: String,
        @Field("weatherdate") weatherdate: String,
        @Header("authorization") token: String
    ): Call<VoteResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getuservotingList)
    fun getUservotinglistMetrologist(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<UserVotingListResponse>


    @Headers("Accept: application/json")
    @GET(getweathermayorlist)
    fun getweathermayorlistsmetrologist(
        @Header("authorization") token: String?
    ): Call<WeatherMayorListResponse>
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getmakingweather)
    fun getmakingweathervotemetrologist(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<MakingWeatherResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getdeleteaccount)
    fun getdeleteaccountmetrologist(
        @Field("user_id") user_id: String,
        @Header("authorization") token: String?
    ): Call<DeleteAccountResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getsendalert)
    fun getsendalerts(
        @Field("message") message: String,
        @Field("title") title: String,
        @Field("user_id") userId: String,
        @Header("authorization") token: String?
    ): Call<SendAlertResponse>


    @GET("https://maps.googleapis.com/maps/api/geocode/json?")
    fun getalllocationmetrologist(
        @Query("latlng")latlng:String,
        @Query("key")key:String,
    ):Call<GetallLocationResponse>


    @GET(getButterFlySpeices)
    fun getButterFlySpeices(
    ): Call<ButterflySpeciesResponse>


    @GET(getFollowers)
    fun getFollowers(
        @Header("authorization") token: String?
    ): Call<FollowersResponse>


    @GET(getFollowings)
    fun getFollowings(
        @Header("authorization") token: String?
    ): Call<FollowingResponse>

    @GET(getChallengeByMe)
    fun getChallenegeByMe(
        @Header("authorization") token: String?
    ): Call<ChallengeByMeResponse>


    @GET(getChallengeByFriends)
    fun getChallenegeByFriends(
        @Header("authorization") token: String?
    ): Call<ChallengeByFriendsResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getSelectButterfly)
    fun getSelectButterefly(
        @Field("select_butterfly") select_butterfly: String,
        @Field("user_id") user_id: String,
    ): Call<SelectButterFlyResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getChallengeVote)
    fun getChallengeVote(
        @Header("authorization") token: String?,
        @Field("competitor_id") competitor_id: String,
        @Field("is_temp") is_temp: String,
        @Field("precipitation_id") precipitation_id: String,
        @Field("vote_temp_value") vote_temp_value: String,
        @Field("vote_date") vote_date: String,
        @Field("city") city: String,
        @Field("city_code") city_code: String,
    ): Call<ChallengeVoteResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getPrivatePublic)
    fun getPrivatePublic(
        @Field("toggle") toggle: String,
        @Header("authorization") token: String?
    ): Call<PublicPrivateResponse>



    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getChallengeAccept)
    fun getChallengeAccept(
        @Field("challenge_id") challenge_id: String,
        @Field("vote_temp_value_by_competitor") vote_temp_value_by_competitor: String,
        @Field("is_temp_by_competitor") is_temp_by_competitor: String,
        @Field("precipitation_id_by_competitor") precipitation_id_by_competitor: String,
        @Header("authorization") token: String?
    ): Call<ChallengeAcceptResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getRequestAcceptReject)
    fun getRequestAcceptReject(
        @Field("status") status: String,
        @Field("requestFrom") requestFrom: String,
        @Header("authorization") token: String?
    ): Call<RequestAcceptRejectResponse>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST(getPostById)
    fun getPostById(
        @Field("post_id") post_id: String,
        @Header("authorization") token: String?
    ): Call<PostByIdResponse>

}