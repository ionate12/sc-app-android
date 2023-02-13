package au.com.safetychampion.data.visitor.domain.usecase

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.doOnSucceed
import au.com.safetychampion.data.util.extension.jsonObjectOf
import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.domain.models.* // ktlint-disable no-wildcard-imports
import com.google.gson.JsonObject
import java.util.*

class ArriveAndUpdateUseCase : BaseVisitorUseCase() {
    /**
     * On arrive submission succeed, do the followings:
     * 1. Include token and site to newly evidence response.
     * 2. Create and insert a new activity record.
     * 3. Set Shared Pref
     * 4. Save profile to db.
     * @return [VisitorEvidence]
     */
    suspend operator fun invoke(
        payload: VisitorPayload.Arrive,
        site: VisitorSite,
        profile: VisitorProfile
    ): Result<VisitorEvidence> {
        return remoteRepository.arrive(payload)
            .doOnSucceed { evidence ->
                evidence.token = payload.token
                evidence.site = site
                val activityEntity = VisitorActivityEntity(
                    _id = evidence._id,
                    arriveDate = Date(),
                    data = jsonObjectOf(evidence) ?: JsonObject(),
                    profileId = profile._id,
                    isActive = true,
                    status = VisitorStatus.IN,
                    siteId = site._id,
                    siteTitle = site.tier.name,
                    siteDescription = site.title,
                    token = payload.token
                )
                // TODO("SharedPrefCollection.currentVisitorProfileId = profile._id")
                localRepository.saveProfile(profile)
                localRepository.saveActivity(activityEntity)
            }
    }
}
