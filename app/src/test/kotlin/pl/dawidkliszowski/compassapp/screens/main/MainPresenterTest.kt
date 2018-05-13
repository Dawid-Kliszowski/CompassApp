package pl.dawidkliszowski.compassapp.screens.main

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.dawidkliszowski.compassapp.R
import pl.dawidkliszowski.compassapp.model.Location
import pl.dawidkliszowski.compassapp.screens.main.state.MainPresenterStateHandler
import pl.dawidkliszowski.compassapp.utils.*
import pl.dawidkliszowski.githubapp.BaseTest

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest : BaseTest() {

    @Mock lateinit var viewMock: MainView
    @Mock lateinit var navigatorMock: MainNavigator
    @Mock lateinit var compassUtilMock: CompassUtil
    @Mock lateinit var rxPermissionsMock: RxPermissions
    @Mock lateinit var locationManagerMock: LocationManager
    @Mock lateinit var bearingCalculatorMock: BearingCalculator
    @Mock lateinit var stringProviderMock: StringProvider
    @Mock lateinit var mainPresenterStateHandlerMock: MainPresenterStateHandler

    lateinit var presenter: MainPresenter

    private val testSelfLocation = Location(0.0, 0.0)
    private val testTargetLocation = Location(1.0, 1.0)
    private val azimuthTestSubject = PublishSubject.create<Float>()

    @Before
    fun setUp() {
        whenever(compassUtilMock.observeCompassAzimuth())
                .thenReturn(azimuthTestSubject)
        whenever(rxPermissionsMock.request(any()))
                .thenReturn(Observable.just(true))
        whenever(locationManagerMock.observeLocation())
                .thenReturn(Observable.just(testSelfLocation))
        whenever(navigatorMock.navigateToMapAndPickLocation())
                .thenReturn(Maybe.just(testTargetLocation))

        createPresenterInstance()

        presenter.attachNavigator(navigatorMock)
        presenter.attachView(viewMock)
    }

    private fun createPresenterInstance() { //initialized manually because of early usage of compassUtilMock
        presenter = MainPresenter(
                compassUtilMock,
                rxPermissionsMock,
                locationManagerMock,
                bearingCalculatorMock,
                stringProviderMock,
                mainPresenterStateHandlerMock
        )
    }

    @After
    fun finish() {
        presenter.detachView()
        presenter.detachNavigator()
        presenter.onDestroy()
    }

    @Test
    fun `shows target location after pick`() {
        presenter.onPickPlace()
        verify(viewMock).showTargetLocation(testTargetLocation.getDisplayedText())
    }

    @Test
    fun `disables and enables button when pick location`() {
        presenter.onPickPlace()

        verify(viewMock).disablePickLocationButton()
        verify(viewMock).enablePickLocationButton()
    }

    @Test
    fun `shows current bearing`() {
        val compassMeasurementsBufferingPeriodMillis = 300L

        presenter.onPickPlace()

        advanceRxTime(compassMeasurementsBufferingPeriodMillis)
        verify(viewMock).showCurrentTargetBearing(any())
    }

//    @Test
//    fun `shows self location`() {
//        verify(viewMock).showSelfLocation(testSelfLocation.getDisplayedText())
//    }

//    @Test
//    fun `shows distance between locations`() {
//        val testDistance = 5f
//        whenever(bearingCalculatorMock.calculateDistanceMeters(testSelfLocation, testTargetLocation))
//                .thenReturn(testDistance)
//        whenever(stringProviderMock.getString(R.string.screen_main_formatted_distance_meters, testDistance))
//                .thenReturn(String.format("%.2f meters", testDistance))
//
//        presenter.onPickPlace()
//        verify(viewMock).showCurrentDistance("5,00 meters")
//    }

//    @Test
//    fun `shows measured self location`() {
//        val testAzimuth = 180f
//        val compassMeasurementsBufferingPeriodMillis = 300L
//
//        azimuthTestSubject.onNext(testAzimuth)
//        advanceRxTime(compassMeasurementsBufferingPeriodMillis)
//        verify(viewMock).showCurrentMeasuredAzimuth(testAzimuth)
//    }
}