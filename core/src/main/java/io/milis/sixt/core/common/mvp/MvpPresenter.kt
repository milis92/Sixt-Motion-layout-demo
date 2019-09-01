package io.milis.sixt.core.common.mvp

import androidx.annotation.CallSuper
import androidx.annotation.VisibleForTesting
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

open class MvpPresenter<T : MvpView> {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var viewReference: WeakReference<T>? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val view
        get() = viewReference?.get()

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    @CallSuper
    open fun onCreate(view: T) {
        this.viewReference = WeakReference(view)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    @CallSuper
    open fun onDestroy() {
        viewReference?.clear()
    }
}

open class MvpRxPresenter<T : MvpView> : MvpPresenter<T>() {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val compositeDisposable = CompositeDisposable()

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    @CallSuper
    override fun onDestroy() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onDestroy()
    }
}