package io.milis.sixt.home.ui.home

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.milis.sixt.core.common.mvp.MvpPresenter
import io.milis.sixt.core.common.mvp.MvpPresenterFactory
import io.milis.sixt.core.common.mvp.MvpPresenterProvider
import io.milis.sixt.core.dagger.mapkeys.PresenterClass

@Module
internal abstract class HomeModule {

    @Binds
    abstract fun bindPresenterFactory(presenterFactory: MvpPresenterFactory): MvpPresenterProvider.Factory

    @Binds
    @IntoMap
    @PresenterClass(HomePresenter::class)
    abstract fun bindSignInPresenter(presenter: HomePresenter): MvpPresenter<*>

}
