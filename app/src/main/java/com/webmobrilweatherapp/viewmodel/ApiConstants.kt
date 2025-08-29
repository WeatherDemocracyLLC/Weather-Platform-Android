package com.webmobrilweatherapp.viewmodel

class ApiConstants {

    companion object {
        //BASE URL
        const val BASE_URL = "https://weatherdemocracy.com/api/"

        const val IMAGE_URL = "https://weatherdemocracy.com/storage/app/"
        const val im_url="https://weatherdemocracy.com/"
        const val IMAGE_URL_VOTING_LIST = "https://weatherdemocracy.com/"
        const val LOCATION_BASE_URL = "https://dataservice.accuweather.com/"
        const val API_SIGN_UP = "register"
        const val API_LOGIN = "login"
        const val API_VERIFY_OTP = "verify_forgot_otp"
        const val VerfiyOtp = "verify_otp"
        const val API_FORGOT_PASSWORD = "forgot_password"
        const val API_RESET_PASSWORD = "reset_password"
        const val API_GET_STATIC_CONTENT = "get_static_content"
        const val Homepage = "locations/v1/cities/geoposition/search"
        const val LocationCity = "locations/v1/cities/search"
        const val Homepages = "currentconditions/v1/{key}"
        const val getvote = "vote"
        const val getCreatepost = "create_post"
        const val getprecipitations = "precipitations"
        const val getpostimageUser = "get_post_images_by_user"
        const val getchangepassword = "change-password"
        const val getupdateprofile = "update_profile"
        const val getprofilehomepage = "home-posts-paginate"
        const val getuserprofile = "front-user-profile"
        const val getupdateprofileImage = "update_profile_image"
        const val getsearchprofile = "type-of-users"
        const val getView_Vote = "view_votes"


        const val getemailverification = "email_verfiy"
        const val getpostbyuser = "get_post_by_user"
        const val getuservotingList = "my-weather-vote"
        const val getuserflow = "follow-unfollow"
        const val getcontactUs = "contact-us"
        const val getTopfive = "top-five"
        const val getlike = "post-like"
        const val getcomment = "post-comment"
        const val getPostcomment = "get-post-comment"
        const val getchat = "get-chat"
        const val getnotification = "pushnofification"
        const val getfilter = "my-weather-vote"
        const val getpostdeletes = "delete-post/{version}"
        const val getupdatepost = "edit-post/{version}"
        const val getWeatherAlert="get-alerts"

        // const val getmayorlist = "mayor-list"


        const val getButterFlySpeices="butterfly-badges"
        const val getFollowers="follower-list"
        const val getPostById="post-by-id"
        const val getFollowings="following-list"
        const val getSelectButterfly="select-butterfly"
        const val getChallengeByMe="challenge-by-me-list"
        const val getChallengeVote="challenge-vote"
        const val getChallengeByFriends="challenge-by-friends-list"
        const val getPrivatePublic="public-private-option"
        const val getChallengeAccept="challenge-accept"
        const val getTendaysApi="http://dataservice.accuweather.com/forecasts/v1/daily/10day/{key}"
        const val getRequestAcceptReject="follow-request-accept-reject"


        const val getlogout = "logout"
        const val getmyweathervote = "my-weather-vote-list"
        const val getweathermayorlist = "mayor-list"
        const val getmakingweather = "me-as-mayor"
        const val getdeleteaccount = "account-delete"
        const val getusermayorhomepage = "mobile-home-mayor"
        const val getsendalert = "push"

        //trems condition
        const val TermsCondition = "https://weatherdemocracy.com/term_conditions_application"
        const val privacyPolicy = "https://weatherdemocracy.com/privacy_policy_application"
        const val AboutUs = "https://weatherdemocracy.com/about_us"

        //// metrologistBaseUrl
        const val BASE_URLS = "https://weatherdemocracy.com/api/"
        const val API_SIGN_UP_METROLOGIST = "meteorologist_register"
        const val API_LOGIN_METROLOGIST = "meteorologist_login"
        const val API_FORGOT_METROLOGIST = "meteorologist_forgot_password"
        const val API_VERIFY_OTP_METROLOGIST = "meteorologist_verify_forgot_otp"
        const val API_RESET_PASSWORD_METROLOGIST = "meteorologist_reset_password"
        const val API_EMAIL_VERIFICATION_METROLOGIST = "meteorologist_email_verfiy"
        const val API_SIGN_VERIFY_OTP_METROLOGIST = "meteorologist_verify_otp"



        ///new api's
        //const val BASE_URL_NEWS = "https://bing-news-search1.p.rapidapi.com/"
        const val BASE_URL_NEWS = "https://weather338.p.rapidapi.com/"
        const val API_NEW_USER = "news/list?offset=0&limit=10"

    }
}