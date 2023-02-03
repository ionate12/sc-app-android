package au.com.safetychampion.data.visitor.models

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.customvalues.ICusval
import au.com.safetychampion.scmobile.visitorModule.models.VisitorProfile
import au.com.safetychampion.scmobile.visitorModule.models.VisitorRole
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import java.util.TimeZone

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
    ) : BasePL()

    data class SiteFetch(
        val token: String
    ) : BasePL()

    data class FormFetch(
        val token: String,
        val _id: String
    ) : BasePL()

    data class Arrive(
        val token: String,
        val arrive: Form,
        val visitor: Visitor,
        val tz: String = TimeZone.getDefault().id
    ) : BasePL() {
        companion object {
            fun create(profile: VisitorProfile, site: VisitorSite, roleTitle: String) {
            }
        }
    }

    data class Leave(
        val token: String,
        val leave: Form,
        val tz: String = TimeZone.getDefault().id
    ) : BasePL()

    data class VisitFetch(val tokens: List<String>) : BasePL()

    //region SUB classes
    data class Form(
        val _id: String?,
        val type: String = "core.module.visitor.form",
        override var cusvals: MutableList<CustomValue> = mutableListOf()
    ) : BasePL(), ICusval {
        companion object {
            fun emptyForm(): Form = Form(null)
        }
    }

    data class Visitor(
        val role: VisitorRole,
        val pii: Pii
    ) : BasePL()

    data class Pii(
        val name: String,
        val email: String,
        val phone: String,
        val phoneCountryCode: String
    ) : BasePL() {
        fun displayPhone(): String = "+${phoneCountryCode.drop(1)} $phone"
    }

    //endregion
}

data class IdObject(
    val _id: String
)
