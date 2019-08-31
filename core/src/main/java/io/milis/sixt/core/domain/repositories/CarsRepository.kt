package io.milis.sixt.core.domain.repositories

import io.milis.sixt.core.dagger.scopes.ApplicationScope
import io.milis.sixt.core.domain.services.daos.CarLocalService
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.core.domain.services.remote.CarRemoteService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import javax.inject.Inject

@ApplicationScope
class CarsRepository @Inject constructor(private val carLocalService: CarLocalService,
                                         private val carRemoteService: CarRemoteService) : Repository<Car> {

    override fun sync(): Completable = carRemoteService.getCars()
            .doOnSuccess { cars -> carLocalService.sync(cars) }
            .ignoreElement()

    override fun observe(): Flowable<List<Car>> = carLocalService.getAll()

    override fun getAll(): Single<List<Car>> = Single.fromPublisher(carLocalService.getAll())

    override fun getSingle(uuid: String): Single<Car> = carLocalService.getSingle(uuid)

    override fun insert(vararg values: Car): Completable = Completable.fromCallable { carLocalService.insert(*values) }

    @Throws(NotImplementedError::class)
    override fun update(value: Car): Completable = throw NotImplementedError()

    override fun delete(value: Car): Completable = carLocalService.delete(value)

    override fun prune(): Completable = Completable.fromCallable { carLocalService.delete() }

    @CheckReturnValue
    fun findSimilar(make: String, modelName: String) = carLocalService.filter(make, modelName)

}