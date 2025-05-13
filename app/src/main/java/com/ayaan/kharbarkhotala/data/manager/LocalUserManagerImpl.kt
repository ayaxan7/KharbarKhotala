package com.ayaan.kharbarkhotala.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.ayaan.kharbarkhotala.domain.manager.LocalUserManager
import com.ayaan.kharbarkhotala.utils.Constants
import com.ayaan.kharbarkhotala.utils.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit
import javax.inject.Inject

// Define the DataStore as an extension property on Context
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

class LocalUserManagerImpl @Inject constructor(
    private val context: Context
) : LocalUserManager {

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.APP_ENTRY] ?: false
        }
    }
}

private object PreferenceKeys {
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
}