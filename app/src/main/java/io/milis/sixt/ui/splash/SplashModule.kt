package io.milis.sixt.ui.splash

import io.milis.sixt.core.dagger.mapkeys.PresenterClass
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.milis.sixt.core.common.MvpPresenter
import io.milis.sixt.core.common.MvpPresenterFactory
import io.milis.sixt.core.common.MvpPresenterProvider

@Module
internal abstract class SplashModule {

    @Binds
    abstract fun presenterFactory(presenterFactory: MvpPresenterFactory): MvpPresenterProvider.Factory

    @Binds
    @IntoMap
    @PresenterClass(SplashPresenter::class)
    abstract fun bindSplashPresenter(splashPresenter: SplashPresenter): MvpPresenter<*>

}
