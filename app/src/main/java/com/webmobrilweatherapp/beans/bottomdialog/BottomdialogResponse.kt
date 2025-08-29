package com.webmobrilweatherapp.beans.bottomdialog

import com.google.gson.annotations.SerializedName

data class BottomdialogResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class DataItem(

    @field:SerializedName("precipitation_name")
    val precipitationName: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
