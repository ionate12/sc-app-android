package au.com.safetychampion.scmobile.visitorModule.models

import au.com.safetychampion.scmobile.constantValues.Constants
import au.com.safetychampion.scmobile.modules.incident.model.CusvalPojo

/**
 * Created by Minh Khoi MAI on 10/12/20.
 *
 * ALL PAYLOADs from Visitor Module need to specify in here
 */



class VisitorPayload {
    data class Token(
            val org: IdObject,
            val site: IdObject,
            val pin: String?
    )

    data class SiteFetch(
            val token: String
    )

    data class FormFetch(
            val token: String,
            val _id: String
    )

    data class Arrive(
            val token: String,
            val arrive: Form,
            val visitor: Visitor,
            val tz: String = Constants.tz
    ) {
        companion object {
            fun create(profile: VisitorProfile, site: VisitorSite, roleTitle: String) {

            }
        }
    }

    data class Leave(
            val token: String,
            val leave: Form,
            val tz: String = Constants.tz
    )

    data class VisitFetch(val tokens: List<String>)


    //region SUB classes
    data class Form(
            val _id: String?,
            val type: String = "core.module.visitor.form",
            val cusvals: List<CusvalPojo> = listOf()
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
