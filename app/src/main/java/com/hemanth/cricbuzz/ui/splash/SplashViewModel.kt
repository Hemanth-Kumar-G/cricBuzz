package com.hemanth.cricbuzz.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hemanth.cricbuzz.common.Constants
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *<h1>SplashViewModel</h1>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _time = MutableLiveData<Boolean>()
    val routeEvent: LiveData<Boolean> = _time

    fun initStart() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(Constants.SPLASH_DELAY_MILLIS)
            postRouteEvent(true)
        }
    }

    private fun postRouteEvent(isTime: Boolean) {
        _time.postValue(isTime)
    }
}