package com.example.foodie.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import coil.compose.SubcomposeAsyncImage
import com.example.foodie.viewModels.SearchInfoViewModel

@Composable
fun FullScreenImage(searchInfoViewModel: SearchInfoViewModel) {
    searchInfoViewModel.imagen.value?.let {
        Dialog(onDismissRequest = {searchInfoViewModel.imagen.value = null}) {
            Card(modifier = Modifier.fillMaxSize().clickable { searchInfoViewModel.imagen.value = null }){
                SubcomposeAsyncImage(
                    model = searchInfoViewModel.imagen.value,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = null
                )
            }
        }

    }
}