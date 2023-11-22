package com.example.aptlab3.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aptlab3.R
import com.example.aptlab3.imageResMap
import org.json.JSONObject

data class ImageQuestion(val jsonObject: JSONObject) : Question(jsonObject) {

    private val imageResIdString: String = jsonObject.getString("image")
    private val imageResId = imageResMap[imageResIdString] ?: R.drawable.egypt
    @Composable
    override fun QuestionElement() {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .height(500.dp)
        ) {
            DisplayImage(
                imageResId,
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = question,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.weight(0.2f)
            )
            AnswerVariants(modifier = Modifier.weight(0.3f))
        }
    }
    @Composable
    fun DisplayImage(
        imageResId: Int,
        modifier: Modifier = Modifier
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null
        )
    }
}