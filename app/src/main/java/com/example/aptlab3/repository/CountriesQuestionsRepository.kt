package com.example.aptlab3.repository

import android.content.Context

import org.json.JSONObject

class CountriesQuestionsRepository {
    companion object{
        fun jsonImport(context: Context): JSONObject {
            val assetManager = context.assets
            val string = assetManager.open("countries_questions.json")
                .bufferedReader()
                .use { it.readText() }
            return JSONObject(string)
        }
    }
}