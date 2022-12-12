package au.com.safetychampion.data.domain.models

data class TimeField(
    private val hours: Int = 0,
    private val minutes: Int = 0
) {
    override fun toString(): String {
        return if (hours < 0 && minutes < 0) "null" else String.format("%02d:%02d", hours, minutes)
    }
}
