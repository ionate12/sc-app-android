package au.com.safetychampion.data.domain.models.visitor

import androidx.databinding.ObservableField

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
)
