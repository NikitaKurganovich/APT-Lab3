package com.example.aptlab3.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {
    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _currentQuestion = MutableLiveData(0)
    val currentQuestion: LiveData<Int> = _currentQuestion

    fun updateScore() {
        _score.value = _score.value!!.plus(1)
    }

    fun toNextQuestion(){
        _currentQuestion.value = _currentQuestion.value!!.plus(1)
    }

    fun saveResults(){

    }
}