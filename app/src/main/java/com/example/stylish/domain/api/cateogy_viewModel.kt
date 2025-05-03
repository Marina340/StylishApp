//import androidx.compose.runtime.Recomposer
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//
//class ProductViewModel : ViewModel() {
//    private val apiService = DummyJsonApiService.create()
//    private val repository = ProductRepository(apiService)
//
//    private val _productsWithCategories = mutableStateOf<CategoriesResponse?>(null)
//    val productsWithCategories: State<CategoriesResponse?> = _productsWithCategories
//
//    private val _isLoading = mutableStateOf(false)
//    val isLoading: State<Boolean> = _isLoading
//
//    private val _error = mutableStateOf<String?>(null)
//    val error: State<String?> = _error
//
//    init {
//        loadData()
//    }
//
//    fun loadData() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                _productsWithCategories.value = repository.getFilteredProductsAndCategories()
//            } catch (e: Exception) {
//                _error.value = e.message ?: "Unknown error occurred"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//}