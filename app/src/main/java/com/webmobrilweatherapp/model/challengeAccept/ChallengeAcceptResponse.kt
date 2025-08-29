package com.webmobrilweatherapp.model.challengeAccept

import com.google.gson.annotations.SerializedName

data class ChallengeAcceptResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("vote_date")
	val voteDate: String? = null,

	@field:SerializedName("competitor_id")
	val competitorId: Int? = null,

	@field:SerializedName("min_weather")
	val minWeather: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("city_code")
	val cityCode: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("vote_challenge_status")
	val voteChallengeStatus: Int? = null,

	@field:SerializedName("vote_temp_value")
	val voteTempValue: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("is_temp")
	val isTemp: Int? = null,

	@field:SerializedName("vote_temp_value_by_competitor")
	val voteTempValueByCompetitor: String? = null,

	@field:SerializedName("precipitation_id")
	val precipitationId: Int? = null,

	@field:SerializedName("is_vote_accurate")
	val isVoteAccurate: Int? = null,

	@field:SerializedName("max_weather")
	val maxWeather: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("is_challenge_accept")
	val isChallengeAccept: Int? = null
)
