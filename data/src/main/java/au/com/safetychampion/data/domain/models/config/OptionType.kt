package au.com.safetychampion.data.domain.models.config

enum class OptionType(val value: Int) {
    DISABLED(0), OPTIONAL(1), REQUIRED(2);
    companion object {
        fun fromInt(value: Int): OptionType {
            return values().find { it.value == value } ?: DISABLED
        }
    }
}
