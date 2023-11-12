package com.example.aptlab3.model

import org.json.JSONObject

open class Question(private val jsonObject: JSONObject) {
    val availableQuestion: List<String>
        get() {
            return answers.shuffled()
        }

    private val answers = List(jsonObject.getJSONArray("available_answers").length()) { i ->
        jsonObject.getJSONArray("available_answers")[i].toString()
    }
}