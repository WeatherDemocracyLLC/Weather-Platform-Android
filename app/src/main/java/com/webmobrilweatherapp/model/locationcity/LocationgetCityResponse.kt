package com.webmobrilweatherapp.model.locationcity

import com.google.gson.annotations.SerializedName

data class LocationgetCityResponse(

	@field:SerializedName("LocationgetCityResponse")
	val locationgetCityResponse: List<LocationgetCityResponseItem?>? = null
)

data class Imperial(

	@field:SerializedName("UnitType")
	val unitType: Int? = null,

	@field:SerializedName("Value")
	val value: Int? = null,

	@field:SerializedName("Unit")
	val unit: String? = null
)

data class GeoPosition(

	@field:SerializedName("Elevation")
	val elevation: Elevation? = null,

	@field:SerializedName("Latitude")
	val latitude: Double? = null,

	@field:SerializedName("Longitude")
	val longitude: Double? = null
)

data class LocationgetCityResponseItem(

	@field:SerializedName("AdministrativeArea")
	val administrativeArea: AdministrativeArea? = null,

	@field:SerializedName("LocalizedName")
	val localizedName: String? = null,

	@field:SerializedName("SupplementalAdminAreas")
	val supplementalAdminAreas: List<SupplementalAdminAreasItem?>? = null,

	@field:SerializedName("DataSets")
	val dataSets: List<String?>? = null,

	@field:SerializedName("Rank")
	val rank: Int? = null,

	@field:SerializedName("IsAlias")
	val isAlias: Boolean? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("TimeZone")
	val timeZone: TimeZone? = null,

	@field:SerializedName("Version")
	val version: Int? = null,

	@field:SerializedName("PrimaryPostalCode")
	val primaryPostalCode: String? = null,

	@field:SerializedName("Region")
	val region: Region? = null,

	@field:SerializedName("Country")
	val country: Country? = null,

	@field:SerializedName("GeoPosition")
	val geoPosition: GeoPosition? = null,

	@field:SerializedName("Key")
	val key: String? = null,

	@field:SerializedName("EnglishName")
	val englishName: String? = null
)

data class Elevation(

	@field:SerializedName("Metric")
	val metric: Metric? = null,

	@field:SerializedName("Imperial")
	val imperial: Imperial? = null
)

data class Metric(

	@field:SerializedName("UnitType")
	val unitType: Int? = null,

	@field:SerializedName("Value")
	val value: Int? = null,

	@field:SerializedName("Unit")
	val unit: String? = null
)

data class SupplementalAdminAreasItem(

	@field:SerializedName("LocalizedName")
	val localizedName: String? = null,

	@field:SerializedName("Level")
	val level: Int? = null,

	@field:SerializedName("EnglishName")
	val englishName: String? = null
)

data class TimeZone(

	@field:SerializedName("NextOffsetChange")
	val nextOffsetChange: Any? = null,

	@field:SerializedName("GmtOffset")
	val gmtOffset: Double? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("IsDaylightSaving")
	val isDaylightSaving: Boolean? = null,

	@field:SerializedName("Name")
	val name: String? = null
)

data class Country(

	@field:SerializedName("LocalizedName")
	val localizedName: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("EnglishName")
	val englishName: String? = null
)

data class AdministrativeArea(

	@field:SerializedName("CountryID")
	val countryID: String? = null,

	@field:SerializedName("LocalizedType")
	val localizedType: String? = null,

	@field:SerializedName("LocalizedName")
	val localizedName: String? = null,

	@field:SerializedName("Level")
	val level: Int? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("EnglishType")
	val englishType: String? = null,

	@field:SerializedName("EnglishName")
	val englishName: String? = null
)

data class Region(

	@field:SerializedName("LocalizedName")
	val localizedName: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("EnglishName")
	val englishName: String? = null
)
