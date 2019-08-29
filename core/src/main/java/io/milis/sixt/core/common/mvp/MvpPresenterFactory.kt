package io.milis.sixt.core.common.mvp

import javax.inject.Inject
import javax.inject.Provider

class MvpPresenterFactory
@Inject constructor(
        private val presenters: Map<Class<out MvpPresenter<*>>,
                @JvmSuppressWildcards Provider<MvpPresenter<*>>>
) : MvpPresenterProvider.Factory {

    override fun <T : MvpPresenter<*>> create(modelClass: Class<T>): T {
        var creator: Provider<out MvpPresenter<*>>? = presenters[modelClass]
        if (creator == null) {
            for ((key, value) in presenters) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown presenter class $modelClass")
        }
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}