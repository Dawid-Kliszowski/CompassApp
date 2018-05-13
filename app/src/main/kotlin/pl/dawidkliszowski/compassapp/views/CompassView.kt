package pl.dawidkliszowski.compassapp.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_compass.view.*
import pl.dawidkliszowski.compassapp.R

private const val ANIMATION_DURATION_MILLIS = 300L
class CompassView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    var currentCompassAzimuthDegrees: Float = 0f
        set(value) {
            onAzimuthChanged(field, value)
            field = value
        }

    var currentTargetBearing: Float? = null
        set(value) {
            onBearingChanged(field, value)
            field = value
        }

    init {
        View.inflate(context, R.layout.view_compass, this)
    }

    private fun onAzimuthChanged(azimuthFrom: Float, azimuthTo: Float) {
        animateAzimuth(-azimuthFrom, -azimuthTo)
        animateTargetBearingArrow(
                currentBearing = currentTargetBearing,
                currentAzimuth = azimuthFrom,
                newAzimuth = azimuthTo
        )
    }

    private fun onBearingChanged(bearingFrom: Float?, bearingTo: Float?) {
        animateTargetBearingArrow(
                currentAzimuth = currentCompassAzimuthDegrees,
                currentBearing = bearingFrom,
                newBearing = bearingTo
        )
    }

    private fun animateAzimuth(rotationFrom: Float, rotationTo: Float) {
        animateRotation(compassDiskImageView, rotationFrom, rotationTo, ANIMATION_DURATION_MILLIS)
    }

    private fun animateTargetBearingArrow(
            currentBearing: Float?,
            currentAzimuth: Float,
            newBearing: Float? = currentBearing,
            newAzimuth: Float = currentAzimuth
    ) {
        if (newBearing != null) {
            targetBearingImageView.visibility = View.VISIBLE
            val rotationFrom = (currentBearing ?: 0f) - currentAzimuth
            val rotationTo = newBearing - newAzimuth
            animateRotation(targetBearingImageView, rotationFrom, rotationTo, ANIMATION_DURATION_MILLIS)
        } else {
            targetBearingImageView.visibility = View.INVISIBLE
        }
    }

    private fun animateRotation(view: View, rotationFrom: Float, rotation: Float, durationmillis: Long) {
        view.animation?.let { it.cancel() }

        val animation = RotateAnimation(
                rotationFrom,
                rotation,
                RELATIVE_TO_SELF,
                0.5f, //pivotX
                RELATIVE_TO_SELF,
                0.5f //pivotY
        ).apply {
            duration = durationmillis
            fillAfter = true
        }

        view.apply {
            setAnimation(animation)
            animate()
        }
    }
}