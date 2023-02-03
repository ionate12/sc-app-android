package com.safetychampion.visitor.repository

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.VisitorApi
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.manager.INetworkManager
import au.com.safetychampion.data.visitor.models.IdObject
import au.com.safetychampion.data.visitor.models.VisitorPayload
import au.com.safetychampion.scmobile.apiservice.RetrofitClientV2
import au.com.safetychampion.scmobile.exceptions.ErrorEnvThrowable
import au.com.safetychampion.scmobile.exts.flatMapToResItem
import au.com.safetychampion.scmobile.exts.flatMapToResItems
import au.com.safetychampion.scmobile.utils.GsonHelper
import au.com.safetychampion.scmobile.visitorModule.models.*
import au.com.safetychampion.util.koinGet
import io.reactivex.Single

/**
 * Created by Minh Khoi MAI on 8/12/20.
 */


interface IVisitorNetworkRepository {

    suspend fun token(orgId: String, siteId: String, pin: String?): Result<VisitorToken> // right now is a token String

    suspend fun siteFetch(token: String): Result<VisitorSite>

    suspend fun formFetch(token: String, formId: String): Result<VisitorForm>

    suspend fun visitFetch(tokens: List<String>): Result<List<VisitorEvidenceWrapper>>

    suspend fun arrive(token: String, profile: VisitorProfile, arriveForm: VisitorForm): Result<VisitorEvidence>

    suspend fun leave(token: String, leaveForm: VisitorForm?): Result<VisitorEvidence>

    suspend fun signOut(profile: VisitorProfile, site: VisitorSite): Result<Any>
}


/**
 * Visitor NETWORK Repository
 * Often handle Network calls only
 */
internal class VisitorNetworkRepository: BaseRepository(), IVisitorNetworkRepository {

    override suspend fun token(orgId: String, siteId: String, pin: String?): Result<VisitorToken> {
        val payload = VisitorPayload.Token(IdObject(orgId), IdObject(siteId), pin)
        VisitorApi.Token(payload).call()
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
