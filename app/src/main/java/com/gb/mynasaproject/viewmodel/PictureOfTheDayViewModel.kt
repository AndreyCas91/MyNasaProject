package com.gb.mynasaproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.mynasaproject.BuildConfig
import com.gb.mynasaproject.repository.PictureOfTheDayRetrofitImpl
import java.text.SimpleDateFormat
import java.util.*


class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl(),
    private val callback: GeneralCallback = GeneralCallback(liveDataForViewToObserve)
) : ViewModel() {
    fun getData(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    fun sendServerRequest(date : String) {
        liveDataForViewToObserve.value = AppState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = AppState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl(apiKey, date, callback.callbackDay)
        }
    }

    fun getMarsPicture() {
        liveDataForViewToObserve.value = AppState.Loading(0)
        val earthDate : String = getDayBeforeYesterday(-2)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()){
            liveDataForViewToObserve.value = AppState.Error(Throwable("wrong key"))
        } else{
            retrofitImpl.getMarsRetrofitImpl(earthDate, apiKey, callback.marsCallback)
        }
    }

    fun getEpic() {
        liveDataForViewToObserve.value = AppState.Loading(0)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = AppState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getEPIC(apiKey, callback.epicCallback)
        }
    }

    private fun getDayBeforeYesterday(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }
}