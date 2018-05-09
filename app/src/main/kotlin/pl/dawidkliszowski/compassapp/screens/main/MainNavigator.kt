package pl.dawidkliszowski.compassapp.screens.main

import android.app.Activity
import android.content.Intent
import com.google.android.gms.location.places.ui.PlacePicker
import pl.dawidkliszowski.compassapp.screens.base.BaseActivity
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpNavigator
import javax.inject.Inject

private const val PLACE_PICKER_REQUEST_CODE = 1

class MainNavigator @Inject constructor(
        private val activity: Activity
) : MvpNavigator {

    fun navigateToMapAndPickLocation(callback: LocationPickCallback) {
        val placesPickerIntent = PlacePicker.IntentBuilder().build(activity)

        (activity as BaseActivity).registerOnActivityResultCallback { requestCode, resultCode, data ->
            if (requestCode == PLACE_PICKER_REQUEST_CODE) {
                activity.unregisterOnActivityResultCallback()
                handleLocationPickResult(resultCode, data, callback)
            }
        }
        activity.startActivityForResult(placesPickerIntent, PLACE_PICKER_REQUEST_CODE)
    }

    private fun handleLocationPickResult(resultCode: Int, data: Intent?, callback: LocationPickCallback) {
        if (resultCode == Activity.RESULT_OK) {
            val place = PlacePicker.getPlace(activity, data)
            callback.onLocationPicked(place.latLng.latitude, place.latLng.longitude)
        } else {
            callback.onCancel()
        }
    }
}

interface LocationPickCallback {

    fun onLocationPicked(lat: Double, lon: Double)
    fun onCancel()
}