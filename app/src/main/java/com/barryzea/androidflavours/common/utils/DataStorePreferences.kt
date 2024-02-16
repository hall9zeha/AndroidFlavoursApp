package com.barryzea.androidflavours.common.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.barryzea.androidflavours.data.entities.PrefsEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 16/02/2024.
 **/

const val USER_PREFERENCES_NAME = "userPreferences"
private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

class DataStorePreferences @Inject constructor(private val context: Context){
    companion object{
        val SESSION_ID = stringPreferencesKey("sessionId")
    }
    suspend fun saveToDatastore(prefsEntity:PrefsEntity){
        context.dataStore.edit{
            it[SESSION_ID] = prefsEntity.sessionId!!
        }
    }
    fun getFromDatastore() = context.dataStore.data.map{
        PrefsEntity(
            sessionId = it[SESSION_ID]?:""
        )
    }
    suspend fun clearDatastore() = context.dataStore.edit {
        it.clear()
    }
}