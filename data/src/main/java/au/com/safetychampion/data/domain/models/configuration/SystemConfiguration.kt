package au.com.safetychampion.data.domain.models.configuration

import au.com.safetychampion.data.domain.models.mobileadmin.MobileAdmin

data class SystemConfiguration(
    var mobileDataActive: Boolean = false,
    var warningBeforeExit: Boolean = false,
    var simpleAnnouncement: MobileAdmin? = null
)
