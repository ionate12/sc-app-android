package au.com.safetychampion.data.domain.models.customvalues

enum class CusvalType(val value: String) {
    Text("text"),
    Email("email"),
    Telephone("tel"),
    Number("number"),
    Date("date"),
    Time("time"),
    Color("color"),
    Url("url"),
    SingleDrop("select_single"),
    MultipleDrop("select_multiple"),
    Checkbox("checkbox"),
    Radio("radio"),
    Currency("currency");

    companion object {
        fun fromString(value: String): CusvalType? {
            return values().firstOrNull { it.value == value }
        }
    }
}
