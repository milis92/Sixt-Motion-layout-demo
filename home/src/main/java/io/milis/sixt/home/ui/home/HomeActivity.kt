package io.milis.sixt.home.ui.home

import android.os.Bundle
import io.milis.sixt.core.common.MvpActivity
import io.milis.sixt.home.R


class HomeActivity : MvpActivity(), HomeView {

    override val presenter by presenterProvider(HomePresenter::class.java, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

}

