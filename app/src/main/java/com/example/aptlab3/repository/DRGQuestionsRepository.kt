package com.example.aptlab3.repository

import android.content.Context
import com.example.aptlab3.model.Question

class DRGQuestionsRepository: DefaultRepository("drg_questions.json") {
    companion object {
        fun importData(baseContext: Context): List<Question> {
            val repository = DRGQuestionsRepository()
            return repository.getRandomQuestions(baseContext)
        }
    }
}