package pl.dawidkliszowski.compassapp.screens.base

import android.annotation.SuppressLint
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        injectDependencies()
    }

    protected abstract fun injectDependencies()
}