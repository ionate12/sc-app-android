package au.com.safetychampion.data.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
internal interface StorableDao : BaseDao<StorableEntity> {

    @Query("SELECT * FROM STORABLE WHERE id = :id")
    suspend fun findById(id: String): StorableEntity?
}

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(vararg obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(obj: T)

    @Update
    fun update(obj: T)

    @Insert
    suspend fun insert(obj: T)

    @Delete
    suspend fun delete(obj: T)
}
