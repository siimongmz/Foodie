package com.example.foodie

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.foodie.api.data.JsonFoodItem
import com.example.foodie.facades.FoodApiFacade
import com.example.foodie.ui.components.FullScreenImage
import com.example.foodie.ui.components.ProductInfoCard
import com.example.foodie.ui.screens.MainScreen
import com.example.foodie.ui.screens.ProfileScreen
import com.example.foodie.ui.screens.SCREENS
import com.example.foodie.ui.screens.codeScanner
import com.example.foodie.ui.theme.FoodieTheme
import com.example.foodie.viewModels.MainAppViewModel
import com.example.foodie.viewModels.SearchInfoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class BottomItem(
    val title: String,
    var route: SCREENS,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,

    )

class MainActivity : ComponentActivity() {

    val mainAppViewModel by viewModels<MainAppViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val searchInfoViewModel: SearchInfoViewModel by remember {
                mutableStateOf(SearchInfoViewModel())
            }
            val mainAppViewModel: MainAppViewModel by remember {
                mutableStateOf(MainAppViewModel())
            }
            var items = listOf(
                BottomItem(
                    title = "Search",
                    route = SCREENS.SHARE,
                    selectedIcon = Icons.Filled.Share,
                    unselectedIcon = Icons.Outlined.Share
                ), BottomItem(
                    title = "Home",
                    route = SCREENS.HOME,
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home
                ), BottomItem(
                    title = "Allergies",
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
                        floatingActionButton = { ScannerFAB(searchInfoViewModel = searchInfoViewModel) },
                        topBar = { TopBar() },
                        contentWindowInsets = WindowInsets.safeDrawing
                    )
                    { paddingValues ->
                        when (selectedRoute) {
                            SCREENS.HOME -> MainScreen(
                                modifier = Modifier.padding(paddingValues),
                                searchInfoViewModel = searchInfoViewModel
                            )

                            SCREENS.PROFILE -> ProfileScreen(
                                modifier = Modifier.padding(
                                    paddingValues
                                ), state = mainAppViewModel.state.collectAsState().value,
                                onEvent = mainAppViewModel::onEvent
                            )

                            SCREENS.SHARE -> TODO()
                        }

                        ProductInfoCard(
                            searchInfoViewModel = searchInfoViewModel,
                            mainAppState = mainAppViewModel.state.collectAsState().value
                        )
                        FullScreenImage(searchInfoViewModel = searchInfoViewModel)
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

            Icon(
                painter = painterResource(id = R.drawable.foodie_text),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.secondary,
        ), navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Search",
                modifier = Modifier.padding(10.dp, 10.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ScannerFAB(searchInfoViewModel: SearchInfoViewModel) {
    var showScanner by rememberSaveable {
        mutableStateOf(false)
    }
    val foodApiFacade: FoodApiFacade by remember {
        mutableStateOf(FoodApiFacade())
    }

    FloatingActionButton(
        onClick = { showScanner = true },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ) {
        Icon(
            painterResource(id = R.drawable.just_logo),
            "Scann",
            modifier = Modifier.padding(10.dp)
        )
    }
    if (showScanner) {
        codeScanner(LocalContext.current, searchInfoViewModel)
        showScanner = false
        searchInfoViewModel.code.value?.let {
            Log.d("CODIGO", it)
        }

    }
    if (searchInfoViewModel.code.value != null) {
        CoroutineScope(Dispatchers.IO).launch {
            searchInfoViewModel.currentProduct.value =
                foodApiFacade.getProduct(searchInfoViewModel.code.value!!)
            if (searchInfoViewModel.currentProduct.value == null)
                searchInfoViewModel.currentProduct.value =
                    JsonFoodItem(searchInfoViewModel.code.value!!, null, 0, "product not found")
        }
    }
}




