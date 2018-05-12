package pl.dawidkliszowski.compassapp.screens.main

import android.os.Parcelable
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpPresenter
import pl.dawidkliszowski.compassapp.utils.AndroidPermissionProvider
import pl.dawidkliszowski.compassapp.utils.CompassUtil
import pl.dawidkliszowski.compassapp.utils.LocationManager
import pl.dawidkliszowski.compassapp.utils.getDisplayedText
import javax.inject.Inject



class MainPresenter @Inject constructor(
        private val compassUtil: CompassUtil,
        private val rxPermissions: RxPermissions,
        private val locationManager: LocationManager
) : MvpPresenter<MainView, MainNavigator>() {

    private val disposables = CompositeDisposable()
    private var pickedLocation: Location? = null
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

    override fun saveState(): Parcelable? = null

    override fun restoreState(parcel: Parcelable?) = Unit

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
        //todo implement
    }

    private fun onTargetLocationPicked(location: Location) {
        pickedLocation = location
        showPickedLocation()
    }

    private fun onCompassAzimuthMeasured(degrees: Float) {
        currentMeasuredAzimuth = degrees
        showMeasuredAzimuth()
    }

    private fun showPickedLocation() {
        pickedLocation?.let {
            performViewAction { showPickedLocation(it.getDisplayedText()) }
        }
    }

    private fun showMeasuredAzimuth() {
        performViewAction {
            showCurrentMeasuredAzimuth(currentMeasuredAzimuth)
        }
    }

    fun disablePickLocationButton() {
        performViewAction { disablePickLocationButton() }
    }

    fun enablePickLocationButton() {
        performViewAction { enablePickLocationButton() }
    }

    fun restoreViewState() {
        showMeasuredAzimuth()
        showPickedLocation()
    }
}