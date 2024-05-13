package com.example.foodie.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.foodie.api.data.JsonFoodItem
import com.example.foodie.ui.components.SearchScreen
import com.example.foodie.viewModels.SearchInfoViewModel

data class ProductInfo(
    val name: String, val company: String, var color: Color
)

@Composable
fun MainScreen(modifier: Modifier, searchInfoViewModel: SearchInfoViewModel) {
    Column(modifier = modifier) {
        SearchScreen(searchInfoViewModel = searchInfoViewModel)
        ProductList(searchInfoViewModel = searchInfoViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Product(info: JsonFoodItem, modifier: Modifier = Modifier, onItemClick: () -> Unit) {
    Card(
        onClick = onItemClick,
        modifier = Modifier.padding(10.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.background,
        )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp, 10.dp)
                .fillMaxWidth()

        ) {
            Box(
                modifier
                    .width(60.dp)
                    .height(60.dp)
            ){
                SubcomposeAsyncImage(
                    model = info.product?.imageFrontSmallUrl,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = info.product?.productName
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 5.dp)) {
                info.product?.let {
                    Text(
                        text = it.productName,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
                info.product?.let {
                    Text(
                        text = it.brands,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductList(modifier: Modifier = Modifier,searchInfoViewModel: SearchInfoViewModel) {
    val products = searchInfoViewModel.recentProducts
    var clickedItem by remember { mutableStateOf<JsonFoodItem?>(null) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier.imePadding()
    ) {
        products.forEach { productInfo ->
            productInfo.product?.let { Log.d("RECIENTES", it.productName) }
            item {
                Product(productInfo, onItemClick = { clickedItem = productInfo })

            }
        }

    }
    clickedItem?.let {
        ModalBottomSheet(
            onDismissRequest = { clickedItem = null },
            sheetState = rememberModalBottomSheetState()
        ) {
            ProductSheet(info = it)
        }
    }
}

@Composable
fun ProductSheet(info: JsonFoodItem) {
    Box(Modifier.padding(20.dp)) {
        Row {
            Box(
                Modifier
                    .width(150.dp)
                    .height(150.dp)
            ) {
                SubcomposeAsyncImage(
                    model = info.product?.imageFrontSmallUrl,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = info.product?.productName
                )
            }
            Column(Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
                info.product?.let { Text(text = it.productName, fontWeight = FontWeight.W600, fontSize = 25.sp) }
                info.product?.let { Text(text = it.productName, fontWeight = FontWeight.W400) }
            }
        }

    }


}