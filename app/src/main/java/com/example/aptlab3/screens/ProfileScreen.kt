package com.example.aptlab3.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aptlab3.*
import com.example.aptlab3.model.UserData
import com.example.aptlab3.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class ProfileScreen(val dataStoreManager: DataStoreManager) : Screen {
    private val userDataFlow = dataStoreManager.readUserData()

    @Composable
    override fun Content() {
        val isChangeNameDialogShown = remember { mutableStateOf(false) }
        val isAttemptToClear = remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val userDataState = userDataFlow.collectAsState(initial = DefaultUser)
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(0.2f))
            Column(
                Modifier.weight(0.4f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserNameText(userDataState, isChangeNameDialogShown)
                UserTestResults(userDataState)
            }
            Column(
                Modifier.weight(0.2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ClearResultsButton(isAttemptToClear, Modifier.fillMaxWidth().padding(horizontal = 30.dp))
                HomeButton(navigator, Modifier.fillMaxWidth().padding(horizontal = 30.dp))
            }
            Spacer(Modifier.weight(0.2f))
            ClearResultsDialog(isAttemptToClear, userDataState, scope)
            ChangeNameDialog(isChangeNameDialogShown, userDataState, scope)
        }
    }

    @Composable
    fun UserNameText(
        userDataState: State<UserData>,
        isChangeNameDialogShown: MutableState<Boolean>,
        modifier: Modifier = Modifier
    ) {
        Text(
            text = userDataState.value.userName,
            style = Typography.displayMedium,
            modifier = Modifier.clickable { isChangeNameDialogShown.value = true }
        )

    }

    @Composable
    fun UserTestResults(userDataState: State<UserData>) {
        if (userDataState.value.isAnsweredAnyTest) {
            Text("You haven't taken any tests yet", style = Typography.bodyMedium)
        } else {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (userDataState.value.countriesQuestionsResult != EMPTY_RESULT) {
                    Text(
                        "Your last countries test result: \n${userDataState.value.countriesQuestionsResult}",
                        style = Typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
                if (userDataState.value.deepRockQuestionsResult != EMPTY_RESULT) {
                    Text(
                        "Your last DRG test result: \n${userDataState.value.deepRockQuestionsResult}",
                        style = Typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    @Composable
    fun ClearResultsButton(
        isAttemptToClear: MutableState<Boolean>,
        modifier: Modifier = Modifier
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = modifier,
            onClick = { isAttemptToClear.value = true }
        ) {
            Text("Clear results", style = Typography.displaySmall)
        }
    }

    @Composable
    fun HomeButton(
        navigator: Navigator,
        modifier: Modifier = Modifier
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = modifier,
            onClick = { navigator.pop() }
        ) {
            Text("To home", style = Typography.displaySmall)
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
                title = { Text(CLEAR, style = Typography.bodyMedium) },
                text = { Text(CLEAR_MESSAGE, style = Typography.bodySmall) },
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
                        Text("Clear", style = Typography.displaySmall)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { isAttemptToClear.value = false }
                    ) {
                        Text("Cancel", style = Typography.displaySmall)
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
                title = { Text("Change user name", style = Typography.displaySmall) },
                text = {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("New name", style = Typography.displaySmall) }
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
                        Text("Save", style = Typography.displaySmall)
                    }
                },
                dismissButton = { }
            )
        }
    }
}