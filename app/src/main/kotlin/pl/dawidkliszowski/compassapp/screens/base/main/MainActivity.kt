package pl.dawidkliszowski.compassapp.screens.base.main

import android.support.annotation.LayoutRes
import pl.dawidkliszowski.compassapp.R
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpActivity

class MainActivity : MvpActivity<MainView, MainNavigator, MainPresenter>(), MainView {

    @LayoutRes override val layoutResId = R.layout.activity_main

    override fun injectDependencies() {
        activityComponent.inject(this)
    }
}
