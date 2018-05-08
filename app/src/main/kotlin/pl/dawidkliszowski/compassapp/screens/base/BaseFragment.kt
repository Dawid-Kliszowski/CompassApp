package pl.dawidkliszowski.compassapp.screens.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.dawidkliszowski.compassapp.CompassApp
import pl.dawidkliszowski.compassapp.di.component.FragmentComponent
import pl.dawidkliszowski.compassapp.di.module.ActivityModule

abstract class BaseFragment : Fragment() {

    protected val fragmentComponent: FragmentComponent by lazy {
        CompassApp.get(activity!!)
                .applicationComponent
                .fragmentComponent(ActivityModule(activity!!))
    }

    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    protected abstract fun injectDependencies()
}