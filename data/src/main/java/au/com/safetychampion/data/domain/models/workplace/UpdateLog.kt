package au.com.safetychampion.data.domain.models.workplace

open class UpdateLog(
    var by: UpdateBy,
    var comment: String,
    var date: String
) {
    open fun printTitle(): String {
        return "${by.name} - ${by.email} - ($date)"
    }
}
