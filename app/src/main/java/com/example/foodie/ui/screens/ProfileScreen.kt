package com.example.foodie.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodie.ALLERGENS_LIST
import com.example.foodie.Allergen
import com.example.foodie.viewModels.MainAppViewModel


@Composable
fun ProfileScreen(modifier: Modifier, mainAppViewModel: MainAppViewModel) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ALLERGENS_LIST.forEach {
            AllergenListItem(it, mainAppViewModel)
        }
    }

}


@Composable
fun AllergenListItem(alergen: Allergen, mainAppViewModel: MainAppViewModel) {

    Row(Modifier.padding(vertical = 15.dp)) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .weight(1f)
        ) {
            Text(text = alergen.name, fontSize = 30.sp)
        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(end = 20.dp)
                .weight(1f), contentAlignment = Alignment.CenterEnd
        ) {
            Switch(
                checked = mainAppViewModel.allergens[alergen.allergen.ordinal],
                onCheckedChange = {
                    mainAppViewModel.allergens[alergen.allergen.ordinal] = it
                    Log.d("Booleans",
                        mainAppViewModel.allergens[alergen.allergen.ordinal].toString()
                    )
                }
            )
        }
    }

    Divider(thickness = 3.dp, modifier = Modifier.padding(horizontal = 20.dp))

}