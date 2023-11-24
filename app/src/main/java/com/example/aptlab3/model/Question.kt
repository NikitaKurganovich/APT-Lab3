package com.example.aptlab3.model

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aptlab3.ui.theme.APTLab3Theme
import com.example.aptlab3.ui.theme.Typography
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
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DisplayQuestionText(
                Modifier
                    .weight(0.7f)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.weight(0.05f))
            AnswerVariants(Modifier.weight(0.25f))
        }
    }
    @Composable
    open fun DisplayQuestionText(modifier: Modifier = Modifier) {
        Card(
            modifier = modifier.fillMaxSize(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = montserratFont,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun AnswerVariants(modifier: Modifier = Modifier) {
        val isClickable = remember(this) { mutableStateOf(true) }
        APTLab3Theme {
            Column {
                val buttonColor = colorScheme.primary
                answersVariants.forEach { answerVariant ->
                    DisplayAnswerButtonRow(buttonColor, isClickable, answerVariant)
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
            Text(text, style = Typography.displaySmall)
        }
    }

    @Composable
    fun DisplayAnswerButtonRow(buttonColor: Color, isClickable: MutableState<Boolean>, answerVariant: String) {
        Row {
            Spacer(Modifier.weight(0.1f))
            AnswerButton(
                buttonColor,
                isClickable, answerVariant,
                Modifier
                    .padding(all = 3.dp)
                    .weight(0.8f),
            )
            Spacer(Modifier.weight(0.1f))
        }
    }

}