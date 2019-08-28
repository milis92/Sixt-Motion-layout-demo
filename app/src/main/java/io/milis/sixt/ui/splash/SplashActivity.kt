package io.milis.sixt.ui.splash

import android.os.Bundle
import io.milis.sixt.R
import io.milis.sixt.core.common.mvp.MvpActivity
import io.milis.sixt.ext.launchActivity
import io.milis.sixt.LaunchCodes
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : MvpActivity(), SplashView {

    override val presenter by presenterProvider(SplashPresenter::class.java, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        inject()

        iv_logo.animate()
                .alpha(1f)
                .scaleX(1.4f)
                .scaleY(1.4f)
                .setStartDelay(600L)
                .withEndAction {
                    presenter.onCreated()
                }
                .start()
    }

    override fun onLaunchHome() {
        launchActivity(className = LaunchCodes.Home)
    }

}