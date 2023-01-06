package au.com.safetychampion.data.domain.models.contractor

data class Contact(
    var phones: List<String>? = listOf(),
    var emails: List<String> = listOf(),
    var role: String? = null,
    var roleOther: Any? = null,
    var notes: String? = null,
    var name: String? = null,
    var quickView: Boolean = false
) {
    fun displayRole(): String? {
        if (role?.toLowerCase() == "other") {
            return "Other ($roleOther)"
        }
        return role
    }

    fun toQueryString(): String {
        return "$name"
    }
}
