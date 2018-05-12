package pl.dawidkliszowski.compassapp.utils

import android.content.res.Resources
import android.support.annotation.StringRes
import javax.inject.Inject

class StringProvider @Inject constructor(
        private val resources: Resources
) {

    fun getString(@StringRes resId: Int): String = resources.getString(resId)

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return resources.getString(resId, *formatArgs)
    }
}