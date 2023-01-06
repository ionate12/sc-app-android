package au.com.safetychampion.data.domain // ktlint-disable filename

import com.google.gson.JsonObject

data class InspectionOfflineSubTask(
    val type: Type,
    val request: JsonObject
) {

    enum class Type {
        NEW_CHILD, START
    }
}
