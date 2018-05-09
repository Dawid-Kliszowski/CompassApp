package pl.dawidkliszowski.compassapp.di.component

import pl.dawidkliszowski.compassapp.di.ActivityScope
import pl.dawidkliszowski.compassapp.di.module.ActivityModule
import dagger.Subcomponent
import pl.dawidkliszowski.compassapp.screens.main.MainActivity

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}
