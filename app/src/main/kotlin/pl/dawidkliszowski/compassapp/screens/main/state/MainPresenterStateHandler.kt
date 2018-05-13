package pl.dawidkliszowski.compassapp.screens.main.state

import android.os.Parcelable
import pl.dawidkliszowski.compassapp.model.LocationMapper
import javax.inject.Inject

class MainPresenterStateHandler @Inject constructor(
        private val locationMapper: LocationMapper
) {

    fun saveState(mainPresenterState: MainPresenterState): Parcelable {
        with(mainPresenterState) {
            return MainPresenterStateParcel(
                    pickedLocation?.let { locationMapper.toParcel(it) },
                    currentSelfLocation?.let { locationMapper.toParcel(it) },
                    currentMeasuredAzimuth
            )
        }
    }

    fun restoreState(parcel: Parcelable?): MainPresenterState? {
        return parcel?.let {
            with(parcel as MainPresenterStateParcel) {
                return MainPresenterState(
                        locationMapper.fromParcel(pickedLocationParcel),
                        locationMapper.fromParcel(currentSelfLocationParcel),
                        currentMeasuredAzimuth
                )
            }
        }
    }
}