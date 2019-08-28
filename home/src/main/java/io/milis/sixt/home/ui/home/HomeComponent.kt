package io.milis.sixt.home.ui.home

import dagger.Component
import io.milis.sixt.App
import io.milis.sixt.core.dagger.BaseActivityComponent
import io.milis.sixt.core.dagger.CoreComponent

@Component(
        modules = [
            HomeModule::class
        ],
        dependencies =
        [
            CoreComponent::class
        ])
internal abstract class HomeComponent : BaseActivityComponent<HomeActivity> {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): HomeComponent
    }
}

fun HomeActivity.inject(){
    DaggerHomeComponent.factory()
            .create(App.applicationComponent(this))
            .inject(this)
}