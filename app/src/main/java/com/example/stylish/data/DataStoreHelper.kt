package com.example.stylish.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")
val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")

suspend fun isOnboardingCompleted(context: Context): Boolean {
    return context.dataStore.data.map { prefs ->
        prefs[ONBOARDING_KEY] ?: false
    }.first()
}

suspend fun setOnboardingCompleted(context: Context) {
    context.dataStore.edit { prefs ->
        prefs[ONBOARDING_KEY] = true
    }
}
