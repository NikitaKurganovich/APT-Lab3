package com.example.aptlab3.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aptlab3.R
import com.example.aptlab3.imageResMap
import org.json.JSONObject

data class ImageQuestion(val jsonObject: JSONObject) : Question(jsonObject) {

    private val imageResIdString: String = jsonObject.getString("image")

    @Composable
    override fun QuestionElement() {
        val context = LocalContext.current
        val imageResId = imageResMap[imageResIdString] ?: R.drawable.egypt
        val shape = RoundedCornerShape(10.dp)
        val image = context.resources
        Column {
            DisplayImage(imageResId)
            Text(text = question, style = MaterialTheme.typography.displaySmall)
            AnswerVariants()
        }
    }
    @Composable
    fun DisplayImage(imageResId: Int) {
        Image(painter = painterResource(id = imageResId), contentDescription = null)
    }
}