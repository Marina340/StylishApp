package com.example.stylish.domain.api//class ProductRepository(private val apiService: DummyJsonApiService) {
//    // The categories we're interested in
//    private val targetCategories = listOf(
//        "beauty",
//        "fashion",
//        "accessories",
//        "clothing",
//        "tops",
//        "womens-dresses",
//        "womens-shoes",
//        "mens-shirts",
//        "mens-shoes",
//        "mens-watches",
//        "womens-watches",
//        "womens-bags",
//        "womens-jewellery",
//        "sunglasses"
//    )
//
//    suspend fun getFilteredProductsAndCategories(): CategoriesResponse {
//        // Get all categories first
//        val allCategories = apiService.getAllCategories()
//
//        // Filter to only the categories we want
//        val filteredCategories = allCategories.filter { category ->
//            targetCategories.any { target ->
//                category.contains(target, ignoreCase = true)
//            }
//        }
//
//        // Get all products and filter by our categories
//        val allProducts = apiService.getAllProducts().products
//        val filteredProducts = allProducts.filter { product ->
//            targetCategories.any { target ->
//                product.category.contains(target, ignoreCase = true)
//            }
//        }
//
//        return CategoriesResponse(
//            categories = filteredCategories,
//            filteredProducts = filteredProducts
//        )
//    }
//}