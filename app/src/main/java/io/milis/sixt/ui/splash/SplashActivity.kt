package io.milis.sixt.ui.splash

import io.milis.sixt.core.common.MvpActivity


internal class SplashActivity : MvpActivity(), SplashView {

    override val presenter by presenterProvider(SplashPresenter::class.java, this)
}