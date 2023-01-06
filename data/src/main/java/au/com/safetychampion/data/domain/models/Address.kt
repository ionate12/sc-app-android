package au.com.safetychampion.data.domain.models

data class Address(
    val street: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val postcode: String? = null
)
