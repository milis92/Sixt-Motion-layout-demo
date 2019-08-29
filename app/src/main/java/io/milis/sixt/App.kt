package io.milis.sixt

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import io.milis.sixt.core.dagger.CoreComponent
import io.milis.sixt.core.dagger.DaggerCoreComponent

class App : Application() {

    private val coreComponent: CoreComponent by lazy {
        val component = DaggerCoreComponent.factory().create(this)

        WorkManager.initialize(this,
                Configuration.Builder()
                        .setWorkerFactory(component.workerFactory())
                        .build())
        component
    }

    companion object {

        @JvmStatic
        fun applicationComponent(context: Context) =
                (context.applicationContext as App).coreComponent

    }
}