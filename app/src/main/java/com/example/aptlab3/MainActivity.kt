@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aptlab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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

            Navigator(HomeScreen(baseContext))

        }
    }
}

@Composable
fun DisplayImage(imageResId: Int) {
    Image(painter = painterResource(id = imageResId), contentDescription = null)
}

@Composable
fun ImageQuestionElement(questionObject: ImageQuestion) {
    val context = LocalContext.current
    val imageResId = context.resources.getIdentifier(questionObject.imageResIdString, "drawable", context.packageName)

    val shape = RoundedCornerShape(10.dp)

    Column {
        DisplayImage(imageResId)
        AnswerVariants(questionObject)
    }
}

@Composable
fun AnswerVariants(question: Question) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(question.answersVariants) {
            Button(
                onClick = {

                },
                Modifier
                    .padding(all = 3.dp)
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

