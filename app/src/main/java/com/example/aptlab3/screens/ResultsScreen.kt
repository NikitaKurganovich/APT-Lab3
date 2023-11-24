package com.example.aptlab3.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aptlab3.*
import kotlinx.coroutines.launch


data class ResultsScreen(val dataStoreManager: DataStoreManager) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var isChangeNameDialogShown by remember { mutableStateOf(false) }
        var isAttemptToClear by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val userDataFlow = dataStoreManager.readUserData()
        val userDataState = userDataFlow.collectAsState(initial = DefaultUser)
        val scope = rememberCoroutineScope()
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = userDataState.value.userName,
                modifier = Modifier.clickable(
                    onClick = {
                        isChangeNameDialogShown = true
                    }
                )
            )
            if (userDataState.value.isAnsweredAnyTest) {
                Text("You haven't taken any tests yet")
            } else {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (userDataState.value.countriesQuestionsResult != EMPTY_RESULT){
                        Text("Your last countries test result - ${userDataState.value.countriesQuestionsResult}")
                    }
                    if (userDataState.value.deepRockQuestionsResult != EMPTY_RESULT){
                        Text("Your last DRG test result - ${userDataState.value.deepRockQuestionsResult}")
                    }
                }
            }
            Button(
                onClick = {
                    isAttemptToClear = true

                }
            ) {
                Text("Clear results")
            }
            Button(
                onClick = {
                    navigator.pop()
                }
            ) {
                Text("To home")
            }
            if (isAttemptToClear){
                AlertDialog(
                    onDismissRequest = { isAttemptToClear = false },
                    title = { Text(CLEAR) },
                    text = {
                        Text(CLEAR_MESSAGE)
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                userDataState.value.countriesQuestionsResult = EMPTY_RESULT
                                userDataState.value.deepRockQuestionsResult = EMPTY_RESULT
                                scope.launch {
                                    dataStoreManager.saveUserData(userDataState.value)
                                    isAttemptToClear = false
                                }
                            }
                        ) {
                            Text("Clear")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                isAttemptToClear = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
            if (isChangeNameDialogShown) {
                var newName by remember { mutableStateOf(userDataState.value.userName) }
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text("Change user name") },
                    text = {
                        OutlinedTextField(
                            value = newName,
                            onValueChange = {
                                newName = it
                            },
                            label = { Text("New name") }
                        )
                        Text(userDataState.value.userName)
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                isChangeNameDialogShown = false
                                scope.launch {
                                    userDataState.value.userName = newName
                                    dataStoreManager.saveUserData(userDataState.value)
                                }
                            }
                        ) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                    }
                )
            }
        }
    }
}