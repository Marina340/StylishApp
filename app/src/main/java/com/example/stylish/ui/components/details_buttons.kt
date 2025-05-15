
import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.buttonElevation
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.stylish.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Reusable Gray Button Component
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.stylish.presentation.widget.Productt
import com.example.stylish.presentation.widget.Screen

@Composable
fun GrayActionButton(
    text: String,
    onClick: () -> Unit,
    img: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageResId = remember(img) {
        context.resources.getIdentifier(img, "drawable", context.packageName)
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(40.dp), // Optional: consistent height
        border = BorderStroke(1.dp, Color.DarkGray),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
           // containerColor = Color.LightGray,
            contentColor = Color.Black
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(15.dp)
                )
            }

            Text(
                text = text,
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Reusable Red Button Component
@Composable
fun RedActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
      //  modifier = modifier.then(Modifier.weight(1f)), // Correct weight usage
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE53935),
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text)
    }
}
@Composable
fun CartButtons(navController: NavController, product: Productt) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color(0xFF9E9E9E)),
            color = Color.Transparent,
            onClick = {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("product", product)
                navController.navigate(Screen.ShoppingScreen.route)
            }
        ) {
            Text(
                text = "Add to cart",
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(4.dp),
            color = Color(0xFF388E3C),
            onClick = {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("product", product)
                navController.navigate("checkout")
            }
        ) {
            Text(
                text = "Buy Now",
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
