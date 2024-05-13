package com.example.foodie.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodie.viewModels.SearchInfoViewModel

data class ProductInfo(
    val name: String, val company: String, var color: Color
)

@Composable
fun MainScreen(modifier: Modifier, searchInfoViewModel: SearchInfoViewModel) {
    Column(modifier = modifier) {
        SearchScreen(searchInfoViewModel = searchInfoViewModel)
        ProductList()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Product(info: ProductInfo, modifier: Modifier = Modifier, onItemClick: () -> Unit) {
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
                    .background(info.color)

            )
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 5.dp)) {
                Text(
                    text = info.name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = info.company,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProductList(modifier: Modifier = Modifier) {
    val listProductInfo = remember {
        listOf(
            ProductInfo("Aquarius", "Coca-cola Company", Color.Cyan),
            ProductInfo("Nestea", "Cocacola Company", Color.Blue),
            ProductInfo("Lipton", "Pepsi Company", Color.Yellow),
            ProductInfo("Lays", "Pepsi Company", Color.Red),
            ProductInfo("Patatas Campesinas", "Hacendado", Color.Green),
            ProductInfo("Aquarius", "Coca-cola Company", Color.Cyan),
            ProductInfo("Nestea", "Cocacola Company", Color.Blue),
            ProductInfo("Lipton", "Pepsi Company", Color.Yellow),
            ProductInfo("Lays", "Pepsi Company", Color.Red),
            ProductInfo("Patatas Campesinas", "Hacendado", Color.Green),
            ProductInfo("Aquarius", "Coca-cola Company", Color.Cyan),
            ProductInfo("Nestea", "Cocacola Company", Color.Blue),
            ProductInfo("Lipton", "Pepsi Company", Color.Yellow),
            ProductInfo("Lays", "Pepsi Company", Color.Red),
            ProductInfo("Patatas Campesinas", "Hacendado", Color.Green),

            )
    }
    val lazyListState = rememberLazyListState()
    var clickedItem by remember { mutableStateOf<ProductInfo?>(null) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier.imePadding()
    ) {
        listProductInfo.forEach { productInfo ->

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
fun ProductSheet(info: ProductInfo) {
    Box(Modifier.padding(20.dp)) {
        Row {
            Box(
                Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .background(info.color)
            ) {

            }
            Column(Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
                Text(text = info.name, fontWeight = FontWeight.W600, fontSize = 25.sp)
                Text(text = info.company, fontWeight = FontWeight.W400)
            }
        }

    }


}