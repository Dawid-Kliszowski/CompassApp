package pl.dawidkliszowski.compassapp.screens.main

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor() : MvpPresenter<MainView, MainNavigator>() {

    private val disposables = CompositeDisposable()

    fun onPickPlace() {
        performNavigation {
            disposables += navigateToMapAndPickLocation()
                    .subscribeBy(
                            onSuccess = ::onLocationPicked
                    )
        }
    }

    private fun onLocationPicked(location: Location) {
        performViewAction {
            showsPickedLocation(location)
        }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    override fun saveState(): Parcelable? = null

    override fun restoreState(parcel: Parcelable?) = Unit
}