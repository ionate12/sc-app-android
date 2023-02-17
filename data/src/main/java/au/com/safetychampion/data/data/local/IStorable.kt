package au.com.safetychampion.data.data.local

/**
 * IStorable: use for offline mode,
 * `instance of IStorable` data will automatically stored to Storable Table after a successful API Call.
 *  If nw is offline, data will be cached from Storable Table if exists or is valid.
 */
interface IStorable {
    fun customKey(): String? = null
}
