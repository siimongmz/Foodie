package com.example.foodie.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodie.R
import com.example.foodie.events.SearchInfoEvent
import com.example.foodie.ui.screens.codeScanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(title = {

        Icon(
            painter = painterResource(id = R.drawable.foodie_text),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        titleContentColor = MaterialTheme.colorScheme.secondary,
    ), navigationIcon = {
        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = "Search",
            modifier = Modifier.padding(10.dp, 10.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }

    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ScannerFAB(onEvent: (SearchInfoEvent) -> Unit) {
    var showScanner by rememberSaveable {
        mutableStateOf(false)
    }

    FloatingActionButton(
        onClick = { showScanner = true },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ) {
        Icon(
            painterResource(id = R.drawable.just_logo), "Scann", modifier = Modifier.padding(10.dp)
        )
    }
    if (showScanner) {
        codeScanner(LocalContext.current, onEvent)
        showScanner = false

    }
}