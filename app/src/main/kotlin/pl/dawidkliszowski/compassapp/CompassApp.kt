package pl.dawidkliszowski.compassapp

import android.app.Application
import android.content.Context
import pl.dawidkliszowski.compassapp.di.component.ApplicationComponent
import pl.dawidkliszowski.compassapp.di.component.DaggerApplicationComponent
import pl.dawidkliszowski.compassapp.di.module.ApplicationModule

class CompassApp : Application() {

    companion object {
        fun get(context: Context) = context.applicationContext as CompassApp
    }

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}