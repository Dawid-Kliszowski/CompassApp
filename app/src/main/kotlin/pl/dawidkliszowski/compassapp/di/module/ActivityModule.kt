package pl.dawidkliszowski.compassapp.di.module

import android.app.Activity
import android.content.Context
import com.tbruyelle.rxpermissions2.RxPermissions
import pl.dawidkliszowski.compassapp.di.qualifier.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @ActivityContext
    fun provideActivityContext(): Context = activity

    @Provides
    fun provideActivity(): Activity = activity

    @Provides
    fun provideRxPermissions(): RxPermissions = RxPermissions(activity)
}
