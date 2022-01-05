package com.gb.mynasaproject.viewmodel

import androidx.lifecycle.MutableLiveData
import com.gb.mynasaproject.repository.response.EarthPhotoResponseData
import com.gb.mynasaproject.repository.response.MarsPhotosServerResponseData
import com.gb.mynasaproject.repository.response.PictureOfTheDayResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GeneralCallback(
    private val liveDataForViewToObserve: MutableLiveData<AppState>
) {

    val marsCallback = object : Callback<MarsPhotosServerResponseData> {
        override fun onResponse(
            call: Call<MarsPhotosServerResponseData>,
            response: Response<MarsPhotosServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserve.value = AppState.SuccessMars(response.body()!!)
            } else {
                liveDataForViewToObserve.value =
                    AppState.Error(Throwable("Ошибка при получении ответа"))
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            liveDataForViewToObserve.value = AppState.Error(Throwable("Ошибка при запросе $t"))
        }

    }

    val callbackDay = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserve.value = AppState.SuccessDay(response.body()!!)
            } else {
                liveDataForViewToObserve.value =
                    AppState.Error(Throwable("Ошибка при получении ответа"))
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            liveDataForViewToObserve.value = AppState.Error(Throwable("Ошибка при запросе $t"))
        }

    }

    val epicCallback = object : Callback<List<EarthPhotoResponseData>> {

        override fun onResponse(
            call: Call<List<EarthPhotoResponseData>>,
            response: Response<List<EarthPhotoResponseData>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserve.postValue(AppState.SuccessEarthEpic(response.body()!!))
            } else {

                liveDataForViewToObserve.value =
                    AppState.Error(Throwable("Ошибка при получении ответа"))
            }


            fun onFailure(call: Call<EarthPhotoResponseData>, t: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(Throwable("Ошибка при запросе $t"))
            }
        }

        override fun onFailure(call: Call<List<EarthPhotoResponseData>>, t: Throwable) {
            liveDataForViewToObserve.value = AppState.Error(Throwable("Ошибка при запросе $t"))
        }
    }


}