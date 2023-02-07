package au.com.safetychampion.data.visitor.domain.models

import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.GeoLatLng
import au.com.safetychampion.data.visitor.data.VisitorEntityMapper
import au.com.safetychampion.data.visitor.data.VisitorSiteEntity

/**
 * Created by @Minh_Khoi_MAI on 16/11/20
 */
data class VisitorSite(
    val _id: String,
    val type: String,
    val tier: TierInfo,
    val forms: List<VisitorFormTemplate>,
    val title: String,
    val description: String,
    val category: String,
    val categoryOther: String? = null,
    val subcategory: String,
    val subcategoryOther: String? = null,
    val createdBy: CreatedBy,
    val tzDateCreated: String,
    val dateCreated: String,
    private val geoData: GeoLatLng? = null,
    private val geofenceRadius: Long? = null,
    private val control: Control? = null
) {
    data class Control(
        val pin: Boolean,
        val geoLeave: Boolean,
        val geoArrive: Boolean
    )

    internal fun toVisitorSiteEntity(mapper: VisitorEntityMapper, profileID: String): VisitorSiteEntity {
        return mapper.toVisitorSiteEntity(this, profileID)
    }
}
