package au.com.safetychampion.data.domain.models.contractor

import au.com.safetychampion.data.domain.base.BasePL

data class ContractorLinkedTasks(
    val _id: String,
    val taskIds: List<String>
) : BasePL()
