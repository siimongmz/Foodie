package com.example.foodie.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import coil.compose.SubcomposeAsyncImage
import com.example.foodie.events.SearchInfoEvent
import com.example.foodie.states.SearchInfoState

@Composable
fun FullScreenImage(searchInfoState: SearchInfoState, onEvent: (SearchInfoEvent) -> Unit) {
    searchInfoState.imageUrl?.let {
        Dialog(onDismissRequest = { onEvent(SearchInfoEvent.ImageUrlChange(null)) }) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onEvent(SearchInfoEvent.ImageUrlChange(null)) }) {
                SubcomposeAsyncImage(
                    model = searchInfoState.imageUrl,
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