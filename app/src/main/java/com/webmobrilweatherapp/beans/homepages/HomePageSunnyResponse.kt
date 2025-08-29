package com.webmobrilweatherapp.beans.homepages

import com.google.gson.annotations.SerializedName

class HomePageSunnyResponse:ArrayList<HomePageSunnyResponseItem>()

	/*@field:SerializedName("HomePageSunnyResponse")
	val homePageSunnyResponse: List<HomePageSunnyResponseItem?>? = null
)*/

data class Minimum(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Precip1hr(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Past6HourRange(

	@field:SerializedName("Minimum")
	val minimum: Minimum? = null,

	@field:SerializedName("Maximum")
	val maximum: Maximum? = null
)

data class PastHour(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Speed(
	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class HomePageSunnyResponseItem(

	@field:SerializedName("Wind")
	val wind: Wind? = null,

	@field:SerializedName("Temperature")
	val temperature: Temperature? = null,

	@field:SerializedName("Past24HourTemperatureDeparture")
	val past24HourTemperatureDeparture: Past24HourTemperatureDeparture? = null,

	@field:SerializedName("PressureTendency")
	val pressureTendency: PressureTendency? = null,

	@field:SerializedName("ObstructionsToVisibility")
	val obstructionsToVisibility: String? = null,

	@field:SerializedName("Ceiling")
	val ceiling: Ceiling? = null,

	@field:SerializedName("RealFeelTemperatureShade")
	val realFeelTemperatureShade: RealFeelTemperatureShade? = null,

	@field:SerializedName("EpochTime")
	val epochTime: Int? = null,

	@field:SerializedName("RealFeelTemperature")
	val realFeelTemperature: RealFeelTemperature? = null,

	@field:SerializedName("PrecipitationType")
	val precipitationType: Any? = null,

	@field:SerializedName("HasPrecipitation")
	val hasPrecipitation: Boolean? = null,

	@field:SerializedName("RelativeHumidity")
	val relativeHumidity: Int? = null,

	@field:SerializedName("PrecipitationSummary")
	val precipitationSummary: PrecipitationSummary? = null,

	@field:SerializedName("TemperatureSummary")
	val temperatureSummary: TemperatureSummary? = null,

	@field:SerializedName("LocalObservationDateTime")
	val localObservationDateTime: String? = null,

	@field:SerializedName("UVIndexText")
	val uVIndexText: String? = null,

	@field:SerializedName("WeatherText")
	val weatherText: String? = null,

	@field:SerializedName("CloudCover")
	val cloudCover: Int? = null,

	@field:SerializedName("WindGust")
	val windGust: WindGust? = null,

	@field:SerializedName("UVIndex")
	val uVIndex: Int? = null,

	@field:SerializedName("Precip1hr")
	val precip1hr: Precip1hr? = null,

	@field:SerializedName("WeatherIcon")
	val weatherIcon: Int? = null,

	@field:SerializedName("DewPoint")
	val dewPoint: DewPoint? = null,

	@field:SerializedName("Pressure")
	val pressure: Pressure? = null,

	@field:SerializedName("IsDayTime")
	val isDayTime: Boolean? = null,

	@field:SerializedName("IndoorRelativeHumidity")
	val indoorRelativeHumidity: Int? = null,

	@field:SerializedName("ApparentTemperature")
	val apparentTemperature: ApparentTemperature? = null,

	@field:SerializedName("WetBulbTemperature")
	val wetBulbTemperature: WetBulbTemperature? = null,

	@field:SerializedName("Visibility")
	val visibility: Visibility? = null,

	@field:SerializedName("WindChillTemperature")
	val windChillTemperature: WindChillTemperature? = null,

	@field:SerializedName("Link")
	val link: String? = null,

	@field:SerializedName("MobileLink")
	val mobileLink: String? = null
)

data class TemperatureSummary(

	@field:SerializedName("Past6HourRange")
	val past6HourRange: Past6HourRange? = null,

	@field:SerializedName("Past24HourRange")
	val past24HourRange: Past24HourRange? = null,

	@field:SerializedName("Past12HourRange")
	val past12HourRange: Past12HourRange? = null
)

data class Past12Hours(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Metric(

	@field:SerializedName("UnitType")
	val unitType: Int? = null,

	@field:SerializedName("Value")
	val value: Double? = null,

	@field:SerializedName("Unit")
	val unit: String? = null
)

data class Maximum(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Pressure(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class WindChillTemperature(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class PrecipitationSummary(

	@field:SerializedName("Past6Hours")
	val past6Hours: Past6Hours? = null,

	@field:SerializedName("Precipitation")
	val precipitation: Precipitation? = null,

	@field:SerializedName("Past9Hours")
	val past9Hours: Past9Hours? = null,

	@field:SerializedName("Past3Hours")
	val past3Hours: Past3Hours? = null,

	@field:SerializedName("PastHour")
	val pastHour: PastHour? = null,

	@field:SerializedName("Past18Hours")
	val past18Hours: Past18Hours? = null,

	@field:SerializedName("Past24Hours")
	val past24Hours: Past24Hours? = null,

	@field:SerializedName("Past12Hours")
	val past12Hours: Past12Hours? = null
)

data class Past3Hours(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Ceiling(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Past12HourRange(

	@field:SerializedName("Minimum")
	val minimum: Minimum? = null,

	@field:SerializedName("Maximum")
	val maximum: Maximum? = null

)

data class Past6Hours(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null

)

data class Past24Hours(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null

)

data class RealFeelTemperature(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null

)

data class Direction(

	@field:SerializedName("English")
	val english: String? = null,

	@field:SerializedName("Degrees")
	val degrees: Int? = null,

	@field:SerializedName("Localized")
	val localized: String? = null

)

data class DewPoint(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Past18Hours(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class WetBulbTemperature(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Visibility(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Temperature(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class WindGust(
	@field:SerializedName("Speed")
	val speed: Speed? = null
)

data class PressureTendency(

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("LocalizedText")
	val localizedText: String? = null
)

data class Wind(

	@field:SerializedName("Speed")
	val speed: Speed? = null,

	@field:SerializedName("Direction")
	val direction: Direction? = null
)

data class Precipitation(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class ApparentTemperature(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Past24HourRange(

	@field:SerializedName("Minimum")
	val minimum: Minimum? = null,

	@field:SerializedName("Maximum")
	val maximum: Maximum? = null
)

data class Past24HourTemperatureDeparture(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Past9Hours(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class RealFeelTemperatureShade(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Imperial(

	@field:SerializedName("UnitType")
	val unitType: Int? = null,

	@field:SerializedName("Value")
	val value: Double? = null,

	@field:SerializedName("Unit")
	val unit: String? = null
)
