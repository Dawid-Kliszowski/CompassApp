package pl.dawidkliszowski.compassapp.screens.main.state

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.dawidkliszowski.compassapp.model.LocationParcel

@Parcelize
data class MainPresenterStateParcel(
        val pickedLocationParcel: LocationParcel?,
        val currentSelfLocationParcel: LocationParcel?,
        val currentMeasuredAzimuth: Float
) : Parcelable