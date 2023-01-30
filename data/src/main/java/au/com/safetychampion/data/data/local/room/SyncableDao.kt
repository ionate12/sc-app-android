package au.com.safetychampion.data.data.local.room

import androidx.room.Dao
import androidx.room.Query

@Dao
internal interface SyncableDao : BaseDao<SyncableEntity> {
    @Query("SELECT * FROM Syncable WHERE id = :id")
    suspend fun findById(id: String): SyncableEntity

    @Query("SELECT * FROM Syncable")
    suspend fun getAll(): List<SyncableEntity>

    @Query("UPDATE Syncable SET status =:status WHERE id = :id")
    suspend fun updateStatus(id: String, status: SyncableEntity.Status)
}
