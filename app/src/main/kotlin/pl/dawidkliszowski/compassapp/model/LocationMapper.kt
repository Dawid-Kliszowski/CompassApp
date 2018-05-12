package pl.dawidkliszowski.compassapp.model

import com.google.android.gms.maps.model.LatLng
import pl.dawidkliszowski.compassapp.utils.format
import javax.inject.Inject

class LocationMapper @Inject constructor() {

    fun latlngtoDomain(latLng: LatLng): Location {
        return Location(latLng.latitude, latLng.longitude)
    }

    fun locationToDomain(location: android.location.Location): Location {
        return Location(location.latitude, location.longitude)
    }
}