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

val Context.cartDataStore: DataStore<Preferences> by preferencesDataStore(name = "cart")

class CartManager(private val context: Context) {
    private val CART_KEY = stringSetPreferencesKey("cart_items")
    private val gson = Gson()

    suspend fun addToCart(product: Productt) {
        // Ensure default quantity is at least 1
        val safeProduct = if (product.quantity <= 0) {
            product.copy(quantity = 1)
        } else {
            product
        }

        val json = gson.toJson(safeProduct)

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
        context.cartDataStore.edit { preferences ->
            val currentSet = preferences[CART_KEY]?.toMutableSet() ?: mutableSetOf()
            val updatedSet = currentSet.filterNot {
                val existing = gson.fromJson(it, Productt::class.java)
                existing.id == product.id
            }.toSet()
            preferences[CART_KEY] = updatedSet
        }
    }


    suspend fun clearCart() {
        context.cartDataStore.edit { preferences ->
            preferences.remove(CART_KEY)
        }
    }

    suspend fun updateCartItem(updatedProduct: Productt) {
        // Ensure quantity is at least 1
        val safeProduct = if (updatedProduct.quantity <= 0) {
            updatedProduct.copy(quantity = 1)
        } else {
            updatedProduct
        }

        context.cartDataStore.edit { preferences ->
            val currentSet = preferences[CART_KEY]?.toMutableSet() ?: mutableSetOf()
            val updatedSet = currentSet.mapNotNull {
                val product = gson.fromJson(it, Productt::class.java)
                if (product.id == safeProduct.id) {
                    gson.toJson(safeProduct)
                } else {
                    gson.toJson(product)
                }
            }.toSet()
            preferences[CART_KEY] = updatedSet
        }
    }


}
