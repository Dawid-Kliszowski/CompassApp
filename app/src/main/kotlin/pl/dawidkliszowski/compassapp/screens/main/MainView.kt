package pl.dawidkliszowski.compassapp.screens.main

import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpView

interface MainView : MvpView {

    fun showsPickedLocation(location: Location)
}