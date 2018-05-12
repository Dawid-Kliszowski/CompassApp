package pl.dawidkliszowski.compassapp.screens.main

import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpView

interface MainView : MvpView {

    fun showPickedLocation(location: String)

    fun showCurrentMeasuredAzimuth(degrees: Float)

    fun showCurrentTargetBearing(degrees: Float)

    fun disablePickLocationButton()

    fun enablePickLocationButton()
}