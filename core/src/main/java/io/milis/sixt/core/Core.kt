package io.milis.sixt.core

import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri
import androidx.work.*
import io.milis.sixt.core.dagger.CoreComponent
import io.milis.sixt.core.dagger.DaggerCoreComponent
import io.milis.sixt.core.domain.services.workers.DataSyncWorker
import java.util.concurrent.TimeUnit

class Core : ContentProvider() {

    companion object {
        private const val SYNC_JOB_NAME = "sync_job"

        lateinit var component: CoreComponent
    }

    override fun onCreate(): Boolean {

        component = DaggerCoreComponent.factory().create(context!!)

        WorkManager.initialize(context!!,
                Configuration.Builder()
                        .setWorkerFactory(component.workerFactory())
                        .build())

        component.workManager().enqueueUniquePeriodicWork(
                SYNC_JOB_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                PeriodicWorkRequestBuilder<DataSyncWorker>(10, TimeUnit.MINUTES)
                        .setConstraints(Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .setRequiresBatteryNotLow(true)
                                .build())
                        .build()
        )

        return true
    }

    override fun insert(uri: Uri, values: ContentValues?) = null
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?) = null
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?) = 0
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0

    override fun getType(uri: Uri) = null
}