package au.com.safetychampion.data.visitor.data

import androidx.room.Embedded
import androidx.room.Relation
import au.com.safetychampion.data.visitor.domain.models.VisitorProfileEntity

internal data class VisitorProfileAndSiteEntity(
    @Embedded val profile: VisitorProfileEntity,
    @Relation(
        parentColumn = "_id",
        entityColumn = "profileId"
    )
    val sites: List<VisitorSiteEntity>
)
