package au.com.safetychampion.scmobile.visitorModule.vm

import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.scmobile.BaseViewModel
import au.com.safetychampion.scmobile.visitorModule.VisitorRepository
import au.com.safetychampion.scmobile.visitorModule.models.VisitorEvidence
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by @Minh_Khoi_MAI on 13/01/21
 */
class VisitorEvidenceDetailVM : BaseViewModel() {
    var evidence: MutableLiveData<VisitorEvidence> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var autoSignOutCheck: MutableLiveData<Boolean> = MutableLiveData()
    private val repo = VisitorRepository
    fun getEvidence(evidenceId: String) {
        cd.add(VisitorRepository.getFullEvidenceById(evidenceId)
                .flatMap { fetchVisitorEvidence(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ rs ->
                    evidence.value = rs
                    autoSignOutCheck.value = rs.isAutoSignOutActive
                }, { throwable ->
                    throwable.printStackTrace()
                    errorMessage.value = throwable.message
                }))
    }

    fun fetchVisitorEvidence(evidence: VisitorEvidence): Single<VisitorEvidence> {
        return VisitorRepository.getVisitFetch(listOf(evidence)).map { list -> list.find { it._id == evidence._id }?.apply { this.site = evidence.site } }
    }

    fun fetchVisitorSite(token: String): Single<VisitorSite> {
        return VisitorRepository.getSiteFetch(token)
    }

    fun setAutoSignOut(aBoolean: Boolean): Completable = repo.setAutoSignOut(evidence.value!!._id, aBoolean)
}