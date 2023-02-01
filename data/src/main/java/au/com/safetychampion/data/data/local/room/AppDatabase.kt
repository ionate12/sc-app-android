package au.com.safetychampion.data.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [StorableEntity::class, SyncableEntity::class],
    version = AppDatabase.VERSION,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
        const val DB_NAME = "SCMobileDB-Room2"
    }
    internal abstract fun storableDao(): StorableDao
    internal abstract fun syncableDao(): SyncableDao
}
