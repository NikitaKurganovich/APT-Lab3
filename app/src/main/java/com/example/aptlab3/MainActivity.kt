@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aptlab3

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ListItemDefaults.shape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.example.aptlab3.model.ImageQuestion
import com.example.aptlab3.model.Question
import com.example.aptlab3.screens.HomeScreen
import com.example.aptlab3.ui.theme.APTLab3Theme
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigator(HomeScreen())

        }
    }
}

@Composable
fun DisplayImage(imageResId: Int) {
    Image(painter = painterResource(id = imageResId), contentDescription = null)
}

@Composable
fun QuestionElement(questionObject: JSONObject) {
    val question = ImageQuestion(questionObject)
    val context = LocalContext.current
    val variantList = question.availableQuestion
    val imageResIdString = questionObject.getString("image")
    val imageResId = context.resources.getIdentifier(imageResIdString, "drawable", context.packageName)

    val shape = RoundedCornerShape(10.dp)

    Column {
        DisplayImage(imageResId)
        AnswerVariants(variantList)
    }
}

@Composable
fun smsh(){
    val jsonString =
        """
{
    "questions": [
        {
            "question": "Which country this flag is?",
            "question_type": "with_image",
            "image": "latvia",
            "correct_answer": "Latvia",
            "available_answers": [
                "Latvia",
                "Germany",
                "France",
                "Nigeria"
            ]
        },
        {
            "question": "Which country this flag is?",
            "question_type": "with_image",
            "image": "egypt",
            "correct_answer": "Egypt",
            "available_answers": [
                "Liechtenstein",
                "Kosovo",
                "Egypt",
                "Greece"
            ]
        }
    ]
}
"""
    val jsonObject = JSONObject(jsonString)
    var currentQuestion by remember { mutableStateOf(0) }
    val questions = jsonObject.getJSONArray("questions")
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

@Composable
fun AnswerVariants(list: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        items(list) {
            Text(
                it, Modifier.border(
                    BorderStroke(2.dp, Color.Black), shape = shape
                ).padding(all = 3.dp), style = TextStyle(
                    fontSize = 30.sp
                )
            )
        }
    }
}