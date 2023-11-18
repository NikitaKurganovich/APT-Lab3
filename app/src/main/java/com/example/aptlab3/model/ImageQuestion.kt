package com.example.aptlab3.model

import android.media.Image
import org.json.JSONObject

data class ImageQuestion(val jsonObject: JSONObject): Question(jsonObject) {
}