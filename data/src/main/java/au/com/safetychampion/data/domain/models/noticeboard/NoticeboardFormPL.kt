package au.com.safetychampion.data.domain.models.noticeboard

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

data class NoticeboardFormPL(
    val board: Tier,
    val cusvals: List<CustomValue>,
    val form: Tier,
    val submitTo: List<String>,
    val tz: String
) : BasePL()
