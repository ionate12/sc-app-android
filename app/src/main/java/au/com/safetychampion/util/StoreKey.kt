package au.com.safetychampion.util

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

sealed class StoreKey<T : Any>(protected open val key: String) {
    abstract fun prefKey(): Preferences.Key<T>
    sealed class AsString(override val key: String) : StoreKey<String>(key) {
        override fun prefKey(): Preferences.Key<String> = stringPreferencesKey(key)

        object TokenAuthed : AsString("token_authed")
        object TokenMorphed : AsString("token_morphed")
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
        // To add more String Set Value
    }
}
