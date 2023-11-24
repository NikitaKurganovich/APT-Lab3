package com.example.aptlab3.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aptlab3.*
import com.example.aptlab3.model.Question
import com.example.aptlab3.repository.DefaultRepository
import com.example.aptlab3.ui.theme.APTLab3Theme
import com.example.aptlab3.ui.theme.Typography
import com.example.aptlab3.vm.QuestionViewModel
import com.example.aptlab3.vm.QuizViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class QuizScreen(val repository: DefaultRepository, val dataStoreManager: DataStoreManager) : Screen {
    private val quizViewModel = QuizViewModel()

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val questionsList: List<Question> = repository.getRandomQuestions(context)
        val navigator = LocalNavigator.currentOrThrow
        val repositoryName = repository.getQuestionsType(context)
        APTLab3Theme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    quizViewModel.currentQuestion.observeAsState().value
                    Navigator(QuestionScreen(questionsList[quizViewModel.currentQuestion.value!!])) {
                        val questionViewModel = QuestionViewModel(questionsList[quizViewModel.currentQuestion.value!!])
                        questionViewModel.isAnswered.observeAsState().value
                        Text(
                            "Question ${quizViewModel.currentQuestion.value!!.plus(1)} out of $QUESTION_COUNT",
                            style = Typography.bodyMedium
                        )
                        Text(
                            "Current score: ${quizViewModel.score.value}",
                            style = Typography.bodyMedium
                        )
                        CurrentScreen()
                        if (questionViewModel.isAnswered.value!!) {
                            if (quizViewModel.currentQuestion.value != LIST_SIZE) {
                                AlertOnAnswer(questionViewModel, it, questionsList)
                            } else {
                                AlertOnLastQuestion(questionViewModel, repositoryName, scope, navigator)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AlertOnAnswer(
        questionVM: QuestionViewModel,
        innerNavigator: Navigator,
        questionsList: List<Question>
    ) {
        quizViewModel.updateScore(questionVM)

        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    if (questionVM.isAnsweredCorrectly.value!!) {
                        CORRECT
                    } else {
                        INCORRECT
                    },
                    style = Typography.bodyMedium
                )
            },
            text = {
                Text(
                    if (questionVM.isAnsweredCorrectly.value!!) {
                        SCORE_ON_CORRECT
                    } else {
                        SCORE_ON_INCORRECT
                    },
                    style = Typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        quizViewModel.toNextQuestion()
                        innerNavigator.replace(QuestionScreen(questionsList[quizViewModel.currentQuestion.value!!]))
                    }
                ) {
                    Text("To next", style = Typography.displaySmall)
                }
            }
        )
    }

    @Composable
    fun AlertOnLastQuestion(
        questionVM: QuestionViewModel,
        repositoryName: String,
        scope: CoroutineScope,
        navigator: Navigator
    ) {
        quizViewModel.updateScore(questionVM)
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    if (questionVM.isAnsweredCorrectly.value!!) {
                        CORRECT
                    } else {
                        INCORRECT
                    },
                    style = Typography.bodyMedium
                )
            },
            text = {
                Text(
                    LAST_QUESTION +
                    "\nYour score is ${quizViewModel.score.value}",
                    style = Typography.bodySmall
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            dataStoreManager.saveTestResult(repositoryName, quizViewModel.score.value!!)
                        }
                        navigator.pop()
                    }
                ) {
                    Text("To home screen", style = Typography.displaySmall)
                }
            }
        )
    }

}

