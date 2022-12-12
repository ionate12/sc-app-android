package au.com.safetychampion.data.domain.models.hr

import au.com.safetychampion.data.domain.models.SCHolderLink
import au.com.safetychampion.data.domain.models.contractor.LookupItem
import au.com.safetychampion.data.domain.models.incidents.InjuredPersonLink
import au.com.safetychampion.data.domain.uncategory.TaskType

data class HrLookupItem(
    override var _id: String? = null,
    var orgName: String? = null,
    var hrId: String? = null,
    var name: String? = null,
    var position: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var isHeader: Boolean = false
) : LookupItem {

    fun import(args: List<String>) {
        this._id = args.getOrNull(0)
        this.orgName = args.getOrNull(1)
        this.hrId = args.getOrNull(2)
        this.name = args.getOrNull(3)
        this.position = args.getOrNull(4)
        this.email = args.getOrNull(5)
        this.phone = args.getOrNull(6)
    }

    override fun queryString(): String {
        return "${orgName?.toLowerCase()?.trim()}${hrId?.toLowerCase()?.trim()}" +
            "${name?.toLowerCase()?.trim()}${position?.toLowerCase()?.trim()}"
    }

    fun toInjuredPersonLink(): InjuredPersonLink {
        var obj = InjuredPersonLink()
        obj._id = _id
        obj.name = name
        obj.position = position
        obj.referenceId = hrId
        obj.type = TaskType.HR
        return obj
    }

    override fun toSCHolderLink() = SCHolderLink(
        businessName = orgName,
        email = email,
        name = name,
        type = TaskType.HR,
        _id = _id,
        contactEmail = null,
        contactName = null
    )

    companion object {
        fun fromRawData(data: List<String?>): HrLookupItem {
            return with(data) {
                HrLookupItem(
                    _id = getOrNull(0),
                    orgName = getOrNull(1),
                    hrId = getOrNull(2),
                    name = getOrNull(3),
                    position = getOrNull(4),
                    email = getOrNull(5),
                    phone = getOrNull(6)
                )
            }
        }
    }
}
