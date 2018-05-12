package pl.dawidkliszowski.compassapp.model

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

    fun mapToAndroidLocation(location: Location): android.location.Location {
        return android.location.Location(LocationManager.GPS_PROVIDER).apply {
            latitude = location.latitude
            longitude = location.longitude
        }
    }
}