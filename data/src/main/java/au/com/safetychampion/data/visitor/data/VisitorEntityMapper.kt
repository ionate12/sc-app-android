package au.com.safetychampion.data.visitor.data

import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.visitor.domain.models.VisitorSite
import au.com.safetychampion.util.koinInject

internal class VisitorEntityMapper {
    private val gson: IGsonManager by koinInject()

    fun toVisitorSite(en: VisitorSiteEntity): VisitorSite? = gson.gson.fromJson(en.data, VisitorSite::class.java)

    fun toVisitorSiteEntity(site: VisitorSite, profileId: String): VisitorSiteEntity {
        return VisitorSiteEntity(
            _id = site._id,
            profileId = profileId, type = site.type,
            title = site.title,
            description = site.description,
            category = site.category,
            categoryOther = site.categoryOther,
            subcategory = site.subcategory,
            subcategoryOther = site.subcategoryOther,
            data = gson.gson.toJsonTree(site).asJsonObject
        )
    }
}
