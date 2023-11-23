package com.example.aptlab3.model

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aptlab3.ui.theme.APTLab3Theme
import com.example.aptlab3.ui.theme.montserratFont
import org.json.JSONObject

open class Question(private val jsonObject: JSONObject) {

    var isAnswered = mutableStateOf(false)
    var isAnsweredCorrectly: Boolean = false


    protected val question: String = jsonObject.getString("question")
    private val correctAnswer: String = jsonObject.getString("correct_answer")

    private val answers = List(jsonObject.getJSONArray("available_answers").length()) { i ->
        jsonObject.getJSONArray("available_answers")[i].toString()
    }.shuffled()
    private val answersVariants: List<String> = answers


    @Composable
    open fun QuestionElement() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 50.dp)
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = montserratFont,
                modifier = Modifier
                    .weight(0.7f)
                    .align(Alignment.CenterHorizontally)
            )
            AnswerVariants()
        }
    }

    @Composable
    fun AnswerVariants(modifier: Modifier = Modifier) {
        val isClickable = remember(this) { mutableStateOf(true) }
        APTLab3Theme {
            Column {
                val buttonColor = colorScheme.primary
                answersVariants.forEach {
                    Row {
                        Spacer(Modifier.weight(0.15f))
                        AnswerButton(
                            buttonColor,
                            isClickable, it,
                            Modifier
                                .padding(all = 3.dp)
                                .weight(0.7f),
                        )
                        Spacer(Modifier.weight(0.15f))
                    }

                }
            }
        }
    }

    @Composable
    fun AnswerButton(
        buttonColor: Color,
        isClickable: MutableState<Boolean>,
        text: String,
        modifier: Modifier = Modifier
    ) {
        var color by remember(this) { mutableStateOf(buttonColor) }
        Button(
            enabled = isClickable.value,
            onClick = {
                isAnswered.value = true
                isClickable.value = false
                isAnsweredCorrectly = correctAnswer == text
                color = if (isAnsweredCorrectly) {
                    Color.Green
                } else {
                    Color.Red
                }
            },
            modifier = modifier,
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = color,
                    disabledContainerColor = color
                )
        ) {
            Text(
                text, style = TextStyle(
                    fontSize = 25.sp
                )
            )
        }
    }

}