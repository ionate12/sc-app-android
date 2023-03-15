package au.com.safetychampion.data.data.pushnotification

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import au.com.safetychampion.data.domain.models.pushnotification.PushNotificationEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface PushNotificationDAO {

    @Query("SELECT * FROM PushNotification WHERE _id =:itemId")
    suspend fun getById(itemId: String): PushNotificationEntity

    @Query("SELECT * FROM PushNotification WHERE userId =:userId")
    fun getAll(userId: String): Flow<List<PushNotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: PushNotificationEntity)

    @Query("DELETE FROM PushNotification WHERE _id=:itemId")
    suspend fun delete(itemId: String)
}
