package io.milis.sixt.core.domain

import androidx.room.EmptyResultSetException
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.*
import io.milis.core.TestData
import io.milis.sixt.core.domain.repositories.CarsRepository
import io.milis.sixt.core.domain.services.daos.CarLocalService
import io.milis.sixt.core.domain.services.remote.CarRemoteService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock(name = "carLocalService")
    lateinit var carLocalService: CarLocalService

    @Mock(name = "carRemoteService")
    lateinit var carRemoteService: CarRemoteService

    @InjectMocks
    lateinit var repo: CarsRepository

    @Test
    fun syncInsertsData() {

        whenever(carRemoteService.getCars())
                .doReturn(Single.just(TestData.cars))
        whenever(carLocalService.sync(TestData.cars))
                .doAnswer {
                    Truth.assertThat(TestData.cars).isEqualTo(it.getArgument(0))
                }

        val disposable = repo.sync().test()
                .assertNoErrors()
                .assertTerminated()

        disposable.dispose()
    }

    @Test
    fun syncFailsDoestNotCallDb() {
        whenever(carRemoteService.getCars())
                .doReturn(Single.error(Throwable()))

        verifyZeroInteractions(carLocalService)

        val disposable = repo.sync().test()
                .assertError(Throwable::class.java)
                .assertTerminated()

        disposable.dispose()
    }

    @Test
    fun getAllReturnsData() {

        whenever(carLocalService.getAll())
                .doReturn(Flowable.just(TestData.cars))

        val disposable = repo.getAll().test()
                .assertTerminated()
                .assertNoErrors()
                .assertValue(TestData.cars)

        disposable.dispose()
    }

    @Test
    fun getSingleReturnsData() {
        whenever(carLocalService.getSingle(TestData.car1.id))
                .doReturn(Single.just(TestData.car1))

        val disposable = repo.getSingle(TestData.car1.id).test()
                .assertTerminated()
                .assertNoErrors()
                .assertValue(TestData.car1)

        disposable.dispose()
    }

    @Test
    fun getSingleReturnsError() {
        whenever(carLocalService.getSingle(TestData.car1.id))
                .doReturn(Single.error(EmptyResultSetException("")))

        val disposable = repo.getSingle(TestData.car1.id).test()
                .assertTerminated()
                .assertError(EmptyResultSetException::class.java)

        disposable.dispose()
    }

    @Test
    fun insertCompletes() {
        whenever(carLocalService.insert(TestData.car1))
                .doAnswer {
                    Truth.assertThat(TestData.car1).isEqualTo(it.arguments[0])
                }

        val disposable = repo.insert(TestData.car1).test()
                .assertTerminated()
                .assertNoErrors()
                .assertComplete()

        disposable.dispose()
    }

    @Test(expected = NotImplementedError::class)
    fun updateNotImplemented() {
        repo.update(TestData.car1)
    }

    @Test
    fun deleteCompletes() {
        whenever(carLocalService.delete(TestData.car1))
                .doReturn(Completable.complete())

        val disposable = repo.delete(TestData.car1).test()
                .assertTerminated()
                .assertNoErrors()
                .assertComplete()

        disposable.dispose()
    }

    @Test
    fun pruneCompletes() {
        whenever(carLocalService.delete()).doAnswer {

        }

        val disposable = repo.prune().test()
                .assertTerminated()
                .assertNoErrors()
                .assertComplete()

        disposable.dispose()
    }

    @Test
    fun findSimilarReturnsValue() {
        whenever(carLocalService.filter(TestData.car1.make, TestData.car1.modelName))
                .thenReturn(Single.just(TestData.cars))

        val disposable = repo.findSimilar(TestData.car1.make, TestData.car1.modelName).test()
                .assertTerminated()
                .assertNoErrors()
                .assertValue(TestData.cars)

        disposable.dispose()
    }

}