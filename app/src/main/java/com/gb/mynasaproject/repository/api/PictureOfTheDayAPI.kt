package com.gb.mynasaproject.repository.api

import com.gb.mynasaproject.repository.response.EarthPhotoResponseData
import com.gb.mynasaproject.repository.response.MarsPhotosServerResponseData
import com.gb.mynasaproject.repository.response.PictureOfTheDayResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<PictureOfTheDayResponseData>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsPhoto(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>

    @GET("EPIC/api/natural")
    fun getEPIC(
        @Query("api_key") apiKey: String,
    ): Call<List<EarthPhotoResponseData>>
}
