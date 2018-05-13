package pl.dawidkliszowski.compassapp.screens.main.state

import pl.dawidkliszowski.compassapp.model.Location

data class MainPresenterState(
        val pickedLocation: Location?,
        val currentSelfLocation: Location?,
        val currentMeasuredAzimuth: Float
)