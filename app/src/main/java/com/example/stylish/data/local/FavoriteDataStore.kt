package com.example.stylish.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FavoriteDataStore(private val context: Context) {

    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorite_product_ids")
    }

    // Use shared dataStore from DataStoreHelper.kt
    private val dataStore = context.dataStore

    val favoriteIds: Flow<Set<String>> = dataStore.data
        .map { prefs -> prefs[FAVORITES_KEY] ?: emptySet() }

    suspend fun toggleFavorite(id: String) {
        dataStore.edit { prefs ->
            val current = prefs[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()
            if (current.contains(id.toString())) {
                current.remove(id.toString())
            } else {
                current.add(id.toString())
            }
            prefs[FAVORITES_KEY] = current
        }
    }

    suspend fun getFavoriteIds(): Set<String> {
        return dataStore.data.first()[FAVORITES_KEY] ?: emptySet()
    }
}
