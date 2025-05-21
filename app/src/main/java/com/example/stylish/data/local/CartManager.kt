import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import com.example.stylish.data.Models.models.Productt
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// ✅ This goes outside the class, only once per file/app
val Context.cartDataStore: DataStore<Preferences> by preferencesDataStore(name = "cart")

class CartManager(private val context: Context) {
    private val CART_KEY = stringSetPreferencesKey("cart_items")
    private val gson = Gson()

    suspend fun addToCart(product: Productt) {
        val json = gson.toJson(product)
        context.cartDataStore.edit { preferences ->
            val currentSet = preferences[CART_KEY]?.toMutableSet() ?: mutableSetOf()
            currentSet.add(json)
            preferences[CART_KEY] = currentSet
        }
    }

    val cartItems: Flow<List<Productt>> = context.cartDataStore.data.map { preferences ->
        preferences[CART_KEY]?.map { gson.fromJson(it, Productt::class.java) } ?: emptyList()
    }
    suspend fun removeFromCart(product: Productt) {
        val json = gson.toJson(product)
        context.cartDataStore.edit { preferences ->
            val currentSet = preferences[CART_KEY]?.toMutableSet() ?: mutableSetOf()
            currentSet.remove(json)
            preferences[CART_KEY] = currentSet
        }
    }
    suspend fun clearCart() {
        context.cartDataStore.edit { preferences ->
            preferences.remove(CART_KEY)
        }
    }
}
