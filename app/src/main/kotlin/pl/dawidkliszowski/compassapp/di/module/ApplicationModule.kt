package pl.dawidkliszowski.compassapp.di.module

import android.app.Application
import android.content.Context
import com.patloew.rxlocation.RxLocation
import dagger.Module
import dagger.Provides
import pl.dawidkliszowski.compassapp.di.qualifier.AppContext

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @AppContext
    fun provideAppContext(): Context = application

    @Provides
    fun provideRxLocation(@AppContext context: Context) = RxLocation(context)
}