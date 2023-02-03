package au.com.safetychampion.scmobile.visitorModule.models

import androidx.recyclerview.widget.DiffUtil
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.visitorModule.VisitorUtil

data class RecentActivityCell(
        var _id: String,
        var arrive: Info,
        var leave: Info?,
        var location: String? = null,
        var subLocation: String? = null,
        var autoSignOutStatus: AutoSignOutStatus,
        var visitTerm: VisitTerm
) {


    fun displayLocation() = "$location - ${subLocation ?: ""}"

    val displayAutoSignOutStatus by lazy {
        when (autoSignOutStatus) {
            AutoSignOutStatus.YES -> "Location-based Sign-out is activated"
            AutoSignOutStatus.PERMISSION_REQUIRE -> "Location-based Sign-out is paused."
            else -> null
        }
    }

    fun isLeaveContainerVisible() = leave != null || autoSignOutStatus != AutoSignOutStatus.NO

    fun leaveColorStatus() = when (autoSignOutStatus) {
        AutoSignOutStatus.YES -> R.color.action_inProgress
        AutoSignOutStatus.NO -> R.color.action_overdue
        AutoSignOutStatus.PERMISSION_REQUIRE -> R.color.ksr_dark_grey_500
    }

    fun displayLeaveDate(): String {
        return if (leave != null) {
            leave!!.dateTimeTwoLines()
        } else {
            "-"
        }
    }

    companion object {
        fun generateId(profileId: String, siteId: String) = "${profileId}_${siteId}"
    }


    object DiffCallback : DiffUtil.ItemCallback<RecentActivityCell>() {

        override fun areItemsTheSame(oldItem: RecentActivityCell, newItem: RecentActivityCell): Boolean {
//            return oldItem._id == newItem._id
            return false
        }

        override fun areContentsTheSame(oldItem: RecentActivityCell, newItem: RecentActivityCell): Boolean = oldItem == newItem
    }

    data class Info(val date: String?, val time: String?) {

        fun dateTimeTwoLines() = VisitorUtil.dateTimeTwoLines(date, time)

        fun dateTime() = VisitorUtil.dateTime(date, time)
    }
}


enum class VisitorStatus(val value: String) {
    IN("In"), OUT("Out")
}

enum class AutoSignOutStatus {
    YES, PERMISSION_REQUIRE, NO;

}