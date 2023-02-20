package au.com.safetychampion.data.domain.models.mobileadmin

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.uncategory.Constants

data class VersionPL(
    val platform: Platform,
    val version: String = Constants.VERSION_DEBUG
) : BasePL()
