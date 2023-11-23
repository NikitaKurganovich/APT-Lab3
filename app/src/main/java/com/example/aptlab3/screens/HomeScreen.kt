package com.example.aptlab3.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aptlab3.*
import com.example.aptlab3.repository.CountriesQuestionsRepository
import com.example.aptlab3.repository.DRGQuestionsRepository
import com.example.aptlab3.ui.theme.montserratFont

data class HomeScreen(val baseContext: Context) : Screen {
    private val dataStoreManager = DataStoreManager(baseContext)
    private val countriesQuestionsType = CountriesQuestionsRepository().getQuestionsType(baseContext)
    //private val deepRockQuestionsType = DRGQuestionsRepository().getQuestionsType(baseContext)


    @Composable
    override fun Content() {

        val isVisible = remember {mutableStateOf(false)  }
        val navigator = LocalNavigator.currentOrThrow

        var currentType by remember { mutableStateOf(countriesQuestionsType) }
        var currentResults = dataStoreManager.readTestResult(currentType).collectAsState(EMPTY_RESULT).value
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(isVisible.value){
                AlertOnRewriteResults(currentType, navigator, isVisible)
            }
            Button(
                onClick = {
                    currentType = countriesQuestionsType
                    if (currentResults == EMPTY_RESULT){
                        navigator.push(QuizScreen(CountriesQuestionsRepository(), dataStoreManager))
                    } else{
                        isVisible.value = true
                    }
                },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ){
                Text(
                    "Countries Quiz",
                    fontFamily = montserratFont
                )
            }
            Button(
                onClick = {

                },
                Modifier.fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ){
                Text(
                    "DRG Quiz",
                    style = TextStyle(),
                    fontFamily = montserratFont
                )
            }
            Spacer(Modifier.height(200.dp))
            Button(
                onClick = {
                    navigator.push(ResultsScreen(dataStoreManager))
                },
                Modifier.fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ){
                Text(
                    "View profile",
                    style = TextStyle(),
                    fontFamily = montserratFont
                )
            }
        }
    }

    @Composable
    fun AlertOnRewriteResults(type: String , navigator: Navigator, isVisible: MutableState<Boolean>){
        val results =  dataStoreManager.readTestResult(type).collectAsState(EMPTY_RESULT)
        AlertDialog(
            onDismissRequest = {
                isVisible.value = false
            },
            title = { Text(REWRITE)},
            text = { Text("$REWRITE_MESSAGE ${results.value} out of $QUESTION_COUNT") },
            confirmButton = {
                Button(
                    onClick = {
                        navigator.push(QuizScreen(CountriesQuestionsRepository(), dataStoreManager))
                    }
                ) {
                    Text("New attempt")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isVisible.value = false
                    }
                ){
                    Text("Hell nah")
                }
            }
        )
    }
}