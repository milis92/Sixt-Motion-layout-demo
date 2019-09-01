package io.milis.sixt.home

import com.nhaarman.mockitokotlin2.*
import io.milis.sixt.core.domain.repositories.CarsRepository
import io.milis.sixt.home.ui.all_cars.AllCarsPresenter
import io.milis.sixt.home.ui.all_cars.AllCarsView
import io.milis.sixt.home.ui.home.HomePresenter
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
class AllCarsPresenterTest {

    val trampolineScheduler = Schedulers.trampoline()

    @Spy
    var carsRepository = CarsRepository(mock(), mock())

    @Mock
    lateinit var allCarsView: AllCarsView

    @Spy
    var allCarsPresenter = AllCarsPresenter(trampolineScheduler, trampolineScheduler, carsRepository)

    @Before
    fun setUp() {
        allCarsPresenter.onCreate(allCarsView)
    }

    @Test
    fun onCreatedLoadsCars() {
        val actual = TestData.cars

        whenever(carsRepository.observe())
                .doReturn(Flowable.just(actual))

        allCarsPresenter.onCreated()

        verify(allCarsView).onCarsLoaded(actual)
        verify(allCarsView, never()).onFetchError(Throwable())
    }

    @Test
    fun onCreateReturnsError() {
        val actual = Throwable()
        whenever(carsRepository.observe())
                .doReturn(Flowable.error(actual))

        allCarsPresenter.onCreated()

        verify(allCarsView, never()).onCarsLoaded(TestData.cars)
        verify(allCarsView).onFetchError(actual)
    }
}
