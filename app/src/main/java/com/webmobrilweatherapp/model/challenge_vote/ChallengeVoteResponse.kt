package com.webmobrilweatherapp.model.challenge_vote

import com.google.gson.annotations.SerializedName

data class ChallengeVoteResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
