package pl.dawidkliszowski.compassapp.screens.main

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.utils.CompassUtil
import pl.dawidkliszowski.compassapp.utils.getDisplayedText
import pl.dawidkliszowski.githubapp.BaseTest

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest : BaseTest() {

    @Mock lateinit var viewMock: MainView
    @Mock lateinit var navigatorMock: MainNavigator
    @Mock lateinit var compassUtilMock: CompassUtil

    lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        whenever(compassUtilMock.observeCompassAzimuth())
                .thenReturn(Observable.empty())

        presenter = MainPresenter(compassUtilMock) //initialized manually because of early usage of compassUtilMock

        presenter.attachNavigator(navigatorMock)
        presenter.attachView(viewMock)
    }

    @After
    fun finish() {
        presenter.detachView()
        presenter.detachNavigator()
        presenter.onDestroy()
    }

    @Test
    fun `picks location`() {
        val testLocation = Location(0.0, 0.0)
        whenever(navigatorMock.navigateToMapAndPickLocation())
                .thenReturn(Maybe.just(testLocation))

        presenter.onPickPlace()
        verify(viewMock).showTargetLocation(testLocation.getDisplayedText())
    }
}