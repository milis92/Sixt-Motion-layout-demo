package ch.bmapp.core.dagger.mapkeys

import dagger.MapKey
import io.milis.sixt.core.common.MvpPresenter
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class PresenterClass(val value: KClass<out MvpPresenter<*>>)
