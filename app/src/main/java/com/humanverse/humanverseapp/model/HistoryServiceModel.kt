package com.humanverse.humanverseapp.model

data class HistoryServiceModel(
    var title: String,
    var price : Double,
    var status : Int,
    var datetime: String,
    var serviceId: String,
    var contact: String,
    var orderId:String
)
