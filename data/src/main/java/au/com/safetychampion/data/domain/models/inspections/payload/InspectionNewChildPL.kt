package au.com.safetychampion.data.domain.models.inspections.payload

import au.com.safetychampion.data.domain.base.BasePL

data class InspectionNewChildPL(
    val dateDue: String,
    val notes: String?
) : BasePL()
