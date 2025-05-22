package com.example.stylish.data

import android.content.Context
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.stylish.data.local.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
class FavoriteDataStore(private val context: Context) {

    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorite_product_ids")
    }

    // Initialize DataStore
    private val dataStore = context.dataStore

    val favoriteIds: Flow<Set<String>> = dataStore.data
        .map { prefs -> prefs[FAVORITES_KEY] ?: emptySet() }

    suspend fun toggleFavorite(id: Int) {
        dataStore.edit { prefs ->
            val current = prefs[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()
            if (current.contains(id.toString())) {
                current.remove(id.toString()) // Remove if already a favorite
            } else {
                current.add(id.toString()) // Add if not a favorite
            }
            prefs[FAVORITES_KEY] = current
        }
    }

    suspend fun getFavoriteIds(): Set<String> {
        return dataStore.data.first()[FAVORITES_KEY] ?: emptySet()
    }
}

