package au.com.safetychampion.data.domain.models

import android.content.res.Configuration

class GlbScreenConfig {

    companion object {
        private val INSTANCE = GlbScreenConfig()
        fun getInstance() = INSTANCE
    }

    var screenSize: Int? = null
        get() {
            return field ?: Configuration.SCREENLAYOUT_SIZE_NORMAL
        }
        set(value) {
            if (field == null) field = value
        }

    var fontScale: Float? = null
        get() {
            return field ?: 1.0f
        }
        set(value) {
            if (field == null) field = value
        }
}
