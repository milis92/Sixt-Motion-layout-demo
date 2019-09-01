package io.milis.sixt.home.ui.home

import android.view.LayoutInflater
import dagger.BindsInstance
import dagger.Component
import io.milis.sixt.App
import io.milis.sixt.core.Core
import io.milis.sixt.core.dagger.BaseActivityComponent
import io.milis.sixt.core.dagger.CoreComponent
import io.milis.sixt.core.dagger.scopes.ApplicationScope
import io.milis.sixt.core.dagger.scopes.FeatureScope
import javax.inject.Singleton

@FeatureScope
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
        fun create(@ApplicationScope coreComponent: CoreComponent, @BindsInstance layoutInflater: LayoutInflater): HomeComponent
    }
}

fun HomeActivity.inject() {
    DaggerHomeComponent.factory()
            .create(Core.component, layoutInflater)
            .inject(this)
}