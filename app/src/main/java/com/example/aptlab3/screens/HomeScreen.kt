package com.example.aptlab3.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class HomeScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column {
            Button(
                onClick = {
                    navigator.replace(QuizzScreen())
                },
                Modifier.fillMaxWidth()
            ){
                Text("Countries Quiz")
            }
            Button(
                onClick = {

                },
                Modifier.fillMaxWidth(),
            ){
                Text(
                    "DRG Quiz",
                    style = TextStyle()
                )
            }
        }
    }
}