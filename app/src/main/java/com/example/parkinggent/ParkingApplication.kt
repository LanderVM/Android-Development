package com.example.parkinggent

import android.app.Application
import com.example.parkinggent.data.AppContainer
import com.example.parkinggent.data.DefaultAppContainer

class ParkingApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}