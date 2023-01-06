package au.com.safetychampion.data.domain.models

class VersionPlatform(var name: String? = null) {
    companion object {
        const val ANDROID = "ANDROID"
        const val IOS = "IOS"
        const val ALL = "ALL"
    }
}
