package au.com.safetychampion.scmobile.visitorModule.vm

import au.com.safetychampion.scmobile.BaseViewModel
import au.com.safetychampion.scmobile.visitorModule.VisitorRepository
import au.com.safetychampion.scmobile.visitorModule.models.VisitorEvidence
import io.reactivex.Single

/**
 * Created by Minh Khoi MAI on 1/3/21.
 */
class AutoSignoutNotificationReceivedVM: BaseViewModel() {
    val repo = VisitorRepository

    /**
     * Fetch Visit fully from Server including site from DB
     */
    fun fetchVisitAndIncludeSiteFromDB(evidenceId: String): Single<VisitorEvidence> {
        return repo.getFullEvidenceById(evidenceId)
                .flatMap { localEvidence -> repo.getVisitFetch(listOf(localEvidence)).map { list -> list.find { it._id == evidenceId } } }
                .flatMap { evidence ->
                    return@flatMap repo.loadSite(evidence.site._id)
                            .map { site ->
                                evidence.apply { this.site = site }
                            }.onErrorResumeNext { Single.just(evidence) } }
    }



}