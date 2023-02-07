package au.com.safetychampion.data.visitor.domain.models

sealed class Destination {
    data class VisitorWizard(
        val data: VisitorSite,
        val token: String // visitorVM!!.token = token
    ) : Destination()

    class PinCode(val qr: String) : Destination()
}
