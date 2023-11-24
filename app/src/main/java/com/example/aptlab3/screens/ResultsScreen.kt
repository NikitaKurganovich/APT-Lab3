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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aptlab3.*
import com.example.aptlab3.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class ResultsScreen(val dataStoreManager: DataStoreManager) : Screen {
    private val userDataFlow = dataStoreManager.readUserData()

    @Composable
    override fun Content() {
        val isChangeNameDialogShown = remember { mutableStateOf(false) }
        val isAttemptToClear = remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val userDataState = userDataFlow.collectAsState(initial = DefaultUser)
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            UserNameText(userDataState, isChangeNameDialogShown)
            UserTestResults(userDataState)

            ClearResultsButton(isAttemptToClear)
            HomeButton(navigator)

            ClearResultsDialog(isAttemptToClear, userDataState, scope)
            ChangeNameDialog(isChangeNameDialogShown, userDataState, scope)
        }
    }

    @Composable
    fun UserNameText(userDataState: State<UserData>, isChangeNameDialogShown: MutableState<Boolean>) {
        Text(
            text = userDataState.value.userName,
            modifier = Modifier.clickable { isChangeNameDialogShown.value = true }
        )
    }

    @Composable
    fun UserTestResults(userDataState: State<UserData>) {
        if (userDataState.value.isAnsweredAnyTest) {
            Text("You haven't taken any tests yet")
        } else {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (userDataState.value.countriesQuestionsResult != EMPTY_RESULT) {
                    Text("Your last countries test result - ${userDataState.value.countriesQuestionsResult}")
                }
                if (userDataState.value.deepRockQuestionsResult != EMPTY_RESULT) {
                    Text("Your last DRG test result - ${userDataState.value.deepRockQuestionsResult}")
                }
            }
        }
    }

    @Composable
    fun ClearResultsButton(
        isAttemptToClear: MutableState<Boolean>
    ) {
        Button(
            onClick = { isAttemptToClear.value = true }
        ) {
            Text("Clear results")
        }
    }

    @Composable
    fun HomeButton(navigator: Navigator) {
        Button(
            onClick = { navigator.pop() }
        ) {
            Text("To home")
        }
    }

    @Composable
    fun ClearResultsDialog(
        isAttemptToClear: MutableState<Boolean>,
        userDataState: State<UserData>,
        scope: CoroutineScope
    ) {
        if (isAttemptToClear.value) {
            AlertDialog(
                onDismissRequest = { isAttemptToClear.value = false },
                title = { Text(CLEAR) },
                text = { Text(CLEAR_MESSAGE) },
                confirmButton = {
                    Button(
                        onClick = {
                            scope.launch {
                                val updatedUserData = userDataState.value.copy(
                                    countriesQuestionsResult = EMPTY_RESULT,
                                    deepRockQuestionsResult = EMPTY_RESULT
                                )
                                dataStoreManager.saveUserData(updatedUserData)
                                isAttemptToClear.value = false
                            }
                        }
                    ) {
                        Text("Clear")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { isAttemptToClear.value = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ChangeNameDialog(
        isChangeNameDialogShown: MutableState<Boolean>,
        userDataState: State<UserData>,
        scope: CoroutineScope
    ) {
        if (isChangeNameDialogShown.value) {
            var newName by remember { mutableStateOf(userDataState.value.userName) }
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Change user name") },
                text = {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("New name") }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            isChangeNameDialogShown.value = false
                            scope.launch {
                                val updatedUserData = userDataState.value.copy(userName = newName)
                                dataStoreManager.saveUserData(updatedUserData)
                            }
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = { /*...*/ }
            )
        }
    }
}