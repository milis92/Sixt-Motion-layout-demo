package io.milis.sixt.home.ui.all_cars

import android.view.LayoutInflater
import dagger.BindsInstance
import io.milis.sixt.core.dagger.BaseActivityComponent
import io.milis.sixt.core.dagger.CoreComponent
import dagger.Component
import io.milis.sixt.App

@Component(
        modules = [
            AllCarsModule::class
        ],
        dependencies =
        [
            CoreComponent::class
        ])
internal abstract class AllCarsComponent : BaseActivityComponent<AllCarsActivity> {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent,  @BindsInstance layoutInflater: LayoutInflater): AllCarsComponent
    }
}

fun AllCarsActivity.inject() {
    DaggerAllCarsComponent.factory()
            .create(App.applicationComponent(this), layoutInflater)
            .inject(this)
}