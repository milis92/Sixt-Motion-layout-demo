package io.milis.sixt.home.ui.home

import io.milis.sixt.core.common.MvpPresenter
import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Io
import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Main
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class HomePresenter @Inject constructor(@Named(Io) private val schedulerIo: Scheduler,
                                        @Named(Main) private val schedulerMain: Scheduler) : MvpPresenter<HomeView>() {

    fun onMapCreated() {

    }

}

