package com.example.aptlab3.model

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

    private val answersVariants: List<String>
        get() {
            return answers.shuffled()
        }

    @Composable
    open fun QuestionElement(){
        Column(
            modifier = Modifier
                .height(500.dp)
        ){
            Text(
                text = question,
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = montserratFont,
                modifier = Modifier
                    .weight(0.7f)
                    .align(Alignment.CenterHorizontally)
            )
            AnswerVariants(modifier = Modifier.weight(0.3f))
        }
    }
    @Composable
    fun AnswerVariants(modifier: Modifier = Modifier) {
        var isClickable by remember(this){ mutableStateOf(true)}
        APTLab3Theme{
            val buttonColor = colorScheme.primary
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(answersVariants) {
                    var color by remember(it) { mutableStateOf(buttonColor) }
                    Button(
                        enabled = isClickable,
                        onClick = {
                            isAnswered.value = true
                            isClickable = false
                            isAnsweredCorrectly = correctAnswer == it
                            color = if (isAnsweredCorrectly){
                                Color.Green
                            } else {
                                Color.Red
                            }
                        },
                        modifier = Modifier
                            .padding(all = 3.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = color, disabledContainerColor = color)
                    ){
                        Text(
                            it, style = TextStyle(
                                fontSize = 25.sp
                            )
                        )
                    }
                }
            }
        }
    }
    private val answers = List(jsonObject.getJSONArray("available_answers").length()) { i ->
        jsonObject.getJSONArray("available_answers")[i].toString()
    }
}