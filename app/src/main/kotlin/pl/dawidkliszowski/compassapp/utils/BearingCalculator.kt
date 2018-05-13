package pl.dawidkliszowski.compassapp.utils

import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.model.LocationMapper
import javax.inject.Inject

class BearingCalculator @Inject constructor(
        private val locationMapper: LocationMapper
) {

    fun calculateBearing(currentLocation: Location, targetLocation: Location): Float {
        val androidLocationFrom = locationMapper.toAndroidLocation(currentLocation)
        val androidLocationTo = locationMapper.toAndroidLocation(targetLocation)

        return androidLocationFrom.bearingTo(androidLocationTo)
    }

    fun calculateDistanceMeters(currentLocation: Location, targetLocation: Location): Float {
        val androidLocationFrom = locationMapper.toAndroidLocation(currentLocation)
        val androidLocationTo = locationMapper.toAndroidLocation(targetLocation)

        return androidLocationFrom.distanceTo(androidLocationTo)
    }
}