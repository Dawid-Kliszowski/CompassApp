package pl.dawidkliszowski.compassapp.utils

import android.content.Context
import android.hardware.SensorManager
import io.reactivex.ObservableEmitter
import pl.dawidkliszowski.compassapp.model.Location

private const val LOCATION_FORMAT_DIGITS_AFTER_DOT = 5

fun Context.getSensorManager(): SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

fun <T> ObservableEmitter<T>.safeOnNext(value: T) {
    if (!isDisposed) {
        onNext(value)
    }
}

fun Double.format(digitsAfterDot: Int): String {
    return String.format("%.${digitsAfterDot}f", this)
}

fun Location.getDisplayedText(): String {
    val latitudeString = latitude.format(LOCATION_FORMAT_DIGITS_AFTER_DOT)
    val longitudeString = longitude.format(LOCATION_FORMAT_DIGITS_AFTER_DOT)

    return "$latitudeString  $longitudeString"
}