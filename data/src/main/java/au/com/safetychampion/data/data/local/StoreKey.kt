package au.com.safetychampion.data.data.local

import androidx.datastore.preferences.core.*

sealed class StoreKey<T : Any>(open val key: String) {
    abstract fun prefKey(): Preferences.Key<T>
    sealed class AsString(override val key: String) : StoreKey<String>(key) {
        override fun prefKey(): Preferences.Key<String> = stringPreferencesKey(key)
    }

    sealed class AsBoolean(override val key: String) : StoreKey<Boolean>(key) {
        override fun prefKey(): Preferences.Key<Boolean> = booleanPreferencesKey(key)
        // To add more boolean value
    }
    sealed class AsInt(override val key: String) : StoreKey<Int>(key) {
        override fun prefKey(): Preferences.Key<Int> = intPreferencesKey(key)
        // To add more Int Value
    }
    sealed class AsStringSet(override val key: String) : StoreKey<Set<String>>(key) {
        override fun prefKey(): Preferences.Key<Set<String>> = stringSetPreferencesKey(key)
    }

    sealed class AsObject(override val key: String) : AsString(key)

    // region String
    object TokenAuthed : AsString("token_authed")
    object TokenMorphed : AsString("token_morphed")
    // endregion

    // region Object
    object UserInfo : AsObject("user_info")
    object UserCredential : AsObject("user_credential")
    // endregion

    // region String Set
    object VersionBoard : AsObject("version_board")
    object FirebaseToken : AsString("firebase_token")

// endregion
}
