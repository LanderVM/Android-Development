package com.example.parkinggent

import android.app.Application
import com.example.parkinggent.data.AppContainer
import com.example.parkinggent.data.DefaultAppContainer

/**
 * The Application class for the Parking Gent app.
 * Initializes the dependency injection container.
 */
class ParkingApplication : Application() {
    lateinit var container: AppContainer

    /**
     * Called when the application is starting, before any other application objects have been created.
     */
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}