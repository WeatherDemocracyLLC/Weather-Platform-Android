package com.webmobrilweatherapp.model.challengebyfriends

import com.google.gson.annotations.SerializedName

data class ChallengeByFriendsResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Precipitation(

	@field:SerializedName("preciptions_img")
	val preciptionsImg: String? = null,

	@field:SerializedName("precipitation_name")
	val precipitationName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Compititor1(

	@field:SerializedName("profile_image")
	val profileImage: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class DataItem(

	@field:SerializedName("vote_date")
	val voteDate: String? = null,

	@field:SerializedName("competitor_id")
	val competitorId: Int? = null,

	@field:SerializedName("min_weather")
	val minWeather: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("compititor1")
	val compititor1: Compititor1? = null,

	@field:SerializedName("city_code")
	val cityCode: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("vote_challenge_status")
	val voteChallengeStatus: Any? = null,

	@field:SerializedName("vote_temp_value")
	val voteTempValue: String? = null,

	@field:SerializedName("precipitation")
	val precipitation: Precipitation? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("is_temp")
	val isTemp: Int? = null,

	@field:SerializedName("vote_temp_value_by_competitor")
	val voteTempValueByCompetitor: Any? = null,

	@field:SerializedName("is_temp_by_competitor")
	val isTempByCompetitor: Any? = null,

	@field:SerializedName("precipitation_id")
	val precipitationId: Int? = null,

	@field:SerializedName("is_vote_accurate")
	val isVoteAccurate: Any? = null,

	@field:SerializedName("precipitation_id_by_competitor")
	val precipitationIdByCompetitor: Any? = null,

	@field:SerializedName("max_weather")
	val maxWeather: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("is_challenge_accept")
	val isChallengeAccept: Int? = null
)
