package com.example.fenixsport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fenixsport.navigation.AppNavigation
import com.example.fenixsport.ui.theme.FenixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FenixTheme {
                AppNavigation()
            }
        }
    }
}