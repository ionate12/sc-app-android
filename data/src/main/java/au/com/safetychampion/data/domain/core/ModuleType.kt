package au.com.safetychampion.data.domain.core

enum class ModuleType(val code: String, val value: String) {
    USER("core.user", "User"),
    ACTION("core.module.action", "Action"),
    CHEMICAL("core.module.chemical", "Chemical"),
    CRISK("core.module.crisk", "Crisk")
}
