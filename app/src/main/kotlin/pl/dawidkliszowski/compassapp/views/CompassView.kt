package pl.dawidkliszowski.compassapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_compass.view.*
import pl.dawidkliszowski.compassapp.R

class CompassView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    var currentCompassAzimuthDegrees: Float = 0f
        set(value) {
            field = value
            invalidateCompassImage()
        }

    var currentTargetBearing: Float? = null
        set(value) {
            field = value
            invalidateCompassImage()
        }

    init {
        View.inflate(context, R.layout.view_compass, this)
    }

    private fun invalidateCompassImage() {
        compassDiskImageView.rotation = -currentCompassAzimuthDegrees
        currentTargetBearing?.let { targetBearing ->
            targetBearingImageView.visibility = View.VISIBLE
            targetBearingImageView.rotation = targetBearing - currentCompassAzimuthDegrees
        }
    }
}