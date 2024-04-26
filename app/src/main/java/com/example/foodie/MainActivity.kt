package com.example.foodie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodie.ui.theme.FoodieTheme
import com.example.foodie.ui.theme.primaryDark

data class BottomItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,

    )

data class ProductInfo(
    val name: String, val company: String, val color: Color
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val items = listOf(
            BottomItem(
                title = "Share",
                selectedIcon = Icons.Filled.Share,
                unselectedIcon = Icons.Outlined.Share
            ), BottomItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ), BottomItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings
            )
        )
        super.onCreate(savedInstanceState)
        setContent {

            FoodieTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(bottomBar = {

                        NavigationBar (Modifier.background(primaryDark)) {
                            var selectedIndex by rememberSaveable {
                                mutableIntStateOf(1)
                            }

                            items.forEachIndexed { index, unit ->
                                NavigationBarItem(label = { Text(text = unit.title) },
                                    selected = index == selectedIndex,
                                    onClick = { selectedIndex = index },
                                    icon = {
                                        if (index == selectedIndex)
                                            Icon(unit.selectedIcon, unit.title)
                                        else
                                            Icon(unit.unselectedIcon, unit.title)
                                    })
                            }

                        }
                    }) { paddingValues ->
                        ProductList(Modifier.padding(paddingValues))
                    }

                }
            }
        }
    }
}

@Composable
fun Product(info: ProductInfo, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(10.dp, 10.dp)
    ) {
        Box(
            modifier
                .width(60.dp)
                .height(60.dp)
                .background(info.color)
        )
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = info.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(20.dp, 0.dp)
            )
            Text(
                text = info.company,
                style = MaterialTheme.typography.titleSmall,
                modifier = modifier.padding(20.dp, 0.dp),
                color = Color.Gray
            )
        }

    }

}

@Preview
@Composable
fun ProductList(modifier: Modifier = Modifier) {
    val listProductInfo = listOf(
        ProductInfo("Aquarius", "Coca-cola Company", Color.Cyan),
        ProductInfo("Nestea", "Cocacola Company", Color.Blue),
        ProductInfo("Lipton", "Pepsi Company", Color.Yellow),
        ProductInfo("Lays", "Pepsi Company", Color.Red),
        ProductInfo("Patatas Campesinas", "Hacendado", Color.Green),
    )
    LazyColumn {

        listProductInfo.forEach { productInfo ->
            item {
                Product(productInfo)
            }
        }
    }
}
