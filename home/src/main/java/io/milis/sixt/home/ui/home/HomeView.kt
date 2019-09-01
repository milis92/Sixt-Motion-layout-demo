package io.milis.sixt.home.ui.home

import io.milis.sixt.core.common.mvp.MvpView
import io.milis.sixt.core.domain.services.entities.Car

interface HomeView : MvpView {
    fun onCarsLoaded(cars: List<Car>)
    fun onFetchError(throwable: Throwable)
}

