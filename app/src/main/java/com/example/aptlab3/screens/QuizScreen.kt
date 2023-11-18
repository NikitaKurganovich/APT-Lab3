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
import com.example.aptlab3.QuestionElement
import com.example.aptlab3.ui.theme.APTLab3Theme
import org.json.JSONObject

data class QuizScreen(val questionJson: JSONObject): Screen {
    @Composable
    override fun Content() {

        var currentQuestion by remember { mutableStateOf(0) }
        val questions = questionJson.getJSONArray("questions")
        var questionObject by remember { mutableStateOf(questions.getJSONObject(currentQuestion)) }

        APTLab3Theme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Column {
                    QuestionElement(questionObject)
                    Button(
                        onClick = {
                            currentQuestion = if (currentQuestion == 0) 1 else 0
                            questionObject = questions.getJSONObject(currentQuestion)
                        }
                    ) {
                        Text("To next")
                    }
                }
            }
        }
    }
}