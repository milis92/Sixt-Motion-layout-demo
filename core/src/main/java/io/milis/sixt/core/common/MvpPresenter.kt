package io.milis.sixt.core.common

import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class MvpPresenter<T : MvpView> {

    private var viewReference: WeakReference<T>? = null
    protected val view
        get() = viewReference?.get()

    fun onCreate(view: T) {
        this.viewReference = WeakReference(view)
    }

    open fun onDestroy() {
        viewReference?.clear()
    }
}

open class MvpRxPresenter<T : MvpView> : MvpPresenter<T>() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}