package au.com.safetychampion.data.domain.payload

sealed class ActiveTaskPayload : BasePL {
    data class ActiveTaskPL(
        private val filter: Modules? = null
    ) : ActiveTaskPayload() {
        companion object {
            fun fromModuleName(moduleNames: List<String>): ActiveTaskPL {
                return ActiveTaskPL(
                    filter = Modules(
                        modules = moduleNames
                    )
                )
            }
        }
        data class Modules(val modules: List<String?>)
    }

    object EmptyPL : ActiveTaskPayload()
}
