package com.example.finpilotai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.finpilotai.ui.navigation.FinPilotApp
import com.example.finpilotai.ui.theme.FinPilotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            FinPilotTheme {
                FinPilotApp()
            }
        }
    }
}
