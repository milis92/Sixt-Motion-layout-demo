package io.milis.core.common

import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class MvpPresenter<T : MvpView> {

    private var mvpViewReference: WeakReference<T>? = null

    protected val view by lazy {
        mvpViewReference?.get()
    }

    fun onCreate(view: T) {
        this.mvpViewReference = WeakReference(view)
    }

    open fun onDestroy() {
        mvpViewReference?.clear()
    }
}

class MvpRxPresenter<T : MvpView> : MvpPresenter<T>() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}