package io.milis.sixt.core.common.mvp

class MvpPresenterProvider {

    interface Factory {
        fun <P : MvpPresenter<*>> create(modelClass: Class<P>): P
    }

}