package pl.dawidkliszowski.compassapp.screens.main

import android.os.Parcelable
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor() : MvpPresenter<MainView, MainNavigator>() {

    fun onPickPlace() {
        performNavigation {
            navigateToMapAndPickLocation(object : LocationPickCallback {

                override fun onLocationPicked(lat: Double, lon: Double) {
                    //todo implement
                }

                override fun onCancel() {
                    //todo implement
                }
            })
        }
    }

    override fun saveState(): Parcelable? = null

    override fun restoreState(parcel: Parcelable?) = Unit
}