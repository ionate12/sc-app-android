package au.com.safetychampion.data.domain.models

class VersionBoard(
    var version: String? = null,
    var title: String? = null,
    var description: String? = null,
    var update: Int = Type.GONE
)
object Type {
    const val GONE = 0
    const val NOT_IMPORTANT = 1
    const val FORCE = 2
}
