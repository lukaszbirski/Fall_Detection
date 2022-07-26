package pl.birski.android.architecture.blueprints.todoapp.fakes

import android.content.Context
import pl.birski.android.architecture.blueprints.todoapp.components.interfaces.LocationTracker

class LocationTrackerFake : LocationTracker {
    override fun getLongitude() = 20.9854009598633

    override fun getLatitude() = 52.22824846991743

    override fun getAddress() = "Broadway, New York City, NY 10036 (Manhattan), United States"

    override fun getAddressOrLocation() = getAddress()

    override fun locationEnabled() = true

    override fun showSettingsAlert(context: Context) { }
}
