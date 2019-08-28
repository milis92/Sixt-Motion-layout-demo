package io.milis.sixt.home.ui.home

import io.milis.sixt.core.common.mvp.MvpRxPresenter
import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Io
import io.milis.sixt.core.dagger.providers.SchedulerModule.Companion.Main
import io.milis.sixt.core.domain.repositories.CarsRepository
import io.milis.sixt.core.domain.services.entities.Car
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class HomePresenter @Inject constructor(@Named(Io) private val schedulerIo: Scheduler,
                                        @Named(Main) private val schedulerMain: Scheduler,
                                        private val carsRepository: CarsRepository) : MvpRxPresenter<HomeView>() {

    fun onMapCreated() {
        carsRepository.getCars()
                .observeOn(schedulerMain)
                .subscribeOn(schedulerIo)
                .subscribeBy(
                        onNext = {
                            view?.onCarsLoaded(it)
                        },
                        onError = {

                        }).addTo(compositeDisposable)
    }

    fun onSearchConfirmed(make: String, modelName: String) {
        carsRepository.findSimilar(make, modelName)
                .observeOn(schedulerMain)
                .subscribeOn(schedulerIo)
                .subscribeBy(
                        onSuccess = {
                            view?.onCarsLoaded(it)
                        },
                        onError = {

                        }).addTo(compositeDisposable)
    }


}

