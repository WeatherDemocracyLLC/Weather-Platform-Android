package com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network

import android.app.Application
import android.content.Context
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.myapplication.viewmodel.webconfig.ApiConnection.AccountRepositories
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
import com.webmobrilweatherapp.model.postById.PostByIdResponse
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
import com.webmobrilweatherapp.model.usersearching.UserSearchingResponse
import com.webmobrilweatherapp.model.uservotinglist.UserVotingListResponse
import com.webmobrilweatherapp.model.weathermayorlist.WeatherMayorListResponse
import com.webmobrilweatherapp.models.homepage.HomePageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody


class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private var accountRepositories: AccountRepositories
     fun getRegistration(name: String, email: String, password: String, city: String,state: String,country: String,
                         user_type: Int,city_lat:Double,city_long:Double,zipcode:String
    ): LiveData<SignUpResponse?> {
        return accountRepositories.getregistration(name, email, password, city,state,country,user_type,city_lat,city_long,zipcode)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getlogin(email: String, password: String,user_type: String,device_type: String,device_token: String
    ): LiveData<SignInResponse?> {
        return accountRepositories.getlogin(email, password,user_type,device_type,device_token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////


    fun getButterFly(
    ): LiveData<ButterflySpeciesResponse?> {
        return accountRepositories.getButterFly()
    }
    init {
        accountRepositories = AccountRepositories()
    }

    fun getFollowers(token: String
    ): LiveData<FollowersResponse?> {
        return accountRepositories.getFollowers(token)
    }
    init {
        accountRepositories = AccountRepositories()
    }

    fun getFollowings(token: String
    ): LiveData<FollowingResponse?> {
        return accountRepositories.getFollowings(token)
    }
    init {
        accountRepositories = AccountRepositories()
    }


    fun getChallengeByMe(context: Context,token: String
    ): LiveData<ChallengeByMeResponse?> {
        return accountRepositories.getChallenegeByMe(context,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }


    fun getChallengeByFriends(context: Context,token: String
    ): LiveData<ChallengeByFriendsResponse?> {
        return accountRepositories.getChallenegeByFriends(context,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }

    fun getPublicPrivate(toggle:String,token: String
    ): LiveData<PublicPrivateResponse?> {
        return accountRepositories.getPrivatePublic(toggle,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }

    fun getPostById(post_id:String,token: String
    ): LiveData<PostByIdResponse?> {
        return accountRepositories.getPostById(post_id,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }



    fun getSelectButterFly(select_butterfly:String,user_id:String
    ): LiveData<SelectButterFlyResponse?> {
        return accountRepositories.getSelectButterFly(select_butterfly,user_id)
    }
    init {
        accountRepositories = AccountRepositories()
    }


    fun getAcceptChallenge(challenge_id: String,vote_temp_value_by_competitor:String,is_temp_by_competitor:String,precipitation_id_by_competitor:String,token:String
    ): LiveData<ChallengeAcceptResponse?> {
        return accountRepositories.getChallengeAccept(challenge_id,vote_temp_value_by_competitor,is_temp_by_competitor,precipitation_id_by_competitor,token)
    }

    init {
        accountRepositories = AccountRepositories()
    }


    fun getChallengevote(token:String,competitor_id:String,is_temp:String,precipitation_id:String,vote_temp_value:String,vote_date:String,city:String,city_code:String
    ): LiveData<ChallengeVoteResponse?> {
        return accountRepositories.getChallengeVote(token,competitor_id,is_temp,precipitation_id,vote_temp_value,vote_date,city,city_code)
    }
    init {
        accountRepositories = AccountRepositories()
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////

    fun getForgotPass(email:String,user_type: String
    ): LiveData<ForgotPassResponse?> {
        return accountRepositories.getforgot(email,user_type)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getCreateNewPassword(
        email: String,
        new_password: String,
        confirm_password: String
    ): LiveData<CreateNewPassResponse?> {
        return accountRepositories.getCreateNewPassword(email,new_password,confirm_password)
    }
    init {
        accountRepositories = AccountRepositories()
    }

    fun getVerifyOtp(
        email: String,
        otp:String
    ): LiveData<VerifyOtpResponse?> {
        return accountRepositories.getotp(email,otp)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getSignupVerifyOtp(
        email: String,
        otp:String
    ): LiveData<SignUpverifyotpResponse?> {
        return accountRepositories.getSignupVerifyOtp(email,otp)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getTermPrivacy(
        user_type: String,
    ): LiveData<TermPrivacyResponse?> {
        return accountRepositories.getTermPrivacy(user_type)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getHomeapge(
        apikey:String?,
        longitude:String?,
        language:String?,
        details:String?
    ):LiveData<HomePageResponse?>{
        return accountRepositories.getHomeapge(apikey,longitude,language,details)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getalllocation(
        latlng:String,key:String
    ):LiveData<GetallLocationResponse?>{
        return accountRepositories.getalllocation(latlng,key)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getLocationcity(
        apikey:String?,
        longitude:String?,
        language:String?,
        details:String?
    ):LiveData<LocationgetCityResponse?>{
        return accountRepositories.getLocationcity("apikey",longitude,language,details)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getHomeapgesunny(
        key:String?,
        apikey:String?,
        details:String?,
    ):LiveData<HomePageSunnyResponse?>{
        return accountRepositories.getHomeapgesunny(key.toString(),apikey,details)
    }
    init {
        accountRepositories = AccountRepositories()
    }



    fun getTendays(
        key:String?,
        apikey:String?,
    ):LiveData<TendaysWeatherApiResponse?>{
        return accountRepositories.getTendaysWeather(key.toString(),apikey.toString())
    }
    init {
        accountRepositories = AccountRepositories()
    }





    fun getVote(
        is_temp: String, temp_value: String,precipitation_id: String,weatherdate: String,token:String
    ):LiveData<VoteResponse?>{
        return accountRepositories.getVote(is_temp,temp_value,precipitation_id,weatherdate,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }

    fun getCreatePost(
        post_image: List<MultipartBody.Part>, description: RequestBody, token:String
    ):LiveData<CreatePostResponse?>{
        return accountRepositories.getCreatePost(post_image,description,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }


    fun getCreateVideoPost(
        post_video: MultipartBody.Part, description: RequestBody, token:String
    ):LiveData<CreatePostResponse?>{
        return accountRepositories.getCreateVideoPost(post_video,description,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }

    fun getprepration(
        token:String
    ):LiveData<BottomdialogResponse?>{
        return accountRepositories.getprepration(token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getpostimageuser(
        user_id: String,
        token:String
    ):LiveData<GetImagePostuserResponse?>{
        return accountRepositories.getpostimageuser(user_id,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getchangepasswords(
        current_password:String,new_password:String,confirm_password:String,token:String
    ):LiveData<ChangePasswordResponse?>{
        return accountRepositories. getchangepasswords(current_password,new_password,confirm_password,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getupdateprofile(
        name:String,phone:String,email:String,city:String,token:String
    ):LiveData<UpdateProfileResponse?>{
        return accountRepositories. getupdateprofile(name,phone,email,city,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getprofilehomepage(
        user_id:String,page:String,token:String
    ):LiveData<ProfilehomePageResponse?>{
        return accountRepositories. getprofilehomepage(user_id,page,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getuserprofile(
        user_id:String,token:String
    ):LiveData<UserProfileResponse?>{
        return accountRepositories. getuserprofile(user_id,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getpostbyUser(
        user_id:String,page: String,token:String
    ):LiveData<UserPostImagesResponse?>{
        return accountRepositories. getpostbyUser(user_id,page,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }



    fun getupdateprofileImages(
        profile_image: MultipartBody.Part, token:String
    ):LiveData<UpdateProfileImageResponse?>{
        return accountRepositories. getupdateprofileImages(profile_image,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getsearchprofiles(
        user_type:String, token:String
    ):LiveData<UserSearchingResponse?>{
        return accountRepositories. getsearchprofiles(user_type,token);
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getemailverification(
        email: String, user_type: String
    ):LiveData<EmailVerificationResponse?>{
        return accountRepositories.getemailverification(email,user_type)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getcontactus(
        name:String,phone_number:String,email: String,message:String, token:String
    ):LiveData<ContactUsResponse?>{
        return accountRepositories.getcontactus(name,phone_number,email,message,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun gettopfiveUser(
        user_type:String, token:String
    ):LiveData<TopFiveResponse?>{
        return accountRepositories.gettopfiveUser(user_type,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getUservotinglist(
        user_id:String, token:String
    ):LiveData<UserVotingListResponse?>{
        return accountRepositories.getUservotinglist(user_id,token)
    }
    fun getUserfilter(
        user_id:String,filter: String, token:String
    ):LiveData<UserVotingListResponse?>{
        return accountRepositories.getUserfilter(user_id,filter,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getUserflow(
        followed_id:String,event:String, token:String
    ):LiveData<FollowResponse?>{
        return accountRepositories.getUserflow(followed_id,event,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getrlike(
        post_id:String,like:String, token:String
    ):LiveData<LikeResponse?>{
        return accountRepositories.getrlike(post_id,like,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun postcomments(
        post_id:String,comment:String, token:String
    ):LiveData<PostCommentResponse?>{
        return accountRepositories.postcomments(post_id,comment,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getpostUserComment(
        post_id:String, token:String
    ):LiveData<GetCommentResponse?>{
        return accountRepositories.getpostUserComment(post_id,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getchat(
        reciver_id:String, token:String
    ):LiveData<GetChatsResponse?>{
        return accountRepositories.getchat(reciver_id,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getnotification(context:Context,
        token:String
    ):LiveData<NotificationResponse?>{
        return accountRepositories.getnotification(context,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }



    fun getRequestAcceptReject(
        status: String,requestFrom:String,token:String
    ):LiveData<RequestAcceptRejectResponse?>{
        return accountRepositories.getRequestAcceptReject(status,requestFrom,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }




    fun getpostdelete(id: String,
        token:String
    ):LiveData<PostDeleteResponse?>{
        return accountRepositories.getpostdelete(id,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun geteditpost(id: String,description:String,token:String
    ):LiveData<UpdatePostResponse?>{
        return accountRepositories.geteditpost(id,description,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getlogouts(token:String
    ):LiveData<LogoutResponse?>{
        return accountRepositories.getlogouts(token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getmyweathervotelist(filter: String,user_id:String,token:String
    ):LiveData<MyweathervotepersentageResponse?>{
        return accountRepositories.getmyweathervotelist(filter,user_id,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getweathermayorlists(token:String
    ):LiveData<WeatherMayorListResponse?>{
        return accountRepositories.getweathermayorlists(token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getmakingweathervote(t:TextView,context: Context,user_id: String,token:String
    ):LiveData<MakingWeatherResponse?>{
        return accountRepositories.getmakingweathervote(t,context,user_id,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getdeleteaccounts(user_id: String,token:String
    ):LiveData<DeleteAccountResponse?>{
        return accountRepositories.getdeleteaccounts(user_id,token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getViewVoteUser(token:String
    ):LiveData<ViewUserVoteResponse?>{
        return accountRepositories.getViewVoteUser(token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
    fun getusermayorhomepages(token:String
    ):LiveData<MayorHomepageResponse?>{
        return accountRepositories.getusermayorhomepages(token)
    }
    init {
        accountRepositories = AccountRepositories()
    }
}