package pl.birski.falldetector.components.implementations

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.widget.Toast
import pl.birski.falldetector.R
import pl.birski.falldetector.components.interfaces.LocationTracker
import java.util.Locale
import javax.inject.Inject

class LocationTrackerImpl @Inject constructor(
    private val context: Context
) : LocationTracker, Service(), LocationListener {

    internal var location: Location? = null

    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private lateinit var locationManager: LocationManager
    private var locationNetwork: Location? = null
    private var locationGPS: Location? = null
    private var checkGPS = false
    private var checkNetwork = false

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        try {
            locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

            checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!checkGPS && !checkNetwork) {
                Toast.makeText(
                    context,
                    context.getString(R.string.location_tracker_toast_text),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                checkGPS.takeIf { it }.let {
                    if (checkGPS) {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            this
                        )
                        locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).also {
                            it?.let {
                                latitude = it.latitude
                                longitude = it.longitude
                            }
                        }
                    }
                }

                checkNetwork.takeIf { it }.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this
                    )
                    locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).also {
                        it?.let {
                            latitude = it.latitude
                            longitude = it.longitude
                        }
                    }
                }

                location = locationNetwork

                location = selectLocation(
                    locationByGps = locationGPS,
                    locationByNetwork = locationNetwork
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getLongitude(): Double {
        location?.let { longitude = it.longitude }
        return longitude
    }

    override fun getLatitude(): Double {
        location?.let { latitude = it.latitude }
        return latitude
    }

    override fun locationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun selectLocation(locationByGps: Location?, locationByNetwork: Location?): Location? {
        return when {
            locationByGps != null && locationByNetwork != null ->
                selectMoreAccurateLocation(locationByGps, locationByNetwork)

            locationByGps != null && locationByNetwork == null -> locationByGps

            locationByGps == null && locationByNetwork != null -> locationByNetwork

            else -> null
        }
    }

    private fun selectMoreAccurateLocation(
        locationByGps: Location,
        locationByNetwork: Location
    ) = if (locationByGps.accuracy > locationByNetwork.accuracy) {
        locationByGps
    } else {
        locationByNetwork
    }

    override fun showSettingsAlert(context: Context) {
        AlertDialog.Builder(context).create().let {
            it.setTitle(context.getString(R.string.location_tracker_dialog_title_text))
            it.setMessage(context.getString(R.string.location_tracker_dialog_message_text))
            it.setButton(
                AlertDialog.BUTTON_POSITIVE,
                context.getString(R.string.location_tracker_dialog_yes_text)
            ) { _, _ ->
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).let { it ->
                    context.startActivity(it)
                }
            }
            it.setButton(
                AlertDialog.BUTTON_NEGATIVE,
                context.getString(R.string.location_tracker_dialog_no_text)
            ) { dialog, _ ->
                dialog.cancel()
            }
            it.show()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onLocationChanged(loc: Location) {
        when (loc.provider) {
            LocationManager.NETWORK_PROVIDER -> locationNetwork?.let {
                it.latitude = loc.latitude
                it.longitude = loc.longitude
            }

            LocationManager.GPS_PROVIDER -> locationGPS?.let {
                it.latitude = loc.latitude
                it.longitude = loc.longitude
            }
        }

        location = selectLocation(
            locationByGps = locationGPS,
            locationByNetwork = locationNetwork
        )
    }

    override fun getAddress(): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)

        return addresses
            .takeIf { it.isNotEmpty() }
            ?.first()
            ?.getAddressLine(0)
            ?.substringBeforeLast(',')
    }

    override fun getAddressOrLocation(): String {
        return getAddress() ?: "${getLatitude()}, ${getLongitude()}"
    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
    override fun onProviderEnabled(s: String) {}
    override fun onProviderDisabled(s: String) {}

    companion object {
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES = 10f
        private const val MIN_TIME_BW_UPDATES: Long = 500
    }

    init {
        getLocation()
    }
}
