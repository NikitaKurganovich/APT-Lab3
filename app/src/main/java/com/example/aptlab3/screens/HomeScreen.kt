package com.example.aptlab3.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aptlab3.*
import com.example.aptlab3.repository.CountriesQuestionsRepository
import com.example.aptlab3.repository.DRGQuestionsRepository
import com.example.aptlab3.repository.DefaultRepository
import com.example.aptlab3.ui.theme.Typography

data class HomeScreen(val baseContext: Context) : Screen {
    private val dataStoreManager = DataStoreManager(baseContext)
    private val countriesQuestionsType = CountriesQuestionsRepository().getQuestionsType(baseContext)
    private val deepRockQuestionsType = DRGQuestionsRepository().getQuestionsType(baseContext)


    @Composable
    override fun Content() {
        val isVisible = remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val userData = dataStoreManager.readUserData().collectAsState(DefaultUser).value
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var currentType by remember { mutableStateOf(countriesQuestionsType) }

            if (isVisible.value) {
                AlertOnRewriteResults(currentType, navigator, isVisible)
            }
            Button(
                onClick = {
                    currentType = countriesQuestionsType
                    if (userData.countriesQuestionsResult == EMPTY_RESULT) {
                        navigator.push(QuizScreen(CountriesQuestionsRepository(), dataStoreManager))
                    } else {
                        isVisible.value = true
                    }
                },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Text("Countries Quiz", style = Typography.displaySmall)
            }
            Button(
                onClick = {
                    currentType = deepRockQuestionsType
                    if (userData.deepRockQuestionsResult == EMPTY_RESULT) {
                        navigator.push(QuizScreen(DRGQuestionsRepository(), dataStoreManager))
                    } else {
                        isVisible.value = true
                    }
                },
                Modifier.fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Text("DRG Quiz", style = Typography.displaySmall)
            }
            Spacer(Modifier.height(200.dp))
            Button(
                onClick = {
                    navigator.push(ProfileScreen(dataStoreManager))
                },
                Modifier.fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Text("View profile", style = Typography.displaySmall)
            }
        }
    }

    @Composable
    fun AlertOnRewriteResults(type: String, navigator: Navigator, isVisible: MutableState<Boolean>) {
        val results = dataStoreManager.readTestResult(type).collectAsState(EMPTY_RESULT)
        AlertDialog(
            onDismissRequest = {
                isVisible.value = false
            },
            title = { Text(REWRITE,style = Typography.bodyMedium) },
            text = { Text(
                "$REWRITE_MESSAGE ${results.value} out of $QUESTION_COUNT",
                style = Typography.bodySmall
            ) },
            confirmButton = {
                Button(
                    onClick = {
                        navigator.push(QuizScreen(repositoryTypeMap[type] as DefaultRepository, dataStoreManager))
                    }
                ) {
                    Text("New attempt", style = Typography.displaySmall)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isVisible.value = false
                    }
                ) {
                    Text("Cancel", style = Typography.displaySmall)
                }
            }
        )
    }
}