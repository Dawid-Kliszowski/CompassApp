package pl.dawidkliszowski.compassapp.utils

import android.content.Context
import android.hardware.SensorManager
import io.reactivex.ObservableEmitter

fun Context.getSensorManager(): SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

fun <T> ObservableEmitter<T>.safeOnNext(value: T) {
    if (!isDisposed) {
        onNext(value)
    }
}