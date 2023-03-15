package au.com.safetychampion.data.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import au.com.safetychampion.data.data.pushnotification.PushNotificationDAO
import au.com.safetychampion.data.domain.models.pushnotification.PushNotificationEntity
import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.data.VisitorSiteEntity
import au.com.safetychampion.data.visitor.data.local.VisitorDAO
import au.com.safetychampion.data.visitor.domain.models.VisitorProfileEntity

@Database(
    entities = [
        StorableEntity::class,
        SyncableEntity::class,
        VisitorActivityEntity::class,
        VisitorProfileEntity::class,
        VisitorSiteEntity::class,
        PushNotificationEntity::class
    ],
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
    internal abstract fun visitorDao(): VisitorDAO
    internal abstract fun pushNotificationDao(): PushNotificationDAO
}
