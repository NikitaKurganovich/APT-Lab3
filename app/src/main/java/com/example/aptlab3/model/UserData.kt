package com.example.aptlab3.model

import com.example.aptlab3.EMPTY_RESULT

data class UserData(
    var userName: String,
    var countriesQuestionsResult: Int,
    var deepRockQuestionsResult: Int
) {
    val isAnsweredAnyTest
        get() = run {
            this.countriesQuestionsResult == EMPTY_RESULT &&
                    this.deepRockQuestionsResult == EMPTY_RESULT
        }
}