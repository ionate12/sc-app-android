package au.com.safetychampion.data.domain.models.config.module

import android.graphics.Color
import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.config.Configuration
import au.com.safetychampion.data.util.extension.asStringOrNull

class ThemeConfig(config: Configuration) : BaseConfig(config.valuesMap) {
    private val logoUrl: String?
    private val mainBgColor: String?
    private val headerBarBgColor: String?
    private val headerBarTextColor: String?
    private val folderColor: String?
    private val analyticColorTheme: String?

    init {
        if (config.type != ModuleType.THEME) {
            throw IllegalArgumentException("set wrong params, module Type must be THEME")
        }
        val configMap = config.valuesMap
        logoUrl = configMap["ORGANIZATION_LOGO"]?.asStringOrNull()
        mainBgColor = configMap["MAIN_BG_COLOR"]?.asStringOrNull()
        headerBarBgColor = configMap["HEADER_BAR_BG_COLOR"]?.asStringOrNull()
        headerBarTextColor = configMap["HEADER_BAR_TEXT_COLOR"]?.asStringOrNull()
        folderColor = configMap["FOLDER_COLOR"]?.asStringOrNull()
        analyticColorTheme = configMap["ANALYTICS_COLOR_THEME"]?.asStringOrNull()
    }

    fun getHeaderBarBgColor(): Int? = headerBarBgColor?.let { Color.parseColor(it) }
    fun getHeaderBarTextColor(): Int? = headerBarTextColor?.let { Color.parseColor(it) }
    fun getFolderColor(): Int? = folderColor?.let { Color.parseColor(it) }
    fun getMainBgColor(): Int? = mainBgColor?.let { Color.parseColor(it) }
}
