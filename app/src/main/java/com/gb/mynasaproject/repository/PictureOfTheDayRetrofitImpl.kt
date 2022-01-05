package com.gb.mynasaproject.repository

import com.gb.mynasaproject.repository.api.PictureOfTheDayAPI
import com.gb.mynasaproject.repository.response.EarthPhotoResponseData
import com.gb.mynasaproject.repository.response.MarsPhotosServerResponseData
import com.gb.mynasaproject.repository.response.PictureOfTheDayResponseData
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureOfTheDayRetrofitImpl {

    companion object {
        private val baseUrl = "https://api.nasa.gov/"
    }

    private val api by lazy {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        retrofit.create(PictureOfTheDayAPI::class.java)
    }



    fun getRetrofitImpl(api_key: String, data: String, callbackDay: Callback<PictureOfTheDayResponseData>) {
        return api.getPictureOfTheDay(api_key, data).enqueue(callbackDay)
    }

        fun getMarsRetrofitImpl(data: String, api_key: String, callBackMars: Callback<MarsPhotosServerResponseData>) {
        return api.getMarsPhoto(data, api_key).enqueue(callBackMars)
    }

    fun getEPIC(apiKey: String, epicCallback: Callback<List<EarthPhotoResponseData>>) {
        api.getEPIC(apiKey).enqueue(epicCallback)
    }


}


