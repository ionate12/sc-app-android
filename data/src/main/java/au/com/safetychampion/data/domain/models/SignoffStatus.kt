package au.com.safetychampion.data.domain.models

sealed class SignoffStatus(
    var moduleName: String? = "",
    var title: String? = ""
) {

    abstract val message: String

    class OfflineCompleted(
        moduleName: String,
        title: String,
        val isOverwritten: Boolean? = false
    ) : SignoffStatus(moduleName, title) {

        override val message: String
            get() = "OFFLINE - <b><u>$moduleName: $title/u></b> has been save/signed off as a new Offline Task. " +
                "\n**Offline tasks will be synchronised when the device is back online "
    }

    class OnlineCompleted : SignoffStatus() {
        override val message: String
            get() = "You've successfully signed off <b><u>$moduleName: $title</u></b>"
    }

    class OnlineSaved : SignoffStatus() {
        override val message: String
            get() = "You've successfully saved <b><u>$moduleName: $title</u></b>"
    }
}
