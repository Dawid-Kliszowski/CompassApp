package pl.dawidkliszowski.compassapp.screens.main

import android.os.Bundle
import android.support.annotation.LayoutRes
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_compass.*
import pl.dawidkliszowski.compassapp.R
import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpActivity

class MainActivity : MvpActivity<MainView, MainNavigator, MainPresenter>(), MainView {

    @LayoutRes override val layoutResId = R.layout.activity_main

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        locationPickButton.setOnClickListener {
            presenter.onPickPlace()
        }
    }

    override fun showPickedLocation(location: String) {
        targetLocationTextView.text = location
    }

    override fun showCurrentMeasuredAzimuth(degrees: Float) {
        compassView.currentCompassAzimuthDegrees = degrees
    }

    override fun disablePickLocationButton() {
        locationPickButton.isEnabled = false
    }

    override fun enablePickLocationButton() {
        locationPickButton.isEnabled = true
    }

    override fun showCurrentTargetBearing(degrees: Float) {
        compassView.currentTargetBearing = degrees
    }
}
