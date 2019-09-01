package io.milis.sixt.core.domain

import io.milis.core.TestData
import io.milis.core.dagger.DaggerTestComponent
import io.milis.sixt.core.domain.services.remote.CarRemoteService
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class CarRemoteServiceTest {

    companion object {

        lateinit var mockRetrofit: MockRetrofit
        lateinit var networkBehavior: NetworkBehavior

        @JvmStatic
        @BeforeClass
        fun setupGraph() {
            val retrofit = DaggerTestComponent.create().retrofit()
            networkBehavior = NetworkBehavior.create()
            mockRetrofit = MockRetrofit.Builder(retrofit)
                    .networkBehavior(networkBehavior)
                    .build()
        }
    }

    @Before
    fun clearNetworkBehaviour() {
        with(networkBehavior) {
            setDelay(0, TimeUnit.MILLISECONDS);
            setVariancePercent(0)
            setFailurePercent(0)
            setErrorPercent(0)
        }
    }

    @Test
    fun getCarsReturnsValues() {
        val delegate = mockRetrofit.create(CarRemoteService::class.java)

        delegate.returningResponse(TestData.cars)
                .getCars()
                .test()
                .assertTerminated()
                .assertValue(TestData.cars)
                .assertNoErrors()
    }

    @Test
    fun getCarsFails() {
        mockRetrofit.networkBehavior().apply {
            setDelay(0, TimeUnit.MILLISECONDS)
            setVariancePercent(0)
            setFailurePercent(100)
        }

        val delegate = mockRetrofit.create(CarRemoteService::class.java)

        delegate.returningResponse(TestData.cars)
                .getCars()
                .test()
                .assertTerminated()
                .assertError(Throwable::class.java)
    }

}