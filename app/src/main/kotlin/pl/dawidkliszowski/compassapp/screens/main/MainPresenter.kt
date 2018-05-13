package pl.dawidkliszowski.compassapp.screens.main

import android.os.Parcelable
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.dawidkliszowski.compassapp.R
import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpPresenter
import pl.dawidkliszowski.compassapp.screens.main.state.MainPresenterState
import pl.dawidkliszowski.compassapp.screens.main.state.MainPresenterStateHandler
import pl.dawidkliszowski.compassapp.utils.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val COMPASS_MEASUREMENTS_BUFFERING_TIME_MILLIS = 300L

class MainPresenter @Inject constructor(
        private val compassUtil: CompassUtil,
        private val rxPermissions: RxPermissions,
        private val locationManager: LocationManager,
        private val bearingCalculator: BearingCalculator,
        private val stringProvider: StringProvider,
        private val mainPresenterStateHandler: MainPresenterStateHandler
) : MvpPresenter<MainView, MainNavigator>() {

    private val disposables = CompositeDisposable()
    private var pickedLocation: Location? = null
    private var currentSelfLocation: Location? = null
    private var currentMeasuredAzimuth: Float = 0f

    init {
        initCompassSensorsObserving()
        initPositionObserving()
    }

    override fun attachView(view: MainView) {
        super.attachView(view)
        restoreViewState()
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    override fun saveState(): Parcelable? {
        return mainPresenterStateHandler.saveState(
                MainPresenterState(pickedLocation, currentSelfLocation, currentMeasuredAzimuth)
        )
    }

    override fun restoreState(parcel: Parcelable?) {
        val mainPresenterState = mainPresenterStateHandler.restoreState(parcel)
        mainPresenterState?.let {
            this.pickedLocation = it.pickedLocation
            this.currentSelfLocation = it.currentSelfLocation
            this.currentMeasuredAzimuth = it.currentMeasuredAzimuth
        }
    }

    fun onPickPlace() {
        performNavigation {
            disposables += navigateToMapAndPickLocation()
                    .doOnSubscribe { disablePickLocationButton() }
                    .doOnEvent { _, _ -> enablePickLocationButton() }
                    .subscribeBy(
                            onSuccess = ::onTargetLocationPicked
                    )
        }
    }

    private fun initCompassSensorsObserving() {
        disposables += compassUtil.observeCompassAzimuth()
                .buffer(COMPASS_MEASUREMENTS_BUFFERING_TIME_MILLIS, TimeUnit.MILLISECONDS)
                .filter { it.isNotEmpty() }
                .map { it.average().toFloat() }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = ::onCompassAzimuthMeasured
                )
    }

    private fun initPositionObserving() {
        disposables += rxPermissions.request(AndroidPermissionProvider.COARSE_LOCATION_PERMISSION)
                .switchMap { granted ->
                    if (granted) {
                        locationManager.observeLocation()
                    } else {
                        Observable.empty()
                    }
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = ::onSelfLocationUpdate
                )
    }

    private fun onSelfLocationUpdate(location: Location) {
        currentSelfLocation = location
        updateCurrentBearingAndDistance()
        showSelfLocation()
    }

    private fun updateCurrentBearingAndDistance() {
        showCurrentBearing()
        showCurrentDistance()
    }

    private fun onTargetLocationPicked(location: Location) {
        pickedLocation = location
        updateCurrentBearingAndDistance()
        showPickedLocation()
    }

    private fun onCompassAzimuthMeasured(degrees: Float) {
        currentMeasuredAzimuth = degrees
        showMeasuredAzimuth()
    }

    private fun disablePickLocationButton() {
        performViewAction { disablePickLocationButton() }
    }

    private fun enablePickLocationButton() {
        performViewAction { enablePickLocationButton() }
    }

    private fun restoreViewState() {
        showSelfLocation()
        showCurrentBearing()
        showCurrentDistance()
        showPickedLocation()
        showMeasuredAzimuth()
    }

    private fun showSelfLocation() {
        currentSelfLocation?.let {
            performViewAction { showSelfLocation(it.getDisplayedText()) }
        }
    }

    private fun showCurrentBearing() {
        if (currentSelfLocation != null && pickedLocation != null) {
            val bearing = bearingCalculator.calculateBearing(currentSelfLocation!!, pickedLocation!!)
            performViewAction { showCurrentTargetBearing(bearing) }
        }
    }

    private fun showCurrentDistance() {
        if (currentSelfLocation != null && pickedLocation != null) {
            val distance = bearingCalculator.calculateDistanceMeters(currentSelfLocation!!, pickedLocation!!)
            val distanceString = stringProvider.getString(R.string.screen_main_formatted_distance_meters, distance)
            performViewAction { showCurrentDistance(distanceString)}
        }
    }

    private fun showPickedLocation() {
        pickedLocation?.let {
            performViewAction { showTargetLocation(it.getDisplayedText()) }
        }
    }

    private fun showMeasuredAzimuth() {
        performViewAction { showCurrentMeasuredAzimuth(currentMeasuredAzimuth) }
    }
}