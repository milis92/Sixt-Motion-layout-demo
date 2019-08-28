package io.milis.core.domain.repositories

import android.content.SharedPreferences
import io.milis.core.domain.services.remote.RemoteService
import javax.inject.Inject


class AuthRepository @Inject constructor(private val remoteService: RemoteService,
                                         private val sharedPreferences: SharedPreferences) {

}