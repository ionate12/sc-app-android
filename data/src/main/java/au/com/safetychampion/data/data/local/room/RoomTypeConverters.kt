package au.com.safetychampion.data.data.local.room

import androidx.room.TypeConverter
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.util.extension.koinGet
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import java.util.Date

class RoomTypeConverters {
    val gson by lazy { koinGet<IGsonManager>().gson }

    @TypeConverter
    fun toJsonObject(value: String?): JsonObject? {
        return try {
            gson.fromJson(value, JsonObject::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    @TypeConverter
    fun fromJsonObject(obj: JsonObject?): String? {
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromTimestampToDate(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

//    @TypeConverter
//    fun visitorStatusToString(visitorStatus: VisitorStatus): String = visitorStatus.toString()
//
//    @TypeConverter
//    fun stringToVisitorStatus(statusString: String): VisitorStatus = VisitorStatus.valueOf(statusString)
}
