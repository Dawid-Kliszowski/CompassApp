package pl.dawidkliszowski.compassapp.screens.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.dawidkliszowski.compassapp.CompassApp
import pl.dawidkliszowski.compassapp.di.component.ActivityComponent
import pl.dawidkliszowski.compassapp.di.module.ActivityModule

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    protected val activityComponent: ActivityComponent by lazy {
        CompassApp.get(this)
                .applicationComponent
                .activityComponent(ActivityModule(this))
    }

    protected abstract val layoutResId: Int

    private var onActivityResultCallback: ((requestCode: Int, resultCode: Int, data: Intent?) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        injectDependencies()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResultCallback?.invoke(requestCode, resultCode, data)
    }

    protected abstract fun injectDependencies()

    fun registerOnActivityResultCallback(onActivityResult: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit) {
        onActivityResultCallback = onActivityResult
    }

    fun unregisterOnActivityResultCallback() {
        onActivityResultCallback = null
    }
}