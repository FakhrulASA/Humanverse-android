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

    fun setValue() {
        modelList.clear()
        modelList.add(
            ModelDashboardItem(
                "Car Rent",
                R.drawable.car
            )
        )
        modelList.add(
            ModelDashboardItem(
                "Car Wash",
                R.drawable.car_wash
            )
        )
        modelList.add(
            ModelDashboardItem(
                "Auto Repair",
                R.drawable.auto
            )
        )
        modelList.add(
            ModelDashboardItem(
                "Calender",
                R.drawable.calender
            )
        )
        modelList.add(
            ModelDashboardItem(
                "Charities",
                R.drawable.charities
            )
        )
        modelList.add(
            ModelDashboardItem(
                "Ac Service",
                R.drawable.ac
            )
        )
        listItem.postValue(modelList)

    }
}