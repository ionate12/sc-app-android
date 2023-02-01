package au.com.safetychampion.data.data.local

/**
 * ISyncable: use for offline task. this should only be declared in NetworkAPI
 * if nw is offline, `instance of ISyncable` will automatically store to Syncable Table.
 * Which later can be synchronized when nw is back online
 */
interface ISyncable {
    fun customKey(): String? = null
}
