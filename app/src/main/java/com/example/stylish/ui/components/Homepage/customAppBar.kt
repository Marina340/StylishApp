import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stylish.R
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CustomTopBar(navController: NavController,onMenuClick: () -> Unit
                 ) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // 🔹 Brand Logo
                Image(
                    painter = painterResource(id = R.drawable.logo), // Replace with your logo
                    contentDescription = "Brand Logo",
                    modifier = Modifier.size(30.dp),
                )

                Spacer(modifier = Modifier.width(8.dp)) // Space between logo & text

                // 🔹 Brand Name
                Text(
                    text = "stylish",
                    fontSize = 20.sp
                )
            }
        },

        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.userprofile),
                contentDescription = "User Profile",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate("profile")
                    }
            )
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
                MenuItem(Icons.Default.Dashboard, "Dashboard")
                MenuItem(Icons.Default.ShoppingBag, "Product")
                MenuItemWithBadge(Icons.Default.Email, "Messages", "1")
                MenuItem(Icons.Default.ShoppingCart, "Order")

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                SectionTitle("Account")
                MenuItem(Icons.Default.Settings, "Setting")
                MenuItem(Icons.Default.Logout, "Logout")
            }

            Column {
                // Theme toggle
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Icon(Icons.Default.WbSunny, contentDescription = null)
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Switch(checked = false, onCheckedChange = {})
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Icon(Icons.Default.NightlightRound, contentDescription = null)
//                }

//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Profile
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Image(
//                        painter = rememberAsyncImagePainter("https://i.pravatar.cc/150?img=3"),
//                        contentDescription = "User",
//                        modifier = Modifier
//                            .size(44.dp)
//                            .clip(CircleShape)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Column {
//                        Text("john doe", fontWeight = FontWeight.Bold)
//                        Text("j.doe2541@gmail.com", fontSize = 12.sp, color = Color.Gray)
//                    }
//                }
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
fun MenuItem(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun MenuItemWithBadge(icon: ImageVector, label: String, badgeText: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
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

//@Preview(showBackground = true)
//@Composable
//fun PreviewTopBar() {
//    CustomTopBar()
//}
