package com.example.parkinggent.model

data class Parking(
    val title: String = "",
    val location: String = "",
    val used: Int = -1,
    val total: Int = -1,
    val description: String = "",
    val cords: String = "",
    val openingTime: String = "",
    val open: Boolean = false,
    val pay : Boolean = true,
    val phoneNumber: String = "",
    val moreInfo: String = ""
)