package au.com.safetychampion.data.domain.uncategory

sealed class AppToken(open val value: String, val priority: Int) : Comparable<AppToken> {
    // For Login process
    data class MultiUser(override val value: String) : AppToken(value, 1)

    // For Logged user usage
    data class Morphed(override val value: String) : AppToken(value, 2)
    data class Authed(override val value: String) : AppToken(value, 3)

    override fun compareTo(other: AppToken): Int {
        val firstCheck = priority - other.priority
        return when (firstCheck == 0) {
            true -> value.compareTo(other.value)
            false -> firstCheck
        }
    }
}
