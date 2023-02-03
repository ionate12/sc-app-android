package au.com.safetychampion.scmobile.visitorModule.models

import androidx.databinding.ObservableField
import au.com.safetychampion.data.visitor.models.VisitorPayload
import au.com.safetychampion.scmobile.database.visitor.VisitorProfileDB
import au.com.safetychampion.scmobile.visitorModule.VisitorUtil

/**
 * Created by @Minh_Khoi_MAI on 22/9/20
 */
data class VisitorProfile(
        var _id: String = "",
        var name: ObservableField<String?> = ObservableField(),
        var phoneNumber: ObservableField<String?> = ObservableField(),
        var email: ObservableField<String?> = ObservableField(),
        var company: ObservableField<String?> = ObservableField(),
        var address: ObservableField<String?> = ObservableField(),
        var phoneCountryCode: ObservableField<String> = ObservableField("(+61)")
) {

    fun toProfileDB() = VisitorProfileDB(_id, name.get() ?: "", email.get(), phoneNumber.get(), phoneCountryCode.get())

    fun toPhoneString() = "${phoneCountryCode.get()} ${phoneNumber.get()}"

    fun getPhoneCountryCodePayload() = this.phoneCountryCode.get()?.replace("(", "")?.replace(")", "")?.replace('+', '0')
    fun toPayload(role: VisitorRole): VisitorPayload.Visitor {
        return VisitorPayload.Visitor(role, VisitorUtil.profileToPii(this))
    }
}