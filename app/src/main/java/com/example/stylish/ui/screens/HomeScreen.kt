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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stylish.ui.components.Homepage.BannerSection
import com.example.stylish.ui.components.Homepage.CustomTopBar


@Composable
fun HomeScreen(navController: NavController) {
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
            item { CategoryList() }
            item { BannerSection() }
            item { DealsSection() }
            item {
                Text(
                    text = "Products",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item { ProductGrid() }
        }
    }
}
