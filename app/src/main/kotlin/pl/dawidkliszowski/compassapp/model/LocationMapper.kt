package pl.dawidkliszowski.compassapp.model

import com.google.android.gms.maps.model.LatLng
import pl.dawidkliszowski.compassapp.utils.format
import javax.inject.Inject

class LocationMapper @Inject constructor() {

    fun toDomain(latLng: LatLng): Location {
        return Location(latLng.latitude, latLng.longitude)
    }
}