package pl.birski.falldetector.components.interfaces

import android.content.Context

interface LocationTracker {

    fun getLongitude(): Double

    fun getLatitude(): Double

    fun getAddress(): String?

    fun getAddressOrLocation(): String

    fun locationEnabled(): Boolean

    fun showSettingsAlert(context: Context)
}
