package com.example.foodie.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.foodie.api.data.JsonFoodItem
import com.example.foodie.facades.FoodApiFacade
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(padding: Modifier) {
    var currentProduct: JsonFoodItem? by remember {
        mutableStateOf(null)
    }
    var foodApiFacade: FoodApiFacade by remember {
        mutableStateOf(FoodApiFacade())
    }
    val topPadding: Dp by animateDpAsState(targetValue = 100.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = topPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text: String by rememberSaveable {
            mutableStateOf("")
        }

        Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            TextField(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                })

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        currentProduct = foodApiFacade.getProduct(text)
                        if (currentProduct == null)
                            currentProduct = JsonFoodItem(text, null, 0, "product not found")
                    }
                },
                Modifier
                    .height(55.dp)
                    .padding(5.dp)
            ) {
                Text(text = "Buscar")
            }

            currentProduct?.let {
                ModalBottomSheet(
                    onDismissRequest = { currentProduct = null },
                    sheetState = rememberModalBottomSheetState()
                ) {
                    FoodSheet(foodItem = it)
                }
            }
        }


    }

}

@Composable
fun FoodSheet(foodItem: JsonFoodItem) {
    if (foodItem.status_verbose != "product found") ErrorFoodSheet() else SucceededFoodSheet(
        foodItem = foodItem
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
fun SucceededFoodSheet(foodItem: JsonFoodItem) {

    Box(Modifier.padding(20.dp)) {
        Row {
            Box(
                Modifier
                    .width(150.dp)
                    .height(150.dp)
            ) {
                SubcomposeAsyncImage(
                    model = foodItem.product?.imageFrontSmallUrl,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = foodItem.product?.productName
                )
            }
            Column(Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
                foodItem.product?.let {
                    Text(
                        text = it.productName,
                        fontWeight = FontWeight.W600,
                        fontSize = 25.sp
                    )
                }
                foodItem.product?.let { Text(text = it.brands, fontWeight = FontWeight.W400) }
            }
        }
    }
    Box(
        Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        val lazyListState = rememberLazyListState();
        if (foodItem.product?.ingredients != null) {
            LazyColumn(state = lazyListState, modifier = Modifier.fillMaxWidth()) {
                foodItem.product.ingredients.forEach { ingredient ->
                    item {
                        Text(text = ingredient.text)
                    }
                }
            }
        } else {
            //TODO: Cargar la imagen de la foto de los ingredientes (Creando un SubcomposeAsyncImage() y cargando la image_ingredients_url)
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


