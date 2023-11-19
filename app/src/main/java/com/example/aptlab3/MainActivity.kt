@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aptlab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import cafe.adriel.voyager.navigator.Navigator
import com.example.aptlab3.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigator(HomeScreen(baseContext))
        }
    }
}


