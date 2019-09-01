package io.milis.sixt.home

import com.nhaarman.mockitokotlin2.*
import io.milis.sixt.core.domain.repositories.CarsRepository
import io.milis.sixt.home.ui.home.HomePresenter
import io.milis.sixt.home.ui.home.HomeView
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {

    val trampolineScheduler = Schedulers.trampoline()

    @Spy
    var carsRepository = CarsRepository(mock(), mock())

    @Mock
    lateinit var homeView: HomeView

    @Spy
    var homePresenter = HomePresenter(trampolineScheduler, trampolineScheduler, carsRepository)

    @Before
    fun setUp() {
        homePresenter.onCreate(homeView)
    }

    @Test
    fun onMapCreatedLoadsCars() {
        val actual = TestData.cars

        whenever(carsRepository.observe())
                .doReturn(Flowable.just(actual))

        homePresenter.onMapCreated()

        verify(homeView).onCarsLoaded(actual)
        verify(homeView, never()).onFetchError(Throwable())
    }

    @Test
    fun onMapCreatedReturnsError() {
        val actual = Throwable()
        whenever(carsRepository.observe())
                .doReturn(Flowable.error(actual))

        homePresenter.onMapCreated()

        verify(homeView, never()).onCarsLoaded(TestData.cars)
        verify(homeView).onFetchError(actual)
    }

    @Test
    fun onSearchConfirmedLoadsCars() {
        whenever(carsRepository.findSimilar(TestData.car1.make, TestData.car1.modelName))
                .doReturn(Single.just(TestData.cars))

        homePresenter.onSearchConfirmed(TestData.car1.make, TestData.car1.modelName)

        verify(homeView).onCarsLoaded(TestData.cars)
        verify(homeView, never()).onFetchError(Throwable())
    }

    @Test
    fun onSearchRetursError() {
        val actual = Throwable()
        whenever(carsRepository.findSimilar(TestData.car1.make, TestData.car1.modelName))
                .doReturn(Single.error(actual))

        homePresenter.onSearchConfirmed(TestData.car1.make, TestData.car1.modelName)

        verify(homeView, never()).onCarsLoaded(TestData.cars)
        verify(homeView).onFetchError(actual)
    }
}
