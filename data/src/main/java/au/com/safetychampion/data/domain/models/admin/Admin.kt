package au.com.safetychampion.data.domain.models.admin

import com.google.gson.JsonArray

data class Admin(
    var type: String? = null,
    var values: JsonArray? = null
) {
    companion object {
        //    public static final String NODULENAME = "core.module.admin";
        const val NODULENAME = "core.module.admin2"
        const val PERMISSIONS = "PERMISSIONS"
        const val MORPH_PERMISSIONS = "PERMISSIONS"
        const val MORPH_UP = 1
        const val MORPH_DOWN = -1
        const val NO_MORPH = 0
    }
}
