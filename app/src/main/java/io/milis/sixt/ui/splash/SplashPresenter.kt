package io.milis.sixt.ui.splash

import io.milis.sixt.core.common.MvpRxPresenter
import javax.inject.Inject

class SplashPresenter @Inject constructor() : MvpRxPresenter<SplashView>() {

    fun onCreated() {
        view?.onLaunchHome()
    }


}