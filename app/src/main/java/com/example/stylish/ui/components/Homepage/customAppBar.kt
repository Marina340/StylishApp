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
import com.example.stylish.R
import com.example.stylish.data.Models.LoginResponse

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
            Image(
                painter = painterResource(id = R.drawable.userprofile),
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
        },
        actions = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}

@Composable
fun SidebarUI() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .clip(RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp))
            .background(Color.White.copy(alpha = 0.9f))
            .padding(16.dp)
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
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Stylish",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                SectionTitle("Overview")
                MenuEntry(Icons.Default.Dashboard, "Dashboard")
                MenuEntry(Icons.Default.ShoppingBag, "Product")
                MenuEntryWithBadge(Icons.Default.Email, "Messages", "1")
                MenuEntry(Icons.Default.ShoppingCart, "Order")

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                SectionTitle("Account")
                MenuEntry(Icons.Default.Settings, "Setting")
                MenuEntry(Icons.Default.Logout, "Logout")
            }
        }
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

@Composable
fun MenuEntry(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
            .clickable { /* TODO: Handle navigation */ }
    ) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun MenuEntryWithBadge(icon: ImageVector, label: String, badgeText: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
            .clickable { /* TODO: Handle navigation */ }
    ) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color.Blue, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                badgeText,
                color = Color.White,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
