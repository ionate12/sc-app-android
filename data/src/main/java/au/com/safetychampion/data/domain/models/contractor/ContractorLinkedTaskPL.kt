package au.com.safetychampion.data.domain.models.contractor

import au.com.safetychampion.data.domain.base.BasePL

data class ContractorLinkedTaskPL(
    val from: Id
) : BasePL() {
    class Id(
        val _id: String
    )
}
