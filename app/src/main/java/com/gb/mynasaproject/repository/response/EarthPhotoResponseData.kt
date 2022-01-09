package com.gb.mynasaproject.repository.response

import com.google.gson.annotations.SerializedName

data class EarthPhotoResponseData (
    @field:SerializedName("identifier") val identifier: String?,
    @field:SerializedName("caption") val caption: String?,
    @field:SerializedName("image") val image: String?,
    @field:SerializedName("version") val version: String?,
    @field:SerializedName("date") val earth_date: String?
        )