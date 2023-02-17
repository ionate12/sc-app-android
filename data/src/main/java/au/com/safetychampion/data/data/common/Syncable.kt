package au.com.safetychampion.data.data.common

import java.util.Date

data class SyncableRepresentative(
    val id: String,
    val status: Status,
    val createdAt: Date = Date()
) {
    enum class Status {
        PENDING, SYNCING, EDITING
    }
}
