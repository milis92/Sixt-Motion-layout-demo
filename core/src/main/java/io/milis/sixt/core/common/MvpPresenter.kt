package io.milis.sixt.core.common

import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class MvpPresenter<T : MvpView> {

    private var view: WeakReference<T>? = null

    fun  onCreate(view: T) {
        this.view = WeakReference(view)
    }

    open fun onDestroy() {
        view?.clear()
    }
}

open class MvpRxPresenter<T : MvpView> : MvpPresenter<T>() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}