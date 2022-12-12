package au.com.safetychampion.data.domain.models.customvalues

data class CustomValueOption(
    var static: String? = null,
    var name: String? = null,
    var icon: String? = null,
    var options: List<CustomValueOption>? = null,
    @Transient var isSeparator: Boolean = false,
    var isResult: Boolean = false,
    var stringValue: String? = null
)
