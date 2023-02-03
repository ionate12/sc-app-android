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
class VisitorNetworkRepository: BaseRepository(), IVisitorNetworkRepository {

    override suspend fun token(orgId: String, siteId: String, pin: String?): Result<VisitorToken> {
        val payload = VisitorPayload.Token(IdObject(orgId), IdObject(siteId), pin)
        return VisitorApi.Token(payload).call("")
    }

    override suspend fun siteFetch(token: String): Result<VisitorSite> {
        val payload = VisitorPayload.SiteFetch(token)
        return VisitorApi.SiteFetch(payload).call()
    }

    override suspend fun formFetch(token: String, formId: String): Result<VisitorForm> {
        val payload = VisitorPayload.FormFetch(token, formId)
        return VisitorApi.FormFetch(payload).call()
    }

    override suspend fun visitFetch(tokens: List<String>): Result<List<VisitorEvidenceWrapper>> {
        val payload = VisitorPayload.VisitFetch(tokens)
        return VisitorApi.VisitFetch(payload).callAsList() //flatMapToResItem"s" will return a List
    }

    override suspend fun arrive(token: String, profile: VisitorProfile, arriveForm: VisitorForm): Result<VisitorEvidence> {
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
