package io.milis.sixt.ui.splash

import android.os.Bundle
import ch.bmapp.ui.splash.SplashPresenter
import ch.bmapp.ui.splash.SplashView
import io.milis.sixt.core.common.MvpActivity


internal class SplashActivity : MvpActivity(), SplashView {

    override val presenter by presenterProvider(SplashPresenter::class.java, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}