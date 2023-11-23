package com.example.aptlab3.vm

import android.content.Context
import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aptlab3.DataStoreManager

class QuizViewModel: ViewModel() {
    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _currentQuestion = MutableLiveData(0)
    val currentQuestion: LiveData<Int> = _currentQuestion

    fun updateScore(questionViewModel: QuestionViewModel) {
        if (questionViewModel.isAnsweredCorrectly.value!! && questionViewModel.isAnswered.value!!){
            _score.value = _score.value!!.plus(1)
            questionViewModel.setAsUnanswered()
        }
    }

    fun toNextQuestion(){
        _currentQuestion.value = _currentQuestion.value!!.plus(1)
    }
}