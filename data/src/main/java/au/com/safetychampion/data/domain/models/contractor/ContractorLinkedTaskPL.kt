package au.com.safetychampion.data.domain.models.contractor

import au.com.safetychampion.data.domain.base.BasePL

data class ContractorLinkedTaskPL(
    var from: id
) : BasePL()

data class id(
    var _id: String
)
