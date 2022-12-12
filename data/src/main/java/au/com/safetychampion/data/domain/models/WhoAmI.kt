package au.com.safetychampion.data.domain.models

data class WhoAmI(
    var _id: String? = null,
    var type: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var phoneCountryCode: String? = null,
    var tier: Tier? = null,
    var orgAdmin: OrgEntity? = null,
    var org: OrgEntity? = null,
    var workplace: OrgEntity? = null,
    var workerGroup: OrgEntity? = null
)

data class OrgEntity(
    var type: String? = null,
    var _id: String? = null,
    var name: String? = null,
    var state: String? = null,
    var country: String? = null,
    var address: String? = null,
    var referenceID: String? = null
)
