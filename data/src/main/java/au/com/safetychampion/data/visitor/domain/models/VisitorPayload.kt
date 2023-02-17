package au.com.safetychampion.data.visitor.domain.models

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.uncategory.Constants

/**
 * Created by Minh Khoi MAI on 10/12/20.
 *
 * ALL PAYLOADs from Visitor Module need to specify in here
 */

sealed class VisitorPayload : BasePL() {
    data class Token(
        val org: IdObject,
        val site: IdObject,
        val pin: String?
    ) : VisitorPayload()

    data class SiteFetch(
        val token: String
    ) : VisitorPayload()

    data class FormFetch(
        val token: String,
        val _id: String
    ) : VisitorPayload()

    data class Arrive(
        val token: String,
        val arrive: Form,
        val visitor: Visitor,
        val tz: String = Constants.tz
    ) : VisitorPayload() {
        companion object {
            fun create(profile: VisitorProfile, site: VisitorSite, roleTitle: String) {
            }
        }
    }

    data class Leave(
        val token: String,
        val leave: Form,
        val tz: String = Constants.tz
    ) : VisitorPayload()

    data class EvidencesFetch(val tokens: List<String>) : VisitorPayload()

    //region SUB classes
    data class Form(
        val _id: String?,
        val type: String = "core.module.visitor.form",
        val cusvals: List<CustomValue> = listOf()
    ) {
        companion object {
            fun emptyForm(): Form = Form(null)
        }
    }

    data class Visitor(
        val role: VisitorRole,
        val pii: Pii
    )

    data class Pii(
        val name: String,
        val email: String,
        val phone: String,
        val phoneCountryCode: String
    ) {
        fun displayPhone(): String = "+${phoneCountryCode.drop(1)} $phone"
    }

    //endregion
}

data class IdObject(
    val _id: String
)
