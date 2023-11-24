@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aptlab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.example.aptlab3.screens.HomeScreen
import com.example.aptlab3.ui.theme.APTLab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APTLab3Theme{
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
                    Navigator(HomeScreen(baseContext))
                }
            }
        }
    }
}


