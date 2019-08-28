package io.milis.sixt.core.domain.services.daos

import androidx.room.*
import io.milis.sixt.core.domain.services.entities.Car
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import androidx.room.OnConflictStrategy
import io.reactivex.annotations.CheckReturnValue


@Dao
abstract class CarDao {

    @CheckReturnValue
    @Query("SELECT * FROM cars")
    abstract fun getAll(): Flowable<List<Car>>

    @CheckReturnValue
    @Query("SELECT * FROM cars WHERE id=:id")
    abstract fun getSingle(id: String): Single<Car>

    @CheckReturnValue
    @Query("SELECT * FROM cars WHERE make LIKE  '%' || :query || '%' OR model_name LIKE '%' || :query || '%'")
    abstract fun filter(query: String): Single<List<Car>>

    @CheckReturnValue
    @Delete
    abstract fun delete(car: Car): Completable

    @Query("DELETE FROM cars")
    abstract fun delete()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg cars: Car)

    @Transaction
    open fun sync(cars: List<Car>) {
        delete()
        insert(*cars.toTypedArray())
    }


}