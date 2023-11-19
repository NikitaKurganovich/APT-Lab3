package com.example.aptlab3.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aptlab3.repository.CountriesQuestionsRepository

data class HomeScreen(val baseContext: Context) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navigator.push(QuizScreen(CountriesQuestionsRepository.dataImport(baseContext)))
                },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ){
                Text("Countries Quiz")
            }
            Button(
                onClick = {

                },
                Modifier.fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ){
                Text(
                    "DRG Quiz",
                    style = TextStyle()
                )
            }
        }
    }
}