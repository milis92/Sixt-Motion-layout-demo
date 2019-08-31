package io.milis.sixt.ui.splash

import dagger.Component
import io.milis.sixt.App
import io.milis.sixt.core.dagger.BaseActivityComponent
import io.milis.sixt.core.dagger.CoreComponent
import io.milis.sixt.core.dagger.scopes.ApplicationScope
import io.milis.sixt.core.dagger.scopes.FeatureScope
import javax.inject.Singleton

@FeatureScope
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
        fun create(@ApplicationScope coreComponent: CoreComponent): SplashComponent
    }
}

fun SplashActivity.inject() {
    DaggerSplashComponent.factory()
            .create(App.applicationComponent(this))
            .inject(this)
}