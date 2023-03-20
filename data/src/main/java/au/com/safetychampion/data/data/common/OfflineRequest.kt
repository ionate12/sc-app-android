package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.base.BasePL

data class OfflineRequest(val type: Type, val pl: BasePL) {
    enum class Type {
        INSP_NEW_CHILD_START, INSP_START;
    }
}


