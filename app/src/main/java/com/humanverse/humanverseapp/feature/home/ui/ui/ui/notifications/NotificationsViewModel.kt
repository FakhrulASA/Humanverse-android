package com.humanverse.humanverseapp.feature.home.ui.ui.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Profile will be implemented"
    }
    val text: LiveData<String> = _text
}