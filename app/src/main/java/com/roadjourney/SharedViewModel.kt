package com.roadjourney

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _accessToken = MutableLiveData<String>()
    val accessToken: LiveData<String> get() = _accessToken

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> get() = _userId

    fun setUserData(token: String, id: Int) {
        _accessToken.value = token
        _userId.value = id
    }
}
