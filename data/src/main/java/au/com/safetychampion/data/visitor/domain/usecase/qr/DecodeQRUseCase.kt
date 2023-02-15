package au.com.safetychampion.data.visitor.domain.usecase.qr

internal class DecodeQRUseCase {
    operator fun invoke(qrCode: String): Pair<String, String>? {
        return try {
            val orgSIndex = qrCode.indexOf("/org/") + "/org/".length
            val orgEIndex = qrCode.indexOf("/site/")
            val siteSIndex = qrCode.indexOf("/site/") + "/site/".length
            val siteEIndex = qrCode.length
            val orgString = qrCode.substring(orgSIndex, orgEIndex).trim()
            val siteString = qrCode.substring(siteSIndex, siteEIndex).trim()
            Pair(orgString, siteString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
