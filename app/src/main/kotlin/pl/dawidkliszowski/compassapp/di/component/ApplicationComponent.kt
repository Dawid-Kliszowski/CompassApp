package pl.dawidkliszowski.compassapp.di.component

import dagger.Component
import pl.dawidkliszowski.compassapp.di.module.ActivityModule
import pl.dawidkliszowski.compassapp.di.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent
    fun fragmentComponent(activityModule: ActivityModule): FragmentComponent
}