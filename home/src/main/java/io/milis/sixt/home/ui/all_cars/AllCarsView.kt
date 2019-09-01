package io.milis.sixt.home.ui.all_cars

import io.milis.sixt.core.common.mvp.MvpView
import io.milis.sixt.core.domain.services.entities.Car

interface AllCarsView : MvpView {
    fun onCarsLoaded(cars: List<Car>)
    fun onFetchError(throwable: Throwable)
}

