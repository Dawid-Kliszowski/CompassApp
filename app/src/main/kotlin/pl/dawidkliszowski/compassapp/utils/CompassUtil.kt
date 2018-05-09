package pl.dawidkliszowski.compassapp.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import pl.dawidkliszowski.compassapp.di.qualifier.AppContext
import java.lang.IllegalStateException
import javax.inject.Inject

class CompassUtil @Inject constructor(
        @AppContext private val context: Context
) {

    private val sensorManager = context.getSensorManager()
    private val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    fun observeCompassAngle(): Observable<Float> {
        return Observable.create { emitter ->
            val sensorListener = getSensorListener(emitter)

            emitter.setCancellable {
                sensorManager.unregisterListener(sensorListener, gravitySensor)
                sensorManager.unregisterListener(sensorListener, magneticSensor)
            }

            sensorManager.registerListener(sensorListener, gravitySensor, SensorManager.SENSOR_DELAY_GAME)
            sensorManager.registerListener(sensorListener, magneticSensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    private fun getSensorListener(emitter: ObservableEmitter<Float>): SensorEventListener {
        return object : SensorEventListener {

            private val rMatrix = FloatArray(9)
            private val iMatrix = FloatArray(9)

            private var gravityValues: FloatArray? = null
            private var magneticValues: FloatArray? = null

            private var currentAzimuth = 0f

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit //no-op

            override fun onSensorChanged(event: SensorEvent) {
                saveCurrentValues(event)

                if (canCalculateAzimuth()) {
                    val success = SensorManager.getRotationMatrix(rMatrix, iMatrix, gravityValues, magneticValues)
                    if (success) {
                        val orientation = FloatArray(3)
                        SensorManager.getOrientation(rMatrix, orientation)
                        currentAzimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()

                        emitter.safeOnNext(currentAzimuth)
                    } else {
                        emitter.onError(IllegalStateException("Rotation matrix calculation didn't succeed"))
                    }
                }
            }

            private fun canCalculateAzimuth() = gravityValues != null && magneticValues != null

            private fun saveCurrentValues(event: SensorEvent) {
                when (event.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> gravityValues = event.values
                    Sensor.TYPE_MAGNETIC_FIELD -> magneticValues = event.values
                }
            }
        }
    }
}