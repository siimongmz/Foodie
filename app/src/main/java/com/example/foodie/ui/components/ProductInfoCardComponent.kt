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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.foodie.ALLERGENS_LIST
import com.example.foodie.R
import com.example.foodie.api.data.FoodItem
import com.example.foodie.events.SearchInfoEvent
import com.example.foodie.facades.FoodApiFacade
import com.example.foodie.states.MainAppState
import com.example.foodie.states.SearchInfoState
import com.example.foodie.util.tools.internationalize
import com.example.foodie.util.tools.translate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInfoCard(
    searchInfoState: SearchInfoState,
    mainAppState: MainAppState,
    onEvent: (SearchInfoEvent) -> Unit
) {
    val foodApiFacade by remember {
        mutableStateOf(FoodApiFacade())
    }
    if (searchInfoState.currentProduct == null && searchInfoState.code != null) {
        LaunchedEffect(true) {
            CoroutineScope(Dispatchers.IO).launch {
                onEvent(
                    SearchInfoEvent.CurrentProductChange(
                        foodApiFacade.getProduct(
                            searchInfoState.code!!
                        )
                    )
                )
                onEvent(SearchInfoEvent.CodeChange(null))
            }
        }
    }



    searchInfoState.currentProduct?.let {
        ModalBottomSheet(
            onDismissRequest = {
                onEvent(SearchInfoEvent.CurrentProductChange(null))
            }, sheetState = rememberModalBottomSheetState()
        ) {
            FoodSheet(
                foodItem = it,
                mainAppState = mainAppState,
                onEvent = onEvent,
                searchInfoState = searchInfoState
            )
            DeleteDialog(foodItem = it, onEvent = onEvent, searchInfoState = searchInfoState)
        }
    }
}

@Composable
fun FoodSheet(
    foodItem: FoodItem,
    mainAppState: MainAppState,
    searchInfoState: SearchInfoState,
    onEvent: (SearchInfoEvent) -> Unit
) {
    if (foodItem.statusVerbose == "product found") {
        SucceededFoodSheet(
            foodItem = foodItem,
            mainAppState = mainAppState,
            onEvent = onEvent,
            searchInfoState = searchInfoState
        )

    } else ErrorFoodSheet()
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
fun SucceededFoodSheet(
    foodItem: FoodItem,
    mainAppState: MainAppState,
    searchInfoState: SearchInfoState,
    onEvent: (SearchInfoEvent) -> Unit
) {
    LaunchedEffect(key1 = "heart") {
        onEvent(SearchInfoEvent.AddRecentProduct(foodItem))
    }

    Column() {
        ProductPresentation(
            foodItem = foodItem,
            onEvent = onEvent,
            searchInfoState = searchInfoState
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(Modifier.verticalScroll(rememberScrollState())) {
            AllergensInfo(foodItem = foodItem, mainAppState = mainAppState)
            Spacer(modifier = Modifier.height(20.dp))
            IngredientInfo(foodItem = foodItem)
        }

    }

}

@Composable
fun ProductPresentation(
    foodItem: FoodItem,
    searchInfoState: SearchInfoState,
    onEvent: (SearchInfoEvent) -> Unit
) {

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
                            onEvent(SearchInfoEvent.ImageUrlChange(foodItem.product?.imageFrontSmallUrl))
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
                ProductActionButtons(foodItem = foodItem,searchInfoState = searchInfoState, onEvent = onEvent)
            }
        }
        Column(Modifier.height(150.dp)) {

        }
    }
}

@Composable
fun ProductActionButtons(
    foodItem: FoodItem,
    searchInfoState: SearchInfoState,
    onEvent: (SearchInfoEvent) -> Unit
) {

    Row(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .fillMaxHeight()
            .padding(top = 10.dp, bottom = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,

        ) {

        Button(
            onClick = { onEvent(SearchInfoEvent.IsDeleting) },
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
                text = stringResource(R.string.ingredients),
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
                text = stringResource(R.string.alergens),
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

                        for (a in ALLERGENS_LIST) {
                            if (actualAllergen == a.name && mainAppState.allergens[a.allergen.ordinal]) {
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
                                colors = if (allergic) CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer,
                                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                                ) else CardDefaults.cardColors()

                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = internationalize(LocalContext.current,actualAllergen),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Text(text = stringResource(R.string.unknown))
            }
        }

    }
}

@Composable
fun DeleteDialog(
    foodItem: FoodItem,
    onEvent: (SearchInfoEvent) -> Unit,
    searchInfoState: SearchInfoState,
){
    if(searchInfoState.isDeleting){
        AlertDialog(
            text = {
                   Text(text = stringResource(R.string.delete_question))
            },
            icon = {
                   Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
            },
            onDismissRequest = {
                onEvent(SearchInfoEvent.IsDeleting)
            },
            confirmButton = {
                Button(
                    onClick = {
                        onEvent(SearchInfoEvent.RemoveRecentProduct(foodItem))
                        onEvent(SearchInfoEvent.IsDeleting)
                        onEvent(SearchInfoEvent.CurrentProductChange(null))
                    }) {
                    Text(text = stringResource(R.string.confirm))
                }
            },
            dismissButton =  {
                Button(
                    onClick = {
                        onEvent(SearchInfoEvent.IsDeleting)
                    }) {
                    Text(text = stringResource(R.string.cancel))
                }
            }

        )
    }

}