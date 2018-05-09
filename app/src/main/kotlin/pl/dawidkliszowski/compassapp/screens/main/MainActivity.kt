package pl.dawidkliszowski.compassapp.screens.main

import android.os.Bundle
import android.support.annotation.LayoutRes
import kotlinx.android.synthetic.main.activity_main.*
import pl.dawidkliszowski.compassapp.R
import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpActivity

class MainActivity : MvpActivity<MainView, MainNavigator, MainPresenter>(), MainView {

    @LayoutRes override val layoutResId = R.layout.activity_main

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        locationPickButton.setOnClickListener {
            presenter.onPickPlace()
        }
    }

    override fun showsPickedLocation(location: Location) {
        //todo implement
    }
}
