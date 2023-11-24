package com.example.aptlab3.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aptlab3.R
import com.example.aptlab3.imageResMap
import com.example.aptlab3.ui.theme.montserratFont
import org.json.JSONObject

data class ImageQuestion(val jsonObject: JSONObject) : Question(jsonObject) {

    private val imageResIdString: String = jsonObject.getString("image")
    private val imageResId = imageResMap[imageResIdString] ?: R.drawable.null_reference

    @Composable
    override fun QuestionElement() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.weight(0.7f),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    DisplayQuestionText(
                        Modifier
                            //.weight(0.2f)
                            .align(Alignment.CenterHorizontally)
                    )
                    DisplayImage(
                        imageResId,
                        Modifier.weight(0.5f)
                    )
                }
            }

            Spacer(Modifier.weight(0.05f))
            AnswerVariants(Modifier.weight(0.25f))
        }
    }
    @Composable
    override fun DisplayQuestionText(modifier: Modifier) {
        Text(
            text = question,
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = montserratFont
        )
    }
    @Composable
    fun DisplayImage(
        imageResId: Int,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}