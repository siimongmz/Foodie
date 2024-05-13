package com.example.foodie.ui.screens

import android.content.Context
import android.util.Log
import com.example.foodie.viewModels.SearchInfoViewModel
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


fun codeScanner(context: Context,searchInfoViewModel: SearchInfoViewModel) {

    val options = GmsBarcodeScannerOptions.Builder()
        .enableAutoZoom()
        .build()

    val scanner = GmsBarcodeScanning.getClient(context, options)

    scanner.startScan()
        .addOnSuccessListener { barcode ->
            Log.d("CODIGO","Exitoso")
            searchInfoViewModel.code.value = barcode.rawValue
        }
        .addOnCanceledListener {
            Log.d("CODIGO","Cancelado")
        }
        .addOnFailureListener { e ->
            Log.d("CODIGO","Fallido")
        }

}
