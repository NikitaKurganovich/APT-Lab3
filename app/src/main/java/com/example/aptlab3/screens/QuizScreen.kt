package com.example.aptlab3.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aptlab3.QUESTION_COUNT
import com.example.aptlab3.model.Question
import com.example.aptlab3.ui.theme.APTLab3Theme
import com.example.aptlab3.vm.QuizViewModel

data class QuizScreen(val questionsList: List<Question>): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val quizViewModel = QuizViewModel()
        val listSize = QUESTION_COUNT - 1
        APTLab3Theme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Column {
                    quizViewModel.currentQuestion.observeAsState().value
                    Navigator(QuestionScreen(questionsList[quizViewModel.currentQuestion.value!!])){
                        Text("Question ${quizViewModel.currentQuestion.value!!.plus(1)} out of $QUESTION_COUNT")
                        Text("Your score: ${quizViewModel.score.value}")
                        CurrentScreen()
                        Button(
                            onClick = {
                                if(quizViewModel.currentQuestion.value == listSize){
                                    navigator.pop()
                                } else{
                                    quizViewModel.toNextQuestion()
                                    it.replace(QuestionScreen(questionsList[quizViewModel.currentQuestion.value!!]))
                                }
                            }
                        ) {
                            Text("To next")
                        }
                    }
                }
            }
        }
    }
}
