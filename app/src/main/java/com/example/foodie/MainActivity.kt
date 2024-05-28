package com.example.foodie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.foodie.events.SearchInfoEvent
import com.example.foodie.persistance.FoodieDatabase
import com.example.foodie.ui.components.FullScreenImage
import com.example.foodie.ui.components.ProductInfoCard
import com.example.foodie.ui.components.ScannerFAB
import com.example.foodie.ui.components.TopBar
import com.example.foodie.ui.screens.InformationScreen
import com.example.foodie.ui.screens.MainScreen
import com.example.foodie.ui.screens.ProfileScreen
import com.example.foodie.ui.screens.SCREENS
import com.example.foodie.ui.theme.FoodieTheme
import com.example.foodie.viewModels.MainAppViewModel
import com.example.foodie.viewModels.SearchInfoViewModel

data class BottomItem(
    val title: String,
    var route: SCREENS,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,

    )

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            FoodieDatabase::class.java,
            "foodie.db"
        ).build()
    }

    private val mainAppViewModel by viewModels<MainAppViewModel>()

    private val searchInfoViewModel by viewModels<SearchInfoViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchInfoViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(key1 = "d") {
                searchInfoViewModel.onEvent(SearchInfoEvent.OpenAplication)
            }
            val items = listOf(
                BottomItem(
                    title = stringResource(R.string.info),
                    route = SCREENS.INFO,
                    selectedIcon = Icons.Filled.Info,
                    unselectedIcon = Icons.Outlined.Info
                ), BottomItem(
                    title = stringResource(R.string.home),
                    route = SCREENS.HOME,
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home
                ), BottomItem(
                    title = stringResource(R.string.allergies),
                    route = SCREENS.PROFILE,
                    selectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_no_food_24),
                    unselectedIcon = ImageVector.vectorResource(id = R.drawable.outline_no_food_24)
                )
            )
            var selectedRoute by rememberSaveable {
                mutableStateOf(SCREENS.HOME)
            }

            FoodieTheme {


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Scaffold(
                        bottomBar = {

                            NavigationBar {


                                items.forEach { unit ->
                                    NavigationBarItem(label = { Text(text = unit.title) },
                                        selected = unit.route == selectedRoute,
                                        onClick = { selectedRoute = unit.route },
                                        icon = {
                                            if (unit.route == selectedRoute) Icon(
                                                unit.selectedIcon, unit.title
                                            )
                                            else Icon(unit.unselectedIcon, unit.title)
                                        })
                                }

                            }
                        },
                        floatingActionButton = {
                            ScannerFAB(
                                onEvent = searchInfoViewModel::onEvent
                            )
                        },
                        topBar = { TopBar() },
                        contentWindowInsets = WindowInsets.safeDrawing
                    )
                    { paddingValues ->
                        when (selectedRoute) {
                            SCREENS.HOME -> MainScreen(
                                modifier = Modifier.padding(paddingValues),
                                searchInfoState = searchInfoViewModel.state.collectAsState().value,
                                onEvent = searchInfoViewModel::onEvent

                            )

                            SCREENS.PROFILE -> ProfileScreen(
                                modifier = Modifier.padding(paddingValues),
                                mainAppState = mainAppViewModel.state.collectAsState().value,
                                onEvent = mainAppViewModel::onEvent
                            )

                            SCREENS.INFO -> InformationScreen(
                                modifier = Modifier.padding(paddingValues)
                            )
                        }

                        ProductInfoCard(
                            searchInfoState = searchInfoViewModel.state.collectAsState().value,
                            mainAppState = mainAppViewModel.state.collectAsState().value,
                            onEvent = searchInfoViewModel::onEvent
                        )
                        FullScreenImage(
                            searchInfoState = searchInfoViewModel.state.collectAsState().value,
                            onEvent = searchInfoViewModel::onEvent
                        )
                    }

                }
            }
        }
    }
}