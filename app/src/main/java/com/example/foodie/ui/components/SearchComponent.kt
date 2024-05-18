package com.example.foodie.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.foodie.api.data.FoodItem
import com.example.foodie.facades.FoodApiFacade
import com.example.foodie.viewModels.SearchInfoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(searchInfoViewModel: SearchInfoViewModel) {

    val foodApiFacade: FoodApiFacade by remember {
        mutableStateOf(FoodApiFacade())
    }
    var visible by remember {
        mutableStateOf(false)
    }
    val currentProduct = searchInfoViewModel.currentProduct
    val animatedPadding = animateDpAsState(targetValue = if(visible) 30.dp else 0.dp, label = "searchBar")

    LaunchedEffect(key1 = Unit, block = {
        delay(600L)
        visible = true
    })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text: String by rememberSaveable {
            mutableStateOf("")
        }

        Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(animatedPadding.value)
                    ,
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    CoroutineScope(Dispatchers.IO).launch {
                        currentProduct.value = foodApiFacade.getProduct(text)
                        if (currentProduct.value == null)
                            currentProduct.value = FoodItem(text, null, 0, "product not found")
                    }
                }),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                })
        }
    }
}