package com.example.aptlab3.model

import android.media.Image
import androidx.compose.ui.platform.LocalContext
import org.json.JSONObject

data class ImageQuestion(val jsonObject: JSONObject): Question(jsonObject) {

    val imageResIdString: String = jsonObject.getString("image")


}