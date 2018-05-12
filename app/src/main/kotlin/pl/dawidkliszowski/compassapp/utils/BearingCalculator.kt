package pl.dawidkliszowski.compassapp.utils

import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.model.LocationMapper
import javax.inject.Inject

class BearingCalculator @Inject constructor(
        private val locationMapper: LocationMapper
) {

    fun calculateBearing(currentLocation: Location, targetLocation: Location): Float {
        val androidLocationFrom = locationMapper.mapToAndroidLocation(currentLocation)
        val androidLocationTo = locationMapper.mapToAndroidLocation(targetLocation)

        return androidLocationFrom.bearingTo(androidLocationTo)
    }

    fun calculateDistanceMeters(currentLocation: Location, targetLocation: Location): Float {
        val androidLocationFrom = locationMapper.mapToAndroidLocation(currentLocation)
        val androidLocationTo = locationMapper.mapToAndroidLocation(targetLocation)

        return androidLocationFrom.distanceTo(androidLocationTo)
    }
}