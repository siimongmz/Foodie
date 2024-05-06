package com.example.foodie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.foodie.ui.theme.FoodieTheme

data class BottomItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,

    )
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
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
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Scaffold(
                        bottomBar = {

                            NavigationBar{
                                var selectedIndex by rememberSaveable {
                                    mutableIntStateOf(1)
                                }


                                items.forEachIndexed { index, unit ->
                                    NavigationBarItem(label = { Text(text = unit.title) },
                                        selected = index == selectedIndex,
                                        onClick = { selectedIndex = index },
                                        icon = {
                                            if (index == selectedIndex) Icon(
                                                unit.selectedIcon, unit.title
                                            )
                                            else Icon(unit.unselectedIcon, unit.title)
                                        })
                                }

                            }
                        },
                        floatingActionButton = { ScannerFAB() },
                        topBar = { TopBar() },
                        contentWindowInsets = WindowInsets.safeDrawing
                    )
                    { paddingValues ->

                        MainScreen(Modifier.padding(paddingValues))

                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {

            Text(
                text = "Foodie",
                letterSpacing = TextUnit(2.5F, TextUnitType.Sp),
                fontWeight = FontWeight.SemiBold
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.secondary,
        ), navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                modifier = Modifier.padding(10.dp, 10.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

    )
}

@Preview
@Composable
fun ScannerFAB(modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { /*TODO*/ },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Icon(painterResource(id = R.drawable.barcode_scanner), "Scann")
    }
}

