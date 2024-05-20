package com.example.foodie.ui.screens

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
import com.example.foodie.configuration.Allergen
import com.example.foodie.events.MainAppEvent
import com.example.foodie.states.MainAppState


@Composable
fun ProfileScreen(
    modifier: Modifier,
    mainAppState: MainAppState,
    onEvent: (MainAppEvent) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ALLERGENS_LIST.forEach {
            AllergenListItem(it, mainAppState, onEvent)
        }
    }

}


@Composable
fun AllergenListItem(
    alergen: Allergen,
    state: MainAppState,
    onEvent: (MainAppEvent) -> Unit
) {

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
                checked = state.allergens[alergen.allergen.ordinal],
                onCheckedChange = {
                    onEvent(MainAppEvent.AllergenChange(alergen))
                }
            )
        }
    }

    Divider(thickness = 3.dp, modifier = Modifier.padding(horizontal = 20.dp))

}