import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.stylish.R
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CustomTopBar() {
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
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.width(8.dp)) // Space between logo & text

                // 🔹 Brand Name
                Text(
                    text = "stylish",
                    fontSize = 20.sp
                )
            }
        },
        // profile image for user :
        navigationIcon = {
            Image(

                painter = painterResource(id = R.drawable.userprofile), // Replace with your user image
                contentDescription = "User Profile",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(40.dp)
                    .clip(CircleShape) // Makes it circular
            )

            // 🔹 feature 1: language changing

        },
        actions = {
            // 🔹 Drawable Action Icon (Menu icon) on the right
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Language,  // Built-in hamburger icon
                    contentDescription = "Menu"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    CustomTopBar()
}
