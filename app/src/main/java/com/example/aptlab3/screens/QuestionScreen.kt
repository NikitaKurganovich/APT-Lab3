package com.example.aptlab3.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.aptlab3.model.Question

data class QuestionScreen(val question: Question): Screen {
    @Composable
    override fun Content() {
        question.QuestionElement()
    }
}