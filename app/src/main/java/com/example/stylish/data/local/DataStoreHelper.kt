package com.example.stylish.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Single, global DataStore instance for preferences
val Context.dataStore by preferencesDataStore(name = "settings")

// Onboarding key
val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")

// Onboarding helper functions
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
