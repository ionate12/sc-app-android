package au.com.safetychampion.data.domain

import au.com.safetychampion.data.domain.base.BasePL

data class BannerListItem(
    val _id: String,
    val type: String,
    val tierType: String,
    val countryAndState: List<CountryAndState>,
    val content: Content,
    val startDate: String,
    val endDate: String
) {
    data class Content(
        val message: String,
        val messageColor: String,
        val bannerColors: List<String>,
        val buttons: List<Button>
    )

    data class Button(
        val label: String,
        val fgColor: String,
        val bgColor: String,
        val click: ClickAction,
        val type: String
    )

    data class ClickAction(
        val target: String,
        val actionType: String,
        val external: Boolean
    )

    data class CountryAndState(
        val country: String,
        val state: String
    )
}

data class BannerListPayload(
    val filter: Filter = Filter()
) : BasePL() {
    data class Filter(
        val enableOnAndroid: Boolean = true
    )
}
