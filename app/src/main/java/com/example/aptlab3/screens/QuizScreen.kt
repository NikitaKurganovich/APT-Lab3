package com.example.aptlab3.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.example.aptlab3.model.Question
import com.example.aptlab3.ui.theme.APTLab3Theme

data class QuizScreen(val questionsList: List<Question>): Screen {
    @Composable
    override fun Content() {

        var currentQuestion = 0

        APTLab3Theme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Column {
                    Navigator(QuestionScreen(questionsList[currentQuestion])){
                        CurrentScreen()
                        Button(
                            onClick = {
                                currentQuestion = if (currentQuestion == 0) 1 else 0
                                it.replace(QuestionScreen(questionsList[currentQuestion]))
                            }
                        ) {
                            Text("To next")
                        }
                    }
                }
            }
        }
    }
}