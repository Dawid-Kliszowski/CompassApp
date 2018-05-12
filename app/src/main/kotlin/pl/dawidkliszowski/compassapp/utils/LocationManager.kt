package pl.dawidkliszowski.compassapp.utils

import android.annotation.SuppressLint
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import io.reactivex.Observable
import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.model.LocationMapper
import javax.inject.Inject

private const val LOCATION_REQUEST_INTERVAL_MILLIS = 5000L

class LocationManager @Inject constructor(
        private val rxLocation: RxLocation,
        private val locationMapper: LocationMapper
) {

    private val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(LOCATION_REQUEST_INTERVAL_MILLIS)

    @SuppressLint("MissingPermission")
    fun observeLocation(): Observable<Location> {
        return rxLocation.location()
                .updates(locationRequest)
                .map(locationMapper::locationToDomain)
    }
}