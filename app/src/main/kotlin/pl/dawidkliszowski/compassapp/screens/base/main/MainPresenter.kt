package pl.dawidkliszowski.compassapp.screens.base.main

import android.os.Parcelable
import pl.dawidkliszowski.compassapp.screens.base.mvp.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor() : MvpPresenter<MainView, MainNavigator>() {

    override fun saveState(): Parcelable? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun restoreState(parcel: Parcelable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}