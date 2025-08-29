package com.webmobrilweatherapp.model.TendaysWeatherApi

import com.google.gson.annotations.SerializedName

data class TendaysWeatherApiResponse(

	@field:SerializedName("Headline")
	val headline: Headline? = null,

	@field:SerializedName("DailyForecasts")
	val dailyForecasts: List<DailyForecastsItem?>? = null
)

data class Headline(

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("EndEpochDate")
	val endEpochDate: Any? = null,

	@field:SerializedName("EffectiveEpochDate")
	val effectiveEpochDate: Int? = null,

	@field:SerializedName("Severity")
	val severity: Int? = null,

	@field:SerializedName("Text")
	val text: String? = null,

	@field:SerializedName("EndDate")
	val endDate: Any? = null,

	@field:SerializedName("Link")
	val link: String? = null,

	@field:SerializedName("EffectiveDate")
	val effectiveDate: String? = null,

	@field:SerializedName("MobileLink")
	val mobileLink: String? = null
)

data class Temperature(

	@field:SerializedName("Minimum")
	val minimum: Minimum? = null,

	@field:SerializedName("Maximum")
	val maximum: Maximum? = null
)

data class Minimum(

	@field:SerializedName("UnitType")
	val unitType: Int? = null,

	@field:SerializedName("Value")
	val value: Double? = null,

	@field:SerializedName("Unit")
	val unit: String? = null
)

data class Day(

	@field:SerializedName("HasPrecipitation")
	val hasPrecipitation: Boolean? = null,

	@field:SerializedName("IconPhrase")
	val iconPhrase: String? = null,

	@field:SerializedName("Icon")
	val icon: Int? = null
)

data class Maximum(

	@field:SerializedName("UnitType")
	val unitType: Int? = null,

	@field:SerializedName("Value")
	val value: Double? = null,

	@field:SerializedName("Unit")
	val unit: String? = null
)

data class Night(

	@field:SerializedName("HasPrecipitation")
	val hasPrecipitation: Boolean? = null,

	@field:SerializedName("IconPhrase")
	val iconPhrase: String? = null,

	@field:SerializedName("Icon")
	val icon: Int? = null
)

data class DailyForecastsItem(

	@field:SerializedName("Temperature")
	val temperature: Temperature? = null,

	@field:SerializedName("Night")
	val night: Night? = null,

	@field:SerializedName("EpochDate")
	val epochDate: Int? = null,

	@field:SerializedName("Day")
	val day: Day? = null,

	@field:SerializedName("Sources")
	val sources: List<String?>? = null,

	@field:SerializedName("Date")
	val date: String? = null,

	@field:SerializedName("Link")
	val link: String? = null,

	@field:SerializedName("MobileLink")
	val mobileLink: String? = null
)
