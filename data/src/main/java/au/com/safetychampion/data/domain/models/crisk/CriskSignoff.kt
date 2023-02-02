package au.com.safetychampion.data.domain.models.crisk

data class CriskSignoff(
    val body: Crisk?, // TODO("this is notnull, fix CustomValueDropdown bug in cusval first ")
    val task: CriskTask?,
    val taskId: String = task?._id ?: "",
    val moduleId: String? = body?._id
)
