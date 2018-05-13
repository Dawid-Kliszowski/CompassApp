package pl.dawidkliszowski.compassapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationParcel(
        val latitude: Double,
        val longitude: Double
) : Parcelable