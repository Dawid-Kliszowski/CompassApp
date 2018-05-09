package pl.dawidkliszowski.compassapp.screens.main

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.dawidkliszowski.compassapp.model.Location

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock lateinit var viewMock: MainView
    @Mock lateinit var navigatorMock: MainNavigator

    @InjectMocks lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        presenter.attachNavigator(navigatorMock)
        presenter.attachView(viewMock)
    }

    @After
    fun finish() {
        presenter.detachView()
        presenter.detachNavigator()
    }

    @Test
    fun `picks location`() {
        val testLocation = Location(0.0, 0.0)
        whenever(navigatorMock.navigateToMapAndPickLocation())
                .thenReturn(Maybe.just(testLocation))

        presenter.onPickPlace()
        verify(viewMock).showsPickedLocation(testLocation)
    }
}