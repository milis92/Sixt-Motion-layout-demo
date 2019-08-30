package io.milis.sixt.home.ui.all_cars

import io.milis.sixt.core.dagger.mapkeys.PresenterClass
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.milis.sixt.core.common.mvp.MvpPresenter
import io.milis.sixt.core.common.mvp.MvpPresenterFactory
import io.milis.sixt.core.common.mvp.MvpPresenterProvider

@Module
internal abstract class AllCarsModule {

    @Binds
    abstract fun bindPresenterFactory(presenterFactory: MvpPresenterFactory): MvpPresenterProvider.Factory

    @Binds
    @IntoMap
    @PresenterClass(AllCarsPresenter::class)
    abstract fun bindAllCarsPresenter(presenter: AllCarsPresenter): MvpPresenter<*>

}
