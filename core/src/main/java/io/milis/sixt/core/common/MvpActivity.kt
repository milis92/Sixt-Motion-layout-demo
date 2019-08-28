package io.milis.sixt.core.common

import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

abstract class MvpActivity : AppCompatActivity(), MvpView {

    @Inject
    lateinit var presenterFactory: MvpPresenterProvider.Factory

    abstract val presenter: MvpPresenter<*>

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    inline fun <reified V : MvpView, reified P : MvpPresenter<V>> presenterProvider(
            clazz: Class<P>,
            view: V
    ) = lazy {
        this.presenterFactory.create(clazz).apply {
            onCreate(view)
        }
    }
}