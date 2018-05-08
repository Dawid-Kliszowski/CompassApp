package pl.dawidkliszowski.compassapp.di.component

import pl.dawidkliszowski.compassapp.di.FragmentScope
import pl.dawidkliszowski.compassapp.di.module.ActivityModule
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ActivityModule::class])
interface FragmentComponent