package io.milis.sixt.core.domain.repositories

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue

interface Repository<T> {

    @CheckReturnValue
    fun sync(): Completable

    @CheckReturnValue
    fun observe(): Flowable<List<T>>

    @CheckReturnValue
    fun getAll(): Single<List<T>>

    @CheckReturnValue
    fun getSingle(uuid: String): Single<T>

    @CheckReturnValue
    fun insert(vararg values : T): Completable

    @CheckReturnValue
    fun update(value: T): Completable

    @CheckReturnValue
    fun delete(value: T): Completable

    @CheckReturnValue
    fun prune(): Completable
}