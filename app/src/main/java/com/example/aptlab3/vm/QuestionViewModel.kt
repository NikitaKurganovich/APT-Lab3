package com.example.aptlab3.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aptlab3.model.Question

class QuestionViewModel(question: Question): ViewModel() {
    private val _isAnswered = MutableLiveData(question.isAnswered)
    val isAnswered: LiveData<Boolean> = _isAnswered

}
