package io.milis.sixt.ui.splash

import ch.bmapp.ui.splash.SplashModule
import dagger.Component
import io.milis.sixt.core.dagger.BaseActivityComponent
import io.milis.sixt.core.dagger.CoreComponent

@Component(
        modules = [
            SplashModule::class
        ],
        dependencies =
        [
            CoreComponent::class
        ])
internal abstract class SplashComponent : BaseActivityComponent<SplashActivity> {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): SplashComponent
    }
}