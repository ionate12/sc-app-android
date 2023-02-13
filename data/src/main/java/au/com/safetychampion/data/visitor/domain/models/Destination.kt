package au.com.safetychampion.data.visitor.domain.models

import au.com.safetychampion.data.domain.core.SCError

sealed class Destination {
    object Self : Destination()

    class Toast(
        val scError: SCError?,
        duration: Int = android.widget.Toast.LENGTH_SHORT
    ) : Destination()

    class VisitorWizard(
        val site: VisitorSite,
        val evidence: VisitorEvidence?, // null in sign in flow
        val token: String? // visitorVM!!.token = token, null in sign out flow
    ) : Destination()

    class PinCode(val qr: String) : Destination()
}
