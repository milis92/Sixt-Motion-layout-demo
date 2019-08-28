package io.milis.sixt

import android.app.Application
import android.content.Context
import io.milis.sixt.core.dagger.CoreComponent
import io.milis.sixt.core.dagger.DaggerCoreComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(this)
    }

    companion object {

        @JvmStatic
        fun applicationComponent(context: Context) =
                (context.applicationContext as App).coreComponent

    }
}