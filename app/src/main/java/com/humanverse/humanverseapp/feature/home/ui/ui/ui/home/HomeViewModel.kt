package com.humanverse.humanverseapp.feature.home.ui.ui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.model.ModelDashboardItem

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    var listItem: MutableLiveData<MutableList<ModelDashboardItem>> = MutableLiveData()
    private var modelList: MutableList<ModelDashboardItem> = mutableListOf()


}