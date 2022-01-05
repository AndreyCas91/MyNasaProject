package com.gb.mynasaproject.viewmodel

import com.gb.mynasaproject.repository.response.EarthPhotoResponseData
import com.gb.mynasaproject.repository.response.MarsPhotosServerResponseData
import com.gb.mynasaproject.repository.response.PictureOfTheDayResponseData

sealed class AppState {
    data class SuccessDay(val pictureOfTheDayResponseData: PictureOfTheDayResponseData):
        AppState()
    data class SuccessMars(val marsPhotosServerResponseData : MarsPhotosServerResponseData):
        AppState()
    data class SuccessEarthEpic(val earthPhotoResponseData: List<EarthPhotoResponseData>):
        AppState()
    data class Loading(val progress: Int?): AppState()
    data class Error(val error: Throwable): AppState()
}