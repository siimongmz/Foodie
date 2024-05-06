package com.example.foodie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
@Preview(device = "id:pixel_8", showBackground = true)
fun SettingsScreen() {
    Column ( Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
            Text(text = "Ajustes", fontSize = 40.sp)
        }
    }
}