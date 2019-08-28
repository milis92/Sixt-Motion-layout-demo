package io.milis.core.domain.services.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = Migrations.version, entities = [], views = [], exportSchema = false)
abstract class Database : RoomDatabase() {


}