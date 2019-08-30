package io.milis.sixt.ui.splash

import android.os.Bundle
import android.os.Handler
import io.milis.sixt.LaunchCodes
import io.milis.sixt.core.common.mvp.MvpActivity
import io.milis.sixt.ext.launchActivity


class SplashActivity : MvpActivity(), SplashView {

    override val presenter by presenterProvider(SplashPresenter::class.java, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()

        Handler().postDelayed(presenter::onCreated, 600)
    }

    override fun onLaunchHome() {
        launchActivity(className = LaunchCodes.Home)
    }

}