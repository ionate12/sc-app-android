package au.com.safetychampion.data.domain.models.crisk

import au.com.safetychampion.data.domain.uncategory.Constants

data class CriskArchivePayload(
    val notes: String,
    val tz: String = Constants.tz
)
