package pl.dawidkliszowski.compassapp.data

import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class LocationMapper @Inject constructor() {

    fun latlngtoDomain(latLng: LatLng): Location {
        return Location(latLng.latitude, latLng.longitude)
    }

    fun locationToDomain(location: android.location.Location): Location {
        return Location(location.latitude, location.longitude)
    }

    fun toAndroidLocation(location: Location): android.location.Location {
        return android.location.Location(LocationManager.GPS_PROVIDER).apply {
            latitude = location.latitude
            longitude = location.longitude
        }
    }

    fun toParcel(location: Location?): LocationParcel? {
        return location?.let { LocationParcel(it.latitude, it.longitude) }
    }

    fun fromParcel(locationParcel: LocationParcel?): Location? {
        return locationParcel?.let { Location(it.latitude, it.longitude) }
    }
}