package io.milis.sixt.core.dagger.providers

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@Module(includes = [AssistedInject_WorkerAssistedModule::class])
@AssistedModule
abstract class WorkerAssistedModule