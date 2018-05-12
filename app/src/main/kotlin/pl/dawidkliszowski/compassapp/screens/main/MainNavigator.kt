package pl.dawidkliszowski.compassapp.screens.main

import android.app.Activity
import android.content.Intent
import com.google.android.gms.location.places.ui.PlacePicker
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter
import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.model.LocationMapper
import pl.dawidkliszowski.compassapp.screens.base.BaseActivity
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpNavigator
import javax.inject.Inject

private const val PLACE_PICKER_REQUEST_CODE = 1

class MainNavigator @Inject constructor(
        private val activity: Activity,
        private val locationManager: LocationMapper
) : MvpNavigator {

    fun navigateToMapAndPickLocation(): Maybe<Location> {
        return Maybe.create { emitter ->
            val onActivityResultCallback = getPlacePickerResultCallback(emitter)

            emitter.setCancellable {
                (activity as BaseActivity).unregisterOnActivityResultCallback()
            }
            (activity as BaseActivity).registerOnActivityResultCallback(onActivityResultCallback)

            startPickerActivityForResult()
        }
    }

    private fun startPickerActivityForResult() {
        val placesPickerIntent = PlacePicker.IntentBuilder().build(activity)
        activity.startActivityForResult(placesPickerIntent, PLACE_PICKER_REQUEST_CODE)
    }

    private fun getPlacePickerResultCallback(emitter: MaybeEmitter<Location>): (requestCode: Int, resultCode: Int, data: Intent?) -> Unit {
        return { requestCode, resultCode, data ->
            if (requestCode == PLACE_PICKER_REQUEST_CODE) {
                handlePickerActivityResult(resultCode, data, emitter)
            }
        }
    }

    private fun handlePickerActivityResult(resultCode: Int, data: Intent?, emitter: MaybeEmitter<Location>) {
        if (resultCode == Activity.RESULT_OK) {
            val place = PlacePicker.getPlace(activity, data)
            val location = locationManager.latlngtoDomain(place.latLng)
            emitter.onSuccess(location)
        } else {
            emitter.onComplete()
        }
    }
}