package au.com.safetychampion.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import au.com.safetychampion.data.local.BaseAppDataStore

class AppDataStore(context: Context) : BaseAppDataStore() {
    companion object {
        private val Context.appDataStore by preferencesDataStore(name = "SCDataStore")
    }

    override val store: DataStore<Preferences> = context.appDataStore
}
