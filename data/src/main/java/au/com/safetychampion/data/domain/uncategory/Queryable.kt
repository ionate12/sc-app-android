package au.com.safetychampion.data.domain.uncategory

interface Queryable {
    fun containsQueryString(query: String): Boolean
    fun sortKey(): String
}
