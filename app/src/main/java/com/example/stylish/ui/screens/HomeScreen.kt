import com.example.stylish.presentation.widget.SearchBar
import com.example.stylish.presentation.widget.FeaturedSection
import com.example.stylish.presentation.widget.CategoryList
import com.example.stylish.presentation.widget.DealsSection
import com.example.stylish.presentation.widget.ProductGrid
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stylish.presentation.widget.ProductGridd
import com.example.stylish.presentation.widget.horizontal_List

import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.presentation.widget.ProductGridd
import com.example.stylish.presentation.widget.horizontal_List
import com.example.stylish.ui.components.Homepage.BannerSection
import com.example.stylish.ui.components.Homepage.CustomTopBar
import com.example.stylish.ui.components.LoginComponents.PrefsManager

@Composable
fun HomeScreen(navController: NavController , user :LoginResponse?) {
    Scaffold(
        topBar = {
            CustomTopBar()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), // Ensure content doesn't overlap with AppBar
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item { SearchBar() }
            item { FeaturedSection() }
           item { CategoryList () }
            item { BannerSection() }
            item {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Best Seller",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,

                )
            }
            item {
                horizontal_List()
            }
            item { DealsSection() }
            item {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Products",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            item { ProductGrid() }
        }
    }
}
