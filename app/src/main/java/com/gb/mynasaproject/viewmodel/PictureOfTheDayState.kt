package com.gb.mynasaproject.viewmodel

import com.gb.mynasaproject.repository.PictureOfTheDayResponseData

sealed class PictureOfTheDayState {
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData):
        PictureOfTheDayState()
    data class Loading(val progress: Int?): PictureOfTheDayState()
    data class Error(val error: Throwable): PictureOfTheDayState()
}