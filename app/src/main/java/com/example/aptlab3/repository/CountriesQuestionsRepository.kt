package com.example.aptlab3.repository

import android.content.Context
import com.example.aptlab3.model.Question

class CountriesQuestionsRepository : DefaultRepository("countries_questions.json") {
    companion object {
        fun importData(baseContext: Context): List<Question> {
            val repository =  CountriesQuestionsRepository()
            return repository.getRandomQuestions(baseContext)
        }
    }

}