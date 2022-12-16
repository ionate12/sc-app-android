package au.com.safetychampion.data.domain.payload

import au.com.safetychampion.data.domain.base.BasePL

data class ActiveTaskPL(
    private val filter: Modules? = null
) : BasePL() {
    data class Modules(val modules: List<String?>?)
}
