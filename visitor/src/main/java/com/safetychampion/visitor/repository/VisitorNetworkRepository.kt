package au.com.safetychampion.scmobile.visitorModule.repository

import au.com.safetychampion.scmobile.apiservice.RetrofitClient
import au.com.safetychampion.scmobile.apiservice.RetrofitClientV2
import au.com.safetychampion.scmobile.exceptions.ErrorEnvThrowable
import au.com.safetychampion.scmobile.exts.flatMapToResItem
import au.com.safetychampion.scmobile.exts.flatMapToResItems
import au.com.safetychampion.scmobile.utils.GsonHelper
import au.com.safetychampion.scmobile.visitorModule.models.*
import io.reactivex.Single

/**
 * Created by Minh Khoi MAI on 8/12/20.
 */


interface IVisitorNetworkRepository {

    fun token(orgId: String, siteId: String, pin: String?): Single<VisitorToken> //right now is a token String

    fun siteFetch(token: String): Single<VisitorSite>

    fun formFetch(token: String, formId: String): Single<VisitorForm>

    fun visitFetch(tokens: List<String>): Single<List<VisitorEvidenceWrapper>>

    fun arrive(token: String, profile: VisitorProfile, arriveForm: VisitorForm): Single<VisitorEvidence>

    fun leave(token: String, leaveForm: VisitorForm?): Single<VisitorEvidence>

    fun signOut(profile: VisitorProfile, site: VisitorSite): Single<Any>
}


/**
 * Visitor NETWORK Repository
 * Often handle Network calls only
 */
class VisitorNetworkRepository: IVisitorNetworkRepository {
    private val client: VisitorAPI by lazy { RetrofitClient.getClient()
            .create(VisitorAPI::class.java) }

    override fun token(orgId: String, siteId: String, pin: String?): Single<VisitorToken> {
        val payload = VisitorPayload.Token(IdObject(orgId), IdObject(siteId), pin)
        return client.token(payload).flatMap { res ->
            return@flatMap if (res.success) {
                Single.just(GsonHelper.getGson().fromJson(res.result, VisitorToken::class.java))
            } else {
                Single.error(ErrorEnvThrowable(res.error))
            }
        }
    }

    override fun siteFetch(token: String): Single<VisitorSite> {
        val payload = VisitorPayload.SiteFetch(token)
        return client.siteFetch(payload).flatMapToResItem(VisitorSite::class.java)
    }

    override fun formFetch(token: String, formId: String): Single<VisitorForm> {
        val payload = VisitorPayload.FormFetch(token, formId)
        return client.formFetch(payload).flatMapToResItem(VisitorForm::class.java)
    }

    override fun visitFetch(tokens: List<String>): Single<List<VisitorEvidenceWrapper>> {
        val payload = VisitorPayload.VisitFetch(tokens)
        return client.visitFetch(payload).flatMapToResItems(VisitorEvidenceWrapper::class.java) //flatMapToResItem"s" will return a List
    }

    override fun arrive(token: String, profile: VisitorProfile, arriveForm: VisitorForm): Single<VisitorEvidence> {
        arriveForm.selectedRole
                ?: return Single.error(ErrorEnvThrowable("Arrive Form has no selected Role. Please assign it before submitting the form"))
        val mForm = arriveForm.toPayload()
        val mVisitor = profile.toPayload(arriveForm.selectedRole!!)
        val payload = VisitorPayload.Arrive(token, mForm, mVisitor)
        return client.arrive(payload).flatMapToResItem(VisitorEvidence::class.java)
    }

    override fun leave(token: String, leaveForm: VisitorForm?): Single<VisitorEvidence> {
        val payload = VisitorPayload.Leave(token, leaveForm?.toPayload()
                ?: VisitorPayload.Form.emptyForm())
        val serializeNullClient = RetrofitClientV2.instantiateClient(true).create(VisitorAPI::class.java)
        return serializeNullClient.leave(payload).flatMapToResItem(VisitorEvidence::class.java)
    }

    override fun signOut(profile: VisitorProfile, site: VisitorSite): Single<Any> {
        return Single.just("")
    }


}
