import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.stylish.R
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.presentation.widget.Screen
import com.example.stylish.ui.theme.DatkPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    navController: NavController,
    user: LoginResponse?,
    onMenuClick: () -> Unit
) {
    val profileImageUri = user?.image.takeIf { !it.isNullOrEmpty() }
        ?: "android.resource://${LocalContext.current.packageName}/${R.drawable.img}"

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Brand Logo",
                    modifier = Modifier.size(30.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Stylish",
                    fontSize = 20.sp
                )
            }
        },
        navigationIcon = {
            // Moved the menu icon to the left
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            // Moved the user image to the right
            Image(
                painter = rememberImagePainter(
                    data = profileImageUri,
                    builder = {
                        error(R.drawable.img) // صورة افتراضية عند حدوث خطأ
                    }
                ),
                contentDescription = "User Profile",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
                        navController.navigate("profile")
                    }
            )
        }
    )
}
@Composable
fun SidebarUI(navController: NavController, currentRoute: String? , user: LoginResponse?) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .clip(RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp))
            .background(Color.White.copy(alpha = 0.9f))
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Logo
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Logo",
                        tint = DatkPink
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Stylish",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = DatkPink
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                SectionTitle("Overview")

                MenuEntry(
                    icon = Icons.Default.Dashboard,
                    label = "Dashboard",
                    isSelected = currentRoute == "main"  // highlight if already on the main screen
                ) {
                    if (currentRoute != "main") {
                        navController.navigate("main") {
                            launchSingleTop = true
                        }
                    }
                }


                MenuEntry(
                    icon = Icons.Default.ShoppingBag,
                    label = "Product",
                    isSelected = currentRoute == Screen.ShoppingBagScreen.route
                ) {
                    if (currentRoute != Screen.ShoppingBagScreen.route) {
                        navController.navigate(Screen.ShoppingBagScreen.route) {
                            launchSingleTop = true
                        }
                    }
                }

                MenuEntry(
                    icon = Icons.Default.Favorite,
                    label = "Favourite",
                    isSelected = currentRoute == Screen.WishListPage.route
                ) {
                    if (currentRoute != Screen.WishListPage.route) {
                        navController.navigate(Screen.WishListPage.route) {
                            launchSingleTop = true
                        }
                    }
                }

                MenuEntry(
                    icon = Icons.Default.ShoppingCart,
                    label = "Order",
                    isSelected = currentRoute == Screen.ShoppingScreen.route
                ) {
                    if (currentRoute != Screen.ShoppingScreen.route) {
                        navController.navigate(Screen.ShoppingScreen.route) {
                            launchSingleTop = true
                        }
                    }
                }

                MenuEntry(
                    icon = Icons.Default.Settings,
                    label = "Setting",
                    isSelected = currentRoute == Screen.SettingsScreen.route
                ) {
                    if (currentRoute != Screen.SettingsScreen.route) {
                        navController.navigate(Screen.SettingsScreen.route) {
                            launchSingleTop = true
                        }
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))


                MenuEntry(
                    icon = Icons.Default.AccountCircle,
                    label = "LogOut",
                    isSelected = currentRoute == Screen.Profile.route
                ) {
                    if (currentRoute != Screen.Profile.route) {
                        navController.navigate("login") {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuEntry(
    icon: ImageVector,
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val textColor = if (isSelected) DatkPink else Color.Black
    val backgroundColor = if (isSelected) Color(0x1AFF0000) else Color.Transparent

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable(enabled = !isSelected) { onClick() }
            .padding(8.dp)
    ) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(20.dp), tint = textColor)
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = textColor)
    }
}


@Composable
fun SectionTitle(title: String) {
    Text(
        title.uppercase(),
        color = Color.Gray,
        fontSize = 12.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
