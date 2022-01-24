package com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Favorite will be implemented"
    }
    val text: LiveData<String> = _text
}