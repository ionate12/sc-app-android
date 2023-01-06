package au.com.safetychampion.data.domain.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.JsonObject

@Entity(indices = [Index("userId")], tableName = "OfflineTasks")
data class OfflineTask(
    @PrimaryKey
    var _id: String,
    var userId: String,

    /**
     * wpId means Workplace Id .
     */
    var wpId: String? = null,

    /**
     * OwnerId is intented to use for CorrectiveAction.
     */
    var ownerId: // ownerId determine of where this is a sub-task
        String? = null,

    /**
     * ParentId
     */
    var type: String? = null,
    var title: String? = null,
    var createdDate: String? = null,
    var createdTime: Long = 0,
    var status: String? = null,
    var reference: String? = null,

    // Data will consist Form-Data and Attachment package(Uri and Bitmap) JsonObject of FilePart.class
    var data: JsonObject? = null,
    var errorEnv: JsonObject? = null,
    var requestType: String? = null,
    var priority: Int = 0,

    /**
     * To detect if this task is opening for editing.
     */
    var isOpening: Boolean = false,

    /**
     * Error Message if it error
     */
    var description: String? = null
) {
    constructor(
        _id: String,
        userId: String,
        title: String,
        type: String,
        obj: JsonObject
    ) : this(
        _id = _id,
        userId = userId,
        title = title,
        type = type,
        wpId = null,
        ownerId = null,
        createdDate = null,
        createdTime = 0,
        status = null,
        reference = null,
        data = obj,
        isOpening = false,
        description = null
    )

    constructor(
        _id: String,
        userId: String,
        wpId: String,
        title: String,
        type: String,
        obj: JsonObject
    ) : this(
        _id = _id,
        userId = userId,
        wpId = wpId,
        title = title,
        type = type,
        data = obj
    )
}
