package com.example.foodie.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.foodie.ALLERGENS_LIST
import com.example.foodie.api.data.FoodItem
import com.example.foodie.states.MainAppState
import com.example.foodie.tools.translate
import com.example.foodie.viewModels.SearchInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInfoCard(searchInfoViewModel: SearchInfoViewModel, mainAppState: MainAppState) {

    val currentProduct = searchInfoViewModel.currentProduct

    searchInfoViewModel.currentProduct.value?.let {
        ModalBottomSheet(
            onDismissRequest = {
                currentProduct.value = null
            }, sheetState = rememberModalBottomSheetState()
        ) {
            FoodSheet(foodItem = it, searchInfoViewModel = searchInfoViewModel, mainAppState = mainAppState)
        }
    }
}

@Composable
fun FoodSheet(foodItem: FoodItem, searchInfoViewModel: SearchInfoViewModel, mainAppState: MainAppState) {
    if (foodItem.statusVerbose != "product found") ErrorFoodSheet() else SucceededFoodSheet(
        foodItem = foodItem, searchInfoViewModel = searchInfoViewModel, mainAppState = mainAppState
    )
}

@Composable
fun ErrorFoodSheet() {
    Box(Modifier.padding(20.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Error", fontWeight = FontWeight.W600, fontSize = 25.sp)
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            )

        }


    }
}

@Composable
fun SucceededFoodSheet(foodItem: FoodItem, searchInfoViewModel: SearchInfoViewModel, mainAppState: MainAppState) {
    if (!searchInfoViewModel.recentProducts.contains(foodItem)) {
        searchInfoViewModel.recentProducts.add(foodItem)
    }
    Column() {
        ProductPresentation(foodItem = foodItem,searchInfoViewModel)
        Spacer(modifier = Modifier.height(20.dp))
        Column(Modifier.verticalScroll(rememberScrollState())) {
            AllergensInfo(foodItem = foodItem, mainAppState = mainAppState)
            Spacer(modifier = Modifier.height(20.dp))
            IngredientInfo(foodItem = foodItem)
        }

    }

}

@Composable
fun ProductPresentation(foodItem: FoodItem, searchInfoViewModel: SearchInfoViewModel) {

    Box {
        Row {
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground)

            ) {
                SubcomposeAsyncImage(
                    model = foodItem.product?.imageFrontSmallUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            searchInfoViewModel.imagen.value = foodItem.product?.imageFrontSmallUrl
                        },
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = foodItem.product?.productName
                )
            }
            Column(
                Modifier
                    .height(150.dp)
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Column(Modifier.height(IntrinsicSize.Min)) {
                    foodItem.product?.let {
                        Text(
                            text = it.productName, fontWeight = FontWeight.W600, fontSize = 25.sp
                        )
                    }
                    foodItem.product?.let {
                        Text(
                            text = it.brands, fontWeight = FontWeight.W400
                        )
                    }
                }
                ProductActionButtons()
            }
        }
        Column(Modifier.height(150.dp)) {

        }
    }
}

@Composable
fun ProductActionButtons() {
    var liked by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .fillMaxHeight()
            .padding(10.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,

        ) {
        Button(
            onClick = { liked = !liked },
            modifier = Modifier
                .padding(end = 10.dp)
                .fillMaxWidth()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Icon(
                imageVector = likeIcon(liked),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.5f)
            )
        }
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier
                .padding(end = 10.dp)
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Refresh,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.5f)

            )
        }

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            ),
            modifier = Modifier
                .padding(end = 10.dp)
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.5f)
            )
        }


    }
}

@Composable
fun IngredientInfo(foodItem: FoodItem) {
    Box(
        Modifier.padding(20.dp)
    ) {
        Column(modifier = Modifier) {
            Text(
                text = "Ingredientes",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val lazyGridtState = rememberLazyGridState()
            if (foodItem.product?.ingredients != null) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    modifier = Modifier.height(300.dp),
                    state = lazyGridtState
                ) {
                    foodItem.product.ingredients.forEach { ingredient ->
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(2.dp),
                                shape = RoundedCornerShape(5.dp),

                                ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(text = ingredient.text, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                }
            } else {
                SubcomposeAsyncImage(
                    model = foodItem.product?.imageIngredientsUrl,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = foodItem.product?.productName
                )
            }
        }

    }
}

@Composable
fun AllergensInfo(foodItem: FoodItem, mainAppState: MainAppState) {
    Box(
        Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Alergenos",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val lazyGridtState = rememberLazyGridState()
            if (foodItem.product?.allergens != "") {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    modifier = Modifier.height(100.dp),
                    state = lazyGridtState
                ) {
                    foodItem.product?.allergens?.split(",")?.forEach { allergen ->
                        val actualAllergen = translate(allergen.split("en:")[1])
                        var allergic = false

                        for (a in ALLERGENS_LIST){
                            if (actualAllergen == a.name && mainAppState.allergens[a.allergen.ordinal]){
                                allergic = true
                            }
                        }

                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(2.dp),
                                shape = RoundedCornerShape(5.dp),
                                colors = if (allergic) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = MaterialTheme.colorScheme.onErrorContainer) else CardDefaults.cardColors()

                                ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = actualAllergen,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Text(text = "Desconocido")
            }
        }

    }
}

fun likeIcon(liked: Boolean): ImageVector {
    return if (liked) {
        Icons.Filled.Favorite
    } else {
        Icons.Outlined.FavoriteBorder
    }
}
