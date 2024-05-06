package com.example.foodie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ProductInfo(
    val name: String, val company: String, var color: Color
)

@Composable
fun MainScreen(modifier: Modifier) {
    ProductList(modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Product(info: ProductInfo, modifier: Modifier = Modifier, onItemClick:() -> Unit) {
    Card (onClick = onItemClick, colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
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
    LazyColumn(state = lazyListState, modifier = Modifier.imePadding()) {

        listProductInfo.forEach { productInfo ->
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {

                Product(productInfo, onItemClick =  { clickedItem = productInfo })

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