package com.example.aptlab3.repository

import android.content.Context
import com.example.aptlab3.QUESTION_COUNT
import com.example.aptlab3.model.ImageQuestion
import com.example.aptlab3.model.Question
import com.example.aptlab3.model.TextQuestion
import org.json.JSONObject

open class DefaultRepository(private val jsonPath: String) {

    protected fun getRandomQuestions(context: Context): List<Question> {
        return jsonToQuestionList(context).shuffled().take(QUESTION_COUNT)
    }
    private fun jsonToQuestionList(context: Context): List<Question> {
        val assetManager = context.assets
        val string = assetManager.open(jsonPath)
            .bufferedReader()
            .use { it.readText() }
        val jsonList = JSONObject(string).getJSONArray("questions")

        return List(jsonList.length()) {
            fromJson(jsonList[it] as JSONObject)
        }
    }

    private fun fromJson(jsonObject: JSONObject): Question {
        return if (jsonObject.getString("question_type") == "image") {
            ImageQuestion(jsonObject)
        } else {
            TextQuestion(jsonObject)
        }
    }

}