package com.example.aptlab3.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aptlab3.*
import com.example.aptlab3.model.Question
import com.example.aptlab3.ui.theme.APTLab3Theme
import com.example.aptlab3.vm.QuestionViewModel
import com.example.aptlab3.vm.QuizViewModel

data class QuizScreen(val questionsList: List<Question>): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val quizViewModel = QuizViewModel()

        APTLab3Theme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Column {
                    quizViewModel.currentQuestion.observeAsState().value
                    Navigator(QuestionScreen(questionsList[quizViewModel.currentQuestion.value!!])){
                        val questionViewModel = QuestionViewModel(questionsList[quizViewModel.currentQuestion.value!!])
                        questionViewModel.isAnswered.observeAsState().value

                        Text("Question ${quizViewModel.currentQuestion.value!!.plus(1)} out of $QUESTION_COUNT")
                        Text("Current score: ${quizViewModel.score.value}")
                        CurrentScreen()
                        if(questionViewModel.isAnswered.value!!){
                            AlertDialog(
                                onDismissRequest = {  },
                                title = {
                                    Text(
                                        if(questionViewModel.isAnsweredCorrectly.value!!) {
                                            CORRECT
                                        } else {
                                            INCORRECT
                                        }
                                    )},
                                text = {
                                    Text(
                                        if(questionViewModel.isAnsweredCorrectly.value!!) {
                                            SCORE_ON_CORRECT
                                        } else {
                                            SCORE_ON_INCORRECT
                                        }
                                    )},
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            if(quizViewModel.currentQuestion.value != LIST_SIZE){
                                                quizViewModel.updateScore(questionViewModel)
                                                quizViewModel.toNextQuestion()
                                                it.replace(QuestionScreen(questionsList[quizViewModel.currentQuestion.value!!]))
                                            } else{
                                                navigator.pop()
                                            }
                                        }
                                    ) {
                                        Text("To next")
                                    }
                                }
                            )

                        }
                    }
                }
            }
        }
    }
}
