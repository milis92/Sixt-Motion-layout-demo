package io.milis.sixt.core.dagger.mapkeys

import androidx.work.ListenableWorker

import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerClass(val value: KClass<out ListenableWorker>)
