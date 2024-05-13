package com.example.foodie.ui.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.foodie.api.data.JsonFoodItem
import com.example.foodie.viewModels.SearchInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInfoCard(searchInfoViewModel: SearchInfoViewModel) {

    var currentProduct: JsonFoodItem? = searchInfoViewModel.currentProduct.value

    searchInfoViewModel.currentProduct.value?.let {
        ModalBottomSheet(
            //TODO: CAMBIAR searchInfoViewModel.currentProduct.value --> currentProduct
            onDismissRequest = {
                searchInfoViewModel.currentProduct.value = null
            },
            sheetState = rememberModalBottomSheetState()
        ) {
            FoodSheet(foodItem = it, searchInfoViewModel)
        }
    }
}

@Composable
fun FoodSheet(foodItem: JsonFoodItem, searchInfoViewModel: SearchInfoViewModel) {
    if (foodItem.status_verbose != "product found") ErrorFoodSheet() else SucceededFoodSheet(
        foodItem = foodItem,
        searchInfoViewModel
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
fun SucceededFoodSheet(foodItem: JsonFoodItem, searchInfoViewModel: SearchInfoViewModel) {
    if (!searchInfoViewModel.recentProducts.contains(foodItem)){
        searchInfoViewModel.recentProducts.add(foodItem)
    }

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
