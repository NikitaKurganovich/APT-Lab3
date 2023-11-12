package com.example.aptlab3.model

import org.json.JSONObject

data class TextQuestion(val jsonObject: JSONObject): Question(jsonObject) {

}