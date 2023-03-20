package au.com.safetychampion.data.domain.models.inspections.payload

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.uncategory.Constants

data class InspectionTaskStartPL(
    val dateCommenced: String?,
    val tz: String = Constants.tz
) : BasePL()
