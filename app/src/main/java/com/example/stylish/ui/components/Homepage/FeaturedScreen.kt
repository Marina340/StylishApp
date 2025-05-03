package com.example.stylish.presentation.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment

@Composable
fun FeaturedSection(
    onSortClicked: () -> Unit = {},
    onFilterClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title
        Text(
            text = "All Featured",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        // Actions Row (Sort & Filter)
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // Sort Button (Text)
            TextButton(
                onClick = onSortClicked,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("Sort ↑↓")
            }

            // Filter Button (Text)
            TextButton(
                onClick = onFilterClicked,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("Filter ▼")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeaturedSection() {
    MaterialTheme {
        FeaturedSection(
            onSortClicked = { /* Handle sort */ },
            onFilterClicked = { /* Handle filter */ }
        )
    }
}